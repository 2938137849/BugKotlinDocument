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

	override fun generateDocumentationContentStub(contextComment: PsiComment?): String {
		if (!Settings.useDoc || contextComment === null) return ""
		val prefix = contextComment.getDocPrefix()
		return when (val owner = contextComment.parent ?: return "") {
			is KtNamedFunction -> {
				if (!Settings.useFunctionDoc) ""
				else docKtNamedFunction(owner, prefix)
			}
			is KtClass -> {
				if (!Settings.useClassDoc) ""
				else docKtClass(owner, prefix)
			}
			is KtConstructor<*> -> {
				if (!Settings.useConstructorDoc) ""
				else docKtConstructor(owner, prefix)
			}
			else -> ""
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
		for (it in owner.valueParameters) {
			val param = it.nameIdentifier?.text ?: ""
			val type = it.itsType
			// add a space before `param` and after is no used
			appendDoc(prefix, PARAM, param, type)
		}

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

		// order: 1. primary Parameters -> @property
		for (it in owner.primaryConstructorParameters) {
			// is property
			if (it.hasValOrVar()) {
				val param = it.nameIdentifier?.text
				val type = it.itsType
				if (!param.isNullOrEmpty() && type.isNotEmpty()) {
					// add a space before or after is no used
					appendDoc(prefix, PROPERTY, param, type)
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
					appendDoc(prefix, PROPERTY, param, type)
				}
			}

		// @constructor
		if (owner.hasPrimaryConstructor() && Settings.alwaysShowConstructor) {
			// empty class
			if (!owner.getPrimaryConstructorParameterList()?.parameters.isNullOrEmpty()) {
				appendDoc(prefix, CONSTRUCTOR)
			}
		}
	}

	private fun docKtConstructor(owner: KtConstructor<*>, prefix: String): String = buildString {
		// @param
		for (it in owner.valueParameters) {
			val param = it.nameIdentifier?.text
			val type = it.itsType
			if (!param.isNullOrEmpty() && type.isNotEmpty()) {
				appendDoc(prefix, PARAM, param, type)
			}
		}

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

}
