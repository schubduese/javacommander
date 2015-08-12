package de.pd.pjc.bean;

import java.util.Date;

import javax.swing.ImageIcon;

public class FileElement implements Comparable<FileElement> {

	@Override
	public String toString() {
		return mFileName;
	}

	private String mFileName;
	private String mParentPath;
	private String mType;
	private String mAbsolutePath;
	private long mFileSize;
	private Date mLastModified;
	private boolean isLink;
	private boolean mDirectory;
	private boolean mTopElement;
	private boolean mHidden;
	
	private ImageIcon mNormalIcon;
	private ImageIcon mBigIcon;
	private ImageIcon mXXLIcon;
	private ImageIcon mPreviewIcon;
	
	private String mDirContent;
	
	public boolean isTopElement() {
		return mTopElement;
	}

	public void setTopElement(boolean pTopElement) {
		mTopElement = pTopElement;
	}

	public boolean isDirectory() {
		return mDirectory;
	}

	public void setDirectory(boolean pDirectory) {
		mDirectory = pDirectory;
	}

	public boolean isLink() {
		return isLink;
	}

	public void setLink(boolean pIsLink) {
		isLink = pIsLink;
	}

	public FileElement(String pFileName, String pType, long pFileSize, Date pLastModified) {
		mFileName = pFileName;
		mType = pType;
		mFileSize = pFileSize;
		mLastModified = pLastModified;
	}

	public String getFileName() {
		return mFileName;
	}

	public void setFileName(String pFileName) {
		mFileName = pFileName;
	}

	public long getFileSize() {
		return mFileSize;
	}

	public void setFileSize(long pFileSize) {
		mFileSize = pFileSize;
	}

	public Date getLastModified() {
		return mLastModified;
	}

	public void setLastModified(Date pLastModified) {
		mLastModified = pLastModified;
	}

	public String getAbsolutePath() {
		return mAbsolutePath;
	}

	public void setAbsolutePath(String pAbsolutePath) {
		mAbsolutePath = pAbsolutePath;
	}

	public int compareTo(FileElement pCompare) {
		return getFileName().toLowerCase().compareTo(pCompare.getFileName().toLowerCase());
	}

	public String getType() {
		return mType;
	}

	public void setType(String pType) {
		mType = pType;
	}

	public ImageIcon getPreviewIcon() {
		return mPreviewIcon;
	}

	public void setPreviewIcon(ImageIcon pPreviewIcon) {
		mPreviewIcon = pPreviewIcon;
	}

	public ImageIcon getBigIcon() {
		return mBigIcon;
	}

	public void setBigIcon(ImageIcon pBigIcon) {
		mBigIcon = pBigIcon;
	}

	public ImageIcon getNormalIcon() {
		return mNormalIcon;
	}

	public void setNormalIcon(ImageIcon pNormalIcon) {
		mNormalIcon = pNormalIcon;
	}

	public ImageIcon getXXLIcon() {
		return mXXLIcon;
	}

	public void setXXLIcon(ImageIcon pIcon) {
		mXXLIcon = pIcon;
	}

	public String getParentPath() {
		return mParentPath;
	}

	public void setParentPath(String pParentPath) {
		mParentPath = pParentPath;
	}

	/**
	 * @return the dirContent
	 */
	public String getDirContent() {
		return mDirContent;
	}

	/**
	 * @param pDirContent the dirContent to set
	 */
	public void setDirContent(String pDirContent) {
		mDirContent = pDirContent;
	}

	public boolean isHidden() {
		return mHidden;
	}

	public void setHidden(boolean hidden) {
		mHidden = hidden;
	}
	
}
