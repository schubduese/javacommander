package de.pd.pjc.gui.table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.pd.pjc.Constants;
import de.pd.pjc.bean.FileElement;
import de.pd.pjc.bean.FileSizeBean;
import de.pd.pjc.util.PJCUtils;

public class FileTableContainer {

	protected Log mLog = LogFactory.getLog(FileTableContainer.class.getName());
	
	private ArrayList<FileElement> mFiles = new ArrayList<FileElement>();
	
	public void clear() {
		mFiles.clear();
	}
	
	public void addRow(FileElement pElement) {
		mFiles.add(pElement);
	}
	
	public void addRows(Collection<FileElement> pElements) {
		mFiles.addAll(pElements);
	}
	
	public int getSize() {
		return mFiles.size();
	}
	
	public FileElement getFileElement(int row) {
		return mFiles.get(row);
	}
	
	public ArrayList<FileElement> getAllElements() {
		return mFiles;
	}
	
	/**
	 * @param row
	 * @param col
	 * @return
	 */
	public Object getValueAt(int row, int col) {
		
		FileElement element = mFiles.get(row);
		
		switch (col) {
		case Constants.COLUMN_NAME:
			return element;
			
		case Constants.COLUMN_TYPE:
			if(element.isDirectory()) return "";
			return (element.getType() != null && element.getType().length() > 4? element.getType().substring(0, 4) + ".." : element.getType());

		case Constants.COLUMN_SIZE:
			if(element.isDirectory() && element.getDirContent() != null) return new FileSizeBean(0l, element.getDirContent());
			if(element.isDirectory()) return new FileSizeBean(0l, "<DIR>");
			return PJCUtils.fileSizeHumanReadable(element.getFileSize());
			
		case Constants.COLUMN_TIME:
			if(element.getLastModified() == null) return "";
			return getDateFormatted(element.getLastModified());
		default:
			return null;
		}
	}
	
	/**
	 * @param pDate
	 * @return
	 */
	private String getDateFormatted(Date pDate) {
		String string = Constants.DEFAULT_DATE_FORMAT.format(pDate);
		String today = Constants.DEFAULT_DATE_FORMAT.format(new Date());
		if(string.startsWith(today.substring(0, 10))) {
			string = "Today";
		}
		return string;
	}
}
