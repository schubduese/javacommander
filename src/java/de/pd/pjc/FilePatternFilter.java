package de.pd.pjc;

import java.io.File;
import java.io.FileFilter;

import org.springframework.util.StringUtils;

import de.pd.pjc.util.PJCUtils;

public class FilePatternFilter implements FileFilter {

	/** The current pattern.
	 */
	private String mPattern;
	/** If this is true, then the directories will be always shown.
	 */
	private boolean mShowAlwaysDirs;
	private boolean mShowOnlyDirs;
	private boolean mCaseSensitive;
	
	public FilePatternFilter() {
	}
	
	public FilePatternFilter(String pPattern) {
		mPattern = pPattern;
		mShowAlwaysDirs = true;
	}
	
	public FilePatternFilter(String pPattern, boolean pShowAlwaysDirs, boolean pShowOnlyDirs) {
		mPattern = pPattern;
		mShowAlwaysDirs = pShowAlwaysDirs;
		mShowOnlyDirs = pShowOnlyDirs;
	}

	public boolean accept(File pathname) {
		if(!StringUtils.hasLength(mPattern)) return true;
		if(mShowOnlyDirs && !pathname.isDirectory()) return false;
		if(mShowAlwaysDirs && pathname.isDirectory()) return true;
		boolean matched =  PJCUtils.stringMatchesPattern(pathname.getName(), mPattern, mCaseSensitive);
//		System.out.println("Matching " + pathname.getName() + " with " + mPattern + " <- "  + matched);
		return matched;
	}

	public String getPattern() {
		return mPattern;
	}

	public boolean isShowAlwaysDirs() {
		return mShowAlwaysDirs;
	}

	/**
	 * @return the showOnlyDirs
	 */
	public boolean isShowOnlyDirs() {
		return mShowOnlyDirs;
	}

	/**
	 * @param pShowOnlyDirs the showOnlyDirs to set
	 */
	public void setShowOnlyDirs(boolean pShowOnlyDirs) {
		mShowOnlyDirs = pShowOnlyDirs;
	}

	public void setCaseSensitive(boolean pCaseSensitive) {
		mCaseSensitive = pCaseSensitive;
	}

}
