/**
 * PJavaCommander by Petros Dolaschjan
 *
 */ 
package de.pd.pjc.gui.menu;

import java.util.Collection;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

import de.pd.pjc.Settings;
import de.pd.pjc.action.JCMenuItemAction;
import de.pd.pjc.bean.FavouriteBean;
import de.pd.pjc.service.ServiceFactory;
import de.pd.pjc.util.IconFactory;
import de.pd.pjc.util.PJCUtils;

public class JCFavMenu extends JMenu {

	JMenuItem mAddFavMenuItem = new JMenuItem();
	
	public JCFavMenu() {
		super();
		
		setText("Favourites");
		setMnemonic('v');
		
		initAddFavMenuItem();
		loadFavourites();
	}
	
	private void initAddFavMenuItem() {
		add(mAddFavMenuItem);
		mAddFavMenuItem.setText("Add Favourite");
		mAddFavMenuItem.setMnemonic('a');
		
		mAddFavMenuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				String curPath = ServiceFactory.getMainWindowController().getFocusedAdrField().getText();
				String topDir = PJCUtils.getTopDirOfPath(curPath);
				String name = JOptionPane.showInputDialog("Add " + curPath
						+ " to favourites", topDir);
				ServiceFactory.getXMLSettingsService().addFavourite(new FavouriteBean(name, curPath));
				loadFavourites();
			}
		});
	}

	/**
	 * Loads the favourites menu.
	 * 
	 */
	private void loadFavourites() {
		
		mAddFavMenuItem.setBackground(Settings.DEFAULT_BG_COLOR);
//		editFavMenuItem.setBackground(Settings.DEFAULT_BG_COLOR);
//		favSeparator.setBackground(Settings.DEFAULT_BG_COLOR);
		
		// clean first
		removeAll();
		add(mAddFavMenuItem);
//		add(editFavMenuItem);
		add(new JSeparator());

		Collection<FavouriteBean> favs = ServiceFactory.getXMLSettingsService().getFavourites();
		int nn = 1;
		for (FavouriteBean fav : favs) {
			Action action = new JCMenuItemAction(
					JCMenuItemAction.ACTION_OPEN_DIRECTORY);
			action.putValue(JCMenuItemAction.PARAM_PATH, fav.getPath());
			JMenuItem item = new JMenuItem(action);
			item.setBackground(Settings.DEFAULT_BG_COLOR);
			item.setText(nn + ".   " + fav.getName());
			item.setIcon(IconFactory.getIconByName("folder_favorites.png"));
			item.setMnemonic(String.valueOf(nn).charAt(0));
			add(item);
			nn++;
		}

	}
}


/*
 * $Log$
 */