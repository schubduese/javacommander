package de.pd.pjc.gui.table.detail;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import de.pd.pjc.Constants;
import de.pd.pjc.Settings;
import de.pd.pjc.bean.FileElement;
import de.pd.pjc.bean.SessionBean;
import de.pd.pjc.service.impl.FileServiceImpl;
import de.pd.pjc.service.impl.LoadPreviewImageThread;
import de.pd.pjc.util.PJCUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/** Renders a detail table cell.
 * @author petros
 *
 */
public class DetailFileCellRenderer extends DefaultTableCellRenderer {

	protected Log mLog = LogFactory.getLog(DetailFileCellRenderer.class
			.getName());

	/* (non-Javadoc)
	 * @see javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		Component c = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);

		if (!(c instanceof JLabel)) {
			return c;
		}

		JLabel label = (JLabel) c;
		
		setBorder(new javax.swing.border.EmptyBorder(0, 3, 0, 3));
		int alignMent = JLabel.LEFT;
		if (column == Constants.COLUMN_SIZE) {
			alignMent = JLabel.RIGHT;
			if(label.getText().equals("0 Items")) {
				label.setForeground(Color.red);
			}
		} else if (column == Constants.COLUMN_TYPE) {
			alignMent = JLabel.CENTER;
		}
		label.setHorizontalAlignment(alignMent);
//		label.putClientProperty(com.sun.java.swing.SwingUtilities2.AA_TEXT_PROPERTY_KEY, Boolean.TRUE );
		// if rc mode then bigger label fonts
		if(SessionBean.rcMode) {
			label.setFont(Constants.RC_MODE_FONT);
		}

		
//		if (row % 2 != 0 && !isSelected) {
//			label.setBackground(Settings.TABLE_ROW_ODD);
//		}
		
		if (value instanceof FileElement) {
			
			ImageIcon icon = null;
			FileElement fileElement = (FileElement) value;

			boolean isIconBig = table.getRowHeight() == Settings.TABLE_ROW_HEIGHT_BIG;
			if (isIconBig) {
				icon = fileElement.getBigIcon();
			} else {
				icon = fileElement.getNormalIcon();
			}
			
			
			if(Settings.SHOW_IMAGE_PREVIEW && table.getRowHeight() == Settings.TABLE_ROW_HEIGHT_BIG) {
				
				if(fileElement.getPreviewIcon() == null && FileServiceImpl.isImage(fileElement.getAbsolutePath())) {
					Runnable iconThread = new LoadPreviewImageThread(fileElement, table);
					
					Thread thread = new Thread(iconThread);
					thread.start();
					
				}
				
				ImageIcon previewIcon = fileElement.getPreviewIcon();
				if(previewIcon != null) {
					icon = previewIcon;
				}
			}
			
			label.setIcon(icon);
			if(!fileElement.isTopElement()) {
				label.setToolTipText(getFileToolTip(fileElement));
			}

			label.setText(((FileElement) value).getFileName());
		}

		// else label.setIcon(null);
		return label;
	}
	
	private String getFileToolTip(FileElement pElement) {
		StringBuffer buff = new StringBuffer();
		buff.append("<html>");
		buff.append("<b>Filename: </b>" + pElement + "<br>");
		buff.append("<b>Path: </b>" + pElement.getAbsolutePath() + "<br>");
		buff.append("<b>Filesize: </b>" + PJCUtils.fileSizeHumanReadable(pElement.getFileSize()) + "<br>");
		buff.append("<b>Modified at: </b>" + Constants.DEFAULT_DATE_FORMAT.format(pElement.getLastModified()) + "<br>");
//		if (FileServiceImpl.isImage(pElement.getFileName())) {
//			Dimension imageResolution = IconFactory
//					.getImageResolution(pElement);
//			if (imageResolution != null) {
//				buff.append("<b>Resolution:</b> " + imageResolution.width + "x"
//						+ imageResolution.height);
//			}
//		}
		buff.append("</html>");
		
		return buff.toString();
	}
}