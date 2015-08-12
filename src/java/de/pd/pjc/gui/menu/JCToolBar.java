/**
 * PJavaCommander by Petros Dolaschjan
 *
 */ 
package de.pd.pjc.gui.menu;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JToolBar;

import de.pd.pjc.action.JCMenuItemAction;
import de.pd.pjc.bean.FileElement;
import de.pd.pjc.util.IconFactory;

public class JCToolBar extends JToolBar {

	private JButton mBackButton;
	private JButton mForwardButton;
	private JButton mHomeButton;
	private JButton mReloadButton;
	private JButton mCopyButton;
	private JButton mDeleteButton;
	private JCMenuItemAction mCopyAction;
	private JCMenuItemAction mDeleteAction;


	public JCToolBar() {
		super();
		
		addItems();
	}


	/** Adds the items to the toolbar.
	 * 
	 */
	private void addItems() {
		JCMenuItemAction backAction = new JCMenuItemAction(
				JCMenuItemAction.ACTION_GO_DIR_BACK);
		mBackButton = new JCToolbarButton(backAction);
		mBackButton.setToolTipText("Go Back (ALT+Left)");
		mBackButton.setIcon(IconFactory.getIconByName("back.png"));
		mBackButton.setPreferredSize(new Dimension(16, 16));
		add(mBackButton);

		// -------------------------------------------------------
		
		JCMenuItemAction forwardAction = new JCMenuItemAction(
				JCMenuItemAction.ACTION_GO_DIR_FORWARD);
		mForwardButton = new JCToolbarButton(forwardAction);
		mForwardButton.setToolTipText("Go Forward");
		mForwardButton.setIcon(IconFactory.getIconByName("forward.png"));
		add(mForwardButton);
		// forwardButton.setEnabled(!mDirNavigationMap.isEmpty());

		// -------------------------------------------------------
		
		JCMenuItemAction goHomeAction = new JCMenuItemAction(
				JCMenuItemAction.ACTION_GO_HOME);
		mForwardButton = new JCToolbarButton(goHomeAction);
		mForwardButton.setToolTipText("Go Home");
		mForwardButton.setIcon(IconFactory.getIconByName("redhat-home.png"));
		add(mForwardButton);
		
		// -------------------------------------------------------
		
		JCMenuItemAction reloadAction = new JCMenuItemAction(
				JCMenuItemAction.ACTION_RELOAD);
		mReloadButton = new JCToolbarButton(reloadAction);
		mReloadButton.setToolTipText("Reload (Ctrl+R)");
		mReloadButton.setIcon(IconFactory.getIconByName("reload.png"));
		mReloadButton.setMinimumSize(new Dimension(16, 16));
		add(mReloadButton);
		
		// -------------------------------------------------------		
		mCopyAction = new JCMenuItemAction(
						JCMenuItemAction.ACTION_COPY);
		mCopyButton = new JCToolbarButton(mCopyAction);
		mCopyButton.setEnabled(false);
		mCopyButton.setToolTipText("Copy (F5)");
		mCopyButton.setIcon(IconFactory.getIconByName("editcopy.png"));
		mCopyButton.setMinimumSize(new Dimension(24, 24));
		add(mCopyButton);
		
		// -------------------------------------------------------
		
		mDeleteAction = new JCMenuItemAction(
						JCMenuItemAction.ACTION_DELETE);
		mDeleteButton = new JCToolbarButton(mDeleteAction);
		mDeleteButton.setEnabled(false);
		mDeleteButton.setToolTipText("Delete (Del)");
		mDeleteButton.setIcon(IconFactory.getIconByName("trashcan_full_big.png"));
		mDeleteButton.setMinimumSize(new Dimension(24, 24));
		add(mDeleteButton);
		

//		JCMenuItemAction iconViewAction = new JCMenuItemAction(this,
//				JCMenuItemAction.ACTION_ICON_VIEW);
//		JButton iconViewButton = new JButton(iconViewAction);
//		iconViewButton.setToolTipText("Reload (F5)");
//		iconViewButton.setIcon(IconFactory.getIconByName("view_multicolumn.png"));
//		iconViewButton.setMinimumSize(new Dimension(24, 24));
//		add(iconViewButton);
	}
	
	public void updateToolbar(FileElement[] pElements) {
		mCopyAction.putValue(JCMenuItemAction.PARAM_FILE_ELEMENT, pElements);
		mDeleteAction.putValue(JCMenuItemAction.PARAM_FILE_ELEMENT, pElements);
		
		boolean showButtons = pElements.length > 0 && pElements[0] != null && pElements.length > 0 && (pElements.length == 1 && !pElements[0].isTopElement());
		
		if(showButtons) {
			mCopyButton.setEnabled(true);
			mDeleteButton.setEnabled(true);
		} else {
			mCopyButton.setEnabled(false);
			mDeleteButton.setEnabled(false);
		}
	}
}


/*
 * $Log$
 */