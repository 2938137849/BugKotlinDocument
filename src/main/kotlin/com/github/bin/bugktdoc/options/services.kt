package com.github.bin.bugktdoc.options

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

/**
 * @author zxj5470
 * @date 2018/4/2
 */

data class BugKtDocSettings(
	var useBugKtDoc: Boolean = true,
	var theFirstTile: Boolean = true,
	var alwaysShowUnitReturnType: Boolean = false,
	var alwaysShowClassFieldProperty: Boolean = true,
	var alwaysShowConstructor: Boolean = true,
	var useWrapper: Boolean = false,
)

interface BugKtDocGlobalSettings {
	var settings: BugKtDocSettings
}

/**
 * @ref julia-intellij
 */
@State(name = "BugKtDocConfig", storages = [Storage(value = "BugKtDocConfig.xml")])
class BugKtDocGlobalSettingsImpl : BugKtDocGlobalSettings, PersistentStateComponent<BugKtDocSettings> {

	override var settings = BugKtDocSettings()
	override fun getState(): BugKtDocSettings = settings
	override fun loadState(state: BugKtDocSettings) {
		settings = state
	}
}
