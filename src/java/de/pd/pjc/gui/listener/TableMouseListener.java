/**
 * 
 */
package de.pd.pjc.gui.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import de.pd.pjc.bean.FileElement;
import de.pd.pjc.gui.FileTablePanel;
import de.pd.pjc.gui.table.FileTable;
import de.pd.pjc.service.MainWindowService;
import de.pd.pjc.service.ServiceFactory;

/**
 * @author petros
 * 
 */
public class TableMouseListener extends MouseAdapter {

	MainWindowService mMainWindowService;
	
	public TableMouseListener() {
		super();
		mMainWindowService = ServiceFactory.getMainWindowService();
	}

	@Override
	public void mouseClicked(MouseEvent evt) {
		// check if enough items are selected to enable diff

		FileTable table = (FileTable) evt.getSource();
		if (table.getName().equals(FileTablePanel.LEFT_FILE_TABLE)) {
			ServiceFactory.getMainWindowController().focusLeftPanel();
		} else if(table.getName().equals(FileTablePanel.RIGHT_FILE_TABLE)) {
			ServiceFactory.getMainWindowController().focusRightPanel();
		}

		FileElement[] selectedFileElements = mMainWindowService.getSelectedFileElements();
		mMainWindowService.updateFileInfo(selectedFileElements);
		mMainWindowService.updateFileInfoForTableSelection();

		// Double click
		if (evt.getClickCount() == 2) {
			mMainWindowService.execute(table);
		}
		// Right click
		if (evt.getButton() == MouseEvent.BUTTON3) {
			table.requestFocusInWindow();
			if (table.getSelectedRows().length <= 1) {
				int index = table.rowAtPoint(evt.getPoint());
				table.setCurrentIndex(index);
			}
			mMainWindowService.showPopup(evt, table);
		}

	}

}
