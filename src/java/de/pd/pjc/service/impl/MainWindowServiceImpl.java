package de.pd.pjc.service.impl;

import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import de.pd.pjc.Constants;
import de.pd.pjc.FilePatternFilter;
import de.pd.pjc.Settings;
import de.pd.pjc.action.JCMenuItemAction;
import de.pd.pjc.bean.FileElement;
import de.pd.pjc.bean.FileSizeBean;
import de.pd.pjc.bean.SessionBean;
import de.pd.pjc.gui.BasePanelController;
import de.pd.pjc.gui.FileTablePanel;
import de.pd.pjc.gui.MainWindow;
import de.pd.pjc.gui.MediaPreviewPanel;
import de.pd.pjc.gui.dialog.JCImageDialog;
import de.pd.pjc.gui.menu.PJCPopupMenu;
import de.pd.pjc.gui.table.FileTable;
import de.pd.pjc.gui.table.FileTableModel;
import de.pd.pjc.service.FileService;
import de.pd.pjc.service.MainWindowService;
import de.pd.pjc.service.XMLSettingsService;
import de.pd.pjc.thread.CalculateDirSizesThread;
import de.pd.pjc.util.IconFactory;
import de.pd.pjc.util.PJCUtils;

/**
 * @author petros
 *
 */
public class MainWindowServiceImpl implements MainWindowService {

	final static Log mLog = LogFactory.getLog(MainWindowService.class.getName());
	private static ArrayList<String> mPathHistory = new ArrayList<String>();
	
	private FileService mFileService;
	private XMLSettingsService mXMLSettingsService;
	private BasePanelController mPanelController;
	
	/* (non-Javadoc)
	 * @see de.pd.pjc.service.MainWindowService#updateFileInfo(de.pd.pjc.bean.FileElement[])
	 */
	public void updateFileInfo(FileElement[] pFileElements) {

		FileTableModel focusedModel = mPanelController.getFocusedModel();
		if(focusedModel==null) return;
		String info = "Nothing selected";

		if (pFileElements.length == 1) {
			FileSizeBean fileSizeBean = PJCUtils
					.fileSizeHumanReadable(pFileElements[0].getFileSize());
			info = pFileElements[0].getFileName();
			if (!pFileElements[0].isDirectory()) {
				info += " (" + fileSizeBean.getFormatted() + ")";
			}
			if (pFileElements[0].isTopElement()) {
				FileElement[] allElements = focusedModel.getAllElementsArray();
				info = sumarizeFileElements(allElements);
			}
		} else if (pFileElements.length > 1) {
			info = sumarizeFileElements(pFileElements);
		}

		mPanelController.getFileInfoLabel().setText(info);
		
	}
	
	
	/* (non-Javadoc)
	 * @see de.pd.pjc.service.MainWindowService#updateFileInfoForTableSelection()
	 */
	public void updateFileInfoForTableSelection() {
		MainWindow.errorLabel.setText("");
		FileElement[] selectedFileElements = getSelectedFileElements();
		mPanelController.getToolBar().updateToolbar(selectedFileElements);
		updateFileInfo(selectedFileElements);
		if (SessionBean.mediaMode) {
			MediaPreviewPanel mediaPanel = mPanelController.getCurrentMediaPanel();
			// load image
			if (selectedFileElements.length == 1) {
				mediaPanel.updatePanel(selectedFileElements[0]);
			} else {
				mediaPanel.reset();
			}
		}
	}
	
	
	/* (non-Javadoc)
	 * @see de.pd.pjc.service.MainWindowService#getSelectedFileElements()
	 */
	public FileElement[] getSelectedFileElements() {
		return mPanelController.getCurrentTable().getSelectedFileElements();
	}
	
