/*
 * MainWindow.java
 *
 * Created on December 7, 2006, 7:37 PM
 */

package de.pd.pjc.gui;

import java.awt.event.KeyEvent;

import javax.swing.JPopupMenu;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.pd.pjc.gui.menu.JCMenuBar;
import de.pd.pjc.gui.menu.JCToolBar;
import de.pd.pjc.gui.table.FileTableModel;
import de.pd.pjc.gui.table.TableSorter;
import de.pd.pjc.gui.table.detail.DetailFileTable;
import de.pd.pjc.gui.table.detail.DetailFileTableModel;
import de.pd.pjc.gui.table.icon.IconFileTableModel;
import de.pd.pjc.util.PJCUtils;

/**
 * 
 * @author petros
 */
public class MainWindow extends javax.swing.JFrame {

	public static Log mLog = LogFactory.getLog(MainWindow.class.getName());

	protected FileTableModel leftFileTableModel = new DetailFileTableModel();

	protected TableSorter leftSorter = new TableSorter(leftFileTableModel);

	protected FileTableModel rightFileTableModel = new DetailFileTableModel();

	protected TableSorter rightSorter = new TableSorter(rightFileTableModel);

	protected IconFileTableModel rightFileTableModelIcon = new IconFileTableModel();

	private MainWindowPanelController mPanelController;

	protected static JPopupMenu mPopupMenu;

	public MediaPreviewPanel mediaPanelLeft = new MediaPreviewPanel();

	public MediaPreviewPanel mediaPanelRight = new MediaPreviewPanel();

	private boolean mLightMode;

	/** Creates new form MainWindow */
	public MainWindow(MainWindowPanelController pPanelController, boolean pLightMode) {

		mLightMode = pLightMode;
		mPanelController = pPanelController;
		mPanelController.setMainWindow(this);
		
		
		PJCUtils.initSkin();
		initComponents();
		initContent();
		
	}

	protected void initContent() {
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc=" Generated Code
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        FileBrowserPanel = new javax.swing.JPanel();
        leftPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        leftAdrField = new javax.swing.JTextField();
        leftFilterField = new javax.swing.JTextField();
        leftTabPane = new javax.swing.JTabbedPane();
        leftScrollPane = new javax.swing.JScrollPane();
        leftFileTable = new DetailFileTable(leftSorter);
        rightPanel = new javax.swing.JPanel();
        rightAdrField = new javax.swing.JTextField();
        rightTabPane = new javax.swing.JTabbedPane();
        rightScrollPane = new javax.swing.JScrollPane();
        rightFileTable = new DetailFileTable(rightSorter);
        statusBarPanel = new javax.swing.JPanel();
        fileInfoLabel = new javax.swing.JLabel();
        errorLabel = new javax.swing.JLabel();
        toolBar = new JCToolBar();
        menuBar = new JCMenuBar();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PJava Commander 0.1.5");
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        FileBrowserPanel.setLayout(new javax.swing.BoxLayout(FileBrowserPanel, javax.swing.BoxLayout.X_AXIS));

        FileBrowserPanel.setBackground(new java.awt.Color(255, 255, 255));
        FileBrowserPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        leftPanel.setLayout(new javax.swing.BoxLayout(leftPanel, javax.swing.BoxLayout.Y_AXIS));

        leftPanel.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.X_AXIS));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(2147483647, 20));
        leftAdrField.setText("jTextField1");
        leftAdrField.setMaximumSize(new java.awt.Dimension(2147483647, 21));
        leftAdrField.setMinimumSize(new java.awt.Dimension(4, 23));
        leftAdrField.setPreferredSize(new java.awt.Dimension(69, 23));
        leftAdrField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                leftAdrFieldFocusGained(evt);
            }
        });
        leftAdrField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                leftAdrFieldMouseClicked(evt);
            }
        });

        jPanel1.add(leftAdrField);

        leftFilterField.setMaximumSize(new java.awt.Dimension(50, 21));
        leftFilterField.setMinimumSize(new java.awt.Dimension(50, 21));
        leftFilterField.setPreferredSize(new java.awt.Dimension(50, 23));
        jPanel1.add(leftFilterField);

        leftPanel.add(jPanel1);

        leftTabPane.setBackground(new java.awt.Color(255, 204, 102));
        leftTabPane.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        leftScrollPane.setBackground(new java.awt.Color(255, 255, 255));
        leftScrollPane.setBorder(null);
        leftScrollPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                leftScrollPaneMouseClicked(evt);
            }
        });

        leftFileTable.setModel(leftSorter);
        leftFileTable.setFocusTraversalPolicy(getFocusTraversalPolicy());
        leftFileTable.setGridColor(new java.awt.Color(240, 239, 239));
        leftFileTable.setShowHorizontalLines(false);
        leftFileTable.setShowVerticalLines(false);
        leftFileTable.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                leftFileTableFocusGained(evt);
            }
        });

        leftScrollPane.setViewportView(leftFileTable);

        leftTabPane.addTab("tab1", leftScrollPane);

        leftPanel.add(leftTabPane);

        FileBrowserPanel.add(leftPanel);

        rightPanel.setLayout(new javax.swing.BoxLayout(rightPanel, javax.swing.BoxLayout.Y_AXIS));

        rightAdrField.setText("jTextField2");
        rightAdrField.setMaximumSize(new java.awt.Dimension(2147483647, 19));
        rightAdrField.setPreferredSize(new java.awt.Dimension(69, 23));
        rightAdrField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                rightAdrFieldFocusGained(evt);
            }
        });
        rightAdrField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rightAdrFieldMouseClicked(evt);
            }
        });

        rightPanel.add(rightAdrField);

        rightScrollPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rightScrollPaneMouseClicked(evt);
            }
        });

        rightFileTable.setModel(rightSorter);
        rightFileTable.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                rightFileTableFocusGained(evt);
            }
        });

        rightScrollPane.setViewportView(rightFileTable);

        rightTabPane.addTab("tab1", rightScrollPane);

        rightPanel.add(rightTabPane);

        FileBrowserPanel.add(rightPanel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(FileBrowserPanel, gridBagConstraints);

        statusBarPanel.setLayout(new java.awt.GridBagLayout());

        statusBarPanel.setBackground(new java.awt.Color(236, 233, 216));
        statusBarPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        statusBarPanel.setMinimumSize(new java.awt.Dimension(0, 30));
        fileInfoLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        fileInfoLabel.setText("sars");
        fileInfoLabel.setPreferredSize(new java.awt.Dimension(15, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        statusBarPanel.add(fileInfoLabel, gridBagConstraints);

        errorLabel.setForeground(new java.awt.Color(204, 0, 0));
        errorLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        statusBarPanel.add(errorLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(statusBarPanel, gridBagConstraints);

        toolBar.setBackground(java.awt.SystemColor.controlHighlight);
        toolBar.setFloatable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(toolBar, gridBagConstraints);

        menuBar.setBackground(java.awt.SystemColor.controlHighlight);
        setJMenuBar(menuBar);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-1000)/2, (screenSize.height-800)/2, 1000, 800);
    }// </editor-fold>//GEN-END:initComponents

    private void rightAdrFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rightAdrFieldMouseClicked
   	 mPanelController.focusRightPanel(false);
    }//GEN-LAST:event_rightAdrFieldMouseClicked

    private void leftAdrFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_leftAdrFieldMouseClicked
   	 mPanelController.focusLeftPanel(false);
    }//GEN-LAST:event_leftAdrFieldMouseClicked

    private void rightAdrFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_rightAdrFieldFocusGained
