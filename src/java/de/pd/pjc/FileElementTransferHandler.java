/**
 * PJavaCommander by Petros Dolaschjan
 *
 */
package de.pd.pjc;

import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.TransferHandler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.pd.pjc.bean.FileElement;
import de.pd.pjc.gui.FileTablePanel;
import de.pd.pjc.gui.menu.JCDndPopupMenu;
import de.pd.pjc.gui.table.FileTable;

public class FileElementTransferHandler extends TransferHandler {

	public static Log mLog = LogFactory.getLog(FileElementTransferHandler.class.getName());

	DataFlavor mFlavor = DataFlavor.javaFileListFlavor;
	
   private String remoteStringFlavorType = "text/uri-list; class=java.lang.String";
   DataFlavor mStringFlavor;

	FileElement[] mSourceFiles;

	boolean shouldRemove;
	
	public FileElementTransferHandler() {
		try {
			mStringFlavor = new DataFlavor(remoteStringFlavorType);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean importData(JComponent pComp, Transferable pT) {
		FileTable table =getFileTable(pComp);
		String destPath = table.getDestPath();
		
		try {
			FileElement[] elements = (FileElement[]) pT.getTransferData(mFlavor);
			// dest path must not be source path
			if(elements[0].getParentPath().equals(destPath)) {
				return false;
			}
			Point mousePosition = pComp.getMousePosition();
			JCDndPopupMenu popup = new JCDndPopupMenu(elements, destPath);
			popup.show(pComp, mousePosition.x, mousePosition.y);
		} catch (UnsupportedFlavorException ex) {
			mLog.error(ex);
		} catch (IOException ex) {
			mLog.error(ex);
		}
		return true;
	}

	/** Checks the component class type and returns the file table.
	 * @param pComp
	 * @param table
	 * @return null if the class is not supported.
	 * @throws IllegalAccessError
	 */
	private FileTable getFileTable(JComponent pComp) throws IllegalAccessError {
		FileTable table = null;
		if (pComp instanceof JScrollPane) {
			JScrollPane sp = (JScrollPane) pComp;
			table = (FileTable) sp.getViewport().getView();
		} else if (pComp instanceof FileTable) {
			table = (FileTable) pComp;
		} else {
			throw new IllegalAccessError("Class " + pComp.getClass().getName() + " not supported");
		}
		return table;
	}

	@Override
	protected Transferable createTransferable(JComponent pC) {
		FileTable table = (FileTable) pC;
		FileElement[] elements = table.getSelectedFileElements();
		shouldRemove = true;
		boolean isLinux = false;
		if(isLinux) {
			String str = "file://" + elements[0].getAbsolutePath();
			return new StringFileTransferable(str);
		} else {
			return new FileElementTransferable(elements);
		}
	}
	
	@Override
	public int getSourceActions(JComponent pC) {
		return COPY_OR_MOVE;
	}

	@Override
	protected void exportDone(JComponent pSource, Transferable pData, int pAction) {
		mSourceFiles = null;
	}

	@Override
	public boolean canImport(JComponent pComp, DataFlavor[] pTransferFlavors) {
		FileTable fileTable = getFileTable(pComp);
		// file table null
		if(fileTable == null) {
			return false;
		// file name must be left or right table
		} else if(!fileTable.getName().equals(FileTablePanel.LEFT_FILE_TABLE) &&
				!fileTable.getName().equals(FileTablePanel.RIGHT_FILE_TABLE))  {
			return false;
		}
		
		for (int i = 0; i < pTransferFlavors.length; i++) {
			if (!mFlavor.equals(pTransferFlavors[i])) {
				return false;
			}
		}
		return true;
	}

	private class StringFileTransferable implements Transferable {

		String mData;
		
		public StringFileTransferable(String pData) {
			super();
			mData = pData;
		}

		public Object getTransferData(DataFlavor pFlavor) throws UnsupportedFlavorException, IOException {
			if (!isDataFlavorSupported(pFlavor)) throw new UnsupportedFlavorException(pFlavor);
         return mData;
		}

		public DataFlavor[] getTransferDataFlavors() {
         return new DataFlavor[]{ mStringFlavor };
		}

		public boolean isDataFlavorSupported(DataFlavor pFlavor) {
         if(pFlavor.equals(mStringFlavor)) return true;
         return false;
		}
		
	}
	
	/**
	 * The file element transferable as innerclass.
	 * 
	 * @author petros
	 */
	private class FileElementTransferable implements Transferable {

		private FileElement[] mElements;

		public FileElementTransferable(FileElement[] pElements) {
			mElements = pElements;
		}

		public Object getTransferData(DataFlavor pFlavor) throws UnsupportedFlavorException, IOException {
			if (!isDataFlavorSupported(pFlavor)) {
				throw new UnsupportedFlavorException(pFlavor);
			}
			return mElements;
		}

		public DataFlavor[] getTransferDataFlavors() {
			return new DataFlavor[] { mFlavor };
		}

		public boolean isDataFlavorSupported(DataFlavor pFlavor) {
			return mFlavor.equals(pFlavor);
		}

	}
}

/*
 * $Log$
 */