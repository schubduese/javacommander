package de.pd.pjc.gui;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.pd.pjc.Settings;
import de.pd.pjc.gui.dialog.JCImageDialog;
import de.pd.pjc.gui.dialog.JCThumbDialog;
import de.pd.pjc.gui.menu.JCToolBar;
import de.pd.pjc.gui.table.FileTable;
import de.pd.pjc.gui.table.FileTableModel;
import de.pd.pjc.gui.table.TableSorter;
import de.pd.pjc.gui.table.icon.IconFileTable;

public class MainWindowPanelController extends BasePanelController {

	public final static int PANEL_NONE = -1;
	public final static int PANEL_LEFT = 1;
	public final static int PANEL_RIGHT = 2;
	
	private MainWindow mMainWindow;
	private FileTable mCurrentTable;
	
	public int mCurrentPanel = PANEL_NONE;
	
	private String mLeftPath;
	private String mRightPath;
	
	protected Log mLog = LogFactory.getLog(MainWindowPanelController.class.getName());
	
	private JCImageDialog mCurrentImageDialog;
	private JCThumbDialog mCurrentThumbDialog;
	
	public JCThumbDialog getCurrentThumbDialog() {
		return mCurrentThumbDialog;
	}

	public MainWindowPanelController() {
		super();
//		focusLeftPanel();
	}
	
	public void focusThumbTable() {
		
	}
	
	public void refocusByTableName(String pTableName) {
		if(pTableName.equals(FileTablePanel.LEFT_FILE_TABLE)) {
			focusLeftPanel();
		} else if(pTableName.equals(FileTablePanel.RIGHT_FILE_TABLE)) {
			focusRightPanel();
		}
	}
	
	public void focusLeftPanel() {
		focusLeftPanel(true);
	}
	
	/** Focuses the left panel.
	 * @param pFocusTable
	 */
	public void focusLeftPanel(boolean pFocusTable) {
//		mLog.debug("focus left");
		mCurrentPanel = PANEL_LEFT;
		mCurrentTable = getLeftTable();
		
		getLeftTable().setRowSelectionAllowed(true);
		
		getLeftAdrField().setBackground(Color.white);
		getRightAdrField().setBackground(Settings.TABLE_ROW_INACTIVE);
		
		mMainWindow.rightFileTable.setRowSelectionAllowed(false);
		getLeftTablePanel().getTable().setActive(true);
		getRightTablePanel().getTable().setActive(false);
		if(mMainWindow.rightFileTable instanceof IconFileTable) {
			mMainWindow.rightFileTable.setColumnSelectionAllowed(false);
		}
	
		if(pFocusTable) {
			getLeftTable().requestFocusInWindow();
		}
		
		setLeftPath(((FileTable)getLeftTable()).getCurrentPath());
		mMainWindow.leftFileTable.repaint();
		mMainWindow.rightFileTable.repaint();
	}
	

	
	// -- Table Panel --
	public FileTablePanel getLeftTablePanel() {
		if(!(mMainWindow.leftTabPane.getSelectedComponent() instanceof FileTablePanel)) return new FileTablePanel(null);
		return (FileTablePanel) mMainWindow.leftTabPane.getSelectedComponent();
	}
	
	public FileTablePanel getRightTablePanel() {
		if(!(mMainWindow.rightTabPane.getSelectedComponent() instanceof FileTablePanel)) return new FileTablePanel(null);
		return (FileTablePanel) mMainWindow.rightTabPane.getSelectedComponent();
	}
	// -- Table Panel --
	
	// -- table --
	public FileTable getLeftTable() {
		return getLeftTablePanel().getTable();
	}
	public FileTable getRightTable() {
		return getRightTablePanel().getTable();
	}
	public FileTable getCurrentTable() {
		return mCurrentTable;
	}
	// -- table --
	
	
	public void focusRightPanel() {
		focusRightPanel(true);
	}
	
