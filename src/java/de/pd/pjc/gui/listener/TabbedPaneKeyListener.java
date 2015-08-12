package de.pd.pjc.gui.listener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TabbedPaneKeyListener extends KeyAdapter {

	@Override
	public void keyPressed(KeyEvent evt) {
		boolean ctrDown = evt.isControlDown();
		
		if(ctrDown && evt.getKeyCode() == KeyEvent.VK_T) {
			
		}

	}



}
