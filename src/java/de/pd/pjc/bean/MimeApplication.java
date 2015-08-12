package de.pd.pjc.bean;

public class MimeApplication {

	private String mName;
	private String mCommand;
	private String mArgs;
	private String mIcon;
	private boolean mDefault;
	
	public MimeApplication() {
	}
	
	public MimeApplication(String pName, String pCommand, String pIcon, String pArgs, boolean pDefault) {
		super();
		mName = pName;
		mCommand = pCommand;
		mIcon = pIcon;
		mArgs = pArgs;
		mDefault = pDefault;
	}
	public String getCommand() {
		return mCommand;
	}
	public void setCommand(String pCommand) {
		mCommand = pCommand;
	}
	public String getName() {
		return mName;
	}
	public void setName(String pName) {
		mName = pName;
	}
	public boolean isDefault() {
		return mDefault;
	}
	public void setDefault(boolean pDefault) {
		mDefault = pDefault;
	}
	public String getIcon() {
		return mIcon;
	}
	public void setIcon(String pIcon) {
		mIcon = pIcon;
	}
	public String getArgs() {
		return mArgs;
	}
	public void setArgs(String pArgs) {
		mArgs = pArgs;
	}

	@Override
	public String toString() {
		return mName;
	}
	
}
