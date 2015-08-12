package de.pd.pjc.gui.table;

import java.util.ArrayList;


public class FileTableRow {

	private ArrayList<FileTableCell> mCells = new ArrayList<FileTableCell>();
	
	public void addCell(FileTableCell pCell) {
		mCells.add(pCell);
	}

	public Object getValueAt(int col) {
		for (FileTableCell cell : mCells) {
			if(cell.getCol() == col) {
				return cell.getVal();
			}
		}
		return null;
		
	}
}
