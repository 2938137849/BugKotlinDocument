package com.github.bin.bugktdoc.util

import org.jetbrains.kotlin.builtins.isBuiltinExtensionFunctionalType
import org.jetbrains.kotlin.builtins.isBuiltinFunctionalType
import org.jetbrains.kotlin.idea.intentions.SpecifyTypeExplicitlyIntention
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.types.KotlinType
import org.jetbrains.kotlin.types.TypeProjection


val KtNameReferenceExpression.itsType: String
	get() {
		return text
	}

val KtTypeReference?.itsType: String
	get() {
		this ?: return ""
		val element = typeElement ?: return ""
		return element.text
	}

val KtTypeParameter.itsType: String
	get() {
		return text
	}

val KtCallableDeclaration.itsType: String
	get() {
		return SpecifyTypeExplicitlyIntention.getTypeForDeclaration(this).itsType
	}

val TypeProjection.itsType: String
	get() {
		return type.unwrap().toString()
	}

val KotlinType.itsType: String
	get() {
		return if (isBuiltinFunctionalType) {
			buildString {
				var start = 0
				if (isBuiltinExtensionFunctionalType) {
					append(arguments[start++].itsType)
					append(".")
				}
				arguments
					.subList(start, arguments.size - 1)
					.joinTo(this, ", ", "(", ") -> ") { it.itsType }
				append(arguments.last().itsType)
			}
		}
		else {
			toString()
		}
	}
