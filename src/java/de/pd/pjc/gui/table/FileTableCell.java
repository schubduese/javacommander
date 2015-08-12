package de.pd.pjc.gui.table;

public class FileTableCell {

	private int col;
	private Object val;
	public int getCol() {
		return col;
	}
	
	public FileTableCell(int pCol, Object pVal) {
		super();
		col = pCol;
		val = pVal;
	}

	public void setCol(int pCol) {
		col = pCol;
	}
	public Object getVal() {
		return val;
	}
	public void setVal(Object pVal) {
		val = pVal;
	}

	@Override
	public String toString() {
		return " Col: " + col + " Val" + val; 
	}
	
	
	
}
