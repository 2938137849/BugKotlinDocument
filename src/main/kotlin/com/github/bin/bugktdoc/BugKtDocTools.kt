package com.github.bin.bugktdoc

import com.github.bin.bugktdoc.options.BugKtDocGlobalSettings
import com.intellij.AbstractBundle
import com.intellij.openapi.application.ApplicationManager
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.PropertyKey
import java.util.*

var Settings by ApplicationManager.getApplication().getService(BugKtDocGlobalSettings::class.java)::settings

object BugKtDocBundle {
	@NonNls
	private const val BUNDLE = "BugKtDocBundle"
	private val bundle: ResourceBundle by lazy { ResourceBundle.getBundle(BUNDLE) }

	@JvmStatic
	fun message(@PropertyKey(resourceBundle = BUNDLE) key: String) = AbstractBundle.message(bundle, key)

	@JvmStatic
	operator fun invoke(@PropertyKey(resourceBundle = BUNDLE) key: String) = message(key)
}
