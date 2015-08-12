/*
 * JCInputDialog.java
 *
 * Created on August 7, 2003, 12:27 PM
 */

package de.pd.pjc.gui.dialog;

import org.apache.log4j.Logger;

/**
 *
 * @author  petros
 */
public class JCOpenWithDialog extends javax.swing.JDialog {
	
	private String mFile = null;
	private Logger mLog = Logger.getLogger("JCOpenWithDialog");
	
	/** Creates new form JCInputDialog */
	public JCOpenWithDialog(javax.swing.JFrame frame, String file) {
		super(frame, "Open with", true);
		this.mFile = file;
		if(mFile == null || mFile.equals("")) return;
		initComponents();
		tfCommand.setText("test");
	}
	
	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
   private void initComponents() {//GEN-BEGIN:initComponents
      jLabel1 = new javax.swing.JLabel();
      tfCommand = new javax.swing.JTextField();
      jButton1 = new javax.swing.JButton();
      cbDefault = new javax.swing.JCheckBox();
      btOk = new javax.swing.JButton();
      btCancel = new javax.swing.JButton();

      getContentPane().setLayout(null);

      setTitle("Open with");
      setResizable(false);
      addWindowListener(new java.awt.event.WindowAdapter() {
         public void windowClosing(java.awt.event.WindowEvent evt) {
            exitForm(evt);
         }
      });

      jLabel1.setText("Open with:");
      getContentPane().add(jLabel1);
      jLabel1.setBounds(0, 0, 220, 20);

      tfCommand.addKeyListener(new java.awt.event.KeyAdapter() {
         public void keyPressed(java.awt.event.KeyEvent evt) {
            tfCommandKeyPressed(evt);
         }
      });

      getContentPane().add(tfCommand);
      tfCommand.setBounds(10, 30, 230, 20);

      jButton1.setText("...");
      jButton1.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
         }
      });

      getContentPane().add(jButton1);
      jButton1.setBounds(240, 30, 30, 20);

      cbDefault.setText("Use always this command");
      getContentPane().add(cbDefault);
      cbDefault.setBounds(10, 60, 210, 23);

      btOk.setText("OK");
      btOk.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            btOkActionPerformed(evt);
         }
      });

      getContentPane().add(btOk);
      btOk.setBounds(60, 110, 80, 25);

      btCancel.setText("Cancel");
      btCancel.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            btCancelActionPerformed(evt);
         }
      });

      getContentPane().add(btCancel);
      btCancel.setBounds(140, 110, 75, 25);

      java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
      setBounds((screenSize.width-300)/2, (screenSize.height-170)/2, 300, 170);
   }//GEN-END:initComponents

	private void tfCommandKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfCommandKeyPressed
		if(evt.getKeyCode() == evt.VK_ENTER) {
			// add as default to the property file
		if(cbDefault.isSelected()) {
		}
		this.setVisible(false);
		}
	}//GEN-LAST:event_tfCommandKeyPressed

	private void btCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelActionPerformed
		this.setVisible(false);
	}//GEN-LAST:event_btCancelActionPerformed

	private void btOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOkActionPerformed
		// add as default to the property file
		if(cbDefault.isSelected()) {
		}
		this.setVisible(false);
	}//GEN-LAST:event_btOkActionPerformed

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
		tfCommand.setText("test");
	}//GEN-LAST:event_jButton1ActionPerformed
	
	/** Exit the Application */
	private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
		this.setVisible(false);
	}//GEN-LAST:event_exitForm
	
	
   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton btCancel;
   private javax.swing.JButton btOk;
   private javax.swing.JCheckBox cbDefault;
   private javax.swing.JButton jButton1;
   private javax.swing.JLabel jLabel1;
   private javax.swing.JTextField tfCommand;
   // End of variables declaration//GEN-END:variables
	
}
