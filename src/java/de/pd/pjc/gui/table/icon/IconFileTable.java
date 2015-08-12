package de.pd.pjc.gui.table.icon;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import de.pd.pjc.FileElementTransferHandler;
import de.pd.pjc.Settings;
import de.pd.pjc.bean.FileElement;
import de.pd.pjc.gui.FileTablePanel;
import de.pd.pjc.gui.table.FileTable;
import de.pd.pjc.gui.table.FileTableModel;
import de.pd.pjc.gui.table.TableSorter;

public class IconFileTable extends FileTable {

	private int currentCol = 0, oldCol, currentRow = 0;

	public IconFileTable(TableModel pModel) {
		super(pModel);
		setHeader();
		setSelectionType();
		setName(FileTablePanel.ICON_FILE_TABLE);
	}

	public IconFileTable(TableSorter pSorter) {
		super(pSorter);
	}

	private void setHeader() {
		setRowHeight(Settings.TABLE_ICON_HEIGHT);
		TableColumnModel tcm = getColumnModel();
		for (int i = 0; i < tcm.getColumnCount(); i++) {
			tcm.getColumn(i).setPreferredWidth(Settings.TABLE_ICON_WIDTH);
		}
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setTableHeader(null);
	}

	private void setSelectionType() {
		setDragEnabled(true);
		setTransferHandler(new FileElementTransferHandler());
		
		setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		setCellSelectionEnabled(true);

		getColumnModel().getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						try {
							ListSelectionModel lsm = (ListSelectionModel) e
									.getSource();
							if (lsm.isSelectionEmpty())
								return;
							if (e.getValueIsAdjusting())
								return;

							int first = e.getFirstIndex();
							int last = e.getLastIndex();
							oldCol = currentCol;
							currentCol = last == currentCol ? first : last;
							indexOfElementAtMousePosition = ((FileTableModel) getModel())
									.getIndex(currentRow, currentCol);
							((FileTableModel) getModel())
									.setCurrentIndex(indexOfElementAtMousePosition);

							// if(first == last) return;
							int index1 = ((FileTableModel) getModel()).getIndex(
									currentRow, first);
							int index2 = ((FileTableModel) getModel()).getIndex(
									currentRow, last);
							if (shiftDown)
								selectElements(index1, index2, true, null, false);
							else if (ctrlDown)
								selectElement(indexOfElementAtMousePosition); // (***)

						} catch (Exception ex) {
							mLog.error(ex);
						} catch (Error er) {
							mLog.error(er);
						}

					}
				});

		getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				try {
					ListSelectionModel lsm = (ListSelectionModel) e.getSource();
					if (lsm.isSelectionEmpty())
						return;
					if (e.getValueIsAdjusting())
						return;

					int first = e.getFirstIndex();
					int last = e.getLastIndex();

					int oldRow = currentRow;
					int index1, index2;
					if (last == currentRow) {
						currentRow = first;
						index1 = ((FileTableModel) getModel()).getIndex(first,
								currentCol);
						index2 = ((FileTableModel) getModel()).getIndex(last, oldCol);
					} else {
						currentRow = last;
						index1 = ((FileTableModel) getModel())
								.getIndex(first, oldCol);
						index2 = ((FileTableModel) getModel()).getIndex(last,
								currentCol);
					}
					indexOfElementAtMousePosition = ((FileTableModel) getModel())
							.getIndex(currentRow, currentCol);
					((FileTableModel) getModel())
							.setCurrentIndex(indexOfElementAtMousePosition);

					// if(first == last) return;
					if (shiftDown) {
						if (index1 <= index2)
							selectElements(index1, index2, true, null, false);
						else
							selectElements(index2, index1, true, null, false);
					} else if (ctrlDown) {
						// unselect the element selected in the listener for columns -
						// in the line with (***)
						selectElement(((FileTableModel) getModel()).getIndex(oldRow,
								currentCol));

						// select the right element
						selectElement(((FileTableModel) getModel()).getIndex(
								currentRow, currentCol));
					}
				} catch (Exception ex) {
					mLog.error(ex);
				} catch (Error er) {
					mLog.error(er);
				}
			}
		});
	}

	@Override
	public TableCellRenderer getCellRenderer(int pRow, int pColumn) {
		return new IconCellRenderer();
	}

	public void setCurrentIndex(int pRowIdx, int pColIdx) {
		getSelectionModel().setSelectionInterval(pRowIdx, pRowIdx);
		scrollRectToVisible(getCellRect(pRowIdx, pColIdx, false));
		repaint();
	}
	
	public int getCellWidth(){
      return getIconSize() + (false ? 6 : 36); // TODO: false = is windows
  }

  public int getCellHeight(){
      return 30;
  }

  public int getIconSize(){
      return 30;
  }

@Override
protected boolean findPositionByName(String pName) {
	TableModel model = getModel();
	if(model == null) {
		return false;
	}
	for (int idx = 0; idx < model.getRowCount(); idx++) {
		for(int idx2 = 0; idx2 < model.getColumnCount(); idx2++) {
			FileElement element = (FileElement) model.getValueAt(idx,idx2);
			if (element != null && element.getFileName().toLowerCase().startsWith(pName.toLowerCase())) {
				setCurrentIndex(idx);
				return true;
			}
		}
	}
	return false;
}

@Override
public void setCurrentIndex(int pIndex) {
	getSelectionModel().setSelectionInterval(3, 3);
	scrollRectToVisible(getCellRect(1, 3, false));
	repaint();
}
  
  

}
