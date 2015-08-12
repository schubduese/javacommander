package de.pd.pjc.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import de.pd.pjc.gui.dialog.JCImageDialog;
import de.pd.pjc.gui.dialog.JCThumbDialog;
import de.pd.pjc.gui.menu.JCToolBar;
import de.pd.pjc.gui.table.FileTable;
import de.pd.pjc.gui.table.FileTableModel;
import de.pd.pjc.gui.table.TableSorter;

public abstract class BasePanelController {

   public abstract void refocusByTableName(String pTableName);
   public abstract void focusLeftPanel();
   public abstract void focusLeftPanel(boolean pFocusTable);
   public abstract FileTablePanel getLeftTablePanel();
   public abstract FileTablePanel getRightTablePanel();
   public abstract FileTable getLeftTable();
   public abstract FileTable getRightTable();
   public abstract FileTable getCurrentTable();
   public abstract void focusRightPanel();
   public abstract void focusRightPanel(boolean pFocusTable);
   public abstract void switchFocus();
   public abstract JTextField getFocusedFilterField();
   public abstract FileTableModel getFocusedModel();
   public abstract TableSorter getFocusedSorter();
   public abstract JScrollPane getLeftScrollPane();
   public abstract JScrollPane getRightScrollPane();
   public abstract JScrollPane getFocusedScrollPane();
   public abstract String getCurrentPath();
   public abstract String getOppositPath();
   public abstract String getLeftPath();
   public abstract void setLeftPath(String pLeftPath);
   public abstract String getRightPath();
   public abstract void setRightPath(String pRightPath);
	public abstract JPanel getLeftPanel();
	public abstract JPanel getRightPanel();
   public abstract JPanel getCurrentPanel();
   
   public abstract JTabbedPane getFocusedTabPane();
   public abstract JTabbedPane getLeftTabPane();
   public abstract JTabbedPane getRightTabPane();
   public abstract JTabbedPane getTabPaneByTableName(String pTableName);
   
   
   public abstract MediaPreviewPanel getLeftMediaPanel();
   public abstract MediaPreviewPanel getRightMediaPanel();
   public abstract MediaPreviewPanel getCurrentMediaPanel();
   public abstract JTextField getLeftAdrField();
   public abstract JTextField getRightAdrField();
   public abstract JTextField getFocusedAdrField();
   public abstract JTextField getAdrFieldByTableName(String pTableName);
   public abstract JLabel getFileInfoLabel();
   public abstract boolean isLeftPanelSelected();
   public abstract boolean isLeftPanel(String pTableName);
   public abstract boolean isRightPanel(String pTableName);
   public abstract boolean isRightPanelSelected();
   public abstract void printStatus();

   public abstract JCToolBar getToolBar();
   
   public abstract void setCurrentImageDialog(JCImageDialog pImagDialog);
   public abstract JCImageDialog getCurrentImageDialog();
   public abstract void setCurrentThumbDialog(JCThumbDialog pDialog);
   public abstract JCThumbDialog getCurrentThumbDialog();
}