	/**
	 * Calculates the file size and file/dir amount.
	 * 
	 * @param pFileElements
	 * @return
	 */
	private String sumarizeFileElements(FileElement[] pFileElements) {
		StringBuffer info = new StringBuffer();

		long size = 0l;
		int files = 0;
		int dirs = 0;
		int all = 0;
		for (FileElement element : pFileElements) {
			if (element == null || element.isTopElement())
				continue;
			all++;
			if (element.isDirectory()) {
				dirs++;
			} else {
				files++;
				size += element.getFileSize();
			}

		}
		FileSizeBean fileSizeBean = PJCUtils.fileSizeHumanReadable(size);
		info.append(all + " Items");
		if (files > 0) {
			info.append(" - " + files + " Files (" + fileSizeBean + " Total)");
		}
		if (dirs > 0) {
			info.append(" - " + dirs + " Folders");
		}

		return info.toString();
	}
	

	/* (non-Javadoc)
	 * @see de.pd.pjc.service.MainWindowService#updatePathLabels(javax.swing.JTextField, javax.swing.JTabbedPane, java.lang.String)
	 */
	public void updatePathLabels(JTextField pAdrField, JTabbedPane tab, String currentPath) {
		pAdrField.setText(currentPath);
		String shortPath = mFileService.getTopDirectory(currentPath);
		if(shortPath.length() > 8) {
			shortPath = shortPath.substring(0, 7) + "...";
		}
		tab.setTitleAt(tab.getSelectedIndex(), shortPath);
		tab.setToolTipTextAt(tab.getSelectedIndex(), currentPath);
	}
	
	
	/* (non-Javadoc)
	 * @see de.pd.pjc.service.MainWindowService#addHistPath(java.lang.String)
	 */
	public void addHistPath(String pPath) {

		if (mPathHistory.contains(pPath)) {
			mPathHistory.remove(pPath);
		}
		mPathHistory.add(0, pPath);
		if (mPathHistory.size() > 19) {
			mPathHistory.remove(19);
		}
	}

	
	/* (non-Javadoc)
	 * @see de.pd.pjc.service.MainWindowService#loadFileList(java.lang.String)
	 */
	public void loadFileList(String pPath) {
		loadFileList(null, pPath, null, false);
	}

	
	/* (non-Javadoc)
	 * @see de.pd.pjc.service.MainWindowService#loadFileList(java.lang.String, de.pd.pjc.FilePatternFilter)
	 */
	public void loadFileList(String pPath, FilePatternFilter pPattern) {
		loadFileList(null, pPath, pPattern, false);
	}
	
	/* (non-Javadoc)
	 * @see de.pd.pjc.service.MainWindowService#loadFileList(de.pd.pjc.gui.table.FileTable, java.lang.String, de.pd.pjc.FilePatternFilter, boolean)
	 */
	public void loadFileList(FileTable pTable, String pPath,
			FilePatternFilter pPattern, boolean pRecursive) {

		FileTable table = mPanelController.getCurrentTable();
		if (pTable != null) {
			table = pTable;
		}
		if(table == null || !StringUtils.hasText(table.getName())) {
			throw new IllegalArgumentException("Table name must not be null");
		}
		
		try {
			mFileService.loadFileList(table, pPath, pPattern, pRecursive);
		} catch (Exception e) {
			mLog.error(e.getMessage());
			JOptionPane.showMessageDialog(null, e.getMessage());
			String path = (new File(table.getCurrentPath()).exists())?table.getCurrentPath():Constants.USER_HOME;
			loadFileList(table, path, pPattern, pRecursive);
			return;
		}
		
		table.setCurrentPath(pPath);
		JTextField adrField = null;
		JTabbedPane tabPane = null;
		adrField = mPanelController.getAdrFieldByTableName(table.getName());
		tabPane = mPanelController.getTabPaneByTableName(table.getName());
		
		FilePatternFilter pattern = pPattern;
		if(pattern == null) {
			pattern = new FilePatternFilter();
		}
		if(adrField != null && tabPane != null) {
			updatePathLabels(adrField, tabPane, pPath);
		}


		table.setCurrentIndex(0);
		updateFileInfo(table.getFileModel().getAllElementsArray());
		addHistPath(pPath);
		if (table.getName().equals("leftTable")) {
			mPanelController.setLeftPath(pPath);
//			mLeftFilter = pattern.getPattern();
		} else if (table.getName().equals("rightTable")) {
			mPanelController.setRightPath(pPath);
//			mRightFilter = pattern.getPattern();
		}

		if(SessionBean.coundDirSizes) {
			Runnable calTh = new CalculateDirSizesThread(table);
			Thread thread = new Thread(calTh);
			thread.start();
		}
		
		table.repaint();
	}

