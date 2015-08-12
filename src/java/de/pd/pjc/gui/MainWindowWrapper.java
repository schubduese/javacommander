package de.pd.pjc.gui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.KeyStroke;

import de.pd.pjc.Constants;
import de.pd.pjc.FilePatternFilter;
import de.pd.pjc.Settings;
import de.pd.pjc.action.HotkeyAction;
import de.pd.pjc.bean.DirSettingsBean;
import de.pd.pjc.bean.FileElement;
import de.pd.pjc.exception.SettingsPathNotFoundException;
import de.pd.pjc.gui.listener.TabbedPaneChangeListener;
import de.pd.pjc.gui.listener.TabbedPaneMouseListener;
import de.pd.pjc.service.MainWindowService;
import de.pd.pjc.service.ServiceFactory;
import de.pd.pjc.util.IconFactory;
import de.pd.pjc.util.PJCUtils;

public class MainWindowWrapper extends MainWindow {

	private MainWindowService mMainWindowService;
	private static MainWindowPanelController mMainWindowController;
	
	public MainWindowWrapper(MainWindowPanelController pPanelController, boolean pLightMode) {
		super(pPanelController, pLightMode);
	}

	public static String mLeftFilter;
	public static String mRightFilter;
	JTree mTree = new JTree();
	PJPanelFactory mPanelFactory;
	
	@Override
	protected void initContent() {

		boolean lightMode = isLightMode();
		
		mMainWindowService = ServiceFactory.getMainWindowService();
		mMainWindowController = ServiceFactory.getMainWindowController();
		
		super.initContent();
		
		 mPanelFactory = new PJPanelFactory();

		
		try {
			ServiceFactory.getXMLSettingsService().checkSettingDirExists();
		} catch (SettingsPathNotFoundException e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(this, e.getMessage());
			System.exit(1);
		}
		
		
		leftFileTable.setName(FileTablePanel.LEFT_FILE_TABLE);

		// load the lists
		String leftPath = mMainWindowController.getLeftPath();
		if (leftPath == null) {
			mMainWindowController.setLeftPath(Constants.USER_HOME);
		}
		
		if(!lightMode) {
			rightFileTable.setName(FileTablePanel.RIGHT_FILE_TABLE);
			String rightPath = mMainWindowController.getRightPath();
			if (rightPath == null) {
				mMainWindowController.setRightPath(Constants.USER_HOME);
			}
			rightTabPane.removeTabAt(0);
			mMainWindowService.addTabToPane(rightTabPane, FileTablePanel.RIGHT_FILE_TABLE, mMainWindowController.getRightPath());
			
			// set the size
			java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
			int defWidth = (screenSize.width < 1000)?screenSize.width:1000;
			int defHeight = (screenSize.height < 850)?screenSize.height-50:800;
			
	      setBounds((screenSize.width-1000)/2, (screenSize.height-800)/2,defWidth, defHeight);
		} else {
			rightPanel.setVisible(false);
			
			java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	      setBounds((screenSize.width-1000)/2, (screenSize.height-800)/2,400, 600);
		}
		
		leftTabPane.removeTabAt(0);
		mMainWindowService.addTabToPane(leftTabPane, FileTablePanel.LEFT_FILE_TABLE, mMainWindowController.getLeftPath());
		
		
		mMainWindowController.focusLeftPanel();
		
		leftFileTable.requestFocusInWindow();
		// init other stuff
		initListeners();
		beautyfyWindow();
		
		mMainWindowService.updateFileInfo(mMainWindowController.getFocusedModel().getAllElementsArray());


		// String path = System.getenv("PATH");
	}

	/** initializes all listeners.
	 * 
	 */
	private void initListeners() {
		addAdrFieldListener(leftAdrField);
		addAdrFieldListener(rightAdrField);
		addFilterFieldListener(leftFilterField);
		addHotKeys();
		leftTabPane.addChangeListener(new TabbedPaneChangeListener());
		rightTabPane.addChangeListener(new TabbedPaneChangeListener());
		leftTabPane.addMouseListener(new TabbedPaneMouseListener(FileTablePanel.LEFT_FILE_TABLE, leftAdrField));
		rightTabPane.addMouseListener(new TabbedPaneMouseListener(FileTablePanel.RIGHT_FILE_TABLE, rightAdrField));
		
		
	}

