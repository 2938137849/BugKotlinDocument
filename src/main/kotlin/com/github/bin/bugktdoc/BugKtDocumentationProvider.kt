package com.github.bin.bugktdoc

import com.github.bin.bugktdoc.constants.*
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
		(contextElement as? KDocImpl)?.owner?.docComment ?: contextElement

	private fun docKtNamedFunction(owner: KtNamedFunction, contextComment: PsiComment): String {
		val pair: Pair<PsiFile, CodeDocumentationAwareCommenter> = contextComment.pair()
		return buildString {
			// @receiver
			owner.receiverTypeReference?.let {
				appendDoc(RECEIVER, pair, it.text)
			}

			// @param
			owner.valueParameters.forEach {
				val param = it.nameIdentifier?.text ?: ""
				val type = it.itsType
				// add a space before `param` and after is no used
				appendDoc(PARAM, pair, param, " ", type)
			}

			// @return
			if (owner.hasDeclaredReturnType()) {
				appendDoc(RETURN, pair, owner.typeReference?.typeElement?.text)
			}
			else {
				owner.itsType.let {
					if (isAlwaysShowUnitReturnType || it != "Unit") {
						appendDoc(RETURN, pair, it)
					}
				}
			}

			// @throws
			PsiTreeUtil.findChildrenOfType(owner, KtAnnotationEntry::class.java).firstOrNull {
				it.calleeExpression?.text == "Throws"
			}?.run {
				valueArguments.mapNotNull {
					it.getArgumentExpression() as? KtClassLiteralExpression
				}.forEach {
					PsiTreeUtil.findChildOfType(it, KtNameReferenceExpression::class.java)?.text?.let {
						appendDoc(THROWS, pair, it)
					}
				}
			}
		}
	}

	private fun docKtClass(owner: KtClass, contextComment: PsiComment): String {
		val pair: Pair<PsiFile, CodeDocumentationAwareCommenter> = contextComment.pair()
		return buildString {
			owner.typeParameters.forEach {
				appendDoc(PARAM, pair, it.text)
			}

			// order: 1. primary Parameters -> @property
			owner.primaryConstructorParameters.forEach {
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
			if (isAlwaysShowClassFieldProperty)
				owner.getProperties().forEach {
					val param = it.nameIdentifier?.text
					val type = it.itsType
					if (!param.isNullOrEmpty()) {
						// add a space before or after is no used
						appendDoc(PROPERTY, pair, param, " ", type)
					}
				}

			// @constructor
			if (owner.hasPrimaryConstructor() && isAlwaysShowConstructor) {
				// empty class
				if (!owner.getPrimaryConstructorParameterList()?.parameters.isNullOrEmpty()) {
					appendDoc(CONSTRUCTOR, pair)
				}
			}
		}
	}

	private fun docKtConstructor(owner: KtConstructor<*>, contextComment: PsiComment): String {
		val pair: Pair<PsiFile, CodeDocumentationAwareCommenter> = contextComment.pair()
		return buildString {

			// @param
			owner.valueParameters.forEach {
				val param = it.nameIdentifier?.text
				val type = it.itsType
				if (!param.isNullOrEmpty() && type.isNotEmpty()) {
					appendDoc(PARAM, pair, param, " ", type)
				}
			}

			// @constructor
			if (isAlwaysShowConstructor) {
				appendDoc(CONSTRUCTOR, pair)
			}
		}
	}

	private fun PsiComment.pair() =
		Pair(containingFile, LanguageCommenters.INSTANCE.forLanguage(language) as CodeDocumentationAwareCommenter)

	private fun StringBuilder.appendDoc(
		lineData: String, arg: Pair<PsiFile, CodeDocumentationAwareCommenter>, vararg strs: String?,
	) {
		append(CodeDocumentationUtil.createDocCommentLine(lineData, arg.first, arg.second))
		for (s in strs) {
			append(s)
		}
		append(LF)
	}
}
