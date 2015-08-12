/*
 * PropertyFactory.java
 *
 * Created on 19. September 2002, 23:13
 */

package de.pd.pjc.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;


import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import org.apache.log4j.Logger;

import de.pd.pjc.service.PropertyService;

/**
 *
 * @author  $Author: petros $
 * @version $Revision: 1.2 $
 */
public class PropertyServiceImpl implements PropertyService {
	
	private Properties prop;
	private String propertyFile;
	private final Logger mLog = Logger.getLogger(PropertyServiceImpl.class.getName());
	
	/** Creates a new instance of PropertyFactory */
	public PropertyServiceImpl(String file) {
		initialize(file);
	}
		
	/** Reloads the PropertyFile
	 */
	public void reload() {
		if(propertyFile == null) {
			mLog.error("Could not load property file. Please initialize first");
			return;
		}
		initialize(propertyFile);
	}
	
	/** Initializes the PropertyLoader
	 *
	 *@param file The property file
	 */
	private void initialize(String file) {
		propertyFile = file;
		setPropertyFile(file);
	}
	
	/** Set the property file
	 *
	 *@param file the property file
	 */
	private void setPropertyFile(String file) {
		if(file == null) {
			prop = new Properties();
		} else {
			try {
				
				File propFile = new File(getClass().getClassLoader().getResource(file).getFile());
				FileInputStream content = new FileInputStream(propFile);
				mLog.debug("Setting property file " + propFile.getAbsoluteFile());
				prop = new Properties();
				prop.load(content);
			} catch(FileNotFoundException fnex) {
				mLog.error("Can not find configuration file", fnex);
			} catch(IOException ioex) {
				mLog.error("IO Error", ioex);
			}
		}
	}
	
	/** Get property value
	 *
	 *@param key the property key
	 *@return the property
	 */
	public String getProperty(String key) {
		return prop.getProperty(key);
	}
	
	/** Get property keys
	 *
	 *@param key the property key
	 *@param def the default value
	 *@return the property
	 */
	public String getProperty(String key, String def) {
		if(def != null) {
			return prop.getProperty(key, def);
		} else {
			return prop.getProperty(key);
		}
	}
	
	/** Set property
	 *
	 *@param key the key
	 *@param val the value of the key
	 */
	public void setProperty(String key, String val) {
		try {
			StringBuffer buf = new StringBuffer();
			BufferedReader reader = new BufferedReader(new FileReader(propertyFile));
			String line;
			while((line = reader.readLine()) != null) {
				if(!line.startsWith(key)) {
					buf.append(line + "\n");
				}
			}
			buf.append(key + "=" + val);
			BufferedWriter writer = new BufferedWriter(new FileWriter(propertyFile));
			writer.write(buf.toString());
			writer.flush();
			writer.close();
		} catch(FileNotFoundException fnex) {
			mLog.error("Can not find configuration file", fnex);
		} catch(IOException ioex) {
			mLog.error("IO Error", ioex);
		}
	}
	
	/* (non-Javadoc)
	 * @see de.pd.pjc.service.PropertyService#getPropertyNames()
	 */
	public Enumeration getPropertyNames() {
		return prop.propertyNames();
	}
}

/*
 * $Log: PropertyLoader.java,v $
 * Revision 1.2  2003/06/07 11:55:30  petros
 * few changes over 2 weeks
 *
 * Revision 1.1.1.1  2003/05/31 12:52:48  petros
 * Project JavaCommander checkin
 *
 */