	/**
	 * 
	 */
	private void addHotKeys() {
		InputMap inputMap = FileBrowserPanel
				.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				KeyEvent.CTRL_DOWN_MASK), "quit");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK), "refresh");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_1,
				KeyEvent.CTRL_DOWN_MASK), "showOnlyLeftTable");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_2,
				KeyEvent.CTRL_DOWN_MASK), "showOnlyRightTable");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_3,
				KeyEvent.CTRL_DOWN_MASK), "showBothTables");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F,
				KeyEvent.CTRL_DOWN_MASK), "refocus");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, 0), "rcMode"); // activate the remote controller mode

		ActionMap actionMap = FileBrowserPanel.getActionMap();

		// quit
		HotkeyAction quitAction = new HotkeyAction();
		quitAction.putValue(AbstractAction.ACTION_COMMAND_KEY,
				HotkeyAction.ACTION_QUIT);
		actionMap.put("quit", quitAction);

		// refresh
		HotkeyAction refreshAction = new HotkeyAction();
		refreshAction.putValue(AbstractAction.ACTION_COMMAND_KEY,
				HotkeyAction.ACTION_REFRESH);
		actionMap.put("refresh", refreshAction);

		// show only left table
		HotkeyAction showOnlyLeftTableAction = new HotkeyAction();
		showOnlyLeftTableAction.putValue(AbstractAction.ACTION_COMMAND_KEY,
				HotkeyAction.ACTION_SHOW_LEFT_TABLE);
		actionMap.put("showOnlyLeftTable", showOnlyLeftTableAction);

		// show only right table
		HotkeyAction showOnlyRightTableAction = new HotkeyAction();
		showOnlyRightTableAction.putValue(AbstractAction.ACTION_COMMAND_KEY,
				HotkeyAction.ACTION_SHOW_RIGHT_TABLE);
		actionMap.put("showOnlyRightTable", showOnlyRightTableAction);

		// show both tables
		HotkeyAction showBothTablesAction = new HotkeyAction();
		showBothTablesAction.putValue(AbstractAction.ACTION_COMMAND_KEY,
				HotkeyAction.ACTION_SHOW_BOTH_TABLES);
		actionMap.put("showBothTables", showBothTablesAction);
		
		// refocus
		HotkeyAction refocusAction = new HotkeyAction();
		refocusAction.putValue(AbstractAction.ACTION_COMMAND_KEY,
				HotkeyAction.ACTION_REFOCUS);
		actionMap.put("refocus", refocusAction);
		
		// activate the remote controll mode
		HotkeyAction rcAction = new HotkeyAction();
		rcAction.putValue(AbstractAction.ACTION_COMMAND_KEY,
				HotkeyAction.ACTION_RC_MODE);
		actionMap.put("rcMode", rcAction);

	}


	private void beautyfyWindow() {
		setIconImage(IconFactory.getAppIconByName("powerpoint.png").getImage());
		leftScrollPane.getViewport().setBackground(Settings.TABLE_BG);
		rightScrollPane.getViewport().setBackground(Settings.TABLE_BG);
		menuBar.setBackground(Settings.DEFAULT_BG_COLOR);
		toolBar.setBackground(Settings.DEFAULT_BG_COLOR);
		toolBar.setPreferredSize(new Dimension(0, 30));

		leftPanel.setPreferredSize(new Dimension(400, 600));
		leftPanel.setBackground(Settings.DEFAULT_BG_COLOR);
		rightPanel.setPreferredSize(new Dimension(400, 600));
		rightPanel.setBackground(Settings.DEFAULT_BG_COLOR);
		
		leftTabPane.setBackground(Settings.TAB_INACTIVE_BG);
		rightTabPane.setBackground(Settings.TAB_INACTIVE_BG);
		
		leftAdrField.setCursor(new Cursor(Cursor.TEXT_CURSOR));
		
		toolBar.setVisible(true);
	}

	/**
	 * @param pAdrField
	 */
	private void addAdrFieldListener(JTextField pAdrField) {
		pAdrField.addKeyListener(new java.awt.event.KeyAdapter() {

			private String cutLasthPat(String pPath) {
				int lastIndex = pPath.lastIndexOf(File.separator);
				if (lastIndex != -1 && lastIndex > 1) {
					return pPath.substring(0, lastIndex);
				} else {
					return File.separator;
				}

			}

			public void keyPressed(java.awt.event.KeyEvent evt) {
				JTextField source = (JTextField) evt.getSource();
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					if(source.getSelectedText() != null) {
						source.setSelectionStart(source.getText().length());
						source.setSelectionEnd(source.getText().length());
					} else {
						mMainWindowService.loadFileList(source.getText());
						mMainWindowController.getCurrentTable().requestFocusInWindow();
					}
				} else if ((evt.getKeyCode() == KeyEvent.VK_W || evt.getKeyCode() == KeyEvent.VK_BACK_SPACE)
						&& evt.isControlDown()) {
					source.setText(cutLasthPat(mMainWindowController.getFocusedAdrField().getText()));
				} else if (evt.getKeyCode() == KeyEvent.VK_TAB) {
					source.setSelectionEnd(0);
				}

			}
			
			@Override
			public void keyReleased(KeyEvent pE) {
				JTextField source = (JTextField) pE.getSource();
				if(PJCUtils.isKeyboardSearchKeyPressed(pE)) {
					String currTxt = source.getText();
					String completion = ServiceFactory.getFileService().completePath(currTxt);
					source.setText(source.getText() + completion);
					source.setSelectionStart(currTxt.length());
					source.setSelectionEnd(source.getText().length());
				}	
			}
		});
	}

	/**
	 * @param pAdrField
	 */
	private void addFilterFieldListener(JTextField pFilterField) {
		pFilterField.addKeyListener(new java.awt.event.KeyAdapter() {

			public void keyPressed(java.awt.event.KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
					String pattern = mMainWindowController.getFocusedFilterField()
							.getText();
					mMainWindowService.loadFileList(mMainWindowController.getCurrentPath(), new FilePatternFilter(pattern));
					mMainWindowController.getCurrentTable().requestFocusInWindow();
				}

			}
		});
	}

	public final static boolean isNavKeyPressed(KeyEvent evt) {
		return (evt.getKeyCode() == KeyEvent.VK_DOWN
				|| evt.getKeyCode() == KeyEvent.VK_UP
				|| evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN
				|| evt.getKeyCode() == KeyEvent.VK_PAGE_UP
				|| evt.getKeyCode() == KeyEvent.VK_HOME
				|| evt.getKeyCode() == KeyEvent.VK_END
				|| (evt.getKeyCode() == KeyEvent.VK_A && evt.isControlDown()) || (evt
				.getKeyCode() == KeyEvent.VK_SPACE && evt.isControlDown()));
	}



	public void showIconView() {
//		FileTable iconFileTable = new IconFileTable(rightFileTableModelIcon);
//		addTableListener(iconFileTable);
//		FileTable focusedTable = mMainWindowController.getFocusedTable();
//		iconFileTable.setName(focusedTable.getName());
//		if (focusedTable.getName().equals("leftTable")) {
//			leftFileTable = iconFileTable;
//			mMainWindowController.focusLeftPanel();
//		} else if (focusedTable.getName().equals("rightTable")) {
//			rightFileTable = iconFileTable;
//			mMainWindowController.focusRightPanel();
//		}
//
//		mMainWindowController.getFocusedScrollPane().setViewportView(iconFileTable);
//		loadFileList(iconFileTable, null, mMainWindowController.getCurrentPath(), null);
	}




	public static void sortChanged(int pCol, int pDir) {
		if(mMainWindowController.getCurrentTable().getName().equals(FileTablePanel.SEARCH_FILE_TABLE)) return;
		mLog.debug("storing sort");
		DirSettingsBean dirSettings = new DirSettingsBean(mMainWindowController
				.getCurrentPath(), pCol + "," + pDir);
		ServiceFactory.getXMLSettingsService().storeDirSettings(dirSettings);
	}

	public JPopupMenu getPopupMenuForElement(FileElement pElement) {

		if (mPopupMenu == null) {
			mPopupMenu = new JPopupMenu();
		}

		mPopupMenu.add(new JMenuItem("test"));

		return mPopupMenu;

	}
	
}
