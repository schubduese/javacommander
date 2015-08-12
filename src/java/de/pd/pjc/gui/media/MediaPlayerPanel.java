/*
 * MediaPlayerPanel.java
 *
 * Created on March 29, 2007, 8:32 PM
 */

package de.pd.pjc.gui.media;

import java.awt.Dimension;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.UIManager;

import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;

import de.pd.pjc.Settings;
import de.pd.pjc.bean.FileElement;
import de.pd.pjc.gui.media.tag.TagInfo;

/**
 * 
 * @author petros
 */
public class MediaPlayerPanel extends javax.swing.JPanel {

	private final static int WIDTH = 500;
	private final static int HEIGHT = 350;
	private MediaPlayer mPlayer;
	private MediaPlayer mLastPlayer;
	
	private String mCurrentlyPlaying = "";
	
	/** Creates new form MediaPlayerPanel */
	public MediaPlayerPanel() {
		
 		try {
 			UIManager.setLookAndFeel(new PlasticXPLookAndFeel());
         PlasticLookAndFeel.setTabStyle(PlasticLookAndFeel.TAB_STYLE_METAL_VALUE);
         PlasticLookAndFeel.setHighContrastFocusColorsEnabled(true);
         PlasticLookAndFeel.set3DEnabled(true);
         
 			UIManager.put("ScrollBar.is3DEnabled", Boolean.TRUE);
 		   UIManager.put("jgoodies.popupDropShadowEnabled", Boolean.TRUE);
 		} catch (Exception e) {
 		}
		
		initComponents();
		initPanel();
	}

	private void initPanel() {
		setSize(new Dimension(WIDTH, HEIGHT));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setMaximumSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Settings.DEFAULT_BG_COLOR);
		posSlider.setBackground(Settings.DEFAULT_BG_COLOR);
		posSlider.setPaintTrack(true);
		posSlider.setPaintLabels(true);
	}
	
	public void showMediaInfo(FileElement pFile) {
		fileLabel.setText(pFile.getFileName());
		mPlayer = MediaPlayerFactory.getMediaPlayerForFile(pFile.getAbsolutePath());
		TagInfo tagInfo = mPlayer.getTagInfo();
		
		// length
		if(tagInfo != null) {
		long playTime = tagInfo.getPlayTime();
		int max = Math.round(playTime) * 1000;
		posSlider.setMaximum(max);
		int secondsAmount = Math.round(playTime);
      if (secondsAmount < 0) secondsAmount = 0;
		int minutes = secondsAmount / 60;
      int seconds = secondsAmount - (minutes * 60);
      playtimeLabel.setText("Length : " + minutes + ":" + seconds);
      
      Hashtable sliderLabels = new Hashtable();
		sliderLabels.put(new Integer(0), new JLabel("0"));
		sliderLabels.put(new Integer(max), new JLabel("" + playTime));
		posSlider.setLabelTable(sliderLabels);
		}
		
		repaint();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc=" Generated Code
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        playButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        posSlider = new javax.swing.JSlider();
        fileLabel = new javax.swing.JLabel();
        playtimeLabel = new javax.swing.JLabel();

        setLayout(null);

        playButton.setText("Play");
        playButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButtonActionPerformed(evt);
            }
        });

        add(playButton);
        playButton.setBounds(10, 260, 60, 25);

        stopButton.setText("Stop");
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });

        add(stopButton);
        stopButton.setBounds(80, 260, 70, 25);

        posSlider.setForeground(new java.awt.Color(0, 0, 0));
        posSlider.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                posSliderMouseReleased(evt);
            }
        });

        add(posSlider);
        posSlider.setBounds(10, 216, 370, 40);

        fileLabel.setText("jLabel1");
        add(fileLabel);
        fileLabel.setBounds(20, 200, 350, 15);

        playtimeLabel.setText("jLabel1");
        add(playtimeLabel);
        playtimeLabel.setBounds(20, 180, 130, 15);

    }// </editor-fold>//GEN-END:initComponents

    private void posSliderMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_posSliderMouseReleased
   	 JSlider slider = (JSlider)evt.getSource();
   	 System.out.println("released at " + slider.getValue());
   	 mPlayer.seek(slider.getValue());
   	 
    }//GEN-LAST:event_posSliderMouseReleased

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopButtonActionPerformed
   	 mPlayer.stop();
    }//GEN-LAST:event_stopButtonActionPerformed

    private void playButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_playButtonActionPerformed
   	 if(mLastPlayer != null) {
   		 mLastPlayer.stop();
   	 }
   	 
 		new Thread() {
			public synchronized void run() {
				try {
					mPlayer.play();
					while(!mPlayer.isComplete() && mPlayer.isPlaying()) {
						posSlider.setValue(mPlayer.getPosition());
						wait(1000);
					}
					System.out.print("complete");
					posSlider.setValue(0);
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}.start();
   	 mLastPlayer = mPlayer;
   	 mCurrentlyPlaying = mPlayer.getFile();
    }//GEN-LAST:event_playButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel fileLabel;
    private javax.swing.JButton playButton;
    private javax.swing.JLabel playtimeLabel;
    private javax.swing.JSlider posSlider;
    private javax.swing.JButton stopButton;
    // End of variables declaration//GEN-END:variables

}
