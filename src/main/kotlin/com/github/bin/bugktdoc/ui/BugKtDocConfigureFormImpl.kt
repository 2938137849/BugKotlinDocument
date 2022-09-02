package com.github.bin.bugktdoc.ui

import com.github.bin.bugktdoc.BugKtDocBundle
import com.github.bin.bugktdoc.Settings
import com.intellij.openapi.options.Configurable
import com.intellij.ui.components.JBCheckBox
import java.awt.Container
import javax.swing.JComponent
import javax.swing.JPanel
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KMutableProperty1
import com.github.bin.bugktdoc.options.BugKtDocSettings as DocSetting

/**
 * @author bin
 * @date 2018/4/2
 */
class BugKtDocConfigureFormImpl : BugKtDocConfigureForm(), Configurable {
	private var local: DocSetting = Settings.copy()
	private fun JBCheckBox.init(mapping: KMutableProperty1<DocSetting, Boolean>): JBCheckBox {
		isSelected = mapping.get(local)
		addActionListener {
			mapping.set(local, (it.source as JBCheckBox).isSelected)
		}
		return this
	}

	private fun setEnabled(it: JBCheckBox, boxes: Array<out JComponent>) {
		val selected = it.run { isEnabled && isSelected }
		for (box in boxes) {
			box.isEnabled = selected
		}
	}

	private fun JBCheckBox.bind(vararg boxes: JComponent): JBCheckBox {
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

	override fun initComponents() {
		super.initComponents()
		useDoc.init(DocSetting::useDoc).bind(
			separator1,
			useFunctionDoc.init(DocSetting::useFunctionDoc).bind(
				funGeneric.init(DocSetting::funGeneric),
				funContext.init(DocSetting::funContext),
				funReceiver.init(DocSetting::funReceiver),
				funReturn.init(DocSetting::funReturn).bind(
					alwaysShowUnitReturnType.init(DocSetting::alwaysShowUnitReturnType),
				),
				funThrows.init(DocSetting::funThrows),
			),
			useClassDoc.init(DocSetting::useClassDoc).bind(
				classGeneric.init(DocSetting::classGeneric),
				classParam.init(DocSetting::classParam),
				classProperty.init(DocSetting::classProperty),
				classFieldProperty.init(DocSetting::classFieldProperty),
				classConstructor.init(DocSetting::classConstructor),
			),
			useConstructorDoc.init(DocSetting::useConstructorDoc).bind(
				constructorParam.init(DocSetting::constructorParam),
				constructorConstructor.init(DocSetting::constructorConstructor),
			),
			separator2,
			showBuiltinType.init(DocSetting::showBuiltinType)
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

	override fun getPreferredFocusedComponent(): JComponent? = useDoc

	override fun disposeUIResources() {
		fun <T : Container> dispose(vararg boxes: KMutableProperty0<T?>) {
			for (box in boxes) {
				box.get()?.run {
					removeAll()
				}
				box.set(null)
			}
		}
		dispose(this::panel)
		dispose(
			this::useDoc,
			this::useFunctionDoc,
			this::funContext,
			this::funReceiver,
			this::funReturn,
			this::alwaysShowUnitReturnType,
			this::funThrows,
			this::useClassDoc,
			this::classGeneric,
			this::classParam,
			this::classProperty,
			this::classFieldProperty,
			this::classConstructor,
			this::useConstructorDoc,
			this::constructorParam,
			this::constructorConstructor,
		)
	}

}
