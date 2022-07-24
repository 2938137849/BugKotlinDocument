package com.github.zxj5470.bugktdoc

import com.github.zxj5470.bugktdoc.constants.*
import com.intellij.codeInsight.editorActions.CodeDocumentationUtil
import com.intellij.ide.util.PackageUtil
import com.intellij.lang.CodeDocumentationAwareCommenter
import com.intellij.lang.LanguageCommenters
import com.intellij.lang.documentation.CodeDocumentationProvider
import com.intellij.lang.documentation.DocumentationProviderEx
import com.intellij.lang.java.JavaDocumentationProvider.getPackageInfoComment
import com.intellij.openapi.util.Pair
import com.intellij.psi.*
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.containers.isNullOrEmpty
import org.jetbrains.kotlin.kdoc.psi.impl.KDocImpl
import org.jetbrains.kotlin.psi.*


/**
 * @author zxj5470
 * @date 2018/4/6
 */
class BugKtDocumentationProvider : DocumentationProviderEx(), CodeDocumentationProvider {
	/**
	 *
	 * @param startPoint PsiElement
	 * @return Pair<PsiElement, PsiComment>?
	 */
	override fun parseContext(startPoint: PsiElement): Pair<PsiElement, PsiComment> {
		var current = startPoint
		while (true) {
			if (current is KDocImpl) return Pair.create(current, current)
			else if (PackageUtil.isPackageInfoFile(current)) return Pair.create(current, getPackageInfoComment(current))
			current = current.parent
		}
	}

	private fun PsiComment.getOwner() = PsiTreeUtil.getParentOfType<KtDeclaration>(this)
		?: this.parent.takeIf { it is KtFunction || it is KtClass }

	override fun generateDocumentationContentStub(contextComment: PsiComment?): String {
		if (!pluginActive || contextComment == null) return ""

		return when (val owner = contextComment.getOwner()) {
			is KtNamedFunction -> docKtNamedFunction(owner, contextComment)
			is KtClass -> docKtClass(owner, contextComment)
			is KtConstructor<*> -> docKtConstructor(owner, contextComment)
			else -> ""
		}
	}

	override fun findExistingDocComment(contextElement: PsiComment?): PsiComment? =
		if (!isKotlinNative) (contextElement as? KDocImpl)?.getOwner()?.docComment else contextElement

	private fun docKtNamedFunction(owner: KtNamedFunction, contextComment: PsiComment): String {
		val pair: Pair<PsiFile, CodeDocumentationAwareCommenter> = contextComment.pair()
		return buildString {
			// @receiver
			owner.receiverTypeReference?.let {
				append(RECEIVER, pair)
				append("[")
				append(it.text)
				append("]")
				append(LF)
			}

			// @param
			owner.valueParameters.forEach {
				val param = it.nameIdentifier?.text ?: ""
				val type = it.itsType
				append(PARAM, pair)
				// add a space before `param` and after is no used
				append("$param [$type]")
				append(LF)
			}

			// @return
			if (owner.hasDeclaredReturnType()) {
				append(RETURN, pair)
				append("[")
				append(owner.typeReference?.typeElement?.text)
				append("]")
				append(LF)
			}
			else {
				owner.itsType.let {
					if (isAlwaysShowUnitReturnType || it != "Unit") {
						append(RETURN, pair)
						append("[")
						append(it)
						append("]")
						append(LF)
					}
				}
			}

			// @throws
			PsiTreeUtil.findChildrenOfType(owner, KtAnnotationEntry::class.java)
				.firstOrNull { it.calleeExpression?.text == "Throws" }
				?.valueArguments?.forEach {
					(it.getArgumentExpression() as? KtClassLiteralExpression)?.let {
						PsiTreeUtil.findChildOfType(it, KtNameReferenceExpression::class.java)?.text?.let {
							append(THROWS, pair)
							append("[")
							append(it)
							append("]")
							append(LF)
						}
					}
				}
		}
	}

	private fun docKtClass(owner: KtClass, contextComment: PsiComment): String {
		val pair: Pair<PsiFile, CodeDocumentationAwareCommenter> = contextComment.pair()
		return buildString {
			owner.typeParameters.forEach {
				append(PARAM, pair)
				append("[")
				append(it.text)
				append("]")
				append(LF)
			}

			// order: 1. primary Parameters -> @property
			owner.primaryConstructorParameters.forEach {
				// is property
				if (it.hasValOrVar()) {
					val param = it.nameIdentifier?.text
					val type = it.itsType
					if (!param.isNullOrEmpty() && type.isNotEmpty()) {
						append(PROPERTY, pair)
						// add a space before or after is no used
						append("$param [$type]")
						append(LF)
					}
				}
			}

			// order: 2. class fields -> @property
			if (isAlwaysShowClassFieldProperty)
				owner.getProperties().forEach {
					val param = it.nameIdentifier?.text
					val type = it.itsType
					if (!param.isNullOrEmpty()) {
						append(PROPERTY, pair)
						// add a space before or after is no used
						append("$param [$type]")
						append(LF)
					}
				}

			// @constructor
			if (owner.hasPrimaryConstructor() && isAlwaysShowConstructor) {
				// empty class
				if (!owner.getPrimaryConstructorParameterList()?.parameters.isNullOrEmpty()) {
					append(CONSTRUCTOR, pair)
					append(LF)
				}
			}
		}
	}

	private fun docKtConstructor(owner: KtConstructor<*>, contextComment: PsiComment): String {
		val pair: Pair<PsiFile, CodeDocumentationAwareCommenter> = contextComment.pair()
		return buildString {

			// @param
			owner.getValueParameters().forEach {
				val param = it.nameIdentifier?.text
				val type = it.itsType
				if (!param.isNullOrEmpty() && type.isNotEmpty()) {
					append(PARAM, pair)
					append("$param [$type]")
					append(LF)
				}
			}

			// @constructor
			if (isAlwaysShowConstructor) {
				append(CONSTRUCTOR, pair)
				append(LF)
			}
		}
	}

	private fun PsiComment.pair() =
		Pair(containingFile, LanguageCommenters.INSTANCE.forLanguage(language) as CodeDocumentationAwareCommenter)

	private fun StringBuilder.append(str: String, arg: Pair<PsiFile, CodeDocumentationAwareCommenter>) =
		append(CodeDocumentationUtil.createDocCommentLine(str, arg.first, arg.second))
}
