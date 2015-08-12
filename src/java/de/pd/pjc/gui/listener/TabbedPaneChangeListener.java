package de.pd.pjc.gui.listener;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.pd.pjc.gui.FileTablePanel;
import de.pd.pjc.service.ServiceFactory;

/** Update the path labels if the panel stat has been changed.
 * @author petros
 *
 */
public class TabbedPaneChangeListener implements ChangeListener {

	
	public TabbedPaneChangeListener() {
		super();
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent pE) {
		JTabbedPane source = (JTabbedPane) pE.getSource();
		FileTablePanel filePanel = (FileTablePanel) source.getComponentAt(source.getSelectedIndex());
		String currentPath = filePanel.getTable().getCurrentPath();
		if(currentPath != null) {
			ServiceFactory.getMainWindowService().updatePathLabels(ServiceFactory.getMainWindowController().getFocusedAdrField(), 
					source, currentPath);
		}
	}

}
