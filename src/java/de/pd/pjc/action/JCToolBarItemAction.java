/*
 * JCToolBarItemAction.java
 *
 * Created on June 5, 2003, 9:10 PM
 */

package de.pd.pjc.action;

import org.apache.log4j.Logger;
/**
 *
 * @author  petros
 */
public class JCToolBarItemAction {
	
	private Logger mLog = Logger.getLogger("JCToolBarItemAction");

	// Actions
	public static final int DIFF = 0;
	public static final int DELETE = 1;
	public static final int RELOAD = 2;
	public static final int DIRUP = 3;
	public static final int HOME = 4;
	
	
	/** Creates a new instance of JCToolBarItemAction */
	public JCToolBarItemAction() {
	}
	
	public void doAction(int action) {
		switch(action) {
			// Diff files
			case DIFF:
				diffFiles();
				break;
				// Delete file(s)
			case DELETE:
				break;
				// Reload filelist
			case RELOAD:
				break;
				// Go a dir up
			case DIRUP:
				break;
			case HOME:
				break;
			default:
				mLog.debug("No action defined for " + action);
		}
	}
	
	/** Diff two or more files
	 */
	private void diffFiles() {
//		String diffCmd = mProp.getProperty("jc.apps.defaultdiff");
//		String[] files = new String[(mAllItems.size() + 1)];
//		files[0] = diffCmd;
//		for(int ii = 0; ii < mAllItems.size(); ii++) {
//			files[ii + 1] = ((JCListItem)mAllItems.get(ii)).getRealPath();
//		}
//		mHelper.executeCommand(files);
	}
}
