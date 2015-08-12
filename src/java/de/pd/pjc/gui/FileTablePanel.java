package de.pd.pjc.gui;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import de.pd.pjc.FileElementTransferHandler;
import de.pd.pjc.Settings;
import de.pd.pjc.gui.listener.TableKeyListener;
import de.pd.pjc.gui.listener.TableMouseListener;
import de.pd.pjc.gui.table.FileTable;
import de.pd.pjc.gui.table.FileTableModel;
import de.pd.pjc.gui.table.TableSorter;
import de.pd.pjc.gui.table.detail.DetailFileTable;
import de.pd.pjc.gui.table.detail.DetailFileTableModel;
import de.pd.pjc.service.ServiceFactory;

public class FileTablePanel extends JPanel {

	private JScrollPane mScrollPane;

	private FileTable mTable;

	public final static String LEFT_FILE_TABLE = "leftTable";
	public final static String RIGHT_FILE_TABLE = "rightTable";
	public final static String SEARCH_FILE_TABLE = "searchTable";
	public final static String ICON_FILE_TABLE = "iconTable";
	
	public void setActive() {
		Color color = Settings.TABLE_BG;
		mScrollPane.getViewport().setBackground(color);
		setBackground(color);
		mTable.setBackground(color);
	}
	
	/** Sets the background colors for inactive table panel.
	 * 
	 */
	public void setInActive() {
		Color color = Settings.TABLE_ROW_INACTIVE;
		mScrollPane.getViewport().setBackground(color);
		setBackground(color);
		mTable.setBackground(color);
	}

	public FileTablePanel(String pTableName) {
		mScrollPane = new JScrollPane();
		mScrollPane.setTransferHandler(new FileElementTransferHandler());
		
		TableSorter sorter = new TableSorter(new DetailFileTableModel());
		FileTableModel model = new DetailFileTableModel();
		mTable = new DetailFileTable(sorter);

		// TODO: replace with jdk sorter
//		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
//		sorter.setComparator(Constants.COLUMN_NAME, new FileColumnComparator(model));
//		mTable.setRowSorter(sorter);
		
		sorter.setTableHeader(mTable.getTableHeader());

		setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));
		mTable.setName(pTableName);
		mScrollPane.setViewportView(mTable);
		add(mScrollPane);
		addTableListener(mTable);
		
		setActive();
		
		mScrollPane.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent evt) {
				if(mTable.getName().equals(LEFT_FILE_TABLE)) {
					ServiceFactory.getMainWindowController().focusLeftPanel();
				} else {
					ServiceFactory.getMainWindowController().focusRightPanel();		
				}
				
				//	Right click
				if (evt.getButton() == MouseEvent.BUTTON3) {
					mTable.requestFocusInWindow();
					if (mTable.getSelectedRows().length <= 1) {
						int index = mTable.rowAtPoint(evt.getPoint());
						mTable.setCurrentIndex(index);
					}
					ServiceFactory.getMainWindowService().showPopup(evt, mTable);
				}
			}
			
		});
		
		addFocusListener(new FocusAdapter() {
		
			@Override
			public void focusGained(FocusEvent pE) {
				mTable.requestFocusInWindow();
			}
		
		});
			
	}
	
	public JScrollPane getScrollPane() {
		return mScrollPane;
	}

	public FileTable getTable() {
		return mTable;
	}

	private void addTableListener(JTable pTable) {
		pTable.addMouseListener(new TableMouseListener());
		pTable.addKeyListener(new TableKeyListener());
//		pTable.addFocusListener(new TableFocusListener(pTable.getName()));
	}
	
}

