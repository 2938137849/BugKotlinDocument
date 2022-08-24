package com.github.bin.bugktdoc.options

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

/**
 * @ref julia-intellij
 */
@State(name = "BugKtDocConfig_bin", storages = [Storage(value = "BugKtDocConfig_bin.xml")])
class BugKtDocGlobalSettingsImpl : BugKtDocGlobalSettings, PersistentStateComponent<BugKtDocSettings> {

	override var settings = BugKtDocSettings()
	override fun getState(): BugKtDocSettings = settings
	override fun loadState(state: BugKtDocSettings) {
		settings = state
	}
}
