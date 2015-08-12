package de.pd.pjc.action;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import de.pd.pjc.Constants;
import de.pd.pjc.bean.FileElement;
import de.pd.pjc.bean.MimeApplication;
import de.pd.pjc.bean.MimeSettingsBean;
import de.pd.pjc.bean.SessionBean;
import de.pd.pjc.gui.MainWindow;
import de.pd.pjc.gui.dialog.ActionProgressDialog;
import de.pd.pjc.gui.dialog.FilePropertiesDialog;
import de.pd.pjc.gui.dialog.JCThumbDialog;
import de.pd.pjc.gui.dialog.OpenWithDialog;
import de.pd.pjc.gui.menu.PJCPopupMenu;
import de.pd.pjc.gui.table.FileTable;
import de.pd.pjc.service.FileService;
import de.pd.pjc.service.MainWindowService;
import de.pd.pjc.service.ServiceFactory;
import de.pd.pjc.util.PJCUtils;

public class JCMenuItemAction implements Action {

	protected Log mLog = LogFactory.getLog(MainWindow.class.getName());

	/** actions */
	public final static int ACTION_OPEN_DIRECTORY = 0;
	public final static int ACTION_EXECUTE_ELEMENT = 1;
	public final static int ACTION_OPEN_WITH = 2;
	public final static int ACTION_EXECUTE_FILE_WITH = 3;
	public final static int ACTION_GO_DIR_BACK = 4;
	public final static int ACTION_GO_DIR_FORWARD = 5;
	public final static int ACTION_OPEN_CONSOLE = 6;
	public final static int ACTION_RELOAD = 7;
	public final static int ACTION_ICON_VIEW = 8;
	public final static int ACTION_DELETE = 9;
	public final static int ACTION_PROPERTIES = 10;
	public final static int ACTION_RENAME = 11;
	public final static int ACTION_COPY = 12;
	public final static int ACTION_MOVE = 13;
	public final static int ACTION_MAKE_DIR = 14;
	public final static int ACTION_PASTE = 15;
	public final static int ACTION_OPEN_THUMBVIEW = 16;
	public final static int ACTION_GO_HOME = 17;

	/** Params */
	public final static String PARAM_PATH = "path";
	public final static String PARAM_FILE_ELEMENT = "fileElement";
	public final static String PARAM_COMMAND = "command";
	public final static String PARAM_COMMAND_ARGS = "commandArgs";
	public final static String PARAM_OPEN_IN_TAB = "openInTab";
	public final static String PARAM_EVENT = "event";
	public final static String PARAM_CHECK_OPENWITH_EXTENSION = "checkOpenWithExt";
	public final static String PARAM_DEST_PATH = "destPath";
	public final static String PARAM_COPY_CLIPBOARD_ONYL = "clipboardOnly";

	/** memebers */

	private int mAction;

	private Map<String, Object> mParamMap = new HashMap<String, Object>();
	private MainWindowService mMainWindowService;

	public JCMenuItemAction(int pAction) {
		super();
		mAction = pAction;
		mMainWindowService = ServiceFactory.getMainWindowService();
	}

	public void addPropertyChangeListener(PropertyChangeListener pListener) {
		// TODO Auto-generated method stub

	}

	public Object getValue(String pKey) {
		return mParamMap.get(pKey);
	}

	public boolean isEnabled() {
		return true;
	}

	public void putValue(String pKey, Object pValue) {
		mParamMap.put(pKey, pValue);
	}

	public void removePropertyChangeListener(PropertyChangeListener pListener) {
		// TODO Auto-generated method stub

	}

