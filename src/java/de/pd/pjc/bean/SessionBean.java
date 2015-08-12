package de.pd.pjc.bean;

/** Contains information of the settings durina user session.
 * @author petros
 *
 */
public class SessionBean {

	public static boolean mediaMode = false;
	public static Object clipboardEntry = null;
	
	public static boolean showHiddenFiles = false;
	public static void toggleShowHiddenFiles() {
		showHiddenFiles = !showHiddenFiles;
	}
	
	public static boolean coundDirSizes = false;
	public static void toggleCoundDirSizes() {
		coundDirSizes = !coundDirSizes;
	}
	
	/** Thread close events */
	public static boolean DIR_SIZE_CALCULATION_ABBORTED = false;
	
	/** Remote control mode */
	public static boolean rcMode = false;
	public static void toggleRcMode() {
		rcMode = !rcMode;
	}
}
