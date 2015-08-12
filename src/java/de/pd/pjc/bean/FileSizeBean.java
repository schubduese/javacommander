package de.pd.pjc.bean;

public class FileSizeBean implements Comparable<FileSizeBean> {

	private long mSize;
	private String mFormatted;
	public FileSizeBean(long pSize, String pFormatted) {
		super();
		mSize = pSize;
		mFormatted = pFormatted;
	}
	public String getFormatted() {
		return mFormatted;
	}
	public void setFormatted(String pFormatted) {
		mFormatted = pFormatted;
	}
	public long getSize() {
		return mSize;
	}
	public void setSize(long pSize) {
		mSize = pSize;
	}
	@Override
	public String toString() {
		return mFormatted;
	}

	public int compareTo(FileSizeBean anotherFileSize) {
		long thisVal = this.mSize;
		long anotherVal = anotherFileSize.getSize();
		return (thisVal<anotherVal ? -1 : (thisVal==anotherVal ? 0 : 1));
    }	
	
	
	
}
