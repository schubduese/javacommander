package de.pd.pjc.gui.media;

public class MediaPlayerFactory {

	public static MediaPlayer getMediaPlayerForFile(String pFile) {
		return new MP3Player(pFile);
	}
	
}