	/**Focuses the right panel.
	 * @param pFocusTable
	 */
	public void focusRightPanel(boolean pFocusTable) {
//		mLog.debug("focus right");
		mCurrentPanel = PANEL_RIGHT;
		mCurrentTable = getRightTable();
		
		getRightTable().setRowSelectionAllowed(true);

		getRightTablePanel().getTable().setActive(true);
		getLeftTablePanel().getTable().setActive(false);
		if(mMainWindow.rightFileTable instanceof IconFileTable) {
			mMainWindow.rightFileTable.setColumnSelectionAllowed(true);
		}
		
		getRightAdrField().setBackground(Color.white);
		getLeftAdrField().setBackground(Settings.TABLE_ROW_INACTIVE);
		mMainWindow.leftFileTable.setRowSelectionAllowed(false);
		
		
		if(pFocusTable) {
			getRightTable().requestFocusInWindow();
		}
		
		setRightPath(((FileTable)getRightTable()).getCurrentPath());
		mMainWindow.leftFileTable.repaint();
		mMainWindow.rightFileTable.repaint();
	}
	
	public void switchFocus() {
		if(isLeftPanelSelected()) {
			focusRightPanel();
		} else if(isRightPanelSelected()) {
			focusLeftPanel();
		}
	}

	
	// -- filter field --
	public JTextField getFocusedFilterField() {
		if(isLeftPanelSelected()) {
			return mMainWindow.leftFilterField;
		} else if(isRightPanelSelected()){
			return mMainWindow.leftFilterField;
		} else {
			return null;
		}
	}
	// -- filter field --
	
	// -- focues model --
	public FileTableModel getFocusedModel() {
		if(getCurrentTable() == null) return null;
		return getCurrentTable().getFileModel();
	}
	// -- focues model --
	
	/** Returns the current table sorter. If the table has no sorter (e.g. for icon table), the null will be returned.
	 * @return
	 */
	public TableSorter getFocusedSorter() {
		return getCurrentTable().getSorter();
	}
	
	// -- scroll pane --
	public JScrollPane getLeftScrollPane() {
		return mMainWindow.leftScrollPane;
	}
	public JScrollPane getRightScrollPane() {
		return mMainWindow.rightScrollPane;
	}
	public JScrollPane getFocusedScrollPane() {
		if(isLeftPanelSelected()) {
			return getLeftScrollPane();
		} else if(isRightPanelSelected()){
			return getRightScrollPane();
		} else {
			return null;
		}
	}
	// -- scroll pane --

	public String getCurrentPath() {
		if(isLeftPanelSelected()) {
			return mLeftPath;
		} else {
			return mRightPath;
		}
	}
	
	public String getOppositPath() {
		if(isLeftPanelSelected()) {
			return mRightPath;
		} else {
			return mLeftPath;
		}
	}

	public String getLeftPath() {
		return mLeftPath;
	}

	public void setLeftPath(String pLeftPath) {
		mLeftPath = pLeftPath;
	}

	public String getRightPath() {
		return mRightPath;
	}

	public void setRightPath(String pRightPath) {
		mRightPath = pRightPath;
	}

	// -- the base panels --
	public JPanel getLeftPanel() {
		return mMainWindow.leftPanel;
	}
	public JPanel getRightPanel() {
		return mMainWindow.rightPanel;
	}
			
	public JPanel getCurrentPanel() {
		if(isLeftPanelSelected()) {
			return mMainWindow.leftPanel;
		} else {
			return mMainWindow.rightPanel;
		}
	}
	// --
	
	// -- tabbed pane
	public JTabbedPane getFocusedTabPane() {
		if(isLeftPanelSelected()) {
			return mMainWindow.leftTabPane;
		} else {
			return mMainWindow.rightTabPane;
		}
	}
	public JTabbedPane getTabPaneByTableName(String pTableName) {
		if(isLeftPanel(pTableName)) {
			return getLeftTabPane();
		} else if(isRightPanel(pTableName)) {
			return getRightTabPane();
		} else {
			return null;
		}
	}
	
