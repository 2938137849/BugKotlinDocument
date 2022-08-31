package com.github.bin.bugktdoc.util

import com.github.bin.bugktdoc.Settings
import org.jetbrains.kotlin.builtins.*
import org.jetbrains.kotlin.idea.intentions.SpecifyTypeExplicitlyIntention
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.types.KotlinType
import org.jetbrains.kotlin.types.TypeProjection
import org.jetbrains.kotlin.types.Variance

interface KtTypeUtil {

	val KtNameReferenceExpression.itsType: String
		get() {
			return text
		}

	val KtTypeReference.itsType: String
		get() {
			return typeElement?.itsType ?: ""
		}

	private val KtTypeElement.itsType: String
		get() {
			return text
		}

	val KtTypeParameter.itsType: String
		get() {
			return buildString {
				extendsBound?.let {
					if (variance != Variance.INVARIANT) {
						append(variance.label)
						append(' ')
					}
					append(it.itsType)
				}
			}
		}

	val KtContextReceiver.itsType: String
		get() {
			return typeReference()?.itsType ?: ""
		}

	val KtCallableDeclaration.itsType: String
		get() {
			return SpecifyTypeExplicitlyIntention.getTypeForDeclaration(this).itsType
		}

	private val TypeProjection.itsType: String
		get() {
			return type.itsType
		}

	val KotlinType.itsType: String
		get() {
			return if (Settings.showBuiltinType ||!isBuiltinFunctionalType) {
				toString()
			}
			else {
				buildString {
					if (isMarkedNullable) {
						append('(')
					}
					var start = contextFunctionTypeParamsCount()
					if (start > 0) {
						arguments.subList(0, start).joinTo(this, ", ", "context(", ") ") {
							it.itsType
						}
					}
					if (isSuspendFunctionType) {
						append("suspend ")
					}
					if (isBuiltinExtensionFunctionalType) {
						append(arguments[start++].itsType)
						append(".")
					}
					arguments.subList(start, arguments.size - 1).joinTo(this, ", ", "(", ") -> ") {
						it.itsType
					}
					append(arguments.last().itsType)
					if (isMarkedNullable) {
						append(")?")
					}
				}
			}
		}

}
