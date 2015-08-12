package de.pd.pjc.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import de.pd.pjc.bean.SessionBean;
import de.pd.pjc.gui.table.FileTable;
import de.pd.pjc.service.MainWindowService;
import de.pd.pjc.service.ServiceFactory;

public class HotkeyAction extends AbstractAction {

	public final static int ACTION_QUIT = 0;
	public final static int ACTION_REFRESH = 1;
	public final static int ACTION_SHOW_LEFT_TABLE = 2;
	public final static int ACTION_SHOW_RIGHT_TABLE = 3;
	public final static int ACTION_SHOW_BOTH_TABLES = 4;
	public final static int ACTION_REFOCUS = 5;
	public final static int ACTION_RC_MODE = 6;
	
	MainWindowService mMainWindowService;
	
	public HotkeyAction() {
		super();
		mMainWindowService = ServiceFactory.getMainWindowService();
	}

	public void actionPerformed(ActionEvent pE) {
		Integer action = (Integer) getValue(ACTION_COMMAND_KEY);
		
		switch (action) {
		case ACTION_QUIT:
			System.exit(0);
			break;
			
		case ACTION_REFRESH:
			mMainWindowService.reloadTables();
			break;
			
		case ACTION_SHOW_LEFT_TABLE:
			mMainWindowService.showLeftTableOnly();
			break;
			
		case ACTION_SHOW_RIGHT_TABLE:
			mMainWindowService.showRightTableOnly();
			break;
			
		case ACTION_SHOW_BOTH_TABLES:
			mMainWindowService.showBothTables();
			break;
			
		case ACTION_REFOCUS:
			FileTable focusedTable = ServiceFactory.getMainWindowController().getCurrentTable();
			ServiceFactory.getMainWindowController().refocusByTableName(focusedTable.getName());
			break;
			
		case ACTION_RC_MODE:
			SessionBean.toggleRcMode();
			mMainWindowService.reloadTables();
			break;
		default:
			break;
		}
	}

}