	// -- tab pane --
	/**
	 * @return
	 */
	public JTabbedPane getLeftTabPane() {
			return mMainWindow.leftTabPane;
	}
	public JTabbedPane getRightTabPane() {
			return mMainWindow.rightTabPane;
	}
	public JTabbedPane getCurrentTabPane() {
		if(isLeftPanelSelected()) {
			return mMainWindow.leftTabPane;
		} else {
			return mMainWindow.rightTabPane;
		}
	}
	// --

	// --  media panel --
	public MediaPreviewPanel getLeftMediaPanel() {
		return mMainWindow.mediaPanelLeft;
	}
	public MediaPreviewPanel getRightMediaPanel() {
		return mMainWindow.mediaPanelRight;
	}
	public MediaPreviewPanel getCurrentMediaPanel() {
		if(isLeftPanelSelected()) {
			return mMainWindow.mediaPanelLeft;
		} else {
			return mMainWindow.mediaPanelRight;
		}
	}
	// --- 

	// --  Adr Field --
	public JTextField getLeftAdrField() {
		return mMainWindow.leftAdrField;
	}
	public JTextField getRightAdrField() {
		return mMainWindow.rightAdrField;
	}
	public JTextField getFocusedAdrField() {
		if(isLeftPanelSelected()) {
			return getLeftAdrField();
		} else if(isRightPanelSelected()){
			return getRightAdrField();
		} else {
			return null;
		}
	}
	public JTextField getAdrFieldByTableName(String pTableName) {
		if(isLeftPanel(pTableName)) {
			return getLeftAdrField();
		} else if(isRightPanel(pTableName)) {
			return getRightAdrField();
		} else {
			return null;
		}
	}
	// --  Adr Field --
	
	public JLabel getFileInfoLabel() {
		return mMainWindow.fileInfoLabel;
	}
	
	
	/** Returns true, if the left panel is selected at the moment.
	 * @return
	 */
	public boolean isLeftPanelSelected() {
		return mCurrentPanel == MainWindowPanelController.PANEL_LEFT;
	}
	
	/** Determins the panel by table name.
	 * @param pTableName
	 * @return
	 */
	public boolean isLeftPanel(String pTableName) {
		return FileTablePanel.LEFT_FILE_TABLE.equals(pTableName);
	}
	
	/** Determins the panel by table name.
	 * @param pTableName
	 * @return
	 */
	public boolean isRightPanel(String pTableName) {
		return FileTablePanel.RIGHT_FILE_TABLE.equals(pTableName);
	}
	
	/** Returns true, if the right panel is selected at the moment.
	 * @return
	 */
	public boolean isRightPanelSelected() {
		return mCurrentPanel == MainWindowPanelController.PANEL_RIGHT;
	}

	/**
	 * @return the mainWindow
	 */
	public MainWindow getMainWindow() {
		return mMainWindow;
	}

	/**
	 * @param pMainWindow the mainWindow to set
	 */
	public void setMainWindow(MainWindow pMainWindow) {
		mMainWindow = pMainWindow;
	}

	@Override
	public void printStatus() {
		mLog.info("Left path: " + mLeftPath);
		mLog.info("Right path: " + mRightPath);
		mLog.info("Current path: " + getCurrentPath());
	}
	
	public JCToolBar getToolBar() {
		return (JCToolBar) mMainWindow.toolBar;
	}

	public JCImageDialog getCurrentImageDialog() {
		return mCurrentImageDialog;
	}

	public void setCurrentImageDialog(JCImageDialog pCurrentImageDialog) {
		mCurrentImageDialog = pCurrentImageDialog;
	}

	public void setCurrentThumbDialog(JCThumbDialog pCurrentThumbDialog) {
		mCurrentThumbDialog = pCurrentThumbDialog;
	}

	public void setCurrentTable(FileTable pCurrentTable) {
		mCurrentTable = pCurrentTable;
	}
	
	/*
		if(isLeftPanelSelected()) {
		} else if(isRightPanelSelected()){
		} else {
			return null;
		}


	 */
}
