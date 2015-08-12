/**
 * PJavaCommander by Petros Dolaschjan
 *
 */ 
package de.pd.pjc.gui.menu;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import de.pd.pjc.action.JCMenuItemAction;
import de.pd.pjc.bean.FileElement;
import de.pd.pjc.util.IconFactory;

public class JCDndPopupMenu extends JPopupMenu {

	private FileElement[] mElement;
	private String mDestPath;
	public JCDndPopupMenu(FileElement[] pElement, String pDestPath) {
		super();
		mElement = pElement;
		mDestPath = pDestPath;
		init();
	}
	
	private void init() {
		/** copy action */
		JCMenuItemAction copyAction = new JCMenuItemAction(JCMenuItemAction.ACTION_COPY);
		copyAction.putValue(JCMenuItemAction.PARAM_FILE_ELEMENT, mElement);
		copyAction.putValue(JCMenuItemAction.PARAM_COPY_CLIPBOARD_ONYL, Boolean.FALSE);
		copyAction.putValue(JCMenuItemAction.PARAM_DEST_PATH, mDestPath);
		JMenuItem copyItem = new JMenuItem(copyAction);
		copyItem.setText("Copy");
		copyItem.setIcon(IconFactory.getIconByName("editcopy_small.png"));
		copyItem.setMnemonic('c');
		add(copyItem);
		
		/** move action */
		JCMenuItemAction moveAction = new JCMenuItemAction(JCMenuItemAction.ACTION_MOVE);
		moveAction.putValue(JCMenuItemAction.PARAM_FILE_ELEMENT, mElement);
		moveAction.putValue(JCMenuItemAction.PARAM_COPY_CLIPBOARD_ONYL, Boolean.FALSE);
		moveAction.putValue(JCMenuItemAction.PARAM_DEST_PATH, mDestPath);
		JMenuItem moveItem = new JMenuItem(moveAction);
		moveItem.setText("Move");
		moveItem.setIcon(IconFactory.getIconByName("fileimport_small.png"));
		moveItem.setMnemonic('m');
		add(moveItem);
		
	}
	
}


/*
 * $Log$
 */