package com.github.bin.bugktdoc

import com.github.bin.bugktdoc.options.BugKtDocGlobalSettings
import com.intellij.AbstractBundle
import com.intellij.openapi.application.ApplicationManager
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.PropertyKey
import org.jetbrains.kotlin.idea.intentions.SpecifyTypeExplicitlyIntention
import org.jetbrains.kotlin.psi.KtCallableDeclaration
import java.util.*

val globalSettings
	get() = ApplicationManager.getApplication().getService(BugKtDocGlobalSettings::class.java).settings

val isTheFirstTime
	get() = globalSettings.theFirstTile

val pluginActive
	get() = globalSettings.useBugKtDoc

val isAlwaysShowUnitReturnType
	get() = globalSettings.alwaysShowUnitReturnType

val isAlwaysShowClassFieldProperty
	get() = globalSettings.alwaysShowClassFieldProperty

val isAlwaysShowConstructor
	get() = globalSettings.alwaysShowConstructor

object BugKtDocBundle {
	@NonNls
	private const val BUNDLE = "BugKtDocBundle"
	private val bundle: ResourceBundle by lazy { ResourceBundle.getBundle(BUNDLE) }

	@JvmStatic
	fun message(@PropertyKey(resourceBundle = BUNDLE) key: String) = AbstractBundle.message(bundle, key)
}

inline val KtCallableDeclaration.itsType
	get() = SpecifyTypeExplicitlyIntention.getTypeForDeclaration(this).unwrap().toString()
