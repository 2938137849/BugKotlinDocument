package com.github.bin.bugktdoc.ui

import com.github.bin.bugktdoc.options.BugKtDocSettings as DocSetting
import com.github.bin.bugktdoc.BugKtDocBundle
import com.github.bin.bugktdoc.options.BugKtDocGlobalSettingsObject.settings
import com.intellij.openapi.options.Configurable
import com.intellij.ui.components.JBCheckBox
import java.lang.ref.SoftReference
import javax.swing.JComponent
import kotlin.reflect.KMutableProperty1

/**
 * @author bin
 * @date 2018/4/2
 */
class BugKtDocConfigureFormImpl : Configurable {
	private var local: DocSetting = settings.copy()
	private var focused: SoftReference<JComponent> = SoftReference(null)
	private fun JBCheckBox.init(mapping: KMutableProperty1<DocSetting, Boolean>): JBCheckBox {
		isSelected = mapping.get(local)
		val setter = mapping.setter
		addActionListener {
			setter(local, (it.source as JBCheckBox).isSelected)
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

	override fun createComponent(): JComponent? {
		return BugKtDocConfigureForm().run {
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
					classConstructor.init(DocSetting::classConstructor),
				),
				useConstructorDoc.init(DocSetting::useConstructorDoc).bind(
					constructorParam.init(DocSetting::constructorParam),
					constructorConstructor.init(DocSetting::constructorConstructor),
				),
				separator2,
				showBuiltinType.init(DocSetting::showBuiltinType)
			)
			focused = SoftReference(useDoc)
			panel
		}
	}

	override fun isModified(): Boolean {
		return local != settings
	}

	override fun reset() {
		local = settings.copy()
	}

	override fun getDisplayName() = BugKtDocBundle("bugktdoc.settings.title")

	override fun getHelpTopic(): String = BugKtDocBundle("bugktdoc.settings.content")

	override fun apply() {
		settings = local.copy()
	}

	override fun cancel() {
		reset()
	}

	override fun getPreferredFocusedComponent(): JComponent? = focused.get()

}
