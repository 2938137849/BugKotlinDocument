/*
 * Created by JFormDesigner on Tue Aug 23 14:04:31 CST 2022
 */

package com.github.bin.bugktdoc.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import com.intellij.ui.components.*;

import com.intellij.uiDesigner.core.*;

/**
 * @author unknown
 */
public class BugKtDocConfigureForm {
	public BugKtDocConfigureForm() {
		initComponents();
	}

	protected void switchListener(ActionEvent e) {
	}

	protected void useFunction(ActionEvent e) {
	}

	protected void useClass(ActionEvent e) {
	}

	protected void useConstructor(ActionEvent e) {
	}

	@SuppressWarnings({"UseDPIAwareInsets", "Convert2MethodRef", "AlibabaMethodTooLong"})
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		panel = new JPanel();
		useAll = new JBCheckBox();
		var separator1 = new JSeparator();
		useFunction = new JBCheckBox();
		showUnitTypeDefault = new JBCheckBox();
		useClass = new JBCheckBox();
		showClassFieldProperty = new JBCheckBox();
		useConstructor = new JBCheckBox();
		showConstructor = new JBCheckBox();
		var vSpacer1 = new Spacer();

		//======== panel ========
		{
			panel.setLayout(new GridLayoutManager(9, 1, new Insets(0, 0, 0, 0), 4, -1));

			//---- useAll ----
			useAll.setSelected(true);
			useAll.addActionListener(e -> switchListener(e));
			panel.add(useAll, new GridConstraints(0, 0, 1, 1,
				GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				null, null, null));
			panel.add(separator1, new GridConstraints(1, 0, 1, 1,
				GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				null, null, null));

			//---- useFunction ----
			useFunction.addActionListener(e -> {
			useFunction(e);
			switchListener(e);
		});
			panel.add(useFunction, new GridConstraints(2, 0, 1, 1,
				GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				null, null, null, 2));

			//---- showUnitTypeDefault ----
			showUnitTypeDefault.addActionListener(e -> switchListener(e));
			panel.add(showUnitTypeDefault, new GridConstraints(3, 0, 1, 1,
				GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				null, null, null, 4));

			//---- useClass ----
			useClass.addActionListener(e -> {
			useClass(e);
			switchListener(e);
		});
			panel.add(useClass, new GridConstraints(4, 0, 1, 1,
				GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				null, null, null, 2));

			//---- showClassFieldProperty ----
			showClassFieldProperty.setSelected(true);
			showClassFieldProperty.addActionListener(e -> switchListener(e));
			panel.add(showClassFieldProperty, new GridConstraints(5, 0, 1, 1,
				GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				null, null, null, 4));

			//---- useConstructor ----
			useConstructor.addActionListener(e -> {
			useConstructor(e);
			switchListener(e);
		});
			panel.add(useConstructor, new GridConstraints(6, 0, 1, 1,
				GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				null, null, null, 2));

			//---- showConstructor ----
			showConstructor.setSelected(true);
			showConstructor.addActionListener(e -> switchListener(e));
			panel.add(showConstructor, new GridConstraints(7, 0, 1, 1,
				GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
				null, null, null, 4));
			panel.add(vSpacer1, new GridConstraints(8, 0, 1, 1,
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
		useAll.setText(bundle.getString("bugktdoc.options.use"));
		useAll.setToolTipText(bundle.getString("bugktdoc.options.use.toolTip"));
		useFunction.setText(bundle.getString("bugktdoc.options.use.functionDoc"));
		showUnitTypeDefault.setText(bundle.getString("bugktdoc.options.default.unit"));
		useClass.setText(bundle.getString("bugktdoc.options.use.classDoc"));
		showClassFieldProperty.setText(bundle.getString("bugktdoc.options.default.property"));
		useConstructor.setText(bundle.getString("bugktdoc.options.use.constructorDoc"));
		showConstructor.setText(bundle.getString("bugktdoc.options.default.constructor"));
		// JFormDesigner - End of component i18n initialization  //GEN-END:initI18n
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	protected JPanel panel;
	protected JBCheckBox useAll;
	protected JBCheckBox useFunction;
	protected JBCheckBox showUnitTypeDefault;
	protected JBCheckBox useClass;
	protected JBCheckBox showClassFieldProperty;
	protected JBCheckBox useConstructor;
	protected JBCheckBox showConstructor;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
