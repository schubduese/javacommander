package de.pd.pjc.gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import de.pd.pjc.gui.listener.TableKeyListener;
import de.pd.pjc.gui.listener.TableMouseListener;
import de.pd.pjc.gui.table.FileTable;
import de.pd.pjc.gui.table.TableSorter;
import de.pd.pjc.gui.table.detail.DetailFileTable;
import de.pd.pjc.gui.table.detail.DetailFileTableModel;

public class PJPanelFactory {

	public PJPanelFactory() {
	}

	/**
	 * @return
	 */
	public JPanel createFileTablePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new javax.swing.BoxLayout(panel,
				javax.swing.BoxLayout.Y_AXIS));
		JScrollPane sp = new JScrollPane();
		TableSorter sorter = new TableSorter(new DetailFileTableModel());
		FileTable table = new DetailFileTable(sorter);
		table.setName("leftTable");
		sp.setViewportView(table);
		panel.add(sp);
		addTableListener(table);

		return panel;
	}

	/**
	 * @param pTable
	 */
	private void addTableListener(JTable pTable) {
		pTable.addMouseListener(new TableMouseListener());
		pTable.addKeyListener(new TableKeyListener());
	}
}
