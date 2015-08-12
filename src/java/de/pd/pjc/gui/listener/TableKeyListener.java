package de.pd.pjc.gui.listener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import de.pd.pjc.Settings;
import de.pd.pjc.action.JCMenuItemAction;
import de.pd.pjc.bean.DirSettingsBean;
import de.pd.pjc.bean.FileElement;
import de.pd.pjc.bean.SessionBean;
import de.pd.pjc.gui.BasePanelController;
import de.pd.pjc.gui.FileTablePanel;
import de.pd.pjc.gui.MainWindowWrapper;
import de.pd.pjc.gui.dialog.FileSearchDialog;
import de.pd.pjc.gui.table.FileTable;
import de.pd.pjc.service.MainWindowService;
import de.pd.pjc.service.ServiceFactory;
import de.pd.pjc.util.PJCUtils;

public class TableKeyListener extends KeyAdapter {

	private long mWhenClick;
	
	private String mFindName;
	
	protected static Log mLog = LogFactory.getLog(TableKeyListener.class.getName());
	
	MainWindowService mMainWindowService;
	BasePanelController mMainWindowController;

	public TableKeyListener() {
		mMainWindowService = ServiceFactory.getMainWindowService();
		mMainWindowController = ServiceFactory.getMainWindowController();
	}
	
