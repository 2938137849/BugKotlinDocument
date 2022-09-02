package com.github.bin.bugktdoc

import com.github.bin.bugktdoc.constants.*
import com.github.bin.bugktdoc.util.KtTypeUtil
import com.intellij.codeInsight.editorActions.CodeDocumentationUtil
import com.intellij.lang.CodeDocumentationAwareCommenter
import com.intellij.lang.LanguageCommenters
import com.intellij.lang.documentation.CodeDocumentationProvider
import com.intellij.lang.documentation.DocumentationProviderEx
import com.intellij.openapi.util.Pair
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import org.jetbrains.kotlin.idea.caches.resolve.resolveToDescriptorIfAny
import org.jetbrains.kotlin.kdoc.psi.api.KDoc
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.types.KotlinType
import org.jetbrains.kotlin.types.starProjectionType

/**
 * @author zxj5470
 * @author bin
 * @date 2018/4/6
 */
class BugKtDocumentationProvider : DocumentationProviderEx(), CodeDocumentationProvider, KtTypeUtil {
	override fun parseContext(startPoint: PsiElement): Pair<PsiElement, PsiComment>? {
		var current: PsiElement? = startPoint
		while (current !== null) {
			if (current is KDoc) {
				return Pair(current, current)
			}
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
		val type = owner.resolveToDescriptorIfAny() ?: return@buildString

		if (Settings.funGeneric) {
			for (it in type.typeParameters) {
				appendDoc(prefix, PARAM, it.name.asString(), it.starProjectionType())
			}
		}

		if (Settings.funContext) {
			for (parameter in type.contextReceiverParameters) {
				appendDoc(prefix, CONTEXT_RECEIVER, parameter.type)
			}
		}

		// @receiver
		if (Settings.funReceiver) {
			type.extensionReceiverParameter?.let {
				appendDoc(prefix, RECEIVER, it.type)
			}
		}

		// @param
		if (Settings.funParam) {
			for (parameter in type.valueParameters) {
				appendDoc(prefix, PARAM, parameter.name.asString(), parameter.type)
			}
		}

		// @return
		if (Settings.funReturn) {
			val returnType = type.returnType!!
			if (Settings.alwaysShowUnitReturnType || owner.hasDeclaredReturnType() || returnType.itsType != "Unit") {
				appendDoc(prefix, RETURN, returnType)
			}
		}

		// @throws
		if (Settings.funThrows) {
			// TODO: need a better comply
			val annotationEntries = PsiTreeUtil.findChildrenOfType(owner, KtAnnotationEntry::class.java)
			annotationEntries.firstOrNull {
				it.calleeExpression?.text == "Throws"
			}?.run {
				for (argument in valueArguments) {
					val expression = (argument.getArgumentExpression() as? KtClassLiteralExpression) ?: continue
					val it = PsiTreeUtil.findChildOfType(expression, KtNameReferenceExpression::class.java) ?: continue
					appendDoc(prefix, THROWS, it.itsType)
				}
			}
		}
	}

	private fun docKtClass(owner: KtClass, prefix: String): String = buildString {
		if (Settings.classGeneric) {
			for (it in owner.typeParameters) {
				appendDoc(prefix, PARAM, it.name, it.itsType)
			}
		}

		val primaryConstructorParameters = owner.primaryConstructorParameters
		// @param
		if (Settings.classParam) {
			appendDoc(prefix, PARAM, primaryConstructorParameters)
		}

		// order: 1. primary Parameters -> @property
		if (Settings.classProperty) {
			appendDoc(prefix, PROPERTY, primaryConstructorParameters.filter { it.hasValOrVar() })
		}

		// order: 2. class fields -> @property
		if (Settings.classFieldProperty) {
			appendDoc(prefix, PROPERTY, owner.getProperties())
		}

		// @constructor
		if (Settings.classConstructor) {
			appendDoc(prefix, CONSTRUCTOR)
		}
	}

	private fun docKtConstructor(owner: KtConstructor<*>, prefix: String): String = buildString {
		// @param
		if (Settings.constructorParam) {
			appendDoc(prefix, PARAM, owner.valueParameters)
		}

		// @constructor
		if (Settings.constructorConstructor) {
			appendDoc(prefix, CONSTRUCTOR)
		}
	}

	private fun PsiComment.getDocPrefix(): String = CodeDocumentationUtil.createDocCommentLine(
		"", containingFile, LanguageCommenters.INSTANCE.forLanguage(language) as CodeDocumentationAwareCommenter
	)

	private fun StringBuilder.appendDoc(prefix: String, vararg strs: String?) {
		strs.joinTo(this, " ", prefix, LF)
	}

	private fun StringBuilder.appendDoc(prefix: String, type: String, ktType: KotlinType) {
		append(prefix).append(type).append(' ').append(ktType.itsType).appendLine()
	}

	private fun StringBuilder.appendDoc(prefix: String, type: String, name: String, ktType: KotlinType) {
		append(prefix).append(type).append(' ').append(name).append(' ').append(ktType.itsType).appendLine()
	}

	private fun StringBuilder.appendDoc(prefix: String, token: String, it: KtCallableDeclaration) {
		val param = it.name
		val type = it.itsType
		if (!param.isNullOrEmpty() && type.isNotEmpty()) {
			appendDoc(prefix, token, param, type)
		}
	}

	private fun StringBuilder.appendDoc(
		prefix: String, token: String, valueParameters: Iterable<KtCallableDeclaration>,
	) {
		for (it in valueParameters) {
			appendDoc(prefix, token, it)
		}
	}

}
