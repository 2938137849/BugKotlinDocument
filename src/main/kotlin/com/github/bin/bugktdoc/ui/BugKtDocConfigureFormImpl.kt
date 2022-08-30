package com.github.bin.bugktdoc.ui

import com.github.bin.bugktdoc.options.BugKtDocSettings as DocSetting
import com.github.bin.bugktdoc.BugKtDocBundle
import com.github.bin.bugktdoc.Settings
import com.intellij.openapi.options.Configurable
import com.intellij.ui.components.JBCheckBox
import javax.swing.JComponent
import javax.swing.JPanel
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KMutableProperty1

/**
 * @author bin
 * @date 2018/4/2
 */
class BugKtDocConfigureFormImpl : BugKtDocConfigureForm(), Configurable {
	private lateinit var local: DocSetting
	private fun KMutableProperty0<JBCheckBox>.init(mapping: KMutableProperty1<DocSetting, Boolean>): JBCheckBox {
		val box = JBCheckBox()
		set(box)
		box.isSelected = mapping.get(local)
		box.addActionListener {
			mapping.set(local, (it.source as JBCheckBox).isSelected)
		}
		return box
	}

	private fun setEnabled(it: JBCheckBox, boxes: Array<out JBCheckBox>) {
		val selected = it.run { isEnabled && isSelected }
		for (box in boxes) {
			box.isEnabled = selected
		}
	}

	private fun JBCheckBox.bind(vararg boxes: JBCheckBox): JBCheckBox {
		// for isSelected event
		addActionListener {
			setEnabled(it.source as JBCheckBox, boxes)
		}
		// for isEnabled event
		addPropertyChangeListener {
			if (it.propertyName == "enabled") {
				setEnabled(it.source as JBCheckBox, boxes)
			}
		}
		return this
	}

	override fun createUIComponents() {
		reset()
		this::useDoc.init(DocSetting::useDoc).bind(
			this::useFunctionDoc.init(DocSetting::useFunctionDoc).bind(
				this::funContext.init(DocSetting::funContext),
				this::funReceiver.init(DocSetting::funReceiver),
				this::funReturn.init(DocSetting::funReturn).bind(
					this::alwaysShowUnitReturnType.init(DocSetting::alwaysShowUnitReturnType),
				),
				this::funThrows.init(DocSetting::funThrows),
			),
			this::useClassDoc.init(DocSetting::useClassDoc).bind(
				this::classGeneric.init(DocSetting::classGeneric),
				this::classParam.init(DocSetting::classParam),
				this::classProperty.init(DocSetting::classProperty),
				this::classFieldProperty.init(DocSetting::classFieldProperty),
				this::classConstructor.init(DocSetting::classConstructor),
			),
			this::useConstructorDoc.init(DocSetting::useConstructorDoc).bind(
				this::constructorParam.init(DocSetting::constructorParam),
				this::constructorConstructor.init(DocSetting::constructorConstructor),
			),
		)
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

	override fun createComponent(): JPanel? {
		initComponents()
		return panel
	}

	override fun getPreferredFocusedComponent(): JComponent? {
		return useDoc
	}

	override fun disposeUIResources() {
		panel = null
		useDoc = null
		useFunctionDoc = null
		funContext = null
		funReceiver = null
		funReturn = null
		alwaysShowUnitReturnType = null
		funThrows = null
		useClassDoc = null
		classGeneric = null
		classParam = null
		classProperty = null
		classFieldProperty = null
		classConstructor = null
		useConstructorDoc = null
		constructorParam = null
		constructorConstructor = null
	}

}
