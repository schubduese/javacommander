package de.pd.pjc.gui.table.detail;

import de.pd.pjc.gui.table.FileTableModel;

public class DetailFileTableModel extends FileTableModel {

	final String[] names = { "Name", "Type", "Size", "Modified At" };
	

	public int getColumnCount() {
		return names.length;
	}
	
	@Override
	public void addElement(int pIndex) {
		fireTableRowsUpdated(pIndex, pIndex);
	}

	@Override
	public int getColumnForIndex(int pIndex) {
		return 0;
	}

	@Override
	public int getIndex(int pRow, int pColumn) {
		return pRow;
	}

	@Override
	public int getRowForIndex(int pIndex) {
		return pIndex;
	}
	

	// The default implementations of these methods in
	// AbstractTableModel would work, but we can refine them.
	public String getColumnName(int column) {
		return names[column];
	}

}
