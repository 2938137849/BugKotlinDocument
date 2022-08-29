package com.github.bin.bugktdoc.ui

import com.github.bin.bugktdoc.BugKtDocBundle
import com.github.bin.bugktdoc.Settings
import com.intellij.openapi.options.Configurable
import com.intellij.ui.components.JBCheckBox

/**
 * @author zxj5470
 * @date 2018/4/2
 */
class BugKtDocConfigureFormImpl : BugKtDocConfigureForm(), Configurable {

	override fun createUIComponents() {
		reset()
		useDoc = JBCheckBox()
		useDoc.isSelected = local.useDoc
		useDoc.addActionListener {
			local.useDoc = useDoc.isSelected
		}
		useFunctionDoc = JBCheckBox()
		useFunctionDoc.isSelected = local.useFunctionDoc
		useFunctionDoc.addActionListener {
			local.useFunctionDoc = useFunctionDoc.isSelected
			arrayOf(alwaysShowUnitReturnType).forEach {
				it.isEnabled = local.useFunctionDoc
			}
		}
		alwaysShowUnitReturnType = JBCheckBox()
		alwaysShowUnitReturnType.isSelected = local.alwaysShowUnitReturnType
		alwaysShowUnitReturnType.addActionListener {
			local.alwaysShowUnitReturnType = alwaysShowUnitReturnType.isSelected
		}
		useFunctionDoc = JBCheckBox()
		useFunctionDoc.isSelected = local.useFunctionDoc
		useFunctionDoc.addActionListener {
			local.useFunctionDoc = useFunctionDoc.isSelected
			arrayOf(alwaysShowClassFieldProperty).forEach {
				it.isEnabled = local.useFunctionDoc
			}
		}
		alwaysShowClassFieldProperty = JBCheckBox()
		alwaysShowClassFieldProperty.isSelected = local.alwaysShowClassFieldProperty
		alwaysShowClassFieldProperty.addActionListener {
			local.alwaysShowClassFieldProperty = alwaysShowClassFieldProperty.isSelected
		}
		useConstructorDoc = JBCheckBox()
		useConstructorDoc.isSelected = local.useConstructorDoc
		useConstructorDoc.addActionListener {
			local.useConstructorDoc = useConstructorDoc.isSelected
			arrayOf(alwaysShowConstructor).forEach {
				it.isEnabled = local.useConstructorDoc
			}
		}
		alwaysShowConstructor = JBCheckBox()
		alwaysShowConstructor.isSelected = local.alwaysShowConstructor
		alwaysShowConstructor.addActionListener {
			local.alwaysShowConstructor = alwaysShowConstructor.isSelected
		}
	}

	override fun isModified(): Boolean {
		return local != Settings
	}

	override fun reset() {
		local = Settings.copy()
	}

	override fun getDisplayName() = BugKtDocBundle("bugktdoc.settings.title")

	override fun getHelpTopic(): String = BugKtDocBundle("bugktdoc.settings.content")

	override fun apply() {
		Settings = local.copy()
	}

	override fun cancel() {
		reset()
	}

	override fun createComponent() = panel!!
}