//   	 mPanelController.focusRightPanel(false);
    }//GEN-LAST:event_rightAdrFieldFocusGained

    private void leftAdrFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_leftAdrFieldFocusGained
//   	 mPanelController.focusLeftPanel(false);
    }//GEN-LAST:event_leftAdrFieldFocusGained

	private void rightScrollPaneMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_rightScrollPaneMouseClicked
		mPanelController.focusRightPanel();
	}// GEN-LAST:event_rightScrollPaneMouseClicked

	private void leftScrollPaneMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_leftScrollPaneMouseClicked
		mPanelController.focusLeftPanel();
	}// GEN-LAST:event_leftScrollPaneMouseClicked

	private void formKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_formKeyPressed
		if (evt.getKeyCode() == KeyEvent.VK_Q && evt.isControlDown()) {
			System.exit(0);
		}
	}// GEN-LAST:event_formKeyPressed

	private void formWindowActivated(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowActivated
		mPanelController.getCurrentTable().requestFocusInWindow();
	}// GEN-LAST:event_formWindowActivated

	private void leftFileTableFocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_leftFileTableFocusGained
		mPanelController.focusLeftPanel();
	}// GEN-LAST:event_leftFileTableFocusGained

	private void rightFileTableFocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_rightFileTableFocusGained
		mPanelController.focusRightPanel();
	}// GEN-LAST:event_rightFileTableFocusGained

    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JPanel FileBrowserPanel;
    public static javax.swing.JLabel errorLabel;
    protected javax.swing.JLabel fileInfoLabel;
    private javax.swing.JPanel jPanel1;
    protected javax.swing.JTextField leftAdrField;
    protected javax.swing.JTable leftFileTable;
    protected javax.swing.JTextField leftFilterField;
    protected javax.swing.JPanel leftPanel;
    protected javax.swing.JScrollPane leftScrollPane;
    protected javax.swing.JTabbedPane leftTabPane;
    protected javax.swing.JMenuBar menuBar;
    protected javax.swing.JTextField rightAdrField;
    protected javax.swing.JTable rightFileTable;
    protected javax.swing.JPanel rightPanel;
    protected javax.swing.JScrollPane rightScrollPane;
    protected javax.swing.JTabbedPane rightTabPane;
    private javax.swing.JPanel statusBarPanel;
    protected javax.swing.JToolBar toolBar;
    // End of variables declaration//GEN-END:variables

	public boolean isLightMode() {
		return mLightMode;
	}

	public void setLightMode(boolean pLightMode) {
		mLightMode = pLightMode;
	}

}