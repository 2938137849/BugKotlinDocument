/*
 * Created by JFormDesigner on Tue Aug 23 14:04:31 CST 2022
 */

package com.github.bin.bugktdoc.ui;

import com.intellij.ui.components.JBCheckBox;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

/**
 * @author bin
 */
public class BugKtDocConfigureForm {

	@SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
	protected void createUIComponents() {
	}

	@SuppressWarnings({"UseDPIAwareInsets", "AlibabaMethodTooLong"})
	protected void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		createUIComponents();

		panel = new JPanel();
		var separator1 = new JSeparator();
		var vSpacer1 = new Spacer();

		//======== panel ========
		{
			panel.setLayout(new GridLayoutManager(21, 1, new Insets(0, 0, 0, 0), 4, -1));
			panel.add(useDoc, new GridConstraints(0, 0, 1, 1,
				GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				null, null, null));
			panel.add(separator1, new GridConstraints(1, 0, 1, 1,
				GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				null, null, null));
			panel.add(useFunctionDoc, new GridConstraints(2, 0, 1, 1,
				GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				null, null, null, 2));
			panel.add(funContext, new GridConstraints(3, 0, 1, 1,
				GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				null, null, null, 4));
			panel.add(funReceiver, new GridConstraints(4, 0, 1, 1,
				GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				null, null, null, 4));
			panel.add(funReturn, new GridConstraints(5, 0, 1, 1,
				GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				null, null, null, 4));
			panel.add(alwaysShowUnitReturnType, new GridConstraints(6, 0, 1, 1,
				GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				null, null, null, 6));
			panel.add(funThrows, new GridConstraints(7, 0, 1, 1,
				GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				null, null, null, 4));
			panel.add(useClassDoc, new GridConstraints(9, 0, 1, 1,
				GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				null, null, null, 2));
			panel.add(classGeneric, new GridConstraints(10, 0, 1, 1,
				GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				null, null, null, 4));
			panel.add(classParam, new GridConstraints(11, 0, 1, 1,
				GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				null, null, null, 4));
			panel.add(classProperty, new GridConstraints(12, 0, 1, 1,
				GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				null, null, null, 4));
			panel.add(classFieldProperty, new GridConstraints(13, 0, 1, 1,
				GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				null, null, null, 4));
			panel.add(classConstructor, new GridConstraints(14, 0, 1, 1,
				GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				null, null, null, 4));
			panel.add(useConstructorDoc, new GridConstraints(16, 0, 1, 1,
				GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				null, null, null, 2));
			panel.add(constructorParam, new GridConstraints(17, 0, 1, 1,
				GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				null, null, null, 4));
			panel.add(constructorConstructor, new GridConstraints(18, 0, 1, 1,
				GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				null, null, null, 4));
			panel.add(vSpacer1, new GridConstraints(19, 0, 1, 1,
				GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL,
				GridConstraints.SIZEPOLICY_CAN_SHRINK,
				GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
				null, null, null));
		}

		initComponentsI18n();
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	private void initComponentsI18n() {
		// JFormDesigner - Component i18n initialization - DO NOT MODIFY  //GEN-BEGIN:initI18n
		ResourceBundle bundle = ResourceBundle.getBundle("BugKtDocBundle");
		useDoc.setText(bundle.getString("bugktdoc.options.use"));
		useDoc.setToolTipText(bundle.getString("bugktdoc.options.use.toolTip"));
		useFunctionDoc.setText(bundle.getString("bugktdoc.options.use.functionDoc"));
		funContext.setText(bundle.getString("bugktdoc.options.use.functionDoc.@context"));
		funReceiver.setText(bundle.getString("bugktdoc.options.use.functionDoc.@receiver"));
		funReturn.setText(bundle.getString("bugktdoc.options.use.functionDoc.@return"));
		alwaysShowUnitReturnType.setText(bundle.getString("bugktdoc.options.use.functionDoc.returnUnit"));
		funThrows.setText(bundle.getString("bugktdoc.options.use.functionDoc.@throws"));
		useClassDoc.setText(bundle.getString("bugktdoc.options.use.classDoc"));
		classGeneric.setText(bundle.getString("bugktdoc.options.use.classDoc.generic@param"));
		classParam.setText(bundle.getString("bugktdoc.options.use.classDoc.@param"));
		classProperty.setText(bundle.getString("bugktdoc.options.use.classDoc.@property"));
		classFieldProperty.setText(bundle.getString("bugktdoc.options.use.classDoc.field@property"));
		classConstructor.setText(bundle.getString("bugktdoc.options.use.classDoc.@constructor"));
		useConstructorDoc.setText(bundle.getString("bugktdoc.options.use.constructorDoc"));
		constructorParam.setText(bundle.getString("bugktdoc.options.use.constructorDoc.@param"));
		constructorConstructor.setText(bundle.getString("bugktdoc.options.use.constructorDoc.@constructor"));
		// JFormDesigner - End of component i18n initialization  //GEN-END:initI18n
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	protected JPanel panel;
	protected JBCheckBox useDoc;
	protected JBCheckBox useFunctionDoc;
	protected JBCheckBox funContext;
	protected JBCheckBox funReceiver;
	protected JBCheckBox funReturn;
	protected JBCheckBox alwaysShowUnitReturnType;
	protected JBCheckBox funThrows;
	protected JBCheckBox useClassDoc;
	protected JBCheckBox classGeneric;
	protected JBCheckBox classParam;
	protected JBCheckBox classProperty;
	protected JBCheckBox classFieldProperty;
	protected JBCheckBox classConstructor;
	protected JBCheckBox useConstructorDoc;
	protected JBCheckBox constructorParam;
	protected JBCheckBox constructorConstructor;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
