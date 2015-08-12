/**
 * PJavaCommander by Petros Dolaschjan
 *
 */ 
package de.pd.pjc.thread;

import java.util.ArrayList;

import de.pd.pjc.bean.FileCountBean;
import de.pd.pjc.bean.FileElement;
import de.pd.pjc.bean.SessionBean;
import de.pd.pjc.gui.table.FileTable;
import de.pd.pjc.gui.table.FileTableModel;
import de.pd.pjc.service.ServiceFactory;

/** Calculates the file ammount of the directories.
 * @author petros
 *
 */
public class CalculateDirSizesThread implements Runnable {

	private FileTable mTable;
	
	public CalculateDirSizesThread(FileTable pTable) {
		super();
		SessionBean.DIR_SIZE_CALCULATION_ABBORTED = false;
		mTable = pTable;
	}

	public void run() {
		FileTableModel model = mTable.getFileModel();
		ArrayList<FileElement> allElements = model.getAllElements();
		for (FileElement element : allElements) {
			if(element.isTopElement() || !element.isDirectory()) continue;
			FileCountBean countBean = ServiceFactory.getFileService().calculateDirSize(element.getAbsolutePath(), false);
			String cnt = countBean.files + countBean.dirs + " Items";
			element.setDirContent(cnt);
			mTable.repaint();
		}
	}

}


/*
 * $Log$
 */