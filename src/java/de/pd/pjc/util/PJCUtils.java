package de.pd.pjc.util;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.UIManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.oro.text.perl.Perl5Util;
import org.springframework.util.StringUtils;

import com.jgoodies.looks.Options;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;

import de.pd.pjc.bean.FileElement;
import de.pd.pjc.bean.FileSizeBean;
import de.pd.pjc.bean.SessionBean;

public class PJCUtils {

	final static Log mLog = LogFactory.getLog(PJCUtils.class.getName());

	
	public static void initSkin() {
		try {
			UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
			PlasticLookAndFeel
					.setTabStyle(PlasticLookAndFeel.TAB_STYLE_METAL_VALUE);
			PlasticLookAndFeel.setHighContrastFocusColorsEnabled(true);
			PlasticLookAndFeel.set3DEnabled(true);

			UIManager.put("ScrollBar.is3DEnabled", Boolean.TRUE);
			UIManager.put("jgoodies.popupDropShadowEnabled", Boolean.TRUE);
			Options.setDefaultIconSize(new Dimension(18, 18));
		} catch (Exception e) {
			mLog.error("error setting sking", e);
		}
	}
	
	/**
	 * @param pPath
	 * @return
	 */
	public static String getTopDirOfPath(String pPath) {
		int lastIndex = pPath.lastIndexOf(File.separator);
		if (lastIndex == -1) {
			return "";
		}

		return pPath.substring(lastIndex + 1);
	}

	private final static long KILOBYTE = 1024L;
	private final static long MEGABYTE = 1024L * 1024L;
	private final static long GIGABYTE = (1024L * 1024L) * 1024L;

	/**
	 * @param pFileSize
	 * @return
	 */
	public static FileSizeBean fileSizeHumanReadable(long pFileSize) {

		double dval = new Double(pFileSize).doubleValue();
		String ret = "";

		NumberFormat format = new DecimalFormat("#.#");

		if (pFileSize < KILOBYTE) {
			ret = format.format(dval) + " b";
		} else if (pFileSize >= KILOBYTE && pFileSize < (MEGABYTE)) {
			ret = format.format(dval / KILOBYTE) + " KB";
		} else if (pFileSize > MEGABYTE && pFileSize < GIGABYTE) {
			ret = format.format(dval / MEGABYTE) + " MB";
		} else if (pFileSize >= GIGABYTE) {
			ret = format.format(dval / GIGABYTE) + " GB";
		} else {
			mLog.warn("for some reason no if condition matched for " + pFileSize);
		}

		FileSizeBean fsBean = new FileSizeBean(pFileSize, ret);
		return fsBean;
	}

	private static Perl5Util mPerl = new Perl5Util();

	public static boolean isKeyboardSearchKeyPressed(KeyEvent evt) {
		return evt.getKeyCode() >= ' ' && evt.getKeyCode() <= '~'
				&& !evt.isAltDown() && !evt.isControlDown() && !evt.isShiftDown()
				&& !(evt.getKeyCode() >= 33 && evt.getKeyCode() <= 40)
				&& !(evt.getKeyCode() >= 112 && evt.getKeyCode() <= 123);
	}

	public static boolean stringMatchesPattern(String pInstr, String pPattern) {
		return PJCUtils.stringMatchesPattern(pInstr, pPattern, false);
	}
	
	/**
	 * returns true, if the instr matches the pattern.
	 * 
	 * @param pInstr
	 * @param pPattern
	 * @return
	 */
	public static boolean stringMatchesPattern(String pInstr, String pPattern, boolean pCaseSensitive) {
		String pattern = "";
		if (StringUtils.hasText(pInstr)) {
			pattern = pPattern.replaceAll("\\*.", "\\.*");
		}
		try {
			return mPerl.match("/" + pattern + "/" + (pCaseSensitive?"":"i"), pInstr);
		} catch (Exception e) {
			return false;
		}
	}

	public static void main(String[] args) {
		System.out.println(stringMatchesPattern("public_html", "^t"));
	}

	
	/**
	 * Copy a string into the clipboard
	 * 
	 */
	public static void copyToClipboard(FileElement[] pElements) {
		if(pElements == null || pElements.length == 0) {
			SessionBean.clipboardEntry = null;
			return;
		}
		SessionBean.clipboardEntry = pElements;
		Transferable tt = new StringSelection(pElements[0].getAbsolutePath());
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(tt, null);
	}

	/**
	 * returns true if its the root path
	 * 
	 * @param pPath
	 * @return
	 */
	public static boolean isRootPath(String pPath) {
		if (isLinux() && pPath.equals("/")) {
			return true;
		} else if(isWindows() && pPath.matches("^[A-Za-z]:\\\\")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isWindows() {
		if (System.getProperty("os.name") == null)
			return false;
		return System.getProperty("os.name").startsWith("Windows");
	}

	public static boolean isWindows2000() {
		if (System.getProperty("os.name") == null)
			return false;
		return System.getProperty("os.name").startsWith("Windows 2000");
	}

	public static boolean isWindowsXP() {
		if (System.getProperty("os.name") == null)
			return false;
		return System.getProperty("os.name").startsWith("Windows XP");
	}

	public static boolean isWindows9x() {
		if (System.getProperty("os.name") == null)
			return false;
		return System.getProperty("os.name").startsWith("Windows 95")
				|| System.getProperty("os.name").startsWith("Windows 98");
	}

	public static boolean isLinux() {
		if (System.getProperty("os.name") == null)
			return false;
		return System.getProperty("os.name").startsWith("Linux");
	}
	
	public static void strokeKey(int pKeyCode) {
		try {
			Robot robot = new Robot();
			robot.keyPress(pKeyCode);
			robot.keyRelease(pKeyCode);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

}
