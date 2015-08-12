package de.pd.pjc.gui.table;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

public class FileTableTransferHandler extends TransferHandler {

	/* (non-Javadoc)
	 * @see javax.swing.TransferHandler#canImport(javax.swing.JComponent, java.awt.datatransfer.DataFlavor[])
	 */
	@Override
	public boolean canImport(JComponent pComp, DataFlavor[] pTransferFlavors) {
		// TODO Auto-generated method stub
		return super.canImport(pComp, pTransferFlavors);
	}

	/* (non-Javadoc)
	 * @see javax.swing.TransferHandler#createTransferable(javax.swing.JComponent)
	 */
	@Override
	protected Transferable createTransferable(JComponent pC) {
		// TODO Auto-generated method stub
		return super.createTransferable(pC);
	}

	/* (non-Javadoc)
	 * @see javax.swing.TransferHandler#importData(javax.swing.JComponent, java.awt.datatransfer.Transferable)
	 */
	@Override
	public boolean importData(JComponent pComp, Transferable pT) {
		return super.importData(pComp, pT);
	}

}
