package com.github.bin.bugktdoc.util

import org.jetbrains.kotlin.builtins.*
import org.jetbrains.kotlin.idea.intentions.SpecifyTypeExplicitlyIntention
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.types.KotlinType
import org.jetbrains.kotlin.types.TypeProjection


val KtNameReferenceExpression.itsType: String
	get() {
		return text
	}

val KtTypeReference.itsType: String
	get() {
		return typeElement?.itsType ?: ""
	}

val KtTypeElement.itsType: String
	get() {
		return text
	}

val KtTypeParameter.itsType: String
	get() {
		return text
	}

val KtContextReceiver.itsType: String
	get() {
		return buildString {
			labelName()?.let { append(it).append("@ ") }
			append(typeReference()?.itsType)
		}
	}

val KtCallableDeclaration.itsType: String
	get() {
		return SpecifyTypeExplicitlyIntention.getTypeForDeclaration(this).itsType
	}

val TypeProjection.itsType: String
	get() {
		return type.itsType
	}

val KotlinType.itsType: String
	get() {
		return if (!isBuiltinFunctionalType) {
			toString()
		}
		else {
			buildString {
				var start = contextFunctionTypeParamsCount()
				if (start > 0) {
					arguments.subList(0, start).joinTo(this, ", ", "context(", ")") {
						it.itsType
					}
				}
				if (isBuiltinExtensionFunctionalType) {
					append(arguments[start++].itsType)
					append(".")
				}
				arguments.subList(start, arguments.size - 1).joinTo(this, ", ", "(", ") -> ") {
					it.itsType
				}
				append(arguments.last().itsType)
			}
		}
	}
