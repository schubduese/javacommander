package de.pd.pjc.service;

import java.awt.event.MouseEvent;

import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import de.pd.pjc.FilePatternFilter;
import de.pd.pjc.bean.FileElement;
import de.pd.pjc.gui.FileTablePanel;
import de.pd.pjc.gui.table.FileTable;

/** Is responsible for the interactivity on the main window.
 * @author petros
 *
 */
public interface MainWindowService {

	/** Updates the file information in the label at the bottom.
	 * @param pFileElements
	 */
	public void updateFileInfo(FileElement[] pFileElements);
	
	/** Updates the file information for the current selection.
	 * 
	 */
	public void updateFileInfoForTableSelection();
	
	/**
	 * returns the selected file element.
	 * 
	 * @return
	 */
	public FileElement[] getSelectedFileElements();
	
	/** Updates the labels of all path components.
	 * @param pAdrField
	 * @param tab
	 * @param currentPath
	 */
	public void updatePathLabels(JTextField pAdrField, JTabbedPane tab, String currentPath);
	
	/** Adds a path to the path history.
	 * @param pPath
	 */
	public void addHistPath(String pPath);
	
	/**
	 * wrapper.
	 * 
	 * @param pPath
	 */
	public void loadFileList(String pPath);
	
	/** Wrapper.
	 * @param pPath
	 * @param pPattern
	 */
	public void loadFileList(String pPath, FilePatternFilter pPattern);
	
	/** Loads the file list via the file service.
	 * @param pTable
	 * @param pPath
	 * @param pPattern
	 * @param pRecursive
	 */
	public void loadFileList(FileTable pTable, String pPath,
			FilePatternFilter pPattern, boolean pRecursive);
	
	/**
	 * executes the double click command from the table
	 * 
	 */
	public void execute(FileTable pTable);
	
	/**
	 * executes a command for an element.
	 * 
	 * @param pElement
	 */
	public void execute(FileElement pElement, FileTable pTable);
	
	/**
	 * executes a file by command. if command is null, then default will be used.
	 * 
	 * @param pFile
	 * @param pCommand
	 */
	public void executeFile(FileElement pFile, String pCommand, String pArgs, String pDir);
	
	/**
	 * 
	 */
	public void goBack();
	
	/** Go a directory up
	 * 
	 */
	public void goDirUp();
	
	/** Go a dir forward.
	 * 
	 */
	public void goForward();
	
	/** Shows up a popup menu.
	 * @param pEvt
	 */
	public void showPopup(MouseEvent pEvt, FileTable pFileTable);
	
	/** Opens the console in the current dir.
	 * 
	 */
	public void openConsoleInCurrentDir();
	
	/** wrapper.
	 * @param pTab
	 * @param pTableName
	 */
	public FileTablePanel addTabToPane(JTabbedPane pTab, String pTableName);
	
	/** Adds a tab to the pane.
	 * @param pTab
	 * @param pTableName
	 * @param pPath
	 */
	public FileTablePanel addTabToPane(JTabbedPane pTab, String pTableName, String pPath);
	
	/** Reloads the tables.
	 * 
	 */
	public void reloadTables();
	
	/** Shows the path tree.
	 * 
	 */
	public void showPathTree();
	
	/** Show the path history as a popup menue.
	 * 
	 */
	public void showHistoryPopup();
	
	/** Switch to media mode.
	 * 
	 */
	public void showMediaMode();
	
	/** Hide the media mode.
	 * 
	 */
	public void hideMediaMode();
	
	/** Shows both tables.
	 * 
	 */
	public void showBothTables();
		
	/** Shows the left table in fullscreen mode (with toggle).
	 * 
	 */
	public void showLeftTableOnly();

	/** Shows the right table in fullscreen mode (with toggle).
	 * 
	 */
	public void showRightTableOnly();
	
	/** Calculates the directory sizes of the currently selected table.
	 * 
	 */
	public void calculateCurrentDirSizes();
}
