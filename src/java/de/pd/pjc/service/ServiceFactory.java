package de.pd.pjc.service;

import de.pd.pjc.gui.MainWindowPanelController;


public class ServiceFactory {

	private static FileService fileService;
	private static XMLSettingsService XMLSettingsService;
	private static MainWindowService mainWindowService;
	private static MainWindowPanelController mainWindowController;
	/**
	 * @return the fileService
	 */
	public static FileService getFileService() {
		return fileService;
	}
	/**
	 * @param pFileService the fileService to set
	 */
	public static void setFileService(FileService pFileService) {
		fileService = pFileService;
	}
	/**
	 * @return the mainWindowService
	 */
	public static MainWindowService getMainWindowService() {
		return mainWindowService;
	}
	/**
	 * @param pMainWindowService the mainWindowService to set
	 */
	public static void setMainWindowService(MainWindowService pMainWindowService) {
		mainWindowService = pMainWindowService;
	}
	/**
	 * @return the xMLSettingsService
	 */
	public static XMLSettingsService getXMLSettingsService() {
		return XMLSettingsService;
	}
	/**
	 * @param pSettingsService the xMLSettingsService to set
	 */
	public static void setXMLSettingsService(XMLSettingsService pSettingsService) {
		XMLSettingsService = pSettingsService;
	}
	/**
	 * @return the mainWindowController
	 */
	public static MainWindowPanelController getMainWindowController() {
		return mainWindowController;
	}
	/**
	 * @param pMainWindowController the mainWindowController to set
	 */
	public static void setMainWindowController(
			MainWindowPanelController pMainWindowController) {
		mainWindowController = pMainWindowController;
	}
	
	
}
