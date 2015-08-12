/**
 * PJavaCommander by Petros Dolaschjan
 *
 */
package de.pd.pjc.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import de.pd.pjc.Settings;
import de.pd.pjc.action.JCMenuItemAction;
import de.pd.pjc.bean.SessionBean;
import de.pd.pjc.gui.dialog.FileSearchDialog;
import de.pd.pjc.gui.dialog.ManageMimeSettingsDialog;
import de.pd.pjc.service.ServiceFactory;
import de.pd.pjc.util.IconFactory;

/**
 * @author petros
 *
 */
public class JCMenuBar extends JMenuBar {

	private JMenu mFileMenu = new JMenu();
	private JMenu mOptionsMenu = new JMenu();

	public JCMenuBar() {
		super();

		initFileMenu();
		add(new JCFavMenu());
		initOptionsMenu();
	}

	/** Initialize the file menu.
	 * 
	 */
	private void initFileMenu() {
		add(mFileMenu);
		mFileMenu.setMnemonic('f');
		mFileMenu.setText("File");

		// new dir
		JMenuItem newDir = new JMenuItem();
		newDir.setText("Make Directory");
		newDir.setBackground(Settings.DEFAULT_BG_COLOR);
		newDir.setIcon(IconFactory.getIconByName("folder3.png"));
		newDir.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent pE) {
				JCMenuItemAction mkdirAction = new JCMenuItemAction(
						JCMenuItemAction.ACTION_MAKE_DIR);
				mkdirAction.putValue(JCMenuItemAction.PARAM_FILE_ELEMENT,
						ServiceFactory.getMainWindowService()
								.getSelectedFileElements());
				mkdirAction.actionPerformed(null);
			}

		});

		mFileMenu.add(newDir, 0);
		mFileMenu.addSeparator();

		// search
		JMenuItem searchItem = new JMenuItem();
		searchItem.setText("Search Files");
		searchItem.setIcon(IconFactory.getAppIconByName("kappfinder.png"));
		searchItem.setBackground(Settings.DEFAULT_BG_COLOR);

		searchItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent pE) {
				new FileSearchDialog(ServiceFactory.getMainWindowController().getCurrentPath())
						.setVisible(true);
			}
		});
		
		mFileMenu.add(searchItem, 1);
		
		// exit
		JMenuItem exitItem = new JMenuItem();
		exitItem.setText("Quit");
		exitItem.setMnemonic('Q');
		exitItem.setIcon(IconFactory.getAppIconByName("xapp.png"));
		exitItem.setBackground(Settings.DEFAULT_BG_COLOR);

		exitItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent pE) {
				System.exit(0);
			}
		});
		
		mFileMenu.add(exitItem);
	}

	private void initOptionsMenu() {
		add(mOptionsMenu);
		mOptionsMenu.setText("Options");
		mOptionsMenu.setMnemonic('o');
		
		JMenuItem showHiddenFiles = new JCheckBoxMenuItem();
		showHiddenFiles.setBackground(Settings.DEFAULT_BG_COLOR);
		showHiddenFiles.setText("Show hidden files");
		showHiddenFiles.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent pE) {
				SessionBean.toggleShowHiddenFiles();
				ServiceFactory.getMainWindowService().reloadTables();
			}

		});

		mOptionsMenu.add(showHiddenFiles, 0);
		
		JMenuItem calculateDirs = new JCheckBoxMenuItem();
		calculateDirs.setBackground(Settings.DEFAULT_BG_COLOR);
		calculateDirs.setText("Calculate dir size");
		calculateDirs.setIcon(IconFactory.getAppIconByName("kcalc.png"));
		calculateDirs.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent pE) {
				SessionBean.toggleCoundDirSizes();
				ServiceFactory.getMainWindowService().reloadTables();
			}

		});

		mOptionsMenu.add(calculateDirs);
		
		// manage mime types
		JMenuItem mimeSettings = new JMenuItem();
		mimeSettings.setText("Edit Filetypes");
		mimeSettings.setIcon(IconFactory.getAppIconByName("filetypes.png"));
		mimeSettings.setBackground(Settings.DEFAULT_BG_COLOR);

		mimeSettings.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent pE) {
				ManageMimeSettingsDialog dialog = new ManageMimeSettingsDialog(ServiceFactory.getMainWindowController().getMainWindow(), true);
				dialog.setVisible(true);
			}
		});
		
		mOptionsMenu.add(mimeSettings);
	}
}

/*
 * $Log$
 */