package de.pd.pjc.gui.media;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import de.pd.pjc.gui.media.tag.TagInfo;
import de.pd.pjc.gui.media.tag.TagInfoFactory;

public class MP3Player implements MediaPlayer {

	private final static int FRAME_FACTOR = 40;
	
	private String mFile;

	private AdvancedPlayer player;

	private boolean isPlaying;
	
	TagInfo tagInfo;

	public MP3Player(String pFile) {
		setFile(pFile);
	}

	public void play() {
		try {
			FileInputStream fis = new FileInputStream(mFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			player = new AdvancedPlayer(bis);
		} catch (Exception e) {
			System.out.println("Problem playing file " + mFile);
			System.out.println(e);
		}

		isPlaying = true;
		// run in new thread to play in background
		new Thread() {
			public void run() {
				try {
					player.play();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}.start();
	}

	public void stop() {
		if(player != null) {
			isPlaying = false;
			player.close();
		}
	}

	public boolean isPlaying() {
		return isPlaying;
	}

	public void setFile(String pFile) {
		mFile = pFile;
		TagInfoFactory infoFactory = TagInfoFactory.getInstance();
		tagInfo = infoFactory.getTagInfo(mFile);
	}

	public String getFile() {
		return mFile;
	}

	public int getPosition() {
		if(player == null) return 0;
		return player.getPosition();
	}

	public boolean isComplete() {
		if(player == null) return true;
		return player.isComplete();
	}

	public TagInfo getTagInfo() {
		return tagInfo;
	}

	public void seek(int pPos) {
//		long playTime = tagInfo.getPlayTime()*3;
		
		try {
			player.skipFrames(40);
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}
		
	}

}
