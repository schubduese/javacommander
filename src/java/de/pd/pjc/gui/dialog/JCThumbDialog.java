/**
 * PJavaCommander by Petros Dolaschjan
 *
 */ 
package de.pd.pjc.gui.dialog;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.pd.pjc.FilePatternFilter;
import de.pd.pjc.Settings;
import de.pd.pjc.bean.FileElement;
import de.pd.pjc.gui.listener.TableKeyListener;
import de.pd.pjc.gui.listener.TableMouseListener;
import de.pd.pjc.gui.table.FileTable;
import de.pd.pjc.gui.table.icon.IconFileTable;
import de.pd.pjc.gui.table.icon.IconFileTableModel;
import de.pd.pjc.service.ServiceFactory;
import de.pd.pjc.service.impl.FileServiceImpl;

public class JCThumbDialog extends JDialog {

	ArrayList<FileElement> fileArr = new ArrayList<FileElement>();
	private IconFileTableModel mModel;
	private FileTable mTable;
	private JScrollPane mSp;

	public FileTable getTable() {
		return mTable;
	}

	public JCThumbDialog(FileElement pFileElement) {
		super();
		
		ArrayList<FileElement> files = null;
		try {
			files = (ArrayList<FileElement>) ServiceFactory.getFileService().getFiles(pFileElement.getParentPath(), new FilePatternFilter());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Collections.sort(files);
		for (FileElement fileElement : files) {
			if(fileElement.isTopElement() ||
					fileElement.isDirectory() ||
					!FileServiceImpl.isImage(fileElement.getFileName())) {
				continue;
			}
			fileArr.add(fileElement);
		}
		
		init();
		
		ServiceFactory.getMainWindowController().setCurrentTable(mTable);
	}
	
	/**
	 * @param pFileElements
	 * @throws HeadlessException
	 */
	public JCThumbDialog(ArrayList<FileElement> pFileElements) throws HeadlessException {
		super();
		fileArr = pFileElements;
		
		init();
	}
	
	/**
	 * 
	 */
	private void init() {
		setSize(800, 600);
		
		getContentPane().setLayout(new GridLayout(1, 0));
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1, 0));
		
		getContentPane().add(mainPanel);
		mainPanel.setBackground(Color.white);
		setTitle(fileArr.get(0).getParentPath());
		
		mSp = new JScrollPane();
		mSp.getViewport().setBackground(Color.white);
		
		mModel = new IconFileTableModel();
		mModel.setColCount(getColCount());
		fillModel();
		
		mainPanel.add(mSp);

		addComponentListener(new java.awt.event.ComponentAdapter() {
			public void componentResized(java.awt.event.ComponentEvent evt) {
				mModel.setColCount(getColCount());
				fillModel();
			}
		});
		
		addFocusListener(new FocusAdapter() {
		
			@Override
			public void focusGained(FocusEvent pE) {
				ServiceFactory.getMainWindowController().setCurrentTable(mTable);
			}
		
		});
	}
	
	private void fillModel() {
		mModel.clear();
		for (FileElement fileElement : fileArr) {
			mModel.addFile(fileElement);
		}
		mTable = new IconFileTable(mModel);
		mTable.addMouseListener(new TableMouseListener());
		mTable.addKeyListener(new TableKeyListener());
		mSp.setViewportView(mTable);
	}
	
	private int getColCount() {
		int colCount = getWidth() / Settings.TABLE_ICON_WIDTH;
		return colCount;
	}
}


/*
 * $Log$
 */