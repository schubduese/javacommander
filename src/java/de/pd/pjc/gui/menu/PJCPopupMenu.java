package de.pd.pjc.gui.menu;

import java.util.Collection;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.springframework.util.StringUtils;

import de.pd.pjc.action.JCMenuItemAction;
import de.pd.pjc.bean.FileElement;
import de.pd.pjc.bean.MimeApplication;
import de.pd.pjc.bean.MimeSettingsBean;
import de.pd.pjc.bean.SessionBean;
import de.pd.pjc.service.ServiceFactory;
import de.pd.pjc.service.impl.FileServiceImpl;
import de.pd.pjc.util.IconFactory;

public class PJCPopupMenu extends JPopupMenu {

	private static PJCPopupMenu mMe;

	public PJCPopupMenu(FileElement[] pElements) {
		init(pElements);
		// loadItems(pElement, pWrapper);
	}

	/**
	 * @param pElement
	 * @param pWrapper
	 * @return
	 */
	public static PJCPopupMenu getInstance(FileElement[] pElements) {
		if (mMe == null) {
			mMe = new PJCPopupMenu(pElements);
		}
		return mMe;
	}

	/**
	 * @param pElement
	 * @param pWrapper
	 */
	private void init(FileElement[] pElements) {

		/** Open item */
		JCMenuItemAction openItemAction = new JCMenuItemAction(
				JCMenuItemAction.ACTION_EXECUTE_ELEMENT);
		openItemAction.putValue(JCMenuItemAction.PARAM_FILE_ELEMENT, pElements);
		
		JMenuItem openItem = new JMenuItem(openItemAction);
		openItem.setText("Open");
		add(openItem);
		
		if(pElements.length == 1 && pElements[0].isDirectory()) {
			JCMenuItemAction openTabItemAction = new JCMenuItemAction(
					JCMenuItemAction.ACTION_EXECUTE_ELEMENT);
			openTabItemAction.putValue(JCMenuItemAction.PARAM_FILE_ELEMENT, pElements);
			openTabItemAction.putValue(JCMenuItemAction.PARAM_OPEN_IN_TAB, Boolean.TRUE);
			JMenuItem openInTabItem = new JMenuItem(openTabItemAction);
			openInTabItem.setText("Open in tab");
			add(openInTabItem);
		}
		/** ====================================== */
		
		addSeparator();
		/** Open item */
		JCMenuItemAction renameAction = new JCMenuItemAction(
				JCMenuItemAction.ACTION_RENAME);
		renameAction.putValue(JCMenuItemAction.PARAM_FILE_ELEMENT, pElements);
		
		JMenuItem renameItem = new JMenuItem(renameAction);
		renameItem.setText("Rename");
		add(renameItem);
		/** ====================================== */
		
		addSeparator();
		
		
		/** open console here */
		JCMenuItemAction openConsoleAction = new JCMenuItemAction(
				JCMenuItemAction.ACTION_OPEN_CONSOLE);
		JMenuItem openConsole = new JMenuItem(openConsoleAction);
		openConsole.setText("Open Console here");
		openConsole.setIcon(IconFactory.getAppIconByName("konsole.png"));
		add(openConsole);
		/** ====================================== */

		if(allElementsSameExtension(pElements)) {
			/** Open with item */
			JCMenuItemAction openWithOtherItemAction = new JCMenuItemAction(
					JCMenuItemAction.ACTION_OPEN_WITH);
			openWithOtherItemAction.putValue(JCMenuItemAction.PARAM_FILE_ELEMENT, pElements);
			
			JMenu openWithMenu = new JMenu("Open With...");
			
			String fileExtension = null;
			if (!pElements[0].isDirectory()) {
				fileExtension = StringUtils.getFilenameExtension(pElements[0].getFileName());
			} else {
				fileExtension = "DIRECTORY";
			}
			
			MimeSettingsBean mimeSettings = ServiceFactory.getXMLSettingsService().getMimeSettings(fileExtension);
			Collection<MimeApplication> apps = mimeSettings.getApps();
			fillMenuItems(pElements, openWithMenu, apps);
			openWithMenu.addSeparator();
			JMenuItem openWithOtherItem = new JMenuItem(openWithOtherItemAction);
			openWithOtherItem.setText("Other");
			openWithMenu.add(openWithOtherItem);
			add(openWithMenu);
			/** ====================================== */
			
			// thumbview for image files
			if(FileServiceImpl.isImage(pElements[0].getFileName())) {
				JCMenuItemAction thumbDialogAction = new JCMenuItemAction(JCMenuItemAction.ACTION_OPEN_THUMBVIEW);
				thumbDialogAction.putValue(JCMenuItemAction.PARAM_FILE_ELEMENT, pElements);
				JMenuItem thumbDialog = new JMenuItem(thumbDialogAction);
				thumbDialog.setText("Open Thumbview");
				add(thumbDialog);
			}
			/** ====================================== */
		}
		
		addSeparator();
		
		/** copy action */
		JCMenuItemAction copyAction = new JCMenuItemAction(JCMenuItemAction.ACTION_COPY);
		copyAction.putValue(JCMenuItemAction.PARAM_FILE_ELEMENT, pElements);
		copyAction.putValue(JCMenuItemAction.PARAM_COPY_CLIPBOARD_ONYL, Boolean.TRUE);
		JMenuItem copyItem = new JMenuItem(copyAction);
		copyItem.setText("Copy");
		copyItem.setIcon(IconFactory.getIconByName("editcopy_small.png"));
		copyItem.setMnemonic('c');
		if(pElements.length > 0 && !pElements[0].isTopElement()) {
			add(copyItem);
		}
		
		/** paste action */
		JCMenuItemAction pasteAction = new JCMenuItemAction(JCMenuItemAction.ACTION_PASTE);
		String destDir = null;
		if(pElements[0].isTopElement()) {
			destDir = ServiceFactory.getMainWindowController().getCurrentPath();
		} else if(pElements[0].isDirectory()){
			destDir = pElements[0].getAbsolutePath();
		} else {
			destDir = pElements[0].getParentPath();
		}
		pasteAction.putValue(JCMenuItemAction.PARAM_DEST_PATH, destDir);
		JMenuItem pasteItem = new JMenuItem(pasteAction);
		pasteItem.setText("Paste");
		pasteItem.setIcon(IconFactory.getIconByName("paste.png"));
		pasteItem.setMnemonic('v');
		pasteItem.setEnabled(SessionBean.clipboardEntry != null);
		add(pasteItem);
		
		/** delete action */
		JCMenuItemAction deleteAction = new JCMenuItemAction(
				JCMenuItemAction.ACTION_DELETE);
		deleteAction.putValue(JCMenuItemAction.PARAM_FILE_ELEMENT, pElements);
		JMenuItem delete = new JMenuItem(deleteAction);
		delete.setText("Delete");
		delete.setIcon(IconFactory.getIconByName("trashcan_full.png"));
		delete.setMnemonic('d');
		if(pElements.length > 0 && !pElements[0].isTopElement()) {
			add(delete);
		}
		/** ====================================== */
		
		addSeparator();
		
		/** load the default apps */
		MimeSettingsBean defaultApps = ServiceFactory.getXMLSettingsService().getMimeSettings("DEFAULTS");
		JMenu defaultAppsMenu = new JMenu();
		fillMenuItems(pElements, defaultAppsMenu, defaultApps.getApps());
		for(int ii = 0; ii < defaultAppsMenu.getItemCount(); ii++) {
			JMenuItem item = defaultAppsMenu.getItem(ii);
			item.setText("Open with " + item.getText());
			add(item);
		}
		/** ====================================== */
		
		addSeparator();
		
		
		/** show element properties */
		JCMenuItemAction propertiesAction = new JCMenuItemAction(
				JCMenuItemAction.ACTION_PROPERTIES);
		propertiesAction.putValue(JCMenuItemAction.PARAM_FILE_ELEMENT, pElements);
		JMenuItem properties = new JMenuItem(propertiesAction);
		properties.setText("Properties");
		properties.setIcon(IconFactory.getIconByName("konqsidebar_metainfo.png"));
		properties.setMnemonic('p');
		if(pElements.length > 0 || !pElements[0].isTopElement()) {
			add(properties);
		}
	}
	
