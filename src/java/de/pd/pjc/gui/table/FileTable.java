package de.pd.pjc.gui.table;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.InputMap;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.pd.pjc.Constants;
import de.pd.pjc.FileElementTransferHandler;
import de.pd.pjc.Settings;
import de.pd.pjc.bean.FileElement;
import de.pd.pjc.gui.MainWindow;
import de.pd.pjc.gui.table.detail.DetailFileCellRenderer;
import de.pd.pjc.gui.table.detail.DetailFileTable;
import de.pd.pjc.util.PJCUtils;

public abstract class FileTable extends JTable {

	protected Log mLog = LogFactory.getLog(MainWindow.class.getName());

	private boolean mActive;

	protected boolean ctrlDown, shiftDown, altDown;

	protected int indexOfElementAtMousePosition = -1;
	
	private String mCurrentPath;
	
	public String getCurrentPath() {
		return mCurrentPath;
	}

	public void setCurrentPath(String pCurrentPath) {
		mCurrentPath = pCurrentPath;
	}

	public FileTable(TableModel pModel) {
		super(pModel);
		removeDefaultKeys();
	}

	public FileTable(TableSorter pSorter) {
		super(pSorter);
		initSettings();
	}

	private void initSettings() {
		setProperties();

		TableColumnModel columnModel = getColumnModel();

		// set the column widths
		columnModel.getColumn(Constants.COLUMN_NAME).setPreferredWidth(200);

		columnModel.getColumn(Constants.COLUMN_TYPE).setMaxWidth(60);

		columnModel.getColumn(Constants.COLUMN_SIZE).setMaxWidth(80);

		columnModel.getColumn(Constants.COLUMN_TIME).setPreferredWidth(100);
		columnModel.getColumn(Constants.COLUMN_TIME).setMaxWidth(160);

		removeDefaultKeys();
	}

	public abstract void setCurrentIndex(int index);

	public void setCurrentIndexes(int[] pIndexes) {
		Collection<Integer> list = new ArrayList<Integer>();
		for (int ii = 0; ii < pIndexes.length; ii++) {
			int idx = pIndexes[ii];
			list.add(new Integer(idx));
		}
		setCurrentIndexes(list);
	}
	
