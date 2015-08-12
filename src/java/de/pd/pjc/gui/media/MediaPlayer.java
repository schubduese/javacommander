package de.pd.pjc.gui.media;

import de.pd.pjc.gui.media.tag.TagInfo;

public interface MediaPlayer {

	public void play();
	
	public void stop();
	
	public boolean isPlaying();
	
	public void setFile(String pFile);
	
	public String getFile();
	
	public int getPosition();
	
	public boolean isComplete();
	
	public TagInfo getTagInfo();
	
	public void seek(int pPos);
}
