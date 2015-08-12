package de.pd.pjc.action;

import java.io.File;

import de.pd.pjc.bean.FileElement;
import de.pd.pjc.gui.dialog.ActionProgressDialog;

public class MoveAction extends CopyAction {

	private FileElement[] mElementsToMove;
	
	public MoveAction(ActionProgressDialog pDialog) {
		super(pDialog);
	}

	@Override
	public void run() {

		if(mElementsToMove == null || mElementsToMove.length < 1) {
			return;
		}
		
		mDialog.setVisible(true);
		mDialog.actionLabel.setText(mDialogTitle);

		long totalFileSize = 0;
		for (FileElement fileElement : mElementsToMove) {
			totalFileSize += fileElement.getFileSize();
		}
		startTotalProgress(totalFileSize);
		startAction();
		
		stopTotalProgress();
	}
	
	@Override
	public void startAction() {

		boolean moveByRename = false;
		
		int cnt = 1;
		for (FileElement fileElement : mElementsToMove) {
			
			if(!moveByRename) {
				mDialog.nrOfFilesLabel.setText("" + cnt + "/" + mElementsToMove.length);
				boolean success = copyFile(fileElement, getDestinationPath());
				if(success) {
					mFileService.delete(fileElement.getAbsolutePath());
				}
			} else {
				String srcFile = fileElement.getAbsolutePath();
				String dstFile = getDestinationPath() + File.separator + fileElement.getFileName();
				mFileService.moveFile(srcFile, dstFile);
			}
			
			cnt++;

		}
	}

	/**
	 * @return the elementsToMove
	 */
	public FileElement[] getElementsToMove() {
		return mElementsToMove;
	}

	/**
	 * @param pElementsToMove the elementsToMove to set
	 */
	public void setElementsToMove(FileElement[] pElementsToMove) {
		mElementsToMove = pElementsToMove;
	}
	
}
