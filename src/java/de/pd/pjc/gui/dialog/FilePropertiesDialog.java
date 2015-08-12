/*
 * FilePropertiesDialog.java
 *
 * Created on January 22, 2007, 8:28 PM
 */

package de.pd.pjc.gui.dialog;

import java.awt.Color;
import java.awt.event.KeyEvent;

import de.pd.pjc.Constants;
import de.pd.pjc.bean.FileCountBean;
import de.pd.pjc.bean.FileElement;
import de.pd.pjc.bean.SessionBean;
import de.pd.pjc.service.FileService;
import de.pd.pjc.service.ServiceFactory;
import de.pd.pjc.service.impl.FileServiceImpl;
import de.pd.pjc.util.IconFactory;
import de.pd.pjc.util.PJCUtils;

/**
 *
 * @author  petros
 */
public class FilePropertiesDialog extends javax.swing.JDialog {
   
	
	private FileElement[] mFileElements;
	private Thread mThread;
	private Runnable mCalcThread;
	java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	
    /** Creates new form FilePropertiesDialog */
    public FilePropertiesDialog(java.awt.Frame parent, boolean modal, FileElement[] pElements) {
        super(parent, modal);
        if(pElements.length < 1) return;
        mFileElements = pElements;
        initComponents();
        loadFileProperties();
        setResizable(false);
        SessionBean.DIR_SIZE_CALCULATION_ABBORTED = false;
    }
    
    private void loadFileProperties() {
   	 
   	 imagePanel.setVisible(false);
   	 getContentPane().setBackground(Color.white);
   	 
   	 if(mFileElements.length == 1) {
	   	 fileNameText.setText(mFileElements[0].getFileName());
	   	 iconButton.setIcon(mFileElements[0].getNormalIcon());
	   	 fileTypeLabel.setText(mFileElements[0].isDirectory()? "Directory" : "File");
	   	 locationLabel.setText(mFileElements[0].getParentPath());
	   	 locationLabel.setToolTipText(mFileElements[0].getAbsolutePath());
	   	 modifiedLabel.setText(Constants.DEFAULT_DATE_FORMAT.format(mFileElements[0].getLastModified()));
	   	 if(FileServiceImpl.isImage(mFileElements[0].getFileName())) {
	   		 setBounds((screenSize.width-363)/2, (screenSize.height-434)/2, 363, 434);
	   		 imageButton.setIcon(IconFactory.getPreviewImage(mFileElements[0], 0, imageButton.getHeight()));
	   		 resolutionLabel.setText(IconFactory.getImageResolutionFormatted(mFileElements[0]));
	   		 imagePanel.setVisible(true);
	   	 }
   	 } else {
   		 fileTypeLabel.setVisible(false);
   		 modifiedLabel.setVisible(false);
   		 fileNameText.setText(mFileElements.length + " items selected");
   		 locationLabel.setText(mFileElements[0].getParentPath());
   		 iconButton.setIcon(IconFactory.getIconByName("kmultiple.png"));
   	 }
   	 
   	 mCalcThread = new FileSizeCalculatorThread();
   	 mThread = new Thread(mCalcThread);
   	 mThread.start();
   	 
   	 okButton.requestFocusInWindow();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        fileNameText = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        okButton = new javax.swing.JButton();
        fileTypeLabel = new javax.swing.JLabel();
        sizeLabel = new javax.swing.JLabel();
        modifiedLabel = new javax.swing.JLabel();
        iconButton = new javax.swing.JButton();
        fileCountLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        imagePanel = new javax.swing.JPanel();
        imageButton = new javax.swing.JButton();
        resolutionLabel = new javax.swing.JLabel();
        locationLabel = new javax.swing.JTextField();

        getContentPane().setLayout(null);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Properties");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        fileNameText.setBackground(new java.awt.Color(255, 255, 255));
        fileNameText.setEditable(false);
        fileNameText.setText("jTextField1");
        getContentPane().add(fileNameText);
        fileNameText.setBounds(80, 10, 250, 30);

        jLabel1.setText("Type:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 60, 33, 15);

        jLabel2.setText("Location:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(10, 80, 56, 15);

        jLabel3.setText("Size:");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(10, 100, 28, 15);

        jLabel4.setText("Modified:");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(10, 140, 58, 15);

        okButton.setMnemonic('o');
        okButton.setText("Close");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        getContentPane().add(okButton);
        okButton.setBounds(272, 120, 70, 30);

        fileTypeLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        fileTypeLabel.setText("jLabel5");
        getContentPane().add(fileTypeLabel);
        fileTypeLabel.setBounds(110, 60, 220, 15);

        sizeLabel.setText("calculating...");
        getContentPane().add(sizeLabel);
        sizeLabel.setBounds(110, 100, 220, 15);

        modifiedLabel.setText("jLabel5");
        getContentPane().add(modifiedLabel);
        modifiedLabel.setBounds(110, 140, 220, 15);

        iconButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                iconButtonKeyPressed(evt);
            }
        });