	public void setCurrentIndexes(Collection<Integer> indexes) {
		getSelectionModel().clearSelection();
		int firstIndex = -1;
		for (Integer index : indexes) {
			getSelectionModel().setSelectionMode(
					ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			getSelectionModel().addSelectionInterval(index, index);
			if (firstIndex == -1)
				firstIndex = index;
		}
		scrollRectToVisible(getCellRect(firstIndex, 0, false));
		repaint();
	}

	/**
	 * @param name
	 * @throws Exception
	 * @throws Error
	 */
	public void movePositionByName(String name) {

		if (name == null)
			return;
		
		
		ArrayList<FileElement> allElements = getFileModel().getAllElements();
		if (null == allElements || allElements.isEmpty())
			return;

		findPositionByName(name);
	}

	public void movePositionByPattern(String pPattern) {

		if (pPattern == null)
			return;
		ArrayList<FileElement> allElements = ((FileTableModel) ((TableSorter) getModel()).tableModel)
				.getAllElements();
		if (null == allElements || allElements.isEmpty())
			return;

		findPositionByPattern(pPattern);
	}

	/**
	 * @param name
	 * @param elements
	 * @param start
	 * @param end
	 * @return
	 * @throws Exception
	 * @throws Error
	 */
	protected boolean findPositionByName(String name) {

		TableSorter sorter = getSorter();
		if(sorter == null) {
			return false;
		}
		for (int idx = 0; idx < sorter.getRowCount(); idx++) {
			FileElement element = (FileElement) sorter.getValueAt(idx,
					Constants.COLUMN_NAME);
			if (element.getFileName().toLowerCase().startsWith(name.toLowerCase())) {
				setCurrentIndex(idx);
				return true;
			}
		}
		return false;
	}
	
	private void findPositionByPattern(String pPattern) {

		Collection<Integer> selectedIndexes = new ArrayList<Integer>();
		TableSorter sorter = (TableSorter) getModel();
		for (int idx = 0; idx < sorter.getRowCount(); idx++) {
			FileElement element = (FileElement) sorter.getValueAt(idx,
					Constants.COLUMN_NAME);
			if (PJCUtils.stringMatchesPattern(element.getFileName(), pPattern)) {
				selectedIndexes.add(idx);
			}
		}
		setCurrentIndexes(selectedIndexes);
	}

	/**
	 * 
	 */
	private void setProperties() {

		setFocusCycleRoot(true);
		setFocusTraversalKeysEnabled(false);

		setDragEnabled(true);
		setTransferHandler(new FileElementTransferHandler());

		setShowHorizontalLines(false);
		setShowVerticalLines(false);
		setRowSelectionAllowed(true);
		setColumnSelectionAllowed(false);
		setSurrendersFocusOnKeystroke(true);

		setRowMargin(0);
		
		setRowHeight(Settings.DEFAULT_TABLE_ROW_HEIGHT);
		getColumnModel().setColumnMargin(0);
		getTableHeader().setReorderingAllowed(false);
		setBackground(Color.WHITE);

	}

	private void removeDefaultKeys() {
		InputMap map = getInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		map.clear();
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "noaction");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "noaction");
		// map.put(KeyStroke.getKeyStroke(KeyEvent.VK_HOME, 0), "noaction");
		// map.put(KeyStroke.getKeyStroke(KeyEvent.VK_END, 0), "noaction");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 0), "noaction");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "noaction");
		// map.put(KeyStroke.getKeyStroke(KeyEvent.VK_A,
		// KeyEvent.CTRL_MASK),"noaction");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_MASK),
				"noaction");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK),
				"noaction");
		map.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK),
				"noaction");
		// map.put(KeyStroke.getKeyStroke(KeyEvent.VK_CONTEXT_MENU,
		// 0),"context_menu");
		getActionMap().put("noaction", null);
		// getActionMap().put("context_menu", new PropertiesAction());
	}

	@Override
	public TableCellRenderer getCellRenderer(int pRow, int pColumn) {
		return new DetailFileCellRenderer();
	}

	public boolean isActive() {
		return mActive;
	}

	public void setActive(boolean pActive) {
		mActive = pActive;
	}

	public void selectElement(int index) throws Exception, Error {
		((FileTableModel) getModel()).addToSelection(index);
		repaint();
		// selectElements(index, index, false, null, true);
	}

	public void selectElements(int start, int end, boolean only, String filter,
			boolean deselect) throws Exception, Error {
		selectElements(start, end, only, filter, deselect, true);
	}

	public void selectElements(int start, int end, boolean only, String filter,
			boolean deselect, boolean repaint) throws Exception, Error {
		((FileTableModel) getModel()).addToSelection(start, end, only, filter,
				deselect);
		if (repaint)
			repaint();
	}

	public void selectElements(List<Integer> indexes, boolean only,
			String filter, boolean repaint) throws Exception, Error {
		((FileTableModel) getModel()).addToSelection(indexes, only, filter);
		if (repaint)
			repaint();
	}
	
	public int getCurrentIndex() {
		TableModel model = getModel();
		if(model instanceof TableSorter) {
			return ((FileTableModel) ((TableSorter) getModel()).tableModel).getCurrentIndex();	
		} else if(model instanceof FileTableModel) {
			return ((FileTableModel) getModel()).getCurrentIndex();
		}
		throw new IllegalArgumentException("Model class cast");
      
  }

	/**
	 * @return
	 */
	public FileTableModel getFileModel() {
		TableModel model = getModel();
		FileTableModel fileModel = null;
		if(model instanceof TableSorter) {
			fileModel = (FileTableModel) ((TableSorter)model).getTableModel();
		} else {
			fileModel = (FileTableModel) model;
		}
		return fileModel;
	}
	
	/**
	 * @return
	 */
	public TableSorter getSorter() {
		TableModel model = getModel();
		if(model instanceof TableSorter) {
			return (TableSorter) model;
		} else {
			return null;
		}
	}
	
	public String getDestPath() {
		String destPath = getCurrentPath();
		if(getSelectedFileElements()[0].isDirectory() && !getSelectedFileElements()[0].isTopElement()) {
			destPath = getSelectedFileElements()[0].getAbsolutePath();
		}
		return destPath;
	}
	
	/**
	 * returns the selected file element.
	 * 
	 * @return
	 */
	public FileElement[] getSelectedFileElements() {
		int[] selectedRows = getSelectedRows();
		int selectedColumn = getSelectedColumn();
		if (this instanceof DetailFileTable) {
			selectedColumn = Constants.COLUMN_NAME;
		}

		FileElement[] elements = new FileElement[selectedRows.length];

		for (int ii = 0; ii < selectedRows.length; ii++) {
			FileElement fileElement = (FileElement) getModel().getValueAt(selectedRows[ii],
							selectedColumn);
			elements[ii] = fileElement;
		}

		return elements;
	}
	
	
}
