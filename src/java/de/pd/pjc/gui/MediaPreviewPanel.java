package de.pd.pjc.gui;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

import de.pd.pjc.Settings;
import de.pd.pjc.bean.FileElement;
import de.pd.pjc.gui.media.MediaImagePanel;
import de.pd.pjc.gui.media.MediaPlayerPanel;
import de.pd.pjc.service.impl.FileServiceImpl;

/** Panel wrapper for some type of media preview panels (image/mp3...).
 * @author petros
 *
 */
public class MediaPreviewPanel extends JPanel {

	private FileElement mCurrentFile;

	private final static int WIDTH = 500;
	private final static int HEIGHT = 350;
	
	private String mMode = MODE_NONE;
	private final static String MODE_NONE = "none";
	private final static String MODE_IMAGE = "imageMode";
	private final static String MODE_SOUND = "soundMode";

	private MediaImagePanel imagePanel = new MediaImagePanel();
	private MediaPlayerPanel mediaPlayerPanel = new MediaPlayerPanel();
	
	public MediaPreviewPanel() {
		setSize(new Dimension(WIDTH, HEIGHT));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setMaximumSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Settings.DEFAULT_BG_COLOR);
		mMode = MODE_NONE;
		setLayout(null);
	}


	public void updatePanel(FileElement pFile) {
		mCurrentFile = pFile;
		if(FileServiceImpl.isImage(pFile.getFileName())) {
			if(!mMode.equals(MODE_IMAGE)) {
				imageMode();
			}
			imagePanel.showImage(mCurrentFile);
		} else if(FileServiceImpl.isAudioFile((pFile.getFileName()))) {
			if(!mMode.equals(MODE_SOUND)) {
				soundMode();
			}
			mediaPlayerPanel.showMediaInfo(mCurrentFile);
		} else {
			reset();
			if(!mCurrentFile.isDirectory()) {
				JButton button = new JButton("No preview for file type " + mCurrentFile.getType());
				button.setIcon(mCurrentFile.getBigIcon());
				button.setBorder(null);
				button.setFocusCycleRoot(true);
				button.setOpaque(false);
				add(button, 0);
			}
			mMode = MODE_NONE;
		}
	}
	
	/**
	 * switch to image preview mode.
	 * 
	 */
	private void imageMode() {
		reset();
		add(imagePanel);
		mMode = MODE_IMAGE;
	}

	/**
	 * 
	 */
	private void soundMode() {
		reset();
		add(mediaPlayerPanel);
		repaint();
		mMode = MODE_SOUND;
	}

	public void reset() {
		removeAll();
		repaint();
	}

}
