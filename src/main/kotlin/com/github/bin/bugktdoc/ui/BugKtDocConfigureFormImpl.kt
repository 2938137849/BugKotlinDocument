package com.github.bin.bugktdoc.ui

import com.github.bin.bugktdoc.BugKtDocBundle
import com.github.bin.bugktdoc.Settings
import com.github.bin.bugktdoc.options.BugKtDocSettings
import com.intellij.openapi.options.Configurable
import java.awt.event.ActionEvent

/**
 * @author zxj5470
 * @date 2018/4/2
 */
class BugKtDocConfigureFormImpl : BugKtDocConfigureForm(), Configurable {
	private var default: BugKtDocSettings = Settings.copy()

	init {
		useAll.isSelected = default.useDoc
		useFunction.isSelected = default.useFunctionDoc
		showUnitTypeDefault.isSelected = default.alwaysShowUnitReturnType
		useClass.isSelected = default.useClassDoc
		showClassFieldProperty.isSelected = default.alwaysShowClassFieldProperty
		useConstructor.isSelected = default.useConstructorDoc
		showConstructor.isSelected = default.alwaysShowConstructor
	}

	override fun useFunction(e: ActionEvent?) {
		val selected = useFunction.isSelected
		arrayOf(showUnitTypeDefault).forEach {
			it.isEnabled = selected
		}
	}

	override fun useClass(e: ActionEvent?) {
		val selected = useClass.isSelected
		arrayOf(showClassFieldProperty).forEach {
			it.isEnabled = selected
		}
	}

	override fun useConstructor(e: ActionEvent?) {
		val selected = useConstructor.isSelected
		arrayOf(showConstructor).forEach {
			it.isEnabled = selected
		}
	}

	override fun switchListener(e: ActionEvent?) {
		default.useDoc = useAll.isSelected
		default.useFunctionDoc = useFunction.isSelected
		default.alwaysShowUnitReturnType = showUnitTypeDefault.isSelected
		default.useClassDoc = useClass.isSelected
		default.alwaysShowClassFieldProperty = showClassFieldProperty.isSelected
		default.useConstructorDoc = useConstructor.isSelected
		default.alwaysShowConstructor = showConstructor.isSelected
	}

	override fun isModified(): Boolean {
		return default != Settings
	}

	override fun reset() {
		default = Settings.copy()
	}

	override fun getDisplayName() = BugKtDocBundle("bugktdoc.settings.title")

	override fun getHelpTopic(): String = BugKtDocBundle("bugktdoc.settings.content")

	override fun apply() {
		Settings = default.copy()
	}

	override fun cancel() {
		default = Settings.copy()
	}

	override fun createComponent() = panel!!
}
