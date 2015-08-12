package de.pd.pjc.bean;

import java.util.ArrayList;
import java.util.Collection;

public class MimeSettingsBean {

	private String mExtension;
	private Collection<MimeApplication> mApps = new ArrayList<MimeApplication>();
	
	public void addApplication(MimeApplication pApp) {
		mApps.add(pApp);
	}

	public void setApps(Collection<MimeApplication> pApps) {
		mApps = pApps;
	}

	public Collection<MimeApplication> getApps() {
		return mApps;
	}
	
	public MimeApplication getDefaultApp() {
		for (MimeApplication app : mApps) {
			if(app.isDefault()) {
				return app;
			}
		}
		return null;
	}

	public String getExtension() {
		return mExtension;
	}

	public void setExtension(String pExtension) {
		mExtension = pExtension;
	}

	@Override
	public String toString() {
		return mExtension;
	}
	
}
