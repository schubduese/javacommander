/**
 * PJavaCommander by Petros Dolaschjan
 *
 */ 
package de.pd.pjc.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;

public class JCImageButton extends JButton {

	public JCImageButton() {
		super();
		
		setBorder(null);
		setFocusCycleRoot(true);
		setOpaque(false);
		setBackground(Color.black);
		setSize(new Dimension(WIDTH, HEIGHT));
		setAlignmentX(JButton.CENTER_ALIGNMENT);
		setMaximumSize(new Dimension(WIDTH, 270));
		setContentAreaFilled(false);
	}
}


/*
 * $Log$
 */