/**
 * PJavaCommander by Petros Dolaschjan
 *
 */ 
package de.pd.pjc.gui.menu;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.JButton;

public class JCToolbarButton extends JButton {

	public JCToolbarButton(Action pA) {
		super(pA);
		setOpaque(true);
		setBorderPainted(false);
		setRolloverEnabled(true);
		
		int is = 9;
		setMargin(new Insets(is,is,is,is));
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent pE) {
				JButton source = (JButton) pE.getSource();
				source.setBorderPainted(true);
				source.setBackground(Color.red);
			}
			
			@Override
			public void mouseExited(MouseEvent pE) {
				JButton source = (JButton) pE.getSource();
				source.setBorderPainted(false);
			}
		});
	}

}


/*
 * $Log$
 */