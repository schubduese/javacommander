package de.pd.pjc.service;

import java.util.Collection;

import de.pd.pjc.bean.DirSettingsBean;
import de.pd.pjc.bean.FavouriteBean;
import de.pd.pjc.bean.MimeApplication;
import de.pd.pjc.bean.MimeSettingsBean;
import de.pd.pjc.exception.SettingsPathNotFoundException;

public interface XMLSettingsService {

	/** Returns a list of all favourites.
	 * @return
	 */
	public Collection<FavouriteBean> getFavourites();
	
	/** Adds an favourite.
	 * @param pFavBean
	 */
	public void addFavourite(FavouriteBean pFavBean);
	
	/** Returns the dir settings from the xml file. 
	 * @param pDir
	 * @return null if no entry found for pDir
	 */
	public DirSettingsBean getDirSettings(String pDir);
	
	/** stores direcotry settings.
	 * 
	 */
	public void storeDirSettings(DirSettingsBean pDirSettings);
	
	/** Returns the mime settings of a file extension.
	 * @param pFileExt
	 * @return
	 */
	public MimeSettingsBean getMimeSettings(String pFileExt);
	
	/** Returns all mime settings.
	 * @return
	 */
	public Collection<MimeSettingsBean> getAllMimeSettings();
	
	/** stores mime settings.
	 * 
	 */
	public void storeMimeSettings(String pFileExtension, MimeApplication pMimeApp);

	/** delete mime settings.
	 * 
	 */
	public void deleteMimeSettings(String pFileExt, String pAppName);
	
	/**
	 * @return
	 */
	public Collection<String> getCommandHistory();
	
	/** Adds a command to the history
	 * @param pCommand
	 */
	public void addCommandHistory(String pCommand);
	
	/** Checks if the dir settings exists.
	 * 
	 */
	public void checkSettingDirExists() throws SettingsPathNotFoundException;
}
