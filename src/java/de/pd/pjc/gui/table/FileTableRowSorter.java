/**
 * PJavaCommander by Petros Dolaschjan
 *
 */ 
package de.pd.pjc.gui.table;

import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class FileTableRowSorter<M extends TableModel> extends TableRowSorter<TableModel> {

	@Override
	protected void fireSortOrderChanged() {
		super.fireSortOrderChanged();
		System.out.println("sorting...");
	}

	
	
}


/*
 * $Log$
 */