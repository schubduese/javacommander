package de.pd.pjc.gui.listener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.pd.pjc.service.ServiceFactory;

public class TabbedPaneMouseListener extends MouseAdapter {

	protected Log mLog = LogFactory.getLog(TabbedPaneMouseListener.class.getName());

	private String mTableName;

	/**
	 * @param pTableName
	 * @param pWrapper
	 */
	public TabbedPaneMouseListener(String pTableName, JTextField pAdrField) {
		super();
		mTableName = pTableName;
	}

	@Override
	public void mouseClicked(MouseEvent evt) {
		JTabbedPane tab = (JTabbedPane) evt.getSource();
		if (evt.getClickCount() == 2) {
			ServiceFactory.getMainWindowService().addTabToPane(tab, mTableName);
		}
		ServiceFactory.getMainWindowController().refocusByTableName(mTableName);
	}

}
