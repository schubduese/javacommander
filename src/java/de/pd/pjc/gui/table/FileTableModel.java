package de.pd.pjc.gui.table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.pd.pjc.bean.FileElement;
import de.pd.pjc.gui.MainWindow;

public abstract class FileTableModel extends AbstractTableModel {

	protected Log mLog = LogFactory.getLog(MainWindow.class.getName());

	protected FileTableContainer data = new FileTableContainer();

	private int currentIndex = -1;

	public void addFile(FileElement pElement) {
		data.addRow(pElement);
	}

	public void addFiles(Collection<FileElement> pElements) {
		data.addRows(pElements);
	}

	public ArrayList<FileElement> getAllElements() {
		return data.getAllElements();
	}
	
	// Add an element in the end of the list
	public abstract void addElement(int index);

	// Get the row for a specific index
	public abstract int getRowForIndex(int index);

	// Get the column for a specific index
	public abstract int getColumnForIndex(int index);

	// Get the index for a specific row and column
	public abstract int getIndex(int row, int column);

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	public int getCurrentIndex(){
		return currentIndex;
	}

	public FileElement[] getAllElementsArray() {
		ArrayList<FileElement> allElements = getAllElements();
		FileElement[] elements = new FileElement[allElements.size()];
		int nn = 0;
		for (FileElement element : allElements) {
			elements[nn] = element;
			nn++;
		}
		return elements;
	}
	
	public void addToSelection(int index) throws Exception, Error{
      int min = 1;
      if(index < min || index >= data.getSize()) return;
      FileElement element = data.getFileElement(index);

//      if(element.isSelected()) listSelection.remove(new Integer(index));
//      else listSelection.add(new Integer(index));

//      element.setSelected(!element.isSelected());
  }

  //Add elements to the selection
  public void addToSelection(List<Integer> indexes, boolean only, String filter) throws Exception, Error{
//      if(only) clearSelection(filter);
//
//      int min = getMinimumToSelect();
//      if(indexes == null) return;
//      for (int i = 0; i < indexes.size(); i++) {
//          Integer Iindex = indexes.get(i);
//          int index = Iindex.intValue();
//          if(index < min || index >= listElements.size()) continue;
//          listElements.get(index).setSelected(true);
//          if(!only || !listSelection.contains(Iindex)) listSelection.add(Iindex);
//      }
  }

  //Add elements to the selection
  public void addToSelection(int start, int end, boolean only, String filter, boolean deselect) throws Exception, Error{
//      if(only) clearSelection(filter);
//
//      int min = getMinimumToSelect();
//      if(start < min) start = min;
//      for (int i = start; i <= end; i++) {
//          if(i >= listElements.size()) break;
//          listElements.get(i).setSelected(!deselect);
//          if(!deselect && (!only || !listSelection.contains(new Integer(i)))) listSelection.add(new Integer(i));
//      }
  }

	public FileElement getFileElement(int row) {
		return data.getFileElement(row);
	}

	public void clear() {
		data.clear();
	}

	public FileTableContainer getData() {
		return data;
	}

	public int getRowCount() {
		return data.getSize();
	}

	public Object getValueAt(int row, int col) {
		return data.getValueAt(row, col);
	}

	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	public boolean isCellEditable(int row, int col) {
		return false;
	}

	public void setValueAt(Object aValue, int row, int column) {
		mLog.info("Setting value at " + row);
	}

}
