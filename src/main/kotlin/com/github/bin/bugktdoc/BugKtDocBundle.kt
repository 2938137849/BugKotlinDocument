package com.github.bin.bugktdoc

import com.intellij.AbstractBundle
import org.jetbrains.annotations.NonNls
import org.jetbrains.annotations.PropertyKey
import java.util.*

object BugKtDocBundle {
	@NonNls
	private const val BUNDLE = "BugKtDocBundle"
	private val bundle: ResourceBundle by lazy(LazyThreadSafetyMode.NONE) { ResourceBundle.getBundle(BUNDLE) }

	@JvmStatic
	fun message(@PropertyKey(resourceBundle = BUNDLE) key: String) = AbstractBundle.message(bundle, key)

	@JvmStatic
	operator fun invoke(@PropertyKey(resourceBundle = BUNDLE) key: String) = message(key)
}
