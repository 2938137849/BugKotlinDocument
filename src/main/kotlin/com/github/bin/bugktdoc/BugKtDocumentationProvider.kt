package com.github.bin.bugktdoc

import com.github.bin.bugktdoc.constants.*
import com.github.bin.bugktdoc.util.itsType
import com.intellij.codeInsight.editorActions.CodeDocumentationUtil
import com.intellij.lang.CodeDocumentationAwareCommenter
import com.intellij.lang.LanguageCommenters
import com.intellij.lang.documentation.CodeDocumentationProvider
import com.intellij.lang.documentation.DocumentationProviderEx
import com.intellij.openapi.util.Pair
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import org.jetbrains.kotlin.kdoc.psi.api.KDoc
import org.jetbrains.kotlin.nj2k.postProcessing.type
import org.jetbrains.kotlin.psi.*

/**
 * @author zxj5470
 * @date 2018/4/6
 */
class BugKtDocumentationProvider : DocumentationProviderEx(), CodeDocumentationProvider {
	override fun parseContext(startPoint: PsiElement): Pair<PsiElement, PsiComment>? {
		var current: PsiElement? = startPoint
		while (current !== null) {
			if (current is KDoc) {
				return Pair(current, current)
			}
			// else if (PackageUtil.isPackageInfoFile(current)) {
			// 	return Pair(current, getPackageInfoComment(current))
			// }
			current = current.parent
		}
		return null
	}

	override fun generateDocumentationContentStub(contextComment: PsiComment?): String? {
		if (!Settings.useDoc || contextComment === null) return null
		val prefix = contextComment.getDocPrefix()
		return when (val owner = contextComment.parent ?: return null) {
			is KtNamedFunction -> {
				if (!Settings.useFunctionDoc) null
				else docKtNamedFunction(owner, prefix)
			}
			is KtClass -> {
				if (!Settings.useClassDoc) null
				else docKtClass(owner, prefix)
			}
			is KtConstructor<*> -> {
				if (!Settings.useConstructorDoc) null
				else docKtConstructor(owner, prefix)
			}
			else -> null
		}
	}

	override fun findExistingDocComment(contextElement: PsiComment?): PsiComment? =
		(contextElement as? KDoc)?.owner?.docComment ?: contextElement

	private fun docKtNamedFunction(owner: KtNamedFunction, prefix: String): String = buildString {
		owner.contextReceivers.forEach {
			appendDoc(prefix, CONTEXT_RECEIVER, it.itsType)
		}

		// @receiver
		owner.receiverTypeReference?.let {
			appendDoc(prefix, RECEIVER, it.itsType)
		}

		// @param
		appendDoc(prefix, PARAM, owner.valueParameters)

		// @return
		if (owner.hasDeclaredReturnType()) {
			val type = owner.type()
			if (type !== null)
				appendDoc(prefix, RETURN, type.itsType)
			else
				appendDoc(prefix, RETURN, owner.typeReference?.itsType)
		}
		else owner.itsType.let {
			if (!Settings.alwaysShowUnitReturnType)
				if (it == "Unit")
					return@let
			appendDoc(prefix, RETURN, it)
		}

		// @throws
		PsiTreeUtil.findChildrenOfType(owner, KtAnnotationEntry::class.java).firstOrNull {
			it.calleeExpression?.text == "Throws"
		}?.run {
			for (it in valueArguments.mapNotNull {
				it.getArgumentExpression() as? KtClassLiteralExpression
			}) {
				PsiTreeUtil.findChildOfType(it, KtNameReferenceExpression::class.java)?.let {
					appendDoc(prefix, THROWS, it.itsType)
				}
			}
		}
	}

	private fun docKtClass(owner: KtClass, prefix: String): String = buildString {
		for (it in owner.typeParameters) {
			appendDoc(prefix, PARAM, it.itsType)
		}

		val primaryConstructorParameters = owner.primaryConstructorParameters
		// @param
		appendDoc(prefix, PARAM, primaryConstructorParameters)

		// order: 1. primary Parameters -> @property
		for (it in primaryConstructorParameters) {
			// is property
			if (it.hasValOrVar()) appendDoc(prefix, PROPERTY, it)
			else appendDoc(prefix, PARAM, it)
		}

		// order: 2. class fields -> @property
		if (Settings.alwaysShowClassFieldProperty) {
			appendDoc(prefix, PROPERTY, owner.getProperties())
		}

		// @constructor
		if (Settings.alwaysShowConstructor) {
			appendDoc(prefix, CONSTRUCTOR)
		}
	}

	private fun docKtConstructor(owner: KtConstructor<*>, prefix: String): String = buildString {
		// @param
		appendDoc(prefix, PARAM, owner.valueParameters)

		// @constructor
		if (Settings.alwaysShowConstructor) {
			appendDoc(prefix, CONSTRUCTOR)
		}
	}

	private fun PsiComment.getDocPrefix(): String = CodeDocumentationUtil.createDocCommentLine(
		"", containingFile, LanguageCommenters.INSTANCE.forLanguage(language) as CodeDocumentationAwareCommenter
	)

	private fun StringBuilder.appendDoc(prefix: String, vararg strs: String?) {
		strs.joinTo(this, " ", prefix, LF)
	}

	private fun StringBuilder.appendDoc(prefix: String, token: String, it: KtCallableDeclaration) {
		val param = it.name
		val type = it.itsType
		if (!param.isNullOrEmpty() && type.isNotEmpty()) {
			appendDoc(prefix, token, param, type)
		}
	}

	private fun StringBuilder.appendDoc(prefix: String, token: String, valueParameters: List<KtCallableDeclaration>) {
		for (it in valueParameters) {
			appendDoc(prefix, token, it)
		}
	}

}