	/* (non-Javadoc)
	 * @see de.pd.pjc.service.MainWindowService#execute(de.pd.pjc.gui.table.FileTable)
	 */
	public void execute(FileTable pTable) {
		FileElement[] elements = pTable.getSelectedFileElements();

		if(FileServiceImpl.isImage(elements[0].getFileName())) {
			
			JCImageDialog imgDia = mPanelController.getCurrentImageDialog();
			if(imgDia == null) {
				imgDia = new JCImageDialog(null, false, elements[0]);
				mPanelController.setCurrentImageDialog(imgDia);
				imgDia.setVisible(true);
			} else {
				imgDia.setVisible(false);
				imgDia = new JCImageDialog(null, false, elements[0]);
				imgDia.setVisible(true);
			}
			return;
		}
		
		for (int ii = 0; ii < elements.length; ii++) {
			FileElement element = elements[ii];

			execute(element, pTable);
		}

	}
	
	/* (non-Javadoc)
	 * @see de.pd.pjc.service.MainWindowService#execute(de.pd.pjc.bean.FileElement, de.pd.pjc.gui.table.FileTable)
	 */
	public void execute(FileElement pElement, FileTable pTable) {
		if (pElement.isDirectory()) {
			loadFileList(pTable, 
					pElement.getAbsolutePath(), 
					new FilePatternFilter(mPanelController.getFocusedFilterField().getText()), false);
		} else {
			executeFile(pElement, null, null, pElement
					.getParentPath());
		}
	}

	/* (non-Javadoc)
	 * @see de.pd.pjc.service.MainWindowService#executeFile(de.pd.pjc.bean.FileElement, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void executeFile(FileElement pFile, String pCommand, String pArgs,
			String pDir) {
		mFileService.executeCommandByFileExt(pFile, pCommand, pArgs, pDir);
	}
	
	/* (non-Javadoc)
	 * @see de.pd.pjc.service.MainWindowService#showPopup(java.awt.event.MouseEvent)
	 */
	public void showPopup(MouseEvent pEvt, FileTable pFileTable) {
		FileElement[] elements = pFileTable.getSelectedFileElements();
		PJCPopupMenu popupMenu = new PJCPopupMenu(elements);
		popupMenu.show(pEvt.getComponent(), pEvt.getX(), pEvt.getY());
	}
	

	/* (non-Javadoc)
	 * @see de.pd.pjc.service.MainWindowService#goDirUp()
	 */
	public void goDirUp() {
		String dirUp = mFileService.getDirUp(mPanelController.getCurrentPath());
		loadFileList(dirUp, new FilePatternFilter(mPanelController.getFocusedFilterField().getText()));
	}
	
	/* (non-Javadoc)
	 * @see de.pd.pjc.service.MainWindowService#goBack()
	 */
	public void goBack() {
		String topDir = mFileService.getTopDirectory(mPanelController.getCurrentPath());
		goDirUp();
		mPanelController.getCurrentTable().movePositionByName(topDir);
	}
	
	/* (non-Javadoc)
	 * @see de.pd.pjc.service.MainWindowService#goForward()
	 */
	public void goForward() {
		int idx = mPathHistory.indexOf(mPanelController.getCurrentPath());
		if (idx >= 0 && mPathHistory.size() >= (idx + 2)) {
			loadFileList(mPathHistory.get(idx + 1));
		}
	}
	
	
	/* (non-Javadoc)
	 * @see de.pd.pjc.service.MainWindowService#openConsoleInCurrentDir()
	 */
	public void openConsoleInCurrentDir() {
		String currentPath = mPanelController.getCurrentPath();
		 mFileService.openConsoleInCurrentDir(currentPath);
	}
	
