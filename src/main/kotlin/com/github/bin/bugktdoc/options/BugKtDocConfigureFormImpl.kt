package com.github.bin.bugktdoc.options

import com.github.bin.bugktdoc.BugKtDocBundle
import com.github.bin.bugktdoc.globalSettings
import com.intellij.openapi.options.Configurable
import javax.swing.JPanel

/**
 * @author zxj5470
 * @date 2018/4/2
 */
class BugKtDocConfigureFormImpl : BugKtDocConfigureForm(), Configurable {
	private val thisPanel: JPanel = mainPanel

	init {
		useBugKtDoc.isSelected = globalSettings.useBugKtDoc
		showUnitTypeDefault.isSelected = globalSettings.alwaysShowUnitReturnType
		showClassFieldProperty.isSelected = globalSettings.alwaysShowClassFieldProperty
		showConstructor.isSelected = globalSettings.alwaysShowConstructor
		addSwitchListener()
		observer()
	}

	private fun addSwitchListener() {
		useBugKtDoc?.addActionListener {
			observer()
		}
	}

	private fun observer() {
		useBugKtDoc?.apply {
			if (this.isSelected) {
				showUnitTypeDefault.isEnabled = true
				showClassFieldProperty.isEnabled = true
				showConstructor.isEnabled = true
			}
			else {
				showUnitTypeDefault.isEnabled = false
				showClassFieldProperty.isEnabled = false
				showConstructor.isEnabled = false
			}
		}
	}

	override fun isModified(): Boolean {
		return true
	}

	override fun reset() {
		globalSettings.useBugKtDoc = true
		globalSettings.alwaysShowUnitReturnType = false
		globalSettings.alwaysShowClassFieldProperty = true
		globalSettings.alwaysShowConstructor = true
		observer()
	}

	override fun getDisplayName() = BugKtDocBundle.message("bugktdoc.settings.title")

	override fun apply() {
		globalSettings.useBugKtDoc = useBugKtDoc.isSelected
		globalSettings.alwaysShowUnitReturnType = showUnitTypeDefault.isSelected
		globalSettings.alwaysShowClassFieldProperty = showClassFieldProperty.isSelected
		globalSettings.alwaysShowConstructor = showConstructor.isSelected
	}

	override fun createComponent() = thisPanel
}
