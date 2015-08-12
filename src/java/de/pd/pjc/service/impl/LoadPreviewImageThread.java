package de.pd.pjc.service.impl;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.pd.pjc.Settings;
import de.pd.pjc.bean.FileElement;
import de.pd.pjc.util.IconFactory;

/**
 * @author petros
 *
 */
public class LoadPreviewImageThread implements Runnable {

	FileElement mElement;
	JTable mTable;
	
	private static int imgLoad = 0;
	protected Log mLog = LogFactory.getLog(LoadPreviewImageThread.class.getName());
	
	public LoadPreviewImageThread(FileElement pElement, JTable pTable) {
		mElement = pElement;
		mTable = pTable;
	}

	public void run() {
		
		if(imgLoad > 1) {
			return;
		}
		
		imgLoad ++;
		ImageIcon previewImage = IconFactory.getPreviewImage(mElement, Settings.THUMB_WIDTH, Settings.THUMB_HEIGHT);
		imgLoad --;
		mElement.setPreviewIcon(previewImage);
		SwingUtilities.invokeLater(new Runnable() {
		
			public void run() {
				mTable.repaint();
			}
		
		});
	}
	
}