	/** Return true, if all elements end with the same extension.
	 * @param pFileElements
	 * @return
	 */
	private boolean allElementsSameExtension(FileElement[] pFileElements) {
		String last = null;
		for (FileElement element : pFileElements) {
			String ext = StringUtils.getFilenameExtension(element.getFileName());
			if(ext == null) ext = "";
			if(last != null && !last.equalsIgnoreCase(ext)) {
				return false;
			}
			last = ext;
		}
		return true;
	}

	/**
	 * @param pElements
	 * @param openWithMenu
	 * @param apps
	 * @param pWrapper
	 */
	public static void fillMenuItems(FileElement[] pElements, 
			JComponent openWithMenu, Collection<MimeApplication> apps) {
		for (MimeApplication app : apps) {
			JCMenuItemAction execAction = new JCMenuItemAction(JCMenuItemAction.ACTION_EXECUTE_FILE_WITH);
			execAction.putValue(JCMenuItemAction.PARAM_FILE_ELEMENT, pElements);
			execAction.putValue(JCMenuItemAction.PARAM_COMMAND, app.getCommand());
			execAction.putValue(JCMenuItemAction.PARAM_COMMAND_ARGS, app.getArgs());
			JMenuItem item = new JMenuItem(execAction);
			String icon = app.getIcon();
			if(icon == null) {
				icon = app.getName().toLowerCase() + ".png";
			}
			item.setIcon(IconFactory.getAppIconByName(icon));
			item.setText(app.getName());
			openWithMenu.add(item);
		}
	}

}
