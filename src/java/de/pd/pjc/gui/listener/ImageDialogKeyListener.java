/**
 * PJavaCommander by Petros Dolaschjan
 *
 */ 
package de.pd.pjc.gui.listener;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import de.pd.pjc.gui.dialog.JCImageDialog;
import de.pd.pjc.gui.dialog.JCThumbDialog;

/** Key listener for the image dialog.
 * @author petros
 *
 */
public class ImageDialogKeyListener extends KeyAdapter {

	JCImageDialog mDialog;
	
	public ImageDialogKeyListener(JCImageDialog pDialog) {
		super();
		mDialog = pDialog;
	}

	@Override
	public void keyPressed(KeyEvent pE) {
		if (pE.getKeyCode() == KeyEvent.VK_F) {
			// fullscreen
			if (!mDialog.isDisplayable()) mDialog.setUndecorated(false);
			GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
			gd.setFullScreenWindow(mDialog);
			GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(mDialog);
			gd.setFullScreenWindow(mDialog);
			mDialog.setVisible(true);
			// next image
		} else if (pE.getKeyCode() == KeyEvent.VK_SPACE) {
			mDialog.nextImage(1);
		} else if (pE.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			mDialog.prevImage(1);
			// copy image
		} else if (pE.getKeyCode() == KeyEvent.VK_C && pE.isControlDown()) {
			mDialog.copyImage();
		} else if (pE.getKeyCode() == KeyEvent.VK_T) {
			mDialog.setVisible(false);
			JCThumbDialog dia = new JCThumbDialog(mDialog.getFileArr());
			dia.setVisible(true);
		} else if(pE.getKeyCode() == KeyEvent.VK_Q) {
			mDialog.setVisible(false);
		} else if(pE.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
			mDialog.nextImage(10);
		} else if(pE.getKeyCode() == KeyEvent.VK_PAGE_UP) {
			mDialog.prevImage(10);
		}
	}

}


/*
 * $Log$
 */