	/* (non-Javadoc)
	 * @see de.pd.pjc.service.MainWindowService#addTabToPane(javax.swing.JTabbedPane, java.lang.String)
	 */
	public FileTablePanel addTabToPane(JTabbedPane pTab, String pTableName) {
		return addTabToPane(pTab, pTableName, null);
	}
	
	/* (non-Javadoc)
	 * @see de.pd.pjc.service.MainWindowService#addTabToPane(javax.swing.JTabbedPane, java.lang.String, java.lang.String)
	 */
	public FileTablePanel addTabToPane(JTabbedPane pTab, String pTableName, String pPath) {
		FileTablePanel fileTablePanel = new FileTablePanel(pTableName);
		String path = pPath;
		if(path == null) path = Constants.USER_HOME;
		pTab.addTab(path, fileTablePanel);
		pTab.setSelectedIndex(pTab.getTabCount() - 1);
		loadFileList(fileTablePanel.getTable(), path, null, false);
		return fileTablePanel;
	}

	/* (non-Javadoc)
	 * @see de.pd.pjc.service.MainWindowService#reloadTables()
	 */
	public void reloadTables() {
		FileTable leftTable = mPanelController.getLeftTablePanel().getTable();
		FileTable rightTable = mPanelController.getRightTablePanel().getTable();
		int[] leftIdx = leftTable.getSelectedRows();
		int[] rightIdx = rightTable.getSelectedRows();
		loadFileList(leftTable, mPanelController.getLeftPath(),
				new FilePatternFilter(mPanelController.getFocusedFilterField().getText()), false);
		leftTable.setCurrentIndexes(leftIdx);
		loadFileList(rightTable, mPanelController.getRightPath(),
				new FilePatternFilter(mPanelController.getFocusedFilterField().getText()), false);
		rightTable.setCurrentIndexes(rightIdx);
		mPanelController.getCurrentTable().requestFocusInWindow();
	}
	
	
	/* (non-Javadoc)
	 * @see de.pd.pjc.service.MainWindowService#showPathTree()
	 */
	public void showPathTree() {
		JPopupMenu popup = new JPopupMenu();

		JLabel label = new JLabel("Path tree");
		label.setFont(Settings.DEFAULT_FONT_BOLD);
		popup.add(label);
		popup.addSeparator();

		int nn = 2;

		String path = mPanelController.getCurrentPath();
		do {

			Action action = new JCMenuItemAction(
					JCMenuItemAction.ACTION_OPEN_DIRECTORY);
			action.putValue(JCMenuItemAction.PARAM_PATH, path);
			JMenuItem item = new JMenuItem(action);
			item.setText(nn + ".   " + path);
			item.setIcon(IconFactory.getIconByName("folder_favorites.png"));
			if (nn < 10) {
				item.setMnemonic(String.valueOf(nn).charAt(0));
			}
			popup.add(item, nn);

			nn++;
			if (PJCUtils.isRootPath(path))
				break;

			path = mFileService.getDownDirectory(path);
		} while (true);

		popup.show(mPanelController.getCurrentPanel(), 0, 0);

	}
	
	
	/* (non-Javadoc)
	 * @see de.pd.pjc.service.MainWindowService#showHistoryPopup()
	 */
	public void showHistoryPopup() {
		JPopupMenu popup = new JPopupMenu();

		JLabel label = new JLabel("Path history ");
		label.setFont(Settings.DEFAULT_FONT_BOLD);
		popup.add(label);
		popup.addSeparator();

		int nn = 1;
		for (String histPath : mPathHistory) {

			Action action = new JCMenuItemAction(JCMenuItemAction.ACTION_OPEN_DIRECTORY);
			action.putValue(JCMenuItemAction.PARAM_PATH, histPath);
			JMenuItem item = new JMenuItem(action);
			item.setText(nn + ".   " + histPath);
			item.setIcon(IconFactory.getIconByName("folder_favorites.png"));
			if (nn < 10) {
				item.setMnemonic(String.valueOf(nn).charAt(0));
			}
			popup.add(item);
			nn++;
		}

		popup.show(mPanelController.getCurrentPanel(), 0, 0);

	}

