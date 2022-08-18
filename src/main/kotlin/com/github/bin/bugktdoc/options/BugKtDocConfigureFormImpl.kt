package com.github.bin.bugktdoc.options

import com.github.bin.bugktdoc.BugKtDocBundle
import com.github.bin.bugktdoc.Settings
import com.intellij.ide.ui.UINumericRange
import com.intellij.openapi.options.Configurable
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.JToggleButton

/**
 * @author zxj5470
 * @date 2018/4/2
 */
class BugKtDocConfigureFormImpl : BugKtDocConfigureForm(), Configurable, ActionListener {
	private val thisPanel: JPanel = mainPanel
	private var default: BugKtDocSettings = Settings.copy()

	init {
		useBugKtDoc.isSelected = default.useBugKtDoc
		showUnitTypeDefault.isSelected = default.alwaysShowUnitReturnType
		showClassFieldProperty.isSelected = default.alwaysShowClassFieldProperty
		showConstructor.isSelected = default.alwaysShowConstructor
		useWrapper.isSelected = default.useWrapper
		addSwitchListener()
		observer()
	}

	private fun addSwitchListener() {
		useBugKtDoc.addActionListener {
			observer()
		}
		arrayOf(useBugKtDoc, showUnitTypeDefault, showClassFieldProperty, showConstructor, useWrapper).forEach {
			it.addActionListener(this)
		}
	}

	override fun actionPerformed(e: ActionEvent?) {
		default.useBugKtDoc = useBugKtDoc.isSelected
		default.alwaysShowUnitReturnType = showUnitTypeDefault.isSelected
		default.alwaysShowClassFieldProperty = showClassFieldProperty.isSelected
		default.alwaysShowConstructor = showConstructor.isSelected
		default.useWrapper = useWrapper.isSelected
	}

	private fun observer() {
		val selected = useBugKtDoc.isSelected
		arrayOf(showUnitTypeDefault, showClassFieldProperty, showConstructor, useWrapper).forEach {
			it.isEnabled = selected
		}
	}

	override fun isModified(): Boolean {
		return default != Settings
	}

	override fun reset() {
		default.useBugKtDoc = true
		default.alwaysShowUnitReturnType = false
		default.alwaysShowClassFieldProperty = true
		default.alwaysShowConstructor = true
		default.useWrapper = false
		observer()
	}

	override fun getDisplayName() = BugKtDocBundle("bugktdoc.settings.title")

	override fun getHelpTopic(): String = BugKtDocBundle("bugktdoc.settings.content")

	override fun apply() {
		Settings = default
	}

	override fun createComponent() = thisPanel
}
