package com.github.bin.bugktdoc.options;

import com.intellij.openapi.options.Configurable;

import javax.swing.*;

public abstract class BugKtDocConfigureForm implements Configurable {
	protected JPanel mainPanel;
	protected JCheckBox useBugKtDoc;
	protected JCheckBox showUnitTypeDefault;
	protected JCheckBox showClassFieldProperty;
	protected JCheckBox showConstructor;
}