	/** Switch to media mode.
	 * 
	 */
	public void showMediaMode() {
		if (mPanelController.isLeftPanelSelected()) {
			mPanelController.getCurrentPanel().add(mPanelController.getLeftMediaPanel());
		} else {
			mPanelController.getCurrentPanel().add(mPanelController.getRightMediaPanel());
		}
		mPanelController.getCurrentPanel().validate();
	}

	/** Hide the media mode.
	 * 
	 */
	public void hideMediaMode() {
		if (mPanelController.isLeftPanelSelected()) {
			mPanelController.getCurrentPanel().remove(mPanelController.getLeftMediaPanel());
		} else {
			mPanelController.getCurrentPanel().remove(mPanelController.getRightMediaPanel());
		}
		mPanelController.getCurrentPanel().validate();
	}

	/* (non-Javadoc)
	 * @see de.pd.pjc.service.MainWindowService#showLeftTableOnly()
	 */
	public void showLeftTableOnly() {
		JPanel leftPanel = mPanelController.getLeftPanel();
		JPanel rightPanel = mPanelController.getRightPanel();
		if (!rightPanel.isVisible()) {
			showBothTables();
			return;
		}
		leftPanel.setVisible(true);
		rightPanel.setVisible(false);
		mPanelController.focusLeftPanel();
		mPanelController.getLeftTable().requestFocusInWindow();
	}

	/* (non-Javadoc)
	 * @see de.pd.pjc.service.MainWindowService#showRightTableOnly()
	 */
	public void showRightTableOnly() {
		JPanel leftPanel = mPanelController.getLeftPanel();
		JPanel rightPanel = mPanelController.getRightPanel();
		if (!leftPanel.isVisible()) {
			showBothTables();
			return;
		}
		rightPanel.setVisible(true);
		leftPanel.setVisible(false);
		mPanelController.focusRightPanel();
		mPanelController.getRightTable().requestFocusInWindow();
	}

	/* (non-Javadoc)
	 * @see de.pd.pjc.service.MainWindowService#showBothTables()
	 */
	public void showBothTables() {
		JPanel leftPanel = mPanelController.getLeftPanel();
		JPanel rightPanel = mPanelController.getRightPanel();
		leftPanel.setVisible(true);
		rightPanel.setVisible(true);
		mPanelController.getCurrentTable().requestFocusInWindow();
	}
	
	/**
	 * @return the fileService
	 */
	public FileService getFileService() {
		return mFileService;
	}


	/**
	 * @param pFileService the fileService to set
	 */
	public void setFileService(FileService pFileService) {
		mFileService = pFileService;
	}


	/**
	 * @return the xMLSettingsService
	 */
	public XMLSettingsService getXMLSettingsService() {
		return mXMLSettingsService;
	}


	/**
	 * @param pSettingsService the xMLSettingsService to set
	 */
	public void setXMLSettingsService(XMLSettingsService pSettingsService) {
		mXMLSettingsService = pSettingsService;
	}


	/**
	 * @return the panelController
	 */
	public BasePanelController getPanelController() {
		return mPanelController;
	}


	/**
	 * @param pPanelController the panelController to set
	 */
	public void setPanelController(BasePanelController pPanelController) {
		mPanelController = pPanelController;
	}


	/* (non-Javadoc)
	 * @see de.pd.pjc.service.MainWindowService#calculateCurrentDirSizes()
	 */
	public void calculateCurrentDirSizes() {
		FileTable currentTable = mPanelController.getCurrentTable();
		FileTableModel model = currentTable.getFileModel();
		ArrayList<FileElement> allElements = model.getAllElements();
		for (FileElement element : allElements) {
			if(element.isTopElement() || !element.isDirectory()) continue;
			element.setFileSize(1);
		}
	}
}
