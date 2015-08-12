/*
 * OpenWithDialog.java
 *
 * Created on January 10, 2007, 11:57 PM
 */

package de.pd.pjc.gui.dialog;

import java.awt.Color;

import de.pd.pjc.util.PJCUtils;


/**
 * 
 * @author petros
 */
public class ActionProgressDialog extends javax.swing.JDialog {

	public boolean mCanceled = false;
	
	/** Creates new form OpenWithDialog */
	public ActionProgressDialog(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();

		PJCUtils.initSkin();
		
		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int width = getWidth();
      int height = getHeight();
      elementProgress.setBackground(Color.white);
      totalProgress.setBackground(Color.white);
      speedLabel.setVisible(false);
      remainLabel.setVisible(false);
      setBounds((screenSize.width-width)/2, (screenSize.height-height)/2, width, height);
	}



	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc=" Generated Code
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        bluePanel = new javax.swing.JPanel();
        actionLabel = new javax.swing.JLabel();
        label1 = new javax.swing.JLabel();
        label2 = new javax.swing.JLabel();
        fromLabel = new javax.swing.JLabel();
        toLabel = new javax.swing.JLabel();
        label3 = new javax.swing.JLabel();
        filenameLabel = new javax.swing.JLabel();
        whitePanel = new javax.swing.JPanel();
        elementProgress = new javax.swing.JProgressBar();
        totalProgress = new javax.swing.JProgressBar();
        bytesCopied = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        speedLabel = new javax.swing.JLabel();
        remainLabel = new javax.swing.JLabel();
        nrOfFilesLabel = new javax.swing.JLabel();
        cancelButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        totalSizeLabel = new javax.swing.JLabel();

        getContentPane().setLayout(null);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Copying");
        bluePanel.setLayout(null);

        bluePanel.setBackground(new java.awt.Color(167, 198, 255));
        actionLabel.setFont(new java.awt.Font("Dialog", 1, 16));
        actionLabel.setForeground(new java.awt.Color(0, 0, 0));
        actionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        actionLabel.setText("Copying");
        bluePanel.add(actionLabel);
        actionLabel.setBounds(10, 10, 380, 20);

        label1.setForeground(new java.awt.Color(0, 0, 0));
        label1.setText("From:");
        bluePanel.add(label1);
        label1.setBounds(10, 30, 35, 15);

        label2.setText("To:");
        bluePanel.add(label2);
        label2.setBounds(10, 50, 19, 15);

        fromLabel.setForeground(new java.awt.Color(0, 0, 0));
        fromLabel.setText("from");
        bluePanel.add(fromLabel);
        fromLabel.setBounds(60, 30, 240, 15);

        toLabel.setForeground(new java.awt.Color(0, 0, 0));
        toLabel.setText("to");
        bluePanel.add(toLabel);
        toLabel.setBounds(60, 50, 240, 15);

        label3.setText("File:");
        bluePanel.add(label3);
        label3.setBounds(10, 70, 30, 15);

        filenameLabel.setForeground(new java.awt.Color(0, 0, 0));
        filenameLabel.setText("file");
        bluePanel.add(filenameLabel);
        filenameLabel.setBounds(60, 70, 320, 15);

        getContentPane().add(bluePanel);
        bluePanel.setBounds(0, 0, 410, 100);

        whitePanel.setLayout(null);

        whitePanel.setBackground(new java.awt.Color(255, 255, 255));
        elementProgress.setStringPainted(true);
        whitePanel.add(elementProgress);
        elementProgress.setBounds(10, 10, 370, 20);

        totalProgress.setStringPainted(true);
        whitePanel.add(totalProgress);
        totalProgress.setBounds(10, 40, 370, 20);

        whitePanel.add(bytesCopied);
        bytesCopied.setBounds(100, 60, 200, 15);

        jLabel1.setText("Total copied:");
        whitePanel.add(jLabel1);
        jLabel1.setBounds(10, 60, 80, 15);

        speedLabel.setText("jLabel2");
        whitePanel.add(speedLabel);
        speedLabel.setBounds(10, 100, 45, 15);

        remainLabel.setText("jLabel2");
        whitePanel.add(remainLabel);
        remainLabel.setBounds(80, 100, 45, 15);

        nrOfFilesLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        nrOfFilesLabel.setText("1/1");
        whitePanel.add(nrOfFilesLabel);
        nrOfFilesLabel.setBounds(340, 60, 40, 15);

        cancelButton.setMnemonic('C');
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        whitePanel.add(cancelButton);
        cancelButton.setBounds(310, 90, 75, 25);

        jLabel2.setText("Total size:");
        whitePanel.add(jLabel2);
        jLabel2.setBounds(10, 80, 70, 15);

        whitePanel.add(totalSizeLabel);
        totalSizeLabel.setBounds(100, 80, 180, 15);

        getContentPane().add(whitePanel);
        whitePanel.setBounds(0, 100, 410, 130);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-403)/2, (screenSize.height-256)/2, 403, 256);
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
   	 mCanceled = true;
   	 this.setVisible(false);
    }//GEN-LAST:event_cancelButtonActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel actionLabel;
    private javax.swing.JPanel bluePanel;
    public javax.swing.JLabel bytesCopied;
    private javax.swing.JButton cancelButton;
    private javax.swing.JProgressBar elementProgress;
    public javax.swing.JLabel filenameLabel;
    public javax.swing.JLabel fromLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel label1;
    private javax.swing.JLabel label2;
    private javax.swing.JLabel label3;
    public javax.swing.JLabel nrOfFilesLabel;
    public javax.swing.JLabel remainLabel;
    public javax.swing.JLabel speedLabel;
    public javax.swing.JLabel toLabel;
    private javax.swing.JProgressBar totalProgress;
    public javax.swing.JLabel totalSizeLabel;
    private javax.swing.JPanel whitePanel;
    // End of variables declaration//GEN-END:variables


    public void updateElementProgress(int pValue) {
   	 elementProgress.setValue(pValue);
    }
    
    public void updateTotalProgress(int pValue) {
   	 totalProgress.setValue(pValue);
    }

}