	@Override
	public void keyPressed(KeyEvent evt) {
		boolean altDown = evt.isAltDown();
		boolean ctrlDown = evt.isControlDown();
		FileTable table = (FileTable) evt.getSource();

		// ==============================================
		// enter
		// ==============================================		
		if (evt.getKeyCode() == KeyEvent.VK_ENTER && !altDown) {
			mMainWindowService.execute(table);
		// ==============================================
			
			
		// ==============================================
		// rename
		// ==============================================
		} else if (evt.getKeyCode() == KeyEvent.VK_F2) {
			JCMenuItemAction renameAction = new JCMenuItemAction(JCMenuItemAction.ACTION_RENAME);
			renameAction.putValue(JCMenuItemAction.PARAM_FILE_ELEMENT, table.getSelectedFileElements());
			renameAction.actionPerformed(null);
		// ==============================================
			
		// ==============================================
		// copy F5
		// ==============================================
		} else if (evt.getKeyCode() == KeyEvent.VK_F5) {
			table.repaint();
			JCMenuItemAction copyAction = new JCMenuItemAction(JCMenuItemAction.ACTION_COPY);
			String oppositPath = mMainWindowController.getOppositPath();
			copyAction.putValue(JCMenuItemAction.PARAM_FILE_ELEMENT, table.getSelectedFileElements());
			copyAction.putValue(JCMenuItemAction.PARAM_DEST_PATH, oppositPath);
			copyAction.actionPerformed(null);
		// 
			

		// ==============================================
		// move F7
		// ==============================================
		} else if (evt.getKeyCode() == KeyEvent.VK_F7) {
			table.repaint();
			JCMenuItemAction moveAction = new JCMenuItemAction(JCMenuItemAction.ACTION_MOVE);
			String oppositPath = mMainWindowController.getOppositPath();
			moveAction.putValue(JCMenuItemAction.PARAM_FILE_ELEMENT, table.getSelectedFileElements());
			moveAction.putValue(JCMenuItemAction.PARAM_DEST_PATH, oppositPath);
			moveAction.actionPerformed(null);
		// 
			
			
		// ==============================================
		// delete
		// ==============================================
		} else if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
			mMainWindowController.getCurrentTable().repaint();
			JCMenuItemAction deleteAction = new JCMenuItemAction(JCMenuItemAction.ACTION_DELETE);
			deleteAction.putValue(JCMenuItemAction.PARAM_FILE_ELEMENT, table.getSelectedFileElements());
			deleteAction.actionPerformed(null);
		// 
			
			
		// ==============================================
		// alt + enter file properties
		// ==============================================
		} else if (evt.getKeyCode() == KeyEvent.VK_ENTER && altDown) {
			JCMenuItemAction propertiesAction = new JCMenuItemAction(JCMenuItemAction.ACTION_PROPERTIES);
			FileElement[] selectedFileElements = table.getSelectedFileElements();
			propertiesAction.putValue(JCMenuItemAction.PARAM_FILE_ELEMENT, selectedFileElements);
			propertiesAction.actionPerformed(null);
		// ==============================================
			
		} else if (evt.getKeyCode() == KeyEvent.VK_F4) {
			mMainWindowService.openConsoleInCurrentDir();
		} else if (evt.getKeyCode() == KeyEvent.VK_J && ctrlDown) {
			JTextField filterField = mMainWindowController.getFocusedFilterField();
			filterField.requestFocusInWindow();
			filterField.selectAll();
		// ==============================================
		// icons bigger
		// ==============================================
		} else if (evt.getKeyCode() == KeyEvent.VK_PLUS && ctrlDown) {
			table.setRowHeight(Settings.TABLE_ROW_HEIGHT_BIG);
			if(table.getName().equals(FileTablePanel.LEFT_FILE_TABLE) || table.getName().equals(FileTablePanel.RIGHT_FILE_TABLE)) {
				storeIconSize(Settings.TABLE_ROW_HEIGHT_BIG);
			}
			
		// ==============================================

		// ==============================================
		// open the search dialog.
		// ==============================================
		} else if (evt.getKeyCode() == KeyEvent.VK_F3) {
			new FileSearchDialog(ServiceFactory.getMainWindowController().getCurrentPath())
			.setVisible(true);
		// ==============================================
				
			
		// ==============================================
		// print debug messages
		// ==============================================
		} else if (evt.getKeyCode() == KeyEvent.VK_D && ctrlDown) {
			mMainWindowController.printStatus();
		// ==============================================
			
		// ==============================================
		// icons smaller
		// ==============================================
		} else if (evt.getKeyCode() == KeyEvent.VK_MINUS && ctrlDown) {
			table.setRowHeight(Settings.TABLE_ROW_HEIGHT_NORMAL);
			storeIconSize(Settings.TABLE_ROW_HEIGHT_NORMAL);
		// ==============================================
			
		// ==============================================
		// ctrl + f select pattern
		// ==============================================
		} else if (evt.getKeyCode() == KeyEvent.VK_F && ctrlDown) {
			String pattern = JOptionPane.showInputDialog("Select Pattern", ".");
			if (!StringUtils.hasLength(pattern))
				return;
			mMainWindowController.getCurrentTable().movePositionByPattern(pattern);
			mMainWindowService.updateFileInfoForTableSelection();
			
		// ==============================================
			
		// ==============================================
		// ctrl + m toggle media mode
		// ==============================================
		} else if (evt.getKeyCode() == KeyEvent.VK_M && ctrlDown) {
			if(SessionBean.mediaMode) {
				mMainWindowService.hideMediaMode();
				SessionBean.mediaMode = false;
			} else {
				mMainWindowService.showMediaMode();
				SessionBean.mediaMode = true;
			}
			
		// ==============================================
			
		// ==============================================
		// ctrl + o quick open with
		// ==============================================
		} else if (evt.getKeyCode() == KeyEvent.VK_O && ctrlDown) {
			JCMenuItemAction openWithOtherItemAction = new JCMenuItemAction(JCMenuItemAction.ACTION_OPEN_WITH);
			openWithOtherItemAction.putValue(JCMenuItemAction.PARAM_FILE_ELEMENT, table.getSelectedFileElements());
			openWithOtherItemAction.putValue(JCMenuItemAction.PARAM_EVENT, evt);
			openWithOtherItemAction.actionPerformed(null);
			
		// ==============================================

		// ==============================================
		// ctrl + h show history popup
		// ==============================================
		} else if (evt.getKeyCode() == KeyEvent.VK_H && ctrlDown) {
			mMainWindowService.showHistoryPopup();
			
		// ==============================================

		// ==============================================
		// alt + down show history popup
		// ==============================================
		} else if (evt.getKeyCode() == KeyEvent.VK_DOWN && altDown) {
			mMainWindowService.showPathTree();
		// ==============================================
			
			
		// ==============================================
		// ctrl + t open tab
		// ==============================================
		} else if (evt.getKeyCode() == KeyEvent.VK_T && ctrlDown) {
			JTabbedPane focusedTabPane = mMainWindowController.getFocusedTabPane();
			mMainWindowController.getAdrFieldByTableName(table.getName());
			mMainWindowService.addTabToPane(focusedTabPane, table.getName());
			
			mMainWindowController.refocusByTableName(table.getName());
		// ==============================================
			
		// ==============================================
		// ctrl + page up/down
		// ==============================================
		} else if ((evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN || (evt.getKeyCode() == KeyEvent.VK_PAGE_UP)) && ctrlDown) {
			JTabbedPane tabPane = mMainWindowController.getFocusedTabPane();
			int idx = 0;
			if(evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
				idx = tabPane.getSelectedIndex() - 1;
				if(tabPane.getSelectedIndex() == 0) idx = tabPane.getTabCount() - 1;
				tabPane.setSelectedIndex(idx);
			} else {
				idx = tabPane.getSelectedIndex() + 1;
				if(tabPane.getSelectedIndex() == tabPane.getTabCount() - 1) idx = 0;
				tabPane.setSelectedIndex(idx);
			}
			mMainWindowController.refocusByTableName(table.getName());
		// ==============================================
			
		// ==============================================
		// ctrl + w close current tab
		// ==============================================
		} else if ((evt.getKeyCode() == KeyEvent.VK_W && ctrlDown)) {
			
			JTabbedPane tabPane = mMainWindowController.getFocusedTabPane();
			if(tabPane.getTabCount() == 1) return;
			tabPane.removeTabAt(tabPane.getSelectedIndex());
			tabPane.updateUI();
			
//			table.requestFocusInWindow(); // FIXME: focus does not work, after tab has been removed!!!
//			mMainWindowService.reloadTables();
			
			
		// ==============================================
			
		// ==============================================
		// ctrl + e execute command
		// ==============================================
		} else if (evt.getKeyCode() == KeyEvent.VK_E && ctrlDown) {
			String cmd = JOptionPane
					.showInputDialog("Execute command in current path\n# = Opposite path\n%1 selected file(s)");
			if (!StringUtils.hasLength(cmd))
				return;
			executeCommand(cmd, table);
			
		// ==============================================

		// ==============================================
		// ctrl + c copy to clibpoard
		// ==============================================
		} else if (evt.getKeyCode() == KeyEvent.VK_C && ctrlDown) {
			PJCUtils.copyToClipboard(table.getSelectedFileElements());
		// ==============================================
			
		// ==============================================
		// ctrl + v copy from clibpoard
		// ==============================================
		} else if (evt.getKeyCode() == KeyEvent.VK_V && ctrlDown) {
			table.repaint();
			JCMenuItemAction pasteAction = new JCMenuItemAction(JCMenuItemAction.ACTION_PASTE);
			pasteAction.putValue(JCMenuItemAction.PARAM_DEST_PATH, mMainWindowController.getCurrentPath());
			pasteAction.actionPerformed(null);
			mMainWindowService.reloadTables();
		// ==============================================
			
		// ==============================================
		// tab
		// ==============================================
		} else if (evt.getKeyCode() == KeyEvent.VK_TAB) {
			mMainWindowController.switchFocus();
			mMainWindowService.updateFileInfoForTableSelection();
			
		// ==============================================
		} else if (evt.getKeyCode() == KeyEvent.VK_HOME) {
			mMainWindowController.getCurrentTable().setCurrentIndex(0);
		} else if (evt.getKeyCode() == KeyEvent.VK_END) {
			mMainWindowController.getCurrentTable().setCurrentIndex(
					mMainWindowController.getCurrentTable().getRowCount() - 1);
		
		// ==============================================
		// f6 focus adr field
		// ==============================================
		} else if (evt.getKeyCode() == KeyEvent.VK_F6) {
			mMainWindowController.getFocusedAdrField().requestFocusInWindow();
			
		// ==============================================
			
		// ==============================================
		// context menu
		// ==============================================
		} else if (evt.getKeyCode() == KeyEvent.VK_LESS) {
			showContextMenu();
			
		// ==============================================
			
		// ==============================================
		// dir up
		// ==============================================
		} else if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			mMainWindowService.goBack();
			mMainWindowService.reloadTables();
		// ==============================================
		// back
		// ==============================================
		} else if (evt.getKeyCode() == KeyEvent.VK_LEFT && altDown) {
			mMainWindowService.goBack();
			mMainWindowService.reloadTables();

		// ==============================================
		// forward
		// ==============================================
		} else if (evt.getKeyCode() == KeyEvent.VK_RIGHT && altDown) {
			mMainWindowService.goForward();
			
		// ==============================================

		// ==============================================
		// rc mode = override search keys
		// ==============================================
		} else if (PJCUtils.isKeyboardSearchKeyPressed(evt) && SessionBean.rcMode) {			

			FileTable currentTable = mMainWindowController.getCurrentTable();
			int selectedRow = currentTable.getSelectedRow();
			//	override rc mode keys
			
			// 1 = key up
			if(evt.getKeyCode() == KeyEvent.VK_1) {
				currentTable.setCurrentIndex(selectedRow -1);
			// 2 = key down
			} else if(evt.getKeyCode() == KeyEvent.VK_2) {
				currentTable.setCurrentIndex(selectedRow +1);
			// 5 = enter
			} else if(evt.getKeyCode() == KeyEvent.VK_5) {
					PJCUtils.strokeKey(KeyEvent.VK_ENTER);
			// 4 = backspace
			} else if(evt.getKeyCode() == KeyEvent.VK_4) {
				PJCUtils.strokeKey(KeyEvent.VK_BACK_SPACE);
			} else if(evt.getKeyCode() == KeyEvent.VK_3) {
				if(currentTable.getName().equals(FileTablePanel.LEFT_FILE_TABLE)) {
					mMainWindowService.showLeftTableOnly();
				} else {
					mMainWindowService.showRightTableOnly();
				}
			}
			
		// ==============================================
		// search in list
		// ==============================================
		} else if (PJCUtils.isKeyboardSearchKeyPressed(evt)) {

			searchInList(evt);
			return;
		}
	}

	private void storeIconSize(int pSize) {
		DirSettingsBean dirSettings = new DirSettingsBean(mMainWindowController.getCurrentPath());
		dirSettings.setIconSize(String.valueOf(pSize));
		ServiceFactory.getXMLSettingsService().storeDirSettings(dirSettings);
	}

	private void searchInList(KeyEvent evt) {
		long whenClick = evt.getWhen();
		if (Math.abs(mWhenClick - whenClick) > 500)
			mFindName = "";
		mFindName += (char) evt.getKeyCode();
		mMainWindowController.getCurrentTable().movePositionByName(mFindName);
		mWhenClick = whenClick;
		mMainWindowService.updateFileInfoForTableSelection();
	}

	private void showContextMenu() {
		int selectedRow = mMainWindowController.getCurrentTable().getSelectedRow();
		int y = 30 + (22 * selectedRow) - 12;
		int x = 0;
		FileTable currentTable = mMainWindowController.getCurrentTable();
		MouseEvent me = new MouseEvent(currentTable, 0,
				0, 0, x, y, 1, true);
		mMainWindowService.showPopup(me, currentTable);
	}

	private void executeCommand(String cmd, FileTable pTable) {
		String command = cmd;
		String args = null;
		int firstIndex = cmd.indexOf(" ");
		if (firstIndex != -1) {
			command = cmd.substring(0, firstIndex);
			args = cmd.substring(firstIndex + 1);
			if (args.indexOf("#") != -1) {
				args = args.replaceAll("#", mMainWindowController.getOppositPath());
			}
		}
		try {
			FileElement[] elements = pTable.getSelectedFileElements();
			for (FileElement element : elements) {
				String file = null;
				String fileArgs = args;
				if(fileArgs == null) {
					file = element.getAbsolutePath();
				} else if(fileArgs.indexOf("%1") != -1) {
					fileArgs = args.replaceAll("%1", element.getAbsolutePath());
				}
				if(file != null && element.isTopElement()) continue;
				ServiceFactory.getFileService().executeCommand(command, file, fileArgs, mMainWindowController
						.getCurrentPath());
			}
			mMainWindowService.reloadTables();
		} catch (IOException e) {
			mLog.error("Could not execute command", e);
		}
	}

	@Override
	public void keyReleased(KeyEvent evt) {
		if (MainWindowWrapper.isNavKeyPressed(evt)) {
			mMainWindowService.updateFileInfoForTableSelection();
		}
	}
}
