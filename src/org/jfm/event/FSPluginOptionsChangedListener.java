package org.jfm.event;

public interface FSPluginOptionsChangedListener extends BroadcastListener {
	
	public void setOptions(FSPluginOptionsChangedEvent event);
}
