package de.pd.pjc.gui.table.icon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.pd.pjc.Settings;
import de.pd.pjc.bean.FileElement;
import de.pd.pjc.gui.JCImageButton;
import de.pd.pjc.gui.table.FileTable;
import de.pd.pjc.gui.table.FileTableModel;
import de.pd.pjc.service.impl.FileServiceImpl;
import de.pd.pjc.service.impl.LoadPreviewImageThread;
import de.pd.pjc.util.IconFactory;

public class IconCellRenderer extends DefaultTableCellRenderer {

	protected Log mLog = LogFactory.getLog(IconCellRenderer.class.getName());

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component c = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);

		JLabel oldlabel = (JLabel) c;
		JLabel label = new JLabel(value == null ? "" : value.toString());

		if (!(c instanceof JLabel))
			return c;

		FileTableModel model = (FileTableModel) table.getModel();
		int index = model.getIndex(row, column);
		int currentindex = ((FileTable) table).getCurrentIndex();

//		label.setOpaque(true);
		// label.setFont(Maestro.getGeneralFont());
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(SwingConstants.TOP);

		FileElement element = value instanceof FileElement ? (FileElement) value
				: null;
		Icon icon = null;
		
		if (element != null) {
			if (element.isTopElement()) {
				icon = IconFactory.getIconByName("up.png");
			} else {
				
				if(element.getPreviewIcon() == null && FileServiceImpl.isImage(element.getAbsolutePath())) {
					Runnable iconThread = new LoadPreviewImageThread(element, table);
					
					Thread thread = new Thread(iconThread);
					thread.start();
				}
				
				icon = element.getPreviewIcon();
				if(icon == null)
					icon = element.getBigIcon();
			}
		}
		JButton btn = new JCImageButton();
		btn.setIcon(icon);
		
		Color defBgColor = Color.white;
		
		JPanel panel = new JPanel(new BorderLayout());
		JPanel panlab = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 2));
		panlab.setBackground(defBgColor);
		panlab.setForeground(Color.black);
		label.setBackground(defBgColor);
		label.setForeground(Color.black);
		panlab.add(label);

//		panel.setBorder(new javax.swing.border.EmptyBorder(3, 3, 3, 3));
		label.setBorder(new javax.swing.border.EmptyBorder(0, 2, 0, 2));

		panel.setBackground(defBgColor);
		panel.add(btn, BorderLayout.CENTER);
		panel.add(panlab, BorderLayout.SOUTH);

		// setting background
		if (currentindex == index && table.isCellSelected(row, column)) {
			label.setBackground(null);
			panel.setBackground(Settings.TABLE_ROW_ODD);
			panlab.setBackground(Settings.TABLE_ROW_ODD);
		} else if (isSelected) {
			label.setBackground(Settings.TABLE_ROW_FILENAME);
			panel.setBackground(Settings.TABLE_ROW_ODD);
			panlab.setBackground(Settings.TABLE_ROW_ODD);
		} else {
			label.setBackground(oldlabel.getBackground());
		}

		// setting foreground
		// if (((FileTable) table).isIndexSelected(index)) {
		// label.setForeground(Maestro.getOptions().getColorOption(
		// Options.OPTION_SELECT_FOREGROUND));
		// } else if (currentindex == index && table.isCellSelected(row, column))
		// {
		// // System.err.println("index: " + index + " - currentindex: " +
		// // currentindex);
		// label.setForeground(Maestro.getOptions().getColorOption(
		// Options.OPTION_CURRENT_FOREGROUND));
		// } else {
		// label.setForeground(Maestro.getForeground());
		// }

		if (element != null && !element.isTopElement())
			panel.setToolTipText(element.getFileName());

		return panel;
	}
}
