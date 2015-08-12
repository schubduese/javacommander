package de.pd.pjc.gui.table.icon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.pd.pjc.gui.table.FileTableModel;

public class IconFileTableModel extends FileTableModel {

	protected Log mLog = LogFactory.getLog(IconFileTableModel.class.getName());
	
	private int colCount = 3;
	
	public int getRowCount() {
      return getRowForIndex(data.getSize() - 1) + 1;
  }
	
   public Object getValueAt(int row, int column) {
      int index = getIndex(row, column);
      if(index < 0 || index >= data.getSize()) return null;
      return data.getAllElements().get(index);
  }
	
	@Override
	public void addElement(int pIndex) {
		fireTableCellUpdated(getRowForIndex(pIndex), getColumnForIndex(pIndex));
	}

	@Override
	public int getColumnForIndex(int pIndex) {
		return pIndex % getColumnCount();
	}

	@Override
	public int getIndex(int pRow, int pColumn) {
		return pRow * getColumnCount() + pColumn;
	}

	@Override
	public int getRowForIndex(int pIndex) {
		return (int)Math.floor(pIndex / getColumnCount());
	}

	public int getColumnCount() {
		return colCount;
	}

	public void setColCount(int pColCount) {
		colCount = pColCount;
	}

	
}
