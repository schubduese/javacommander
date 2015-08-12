package de.pd.pjc.service.impl;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.springframework.util.StringUtils;

import de.pd.pjc.Constants;
import de.pd.pjc.bean.DirSettingsBean;
import de.pd.pjc.bean.FavouriteBean;
import de.pd.pjc.bean.MimeApplication;
import de.pd.pjc.bean.MimeSettingsBean;
import de.pd.pjc.exception.SettingsPathNotFoundException;
import de.pd.pjc.service.XMLSettingsService;
import de.pd.pjc.util.PJCUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class XMLSettingsServiceImpl implements XMLSettingsService {

	private String mFavouritesFile;
	private String mDirSettingsFile;
	private String mMimeSettingsFile;
	private String mCommandHistoryFile;

	protected final static Log mLog = LogFactory.getLog(XMLSettingsServiceImpl.class.getName());

	/**
	 * @return
	 */
	protected Document getDocument(String pXmlFile) {
		SAXBuilder builder = new SAXBuilder();
		Document doc = null;
		try {
			doc = builder.build(getFileUrl(pXmlFile));
		} catch (Exception e) {
			mLog.error("Error getting document for " + pXmlFile, e);
		}
		
		return doc;
	}
	
	/** Returns the file url.
	 * @return
	 */
	private String getFileUrl(String pXmlFile) {
		String file = (PJCUtils.isWindows()?"file:///":"") + Constants.CONF_DIR + pXmlFile;
		return file;
	}
	
	/** Writes a document into a file.
	 * @param pDoc
	 */
	protected void writeDocument(String pXmlFile, Document pDoc) {
		XMLOutputter out = new XMLOutputter(Format.getPrettyFormat());
		try {
			out.output(pDoc, new FileOutputStream(Constants.CONF_DIR + pXmlFile));
		} catch (Exception e) {
			mLog.error("Error writing file " + pXmlFile, e);
		}
	}

	
	
	/* (non-Javadoc)
	 * @see de.pd.pjc.service.FavouritesService#getFavourites()
	 */
	public Collection<FavouriteBean> getFavourites() {
		
		Document document = getDocument(mFavouritesFile);
		Element rootElement = document.getRootElement();
		
		Collection<FavouriteBean> favs = new ArrayList<FavouriteBean>();
		
		List<Element> children = rootElement.getChildren();
		for (Element child : children) {
			String name = child.getAttributeValue("name");
			String value = child.getAttributeValue("path");
			favs.add(new FavouriteBean(name, value));
		}
		
		return favs;
	}

	/* (non-Javadoc)
	 * @see de.pd.pjc.service.FavouritesService#addFavourite(de.pd.pjc.bean.FavouriteBean)
	 */
	public void addFavourite(FavouriteBean pFavBean) {

		Document document = getDocument(mFavouritesFile);
		Element rootElement = document.getRootElement();
		
		Element bookmark = new Element("bookmark");
		bookmark.setAttribute("name", pFavBean.getName());
		bookmark.setAttribute("path", pFavBean.getPath());
		
		rootElement.addContent(bookmark);
		
		writeDocument(mFavouritesFile, document);
	}

	public String getDirSettingsFile() {
		return mDirSettingsFile;
	}

	public void setDirSettingsFile(String pDirSettingsFile) {
		mDirSettingsFile = pDirSettingsFile;
	}

	public String getFavouritesFile() {
		return mFavouritesFile;
	}

	public void setFavouritesFile(String pFavouritesFile) {
		mFavouritesFile = pFavouritesFile;
	}

	/* (non-Javadoc)
	 * @see de.pd.pjc.service.XMLSettingsService#getDirSettings(java.lang.String)
	 */
	public DirSettingsBean getDirSettings(String pDir) {
		Document document = getDocument(mDirSettingsFile);
		Element rootElement = document.getRootElement();
		
		DirSettingsBean dirSettings = getDirSettingsByPath(pDir, rootElement);
		return dirSettings;
	}

	/** 
	 * @param pDir
	 * @param rootElement
	 * @return
	 */
	private DirSettingsBean getDirSettingsByPath(String pDir, Element rootElement) {
		DirSettingsBean dirSettings = null;
		
		List<Element> children = rootElement.getChildren();
		for (Element child : children) {
			String path = child.getAttributeValue("path");
			if(path == null || !path.equals(pDir)) continue;
			String sortOrder = child.getAttributeValue("sortOrder");
			dirSettings = new DirSettingsBean(path, sortOrder);
			dirSettings.setIconSize(child.getAttributeValue("iconSize"));
		}
		return dirSettings;
	}

	/* (non-Javadoc)
	 * @see de.pd.pjc.service.XMLSettingsService#storeDirSettings(de.pd.pjc.bean.DirSettingsBean)
	 */
	public void storeDirSettings(DirSettingsBean pDirSettings) {
		Document document = getDocument(mDirSettingsFile);
		Element rootElement = document.getRootElement();
		
		List<Element> children = rootElement.getChildren();
		boolean dirExists = false;
		for (Element child : children) {
			String path = child.getAttributeValue("path");
			if(path == null || !path.equals(pDirSettings.getPath())) continue;
			if(pDirSettings.getSortOrder() != null) {
				child.setAttribute("sortOrder", pDirSettings.getSortOrder());
			}
			if(pDirSettings.getIconSize() != null) {
				child.setAttribute("iconSize", pDirSettings.getIconSize());
			}
			dirExists = true;
		}
		// if no entry exists for the path, then add
		if(!dirExists) {
			Element dir = new Element("dir");
			dir.setAttribute("path", pDirSettings.getPath());
			if(pDirSettings.getSortOrder() != null) {
				dir.setAttribute("sortOrder", pDirSettings.getSortOrder());
			}
			if(pDirSettings.getIconSize() != null) {
				dir.setAttribute("iconSize", pDirSettings.getIconSize());
			}
			rootElement.addContent(dir);
		}
		
		writeDocument(mDirSettingsFile, document);
	}

	
	/* (non-Javadoc)
	 * @see de.pd.pjc.service.XMLSettingsService#storeMimeSettings(java.lang.String, de.pd.pjc.bean.MimeApplication)
	 */
	public void storeMimeSettings(String pFileExtension, MimeApplication pMimeApp) {
		Document document = getDocument(mMimeSettingsFile);
		Element rootElement = document.getRootElement();
		
		Element extensionElement = null;
		List<Element> extensions = rootElement.getChildren("mime");
		for (Element extension : extensions) {
			String ext = extension.getAttributeValue("extension");
			if(ext == null || !ext.equals(pFileExtension)) continue;
			extensionElement = extension;
			break;
		}

		// create new
		if(extensionElement == null) {
			extensionElement = new Element("mime");
			extensionElement.setAttribute("extension", pFileExtension);
			rootElement.addContent(extensionElement);
			Element mimeApps = new Element("mimeApps");
			extensionElement.addContent(mimeApps);
		}
		
		Element mimeApps = extensionElement.getChild("mimeApps");
		List<Element> apps = mimeApps.getChildren("app");
		Element appElement = null;
		for (Element app : apps) {
			String name = app.getAttributeValue("name");
			if(name != null && !name.equals(pMimeApp.getName())) continue;
			appElement = app;
		}
		
		// create new
		if(appElement == null && pMimeApp != null) {
			appElement = new Element("app");
			appElement.setAttribute("name", pMimeApp.getName());
			mimeApps.addContent(appElement);
		}
		
		if(appElement != null) {
			appElement.setAttribute("command", pMimeApp.getCommand());
			if(StringUtils.hasLength(pMimeApp.getIcon())) {
				appElement.setAttribute("icon", pMimeApp.getIcon());
			}
			if(StringUtils.hasLength(pMimeApp.getArgs())) {
				appElement.setAttribute("args", pMimeApp.getArgs());
			}
			appElement.setAttribute("default", Boolean.toString(pMimeApp.isDefault()));
		}
		
		writeDocument(mMimeSettingsFile, document);
	}
	
	public String getMimeSettingsFile() {
		return mMimeSettingsFile;
	}

	public void setMimeSettingsFile(String pMimeSettingsFile) {
		mMimeSettingsFile = pMimeSettingsFile;
	}

	/* (non-Javadoc)
	 * @see de.pd.pjc.service.XMLSettingsService#getMimeSettings(java.lang.String)
	 */
	public MimeSettingsBean getMimeSettings(String pFileExt) {
		
		MimeSettingsBean mimeSettings = new MimeSettingsBean();
		if(pFileExt == null) {
			return mimeSettings;
		}
		
		Document document = getDocument(mMimeSettingsFile);
		Element rootElement = document.getRootElement();
		
		Element extensionElement = null;
		List<Element> extensions = rootElement.getChildren("mime");
		for (Element extension : extensions) {
			String ext = extension.getAttributeValue("extension");
			if(ext == null || !ext.equalsIgnoreCase(pFileExt)) continue;
			extensionElement = extension;
			break;
		}
		
		if(extensionElement == null) {
			mLog.debug("No mime settings found for extension " + pFileExt);
			return mimeSettings;
		}
		
		Element mimeApps = extensionElement.getChild("mimeApps");
		List<Element> apps = mimeApps.getChildren("app");
		for (Element app : apps) {
			String name = app.getAttributeValue("name");
			String command = app.getAttributeValue("command");
			String defaultApp = app.getAttributeValue("default");
			String icon = app.getAttributeValue("icon");
			String args = app.getAttributeValue("args");
			boolean def = (defaultApp != null && defaultApp.equals("true"));
			mimeSettings.addApplication(new MimeApplication(name, command, icon, args, def));
		}
		
		
		return mimeSettings;
	}

	/* (non-Javadoc)
	 * @see de.pd.pjc.service.XMLSettingsService#getAllMimeSettings()
	 */
	public Collection<MimeSettingsBean> getAllMimeSettings() {
		Collection<MimeSettingsBean> mimes = new ArrayList<MimeSettingsBean>();
		
		Document document = getDocument(mMimeSettingsFile);
		Element rootElement = document.getRootElement();
		
		
		List<Element> extensions = rootElement.getChildren("mime");
		for (Element extension : extensions) {
			MimeSettingsBean mimeSettings = new MimeSettingsBean();
			String ext = extension.getAttributeValue("extension");
			mimeSettings.setExtension(ext);
			
			MimeSettingsBean mimeApps = getMimeSettings(ext);
			mimeSettings.setApps(mimeApps.getApps());
			
			mimes.add(mimeSettings);
		}
		
		return mimes;
	}

	/* (non-Javadoc)
	 * @see de.pd.pjc.service.XMLSettingsService#addCommandHistory(java.lang.String)
	 */
	public void addCommandHistory(String pCommand) {
		
		Collection<String> commandHistory = getCommandHistory();
		if(commandHistory.contains(pCommand)) return;
		
		Document document = getDocument(mCommandHistoryFile);
		Element rootElement = document.getRootElement();
		
		
		Element command = new Element("command");
		command.setText(pCommand);
		
		rootElement.addContent(command);
		
		writeDocument(mCommandHistoryFile, document);
	}

	/* (non-Javadoc)
	 * @see de.pd.pjc.service.XMLSettingsService#getCommandHistory()
	 */
	public Collection<String> getCommandHistory() {

		Collection<String> cmdHistory = new ArrayList<String>();
		
		Document document = getDocument(mCommandHistoryFile);
		Element rootElement = document.getRootElement();
		
		
		List<Element> commands = rootElement.getChildren();
		for (Element cmd : commands) {
			cmdHistory.add(cmd.getValue());
		}
		
		return cmdHistory;
	}

	public String getCommandHistoryFile() {
		return mCommandHistoryFile;
	}

	public void setCommandHistoryFile(String pCommandHistoryFile) {
		mCommandHistoryFile = pCommandHistoryFile;
	}

	public void checkSettingDirExists() throws SettingsPathNotFoundException {
		try {
			SAXBuilder builder = new SAXBuilder();
			builder.build(getFileUrl(mCommandHistoryFile));
		} catch (Exception fe) {
			throw new SettingsPathNotFoundException("error getting xml settings files. Please read the INSTALL.txt file!");
		}
	}

	/* (non-Javadoc)
	 * @see de.pd.pjc.service.XMLSettingsService#deleteMimeSettings(java.lang.String, de.pd.pjc.bean.MimeApplication)
	 */
	public void deleteMimeSettings(String pFileExt, String pAppName) {
		if(pFileExt == null) {
			return;
		}
		
		Document document = getDocument(mMimeSettingsFile);
		Element rootElement = document.getRootElement();
		
		Element extensionElement = null;
		List<Element> extensions = rootElement.getChildren("mime");
		for (Element extension : extensions) {
			String ext = extension.getAttributeValue("extension");
			if(ext == null || !ext.contains(pFileExt)) continue;
			extensionElement = extension;
			break;
		}
		
		if(extensionElement == null) {
			mLog.debug("No mime settings found for extension " + pFileExt);
			return;
		}
		
		Element mimeApps = extensionElement.getChild("mimeApps");
		List<Element> apps = mimeApps.getChildren("app");
		Element toRemove = null;
		for (Element app : apps) {
			String name = app.getAttributeValue("name");
			if(name.equals(pAppName)) {
				toRemove = app;
				mLog.debug("removed command " + name + " for extension " + pFileExt);
				break;
			}
		}
		
		mimeApps.removeContent(toRemove);
		
		writeDocument(mMimeSettingsFile, document);
	}

}
