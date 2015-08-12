package de.pd.pjc.service;

import java.util.Enumeration;

public interface PropertyService {

	/** Reloads the resource file.
	 * 
	 */
	public void reload();
	
	/** returns the property for the key.
	 * @param key
	 * @return
	 */
	public String getProperty(String key);
	
	/** returns the property for the key and def if key not found.
	 * @param key
	 * @param def
	 * @return
	 */
	public String getProperty(String key, String def);
	
	/** Set a property.
	 * @param key
	 * @param val
	 */
	public void setProperty(String key, String val);
	
	/** Returns an enumeration of all the keys in this property 
	 * list, including distinct keys in the default property list 
	 * if a key of the same name has not already been found 
	 * from the main properties list.
	 *
	 * @return an enumeration of all the keys 
	 * in this property list, including the keys in the 
	 * default property list.
	 */
	public Enumeration getPropertyNames();
}
