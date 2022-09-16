package com.github.bin.bugktdoc.options

import com.intellij.openapi.application.ApplicationManager

/**
 * @author bin
 * @date 2022/09/16
 */
object BugKtDocGlobalSettingsObject : BugKtDocGlobalSettings {
	private val Service = ApplicationManager.getApplication().getService(BugKtDocGlobalSettings::class.java)
	override var settings: BugKtDocSettings
		get() = Service.settings
		set(value) {
			Service.settings = value
		}
}