        getContentPane().add(iconButton);
        iconButton.setBounds(20, 10, 40, 30);

        fileCountLabel.setText("calculating...");
        getContentPane().add(fileCountLabel);
        fileCountLabel.setBounds(110, 120, 220, 15);

        getContentPane().add(jSeparator1);
        jSeparator1.setBounds(10, 50, 320, 2);

        getContentPane().add(jSeparator2);
        jSeparator2.setBounds(10, 160, 320, 10);

        imagePanel.setLayout(null);

        imagePanel.setBackground(java.awt.Color.white);
        imagePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Image Info"));
        imageButton.setBorder(null);
        imageButton.setOpaque(false);
        imageButton.setContentAreaFilled(false);
        imagePanel.add(imageButton);
        imageButton.setBounds(10, 20, 310, 190);

        resolutionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        resolutionLabel.setText("jLabel5");
        imagePanel.add(resolutionLabel);
        resolutionLabel.setBounds(10, 210, 310, 15);

        getContentPane().add(imagePanel);
        imagePanel.setBounds(10, 160, 330, 230);

        locationLabel.setBackground(null);
        locationLabel.setEditable(false);
        locationLabel.setText("jTextField1");
        locationLabel.setBorder(null);
        locationLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                locationLabelMouseClicked(evt);
            }
        });

        getContentPane().add(locationLabel);
        locationLabel.setBounds(110, 80, 220, 15);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-363)/2, (screenSize.height-203)/2, 363, 203);
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden
   	 SessionBean.DIR_SIZE_CALCULATION_ABBORTED = true;
    }//GEN-LAST:event_formComponentHidden

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
   	 SessionBean.DIR_SIZE_CALCULATION_ABBORTED = true;
    }//GEN-LAST:event_formWindowClosing

    private void iconButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_iconButtonKeyPressed
   	 if(evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
   		 setVisible(false);
   	 }
    }//GEN-LAST:event_iconButtonKeyPressed

    private void locationLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_locationLabelMouseClicked
   	 locationLabel.selectAll();
    }//GEN-LAST:event_locationLabelMouseClicked

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
   	 setVisible(false);
    }//GEN-LAST:event_okButtonActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
   	 SessionBean.DIR_SIZE_CALCULATION_ABBORTED = true;
    }//GEN-LAST:event_formWindowClosed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel fileCountLabel;
    private javax.swing.JTextField fileNameText;
    private javax.swing.JLabel fileTypeLabel;
    private javax.swing.JButton iconButton;
    private javax.swing.JButton imageButton;
    private javax.swing.JPanel imagePanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField locationLabel;
    private javax.swing.JLabel modifiedLabel;
    private javax.swing.JButton okButton;
    private javax.swing.JLabel resolutionLabel;
    private javax.swing.JLabel sizeLabel;
    // End of variables declaration//GEN-END:variables
    
    public class FileSizeCalculatorThread implements Runnable {

   	 private FileService mFileService;
   	 
   	 
   	 public FileSizeCalculatorThread() {
   		 mFileService = ServiceFactory.getFileService();
   	 }
   	 
		public void run() {
			FileCountBean cnt = new FileCountBean();
			for (FileElement element : mFileElements) {
				if(element.isDirectory()) {
					FileCountBean dirCnt = mFileService.calculateDirSize(element.getAbsolutePath(), true);
					cnt.dirs += dirCnt.dirs;
					cnt.files += dirCnt.files;
					cnt.size += dirCnt.size;
				} else {
					cnt.size += element.getFileSize();
					cnt.files++;
				}
			}
			updateLabel(cnt);
		}
		

		private void updateLabel(FileCountBean cnt) {
			sizeLabel.setText(PJCUtils.fileSizeHumanReadable(cnt.size).getFormatted() + " (" + cnt.size + ")");
			fileCountLabel.setText("" + cnt.files + " files, " + cnt.dirs + " sub-dirs");
		}
		
   	 
    }
}