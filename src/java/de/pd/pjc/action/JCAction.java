package de.pd.pjc.action;

import javax.swing.SwingUtilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.pd.pjc.gui.dialog.ActionProgressDialog;
import de.pd.pjc.service.FileService;
import de.pd.pjc.service.ServiceFactory;

public abstract class JCAction implements Runnable {

	protected ActionProgressDialog mDialog;

	protected String mDialogTitle = "Action";

	protected static Log mLog = LogFactory.getLog(JCAction.class.getName());

	protected long startTime;

	protected long speed;
	protected long remain;
	
	protected int totalProcess = 0;
	protected long totalFileSize = 0;
	protected long doneTotalSize = 0;
	
	protected FileService mFileService;

	public JCAction(ActionProgressDialog pDialog) {
		mDialog = pDialog;
	}

	public abstract void run();

	public synchronized void doWait() throws InterruptedException {
		wait(100);
	}

	protected void startTotalProgress(long pTotalFileSize) {
		totalProcess = 0;
		totalFileSize = 0;
		doneTotalSize = 0;
		totalFileSize = pTotalFileSize;
		startTime = System.currentTimeMillis();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void startAction() {

		int progress = 0;
		while (progress < 100) {
			mDialog.updateElementProgress(progress);

			progress++;

			try {
				doWait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			mLog.debug("running " + progress);
		}

		mDialog.setVisible(false);
	}

	public void stopTotalProgress() {
		SwingUtilities.invokeLater(new Runnable() {
		
			public void run() {
				ServiceFactory.getMainWindowService().reloadTables();
				mDialog.setVisible(false);
			}
		
		});
	}

	/**
	 * @return the fileService
	 */
	public FileService getFileService() {
		return mFileService;
	}

	/**
	 * @param pFileService the fileService to set
	 */
	public void setFileService(FileService pFileService) {
		mFileService = pFileService;
	}
	
}
