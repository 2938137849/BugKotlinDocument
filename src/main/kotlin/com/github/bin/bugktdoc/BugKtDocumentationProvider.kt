package com.github.bin.bugktdoc

import com.github.bin.bugktdoc.constants.*
import com.github.bin.bugktdoc.util.itsType
import com.intellij.codeInsight.editorActions.CodeDocumentationUtil
import com.intellij.ide.util.PackageUtil
import com.intellij.lang.CodeDocumentationAwareCommenter
import com.intellij.lang.LanguageCommenters
import com.intellij.lang.documentation.CodeDocumentationProvider
import com.intellij.lang.documentation.DocumentationProviderEx
import com.intellij.lang.java.JavaDocumentationProvider.getPackageInfoComment
import com.intellij.openapi.util.Pair
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import org.jetbrains.kotlin.kdoc.psi.impl.KDocImpl
import org.jetbrains.kotlin.nj2k.postProcessing.type
import org.jetbrains.kotlin.psi.*

typealias PPC = Pair<PsiFile, CodeDocumentationAwareCommenter>

/**
 * @author zxj5470
 * @date 2018/4/6
 */
class BugKtDocumentationProvider : DocumentationProviderEx(), CodeDocumentationProvider {
	override fun parseContext(startPoint: PsiElement): Pair<PsiElement, PsiComment> {
		var current = startPoint
		while (true) {
			if (current is KDocImpl) {
				return Pair(current, current)
			}
			else if (PackageUtil.isPackageInfoFile(current)) {
				return Pair(current, getPackageInfoComment(current))
			}
			current = current.parent
		}
	}

	private fun PsiComment.getOwner() = PsiTreeUtil.getParentOfType<KtDeclaration>(this)
		?: this.parent.takeIf { it is KtFunction || it is KtClass }

	override fun generateDocumentationContentStub(contextComment: PsiComment?): String {
		if (!Settings.useBugKtDoc || contextComment === null) return ""

		return when (val owner = contextComment.getOwner()) {
			is KtNamedFunction -> docKtNamedFunction(owner, contextComment.pair())
			is KtClass -> docKtClass(owner, contextComment.pair())
			is KtConstructor<*> -> docKtConstructor(owner, contextComment.pair())
			else -> ""
		}
	}

	override fun findExistingDocComment(contextElement: PsiComment?): PsiComment? =
		(contextElement as? KDocImpl)?.owner?.docComment ?: contextElement

	private fun docKtNamedFunction(owner: KtNamedFunction, pair: PPC): String = buildString {
		// @receiver
		owner.receiverTypeReference?.let {
			appendDoc(RECEIVER, pair, it.itsType)
		}

		// @param
		for (it in owner.valueParameters) {
			val param = it.nameIdentifier?.text ?: ""
			val type = it.itsType
			// add a space before `param` and after is no used
			appendDoc(PARAM, pair, param, " ", type)
		}

		// @return
		if (owner.hasDeclaredReturnType()) {
			val type = owner.type()
			if (type !== null)
				appendDoc(RETURN, pair, type.itsType)
			else
				appendDoc(RETURN, pair, owner.typeReference.itsType)
		}
		else owner.itsType.let {
			if (!Settings.alwaysShowUnitReturnType)
				if (it.startsWith("Unit") || it.startsWith("[Unit]"))
					return@let
			appendDoc(RETURN, pair, it)
		}

		// @throws
		PsiTreeUtil.findChildrenOfType(owner, KtAnnotationEntry::class.java).firstOrNull {
			it.calleeExpression?.text == "Throws"
		}?.run {
			for (it in valueArguments.mapNotNull {
				it.getArgumentExpression() as? KtClassLiteralExpression
			}) {
				PsiTreeUtil.findChildOfType(it, KtNameReferenceExpression::class.java)?.let {
					appendDoc(THROWS, pair, it.itsType)
				}
			}
		}
	}

	private fun docKtClass(owner: KtClass, pair: PPC): String = buildString {
		for (it in owner.typeParameters) {
			appendDoc(PARAM, pair, it.itsType)
		}

		// order: 1. primary Parameters -> @property
		for (it in owner.primaryConstructorParameters) {
			// is property
			if (it.hasValOrVar()) {
				val param = it.nameIdentifier?.text
				val type = it.itsType
				if (!param.isNullOrEmpty() && type.isNotEmpty()) {
					// add a space before or after is no used
					appendDoc(PROPERTY, pair, param, " ", type)
				}
			}
		}

		// order: 2. class fields -> @property
		if (Settings.alwaysShowClassFieldProperty)
			for (it in owner.getProperties()) {
				val param = it.nameIdentifier?.text
				val type = it.itsType
				if (!param.isNullOrEmpty()) {
					// add a space before or after is no used
					appendDoc(PROPERTY, pair, param, " ", type)
				}
			}

		// @constructor
		if (owner.hasPrimaryConstructor() && Settings.alwaysShowConstructor) {
			// empty class
			if (!owner.getPrimaryConstructorParameterList()?.parameters.isNullOrEmpty()) {
				appendDoc(CONSTRUCTOR, pair)
			}
		}
	}

	private fun docKtConstructor(owner: KtConstructor<*>, pair: PPC): String = buildString {
		// @param
		for (it in owner.valueParameters) {
			val param = it.nameIdentifier?.text
			val type = it.itsType
			if (!param.isNullOrEmpty() && type.isNotEmpty()) {
				appendDoc(PARAM, pair, param, " ", type)
			}
		}

		// @constructor
		if (Settings.alwaysShowConstructor) {
			appendDoc(CONSTRUCTOR, pair)
		}
	}

	private fun PsiComment.pair(): PPC =
		Pair(containingFile, LanguageCommenters.INSTANCE.forLanguage(language) as CodeDocumentationAwareCommenter)

	private fun StringBuilder.appendDoc(lineData: String, arg: PPC, vararg strs: String?) {
		append(CodeDocumentationUtil.createDocCommentLine(lineData, arg.first, arg.second))
		for (s in strs) {
			append(s)
		}
		append(LF)
	}

}
