package de.pd.pjc.bean;

public class DirSettingsBean {

	
	private String mPath;
	private String mSortOrder;
	private String mIconSize;
	
	public String getIconSize() {
		return mIconSize;
	}
	public void setIconSize(String pIconSize) {
		mIconSize = pIconSize;
	}
	
	public DirSettingsBean(String pPath) {
		mPath = pPath;
	}
	
	public DirSettingsBean(String pPath, String pSortOrder) {
		mPath = pPath;
		mSortOrder = pSortOrder;
	}
	public String getPath() {
		return mPath;
	}
	public void setPath(String pPath) {
		mPath = pPath;
	}
	public String getSortOrder() {
		return mSortOrder;
	}
	public void setSortOrder(String pSortOrder) {
		mSortOrder = pSortOrder;
	}
	
	
}
