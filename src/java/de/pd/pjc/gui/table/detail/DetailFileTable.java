package de.pd.pjc.gui.table.detail;

import de.pd.pjc.gui.table.FileTable;
import de.pd.pjc.gui.table.TableSorter;

public class DetailFileTable extends FileTable {

	public DetailFileTable(TableSorter pSorter) {
		super(pSorter);
	}
	
	/**
	 * @param index
	 */
	public void setCurrentIndex(int index) {
		getSelectionModel().setSelectionInterval(index, index);
		scrollRectToVisible(getCellRect(index, 0, false));
		repaint();
	}

}
