/*
 * JCToolBarItem.java
 *
 * Created on June 5, 2003, 7:30 PM
 */

package de.pd.pjc.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
/**
 *
 * @author  petros
 */
public class JCToolBarItem extends JButton {
	
	private int mItemAction;
	
	/** Creates a new instance of JCToolBarItem */
	public JCToolBarItem() {
		
		setSize(24, 24);
		
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
//				JCToolBarItemAction action = new JCToolBarItemAction();
//				action.doAction(getItemAction());
			}
		});
	}
	
	/** Getter for property mItemAction.
	 * @return Value of property mItemAction.
	 *
	 */
	public int getItemAction() {
		return mItemAction;
	}
	
	/** Setter for property mItemAction.
	 * @param mItemAction New value of property mItemAction.
	 *
	 */
	public void setItemAction(int mItemAction) {
		this.mItemAction = mItemAction;
	}
	
}
