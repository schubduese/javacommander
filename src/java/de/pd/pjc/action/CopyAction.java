package de.pd.pjc.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

import javax.swing.JOptionPane;

import de.pd.pjc.bean.FileElement;
import de.pd.pjc.gui.dialog.ActionProgressDialog;
import de.pd.pjc.util.PJCUtils;

public class CopyAction extends JCAction {

	private FileElement[] mElementsToCopy;

	private String mDestinationPath;

	protected String mDialogTitle = "Copying";

	public String getDestinationPath() {
		return mDestinationPath;
	}

	public void setDestinationPath(String pDestinationPath) {
		mDestinationPath = pDestinationPath;
	}

	public FileElement[] getElementsToCopy() {
		return mElementsToCopy;
	}

	public void setElementsToCopy(FileElement[] pElementsToCopy) {
		mElementsToCopy = pElementsToCopy;
	}

	public CopyAction(ActionProgressDialog pDialog) {
		super(pDialog);
	}

	@Override
	public void run() {

		if (mElementsToCopy == null || mElementsToCopy.length < 1) {
			return;
		}

		mDialog.setVisible(true);
		mDialog.actionLabel.setText(mDialogTitle);

		long totalFileSize = 0;
		for (FileElement fileElement : mElementsToCopy) {
			totalFileSize += fileElement.getFileSize();
		}
		mDialog.totalSizeLabel.setText(PJCUtils.fileSizeHumanReadable(totalFileSize).getFormatted());
		startTotalProgress(totalFileSize);
		startAction();

		stopTotalProgress();
	}

	@Override
	public void startAction() {

		int cnt = 1;
		for (FileElement fileElement : mElementsToCopy) {

			mDialog.nrOfFilesLabel
					.setText("" + cnt + "/" + mElementsToCopy.length);
			copyFile(fileElement, mDestinationPath);

			cnt++;

		}
	}

	/**
	 * Copies a file.
	 * 
	 * @param pSourceFile
	 * @param pDestPath
	 * @return
	 */
	protected boolean copyFile(FileElement pSourceFile, String pDestPath) {

		if(pSourceFile.isTopElement()) return false;
		
		// if source is directory then get the subelements a call the method recursive
		if (pSourceFile.isDirectory()) {
			String destPath = pDestPath + File.separator + pSourceFile.getFileName();
			mFileService.makeDirectory(destPath);
			
			try {
				Collection<FileElement> files = mFileService.getFiles(pSourceFile.getAbsolutePath());
				for (FileElement file : files) {
					copyFile(file, destPath);
				}
			} catch (FileNotFoundException e) {
				mLog.error("Error getting sub elements ",  e);
				return false;
			}
		} else {

			String inFile = pSourceFile.getAbsolutePath();
			String outFile = pDestPath + File.separator
					+ pSourceFile.getFileName();

			mDialog.fromLabel.setText(pSourceFile.getParentPath());
			mDialog.toLabel.setText(pDestPath);
			mDialog.filenameLabel.setText(pSourceFile.getFileName());

			// show confirm dialogue if file already exists.
			if (new File(outFile).exists()) {
				int confirm = JOptionPane.showConfirmDialog(mDialog,
						"The destination file " + outFile + " exists.\nDo you want"
								+ " to overwrite it?", "", JOptionPane.YES_NO_OPTION);
				if (confirm != JOptionPane.YES_OPTION) {
					return false;
				}
			}

			int fileProcess = 0;
			long fileSize = pSourceFile.getFileSize();
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new FileInputStream(inFile);
				out = new FileOutputStream(outFile);

				if (in == null || out == null)
					return false;
				// create the byte array
				byte[] data = new byte[1024*10];

				int read = 0;
				long doneSize = 0;
				while ((read = in.read(data)) >= 0) {
					if (mDialog.mCanceled) {
						mLog.debug("Canceld. abborting copy...");
						return false;
					}
					out.write(data, 0, read);

					doneSize += read;
					doneTotalSize += read;
					// calculate the file process
					fileProcess = (int) ((doneSize * 100) / fileSize);
					totalProcess = (int) ((doneTotalSize * 100) / totalFileSize);

					// update the dialogue labels
					mDialog.updateElementProgress(fileProcess);
					mDialog.updateTotalProgress(totalProcess);
					mDialog.bytesCopied.setText(PJCUtils.fileSizeHumanReadable(
							doneTotalSize).getFormatted() + " of " + PJCUtils.fileSizeHumanReadable(fileSize));
					long seconds = System.currentTimeMillis() - startTime;
					if (seconds > 0) {
						speed = 1000 * totalProcess / seconds;
						mDialog.speedLabel.setText(PJCUtils.fileSizeHumanReadable(
								speed).getFormatted());
						remain = totalProcess != 0 ? (totalFileSize - doneTotalSize)
								* seconds / (1000 * doneTotalSize) : 0;
						mDialog.remainLabel.setText("" + remain);
					}
				}

				out.flush();
				out.close();
				in.close();
			} catch (IOException e) {
				mLog.error("Error while copying", e);
				if(e.getMessage().contains("No space left on device")) {
					JOptionPane.showMessageDialog(mDialog, e.getMessage());
				}
			}

		}

		return true;
	}

}
