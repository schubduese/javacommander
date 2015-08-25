package org.jfm.event;

public class FSPluginOptionsChangedEvent extends BroadcastEvent {

	private String fsName=null;
	
	public FSPluginOptionsChangedEvent(Object source)
	{
		super(source);
	}
	
	
	
	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return BroadcastEvent.FSPLUGIN_EVENT;
	}



	/**
	 * @return the fsName
	 */
	public String getFsName() {
		return fsName;
	}



	/**
	 * @param fsName the fsName to set
	 */
	public void setFsName(String fsName) {
		this.fsName = fsName;
	}

}