	public void setEnabled(boolean pB) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent pE) {
		mLog.debug("Executed action " + mAction);
		FileElement[] elements = (FileElement[]) getValue(PARAM_FILE_ELEMENT);
		switch (mAction) {

		// open dir
		case ACTION_OPEN_DIRECTORY:
			openDiretory();
			break;

		// execute an element
		case ACTION_EXECUTE_ELEMENT:
			executeElement();
			break;

		// show the open with dialog....
		case ACTION_OPEN_WITH:
			execOpenWith();
			break;

		// open element with command...
		case ACTION_EXECUTE_FILE_WITH:
			executeFileWith();
			break;

		// go dir back
		case ACTION_GO_DIR_BACK:
			mMainWindowService.goBack();
			ServiceFactory.getMainWindowController().getCurrentTable().requestFocusInWindow();
			break;

		// go dir forward
		case ACTION_GO_DIR_FORWARD:
			mMainWindowService.goForward();
			ServiceFactory.getMainWindowController().getCurrentTable().requestFocusInWindow();
			break;

		case ACTION_OPEN_CONSOLE:
			mMainWindowService.openConsoleInCurrentDir();
			break;

		case ACTION_RELOAD:
			mMainWindowService.reloadTables();
			break;

		case ACTION_ICON_VIEW:
//			mMainWindowService.showIconView();
			break;

		case ACTION_DELETE:
			delete();
			break;

		case ACTION_PROPERTIES:
			new FilePropertiesDialog(new JFrame(), false, elements).setVisible(true);
			break;

		case ACTION_RENAME:
			rename();
			break;

		case ACTION_COPY:
			Boolean clipboardOnly = (Boolean) getValue(PARAM_COPY_CLIPBOARD_ONYL);
			if(clipboardOnly != null && clipboardOnly) {
				PJCUtils.copyToClipboard(elements);
			} else {
				copyAction(elements);
			}
			break;
			
		case ACTION_PASTE:
			JCMenuItemAction copyAction = new JCMenuItemAction(JCMenuItemAction.ACTION_COPY);
			copyAction.putValue(JCMenuItemAction.PARAM_FILE_ELEMENT, SessionBean.clipboardEntry);
			copyAction.putValue(JCMenuItemAction.PARAM_DEST_PATH, getValue(PARAM_DEST_PATH));
			copyAction.actionPerformed(null);
			break;
			
		case ACTION_MOVE:
			moveAction(elements);
			break;
			
		case ACTION_MAKE_DIR:
			mkdirAction();
			break;
			
		case ACTION_OPEN_THUMBVIEW:
			new JCThumbDialog(elements[0]).setVisible(true);
			break;

		case ACTION_GO_HOME:
			ServiceFactory.getMainWindowService().loadFileList(Constants.USER_HOME);
			break;
			
		default:
			break;
		}

	}

	/** Make a new directory.
	 * 
	 */
	private void mkdirAction() {
		String newName = JOptionPane.showInputDialog("Make Directory");
 		if (!StringUtils.hasLength(newName))
 			return;
 		String dir = ServiceFactory.getMainWindowController().getCurrentPath() + File.separator + newName;
		ServiceFactory.getFileService().makeDirectory(dir);
		ServiceFactory.getMainWindowService().reloadTables();
	}

	private void moveAction(FileElement[] elements) {
		mLog.debug("moving");
		ActionProgressDialog dialog = new ActionProgressDialog(new JFrame(), false);
		MoveAction ac = new MoveAction(dialog);
		ac.setFileService(ServiceFactory.getFileService());
		ac.setElementsToMove(elements);
		String destPath = (String) getValue(PARAM_DEST_PATH);
		ac.setDestinationPath(destPath);
		
		Thread thread = new Thread(ac);
		thread.start();
	}

	/** Copies the selected file elements.opy
	 * @param elements
	 */
	private void copyAction(FileElement[] elements) {
		ActionProgressDialog dialog = new ActionProgressDialog(new JFrame(), false);
		CopyAction ac = new CopyAction(dialog);
		ac.setFileService(ServiceFactory.getFileService());
		ac.setElementsToCopy(elements);
		String destPath = (String) getValue(PARAM_DEST_PATH);
		if(!StringUtils.hasText(destPath)) {
			mLog.error("Dest path must be set: PARAM_DEST_PATH");
			return;
		}
		ac.setDestinationPath(destPath);
		
		Thread thread = new Thread(ac);
		thread.start();
	}

	/** Shows the open with dialog.
	 * 
	 */
	private void execOpenWith() {
		FileElement[] elements = (FileElement[]) getValue(PARAM_FILE_ELEMENT);
		Boolean checkExtension = (Boolean) getValue(PARAM_CHECK_OPENWITH_EXTENSION);

		String extension = "";

		if (elements.length == 1) {
			extension = StringUtils
					.getFilenameExtension(elements[0].getFileName());
		}
		MimeSettingsBean mimeSettings = ServiceFactory.getXMLSettingsService()
				.getMimeSettings(extension);
		Collection<MimeApplication> apps = mimeSettings.getApps();

		if (checkExtension == null && !apps.isEmpty()) {
			// create the menu
			JPopupMenu popup = new JPopupMenu();
			popup.add(new JLabel("Open with"));
			popup.addSeparator();
			PJCPopupMenu.fillMenuItems(elements, popup, apps);
			
			// calculate the popup position
			int selectedRow = ServiceFactory.getMainWindowController().getCurrentTable()
			.getSelectedRow();
			// TODO: find out how the hell to find the fucked up y position of the window!!!
			final int currY = ServiceFactory.getMainWindowController().getMainWindow().getBounds().y;
			int y = 30 + (22 * selectedRow) - 12;
			if(y >= currY) {
				y = currY - popup.getHeight();
			}
				System.out.println("Y " + y + " sel row " + selectedRow + " currY " + currY);
			int x = 0;

			JCMenuItemAction openWithOtherItemAction = new JCMenuItemAction(JCMenuItemAction.ACTION_OPEN_WITH);
			openWithOtherItemAction.putValue(JCMenuItemAction.PARAM_FILE_ELEMENT,
					elements);
			openWithOtherItemAction.putValue(
					JCMenuItemAction.PARAM_CHECK_OPENWITH_EXTENSION, Boolean.FALSE);
			JMenuItem openWithOtherItem = new JMenuItem(openWithOtherItemAction);
			openWithOtherItem.setText("Other");
			popup.addSeparator();
			popup.add(openWithOtherItem);

			popup.show(ServiceFactory.getMainWindowController().getCurrentPanel(), x, y);
			return;
		}

		OpenWithDialog dia = new OpenWithDialog(new JFrame(), true, extension);
		dia.setVisible(true);
		String cmd = dia.getCommand();
		if (!StringUtils.hasLength(cmd))
			return;
		quickOpenWith(cmd);
	}

	private void rename() {
		FileElement[] elements = (FileElement[]) getValue(PARAM_FILE_ELEMENT);
		if (elements.length != 1 || elements[0].isTopElement()) {
			return;
		}
		String newName = JOptionPane.showInputDialog("Rename", elements[0]
				.getFileName());
		if (!StringUtils.hasLength(newName))
			return;
		ServiceFactory.getFileService().rename(elements[0].getParentPath(),
				elements[0].getFileName(), newName);
		int selectedRow = ServiceFactory.getMainWindowController().getCurrentTable()
				.getSelectedRow();
		mMainWindowService.reloadTables();
		ServiceFactory.getMainWindowController().getCurrentTable()
				.setCurrentIndex(selectedRow);
		mMainWindowService.updateFileInfoForTableSelection();
	}

	/**
	 * @param cmd
	 */
	private void quickOpenWith(String cmd) {
		FileElement[] elements = (FileElement[]) getValue(PARAM_FILE_ELEMENT);
		FileElement firstElement = elements[0];
		String command = cmd;
		String args = null;
		int firstIndex = cmd.indexOf(" ");
		if (firstIndex != -1) {
			command = cmd.substring(0, firstIndex);
			args = cmd.substring(firstIndex);
		}
		mMainWindowService.executeFile(firstElement, command, args,
				firstElement.getParentPath());
	}

	/** deletes files
	 * 
	 */
	private void delete() {
		FileElement[] elements = (FileElement[]) getValue(PARAM_FILE_ELEMENT);
		if(elements == null || elements.length < 1 || (elements.length == 1 &&elements[0].isTopElement())) return;
		StringBuffer files = new StringBuffer();
		
		if(elements.length > 5) {
			files.append(elements.length + " Files");
		} else {
			for (FileElement element : elements) {
				if(!element.isTopElement()) {
					files.append(element.getFileName() + "\n");
				}
			}
		}
		int selectedRow = ServiceFactory.getMainWindowController().getCurrentTable()
				.getSelectedRow();
		int confirm = JOptionPane.showConfirmDialog(new JFrame(),
				"Do you really want to delete this files?\n" + files.toString(),
				"Are you sure?", JOptionPane.YES_NO_OPTION);
		if (confirm != JOptionPane.YES_OPTION) {
			return;
		}
		FileService fileService = ServiceFactory.getFileService();
		for (FileElement element : elements) {
			if (!element.isTopElement()) {
				fileService.delete(element.getAbsolutePath());
			}
		}
		mMainWindowService.reloadTables();
		ServiceFactory.getMainWindowController().getCurrentTable().setCurrentIndex(
				selectedRow - 1);
	}

	/**
	 * 
	 */
	private void executeFileWith() {
		FileElement[] elements2 = (FileElement[]) getValue(PARAM_FILE_ELEMENT);
		String cmd = (String) getValue(PARAM_COMMAND);
		String args = (String) getValue(PARAM_COMMAND_ARGS);
		if (cmd == null) {
			mLog.error("Param " + PARAM_COMMAND + " must be set");
		}
		for (FileElement fileElement : elements2) {
			mMainWindowService.executeFile(fileElement, cmd, args,
					fileElement.getParentPath());
		}
	}

	/**
	 * 
	 */
	private void executeElement() {
		FileElement[] element = (FileElement[]) getValue(PARAM_FILE_ELEMENT);
		Boolean openInTab = (Boolean) getValue(PARAM_OPEN_IN_TAB);
		if (element == null) {
			mLog.error("Param " + PARAM_FILE_ELEMENT
					+ " must be set for this action");
		}
		if (openInTab != null && openInTab.booleanValue()) {
			FileTable focusedTable = ServiceFactory.getMainWindowController()
					.getCurrentTable();
			JTabbedPane focusedTabPane = ServiceFactory.getMainWindowController()
					.getFocusedTabPane();
			mMainWindowService.addTabToPane(focusedTabPane, focusedTable.getName(),
					element[0].getAbsolutePath());
			mMainWindowService.updatePathLabels(ServiceFactory.getMainWindowController()
					.getFocusedAdrField(), focusedTabPane,
					ServiceFactory.getMainWindowController().getCurrentPath());
		} else {
			mMainWindowService.execute(element[0], null);

		}
	}

	/**
	 * 
	 */
	private void openDiretory() {
		String path = (String) getValue(PARAM_PATH);
		if (path == null) {
			mLog.error("Param " + PARAM_PATH + " must be set for this action");
		}
		mMainWindowService.loadFileList(path);
		ServiceFactory.getMainWindowController().getCurrentTable().requestFocusInWindow();
	}

}
