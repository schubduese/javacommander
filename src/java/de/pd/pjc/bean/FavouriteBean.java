package de.pd.pjc.bean;

public class FavouriteBean {

	private String mName;
	private String mPath;
	
	
	public FavouriteBean(String pName, String pPath) {
		super();
		mName = pName;
		mPath = pPath;
	}
	public String getName() {
		return mName;
	}
	public void setName(String pName) {
		mName = pName;
	}
	public String getPath() {
		return mPath;
	}
	public void setPath(String pPath) {
		mPath = pPath;
	}
	
}
