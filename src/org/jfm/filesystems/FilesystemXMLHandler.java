package org.jfm.filesystems;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class FilesystemXMLHandler extends DefaultHandler {
   
   /*<filesystem>
	<name>TestOne</name>
	<class>jfm.org.BlaBla</class>
	<dependencies>
		<jar>jfm1.jar</jar>
		<jar>jfm2.jar</jar>
		<jar>jfm3.jar</jar>
	</dependencies>
	<configurationpanel>
		<class>classname</class>
	</configurationpanel>	
</filesystem>*/

	private static String FILESYSTEM_ELEMENT_NAME="filesystem";
	private static String NAME_ELEMENT_NAME="name";
	private static String CLASS_ELEMENT_NAME="class";
	private static String DEPENDENCIES_ELEMENT_NAME="dependencies";
	private static String JAR_ELEMENT_NAME="jar";
	private static String CONFIG_PANEL_ELEMENT_NAME="configurationpanel";
	
	private final static int FILESYSTEM_ELEMENT=0;
	private final static int NAME_ELEMENT=1;
	private final static int CLASS_ELEMENT=2;
	private final static int DEPENDENCIES_ELEMENT=3;
	private final static int JAR_ELEMENT=4;
	private final static int CONFIG_PANEL_ELEMENT=5;
	
	private int currentElement=-1;
	
	private String name;
	private String theClass;
	private java.util.List<String> dependencies=null;
	private String confPanelClass=null;
	
   private int previousElement=-1;
   
   public void startElement (String uri, String localName,
			      String qName, Attributes attributes)
	throws SAXException
    {
	   if(currentElement!=-1)previousElement=currentElement;
		if(FILESYSTEM_ELEMENT_NAME.equals(qName)) currentElement=FILESYSTEM_ELEMENT;
		if(NAME_ELEMENT_NAME.equals(qName)) currentElement=NAME_ELEMENT;
		if(CLASS_ELEMENT_NAME.equals(qName)) currentElement=CLASS_ELEMENT;
		if(DEPENDENCIES_ELEMENT_NAME.equals(qName)) currentElement=DEPENDENCIES_ELEMENT;
		if(JAR_ELEMENT_NAME.equals(qName)) currentElement=JAR_ELEMENT;
		if(CONFIG_PANEL_ELEMENT_NAME.equals(qName)) currentElement=CONFIG_PANEL_ELEMENT;
    }

    public void endElement (String uri, String localName, String qName)
	throws SAXException
    {
    	currentElement=-1;
    	//System.out.println("end: uri:"+uri+",localname:"+localName+" , qname:"+qName);
    }
    
    public void characters (char ch[], int start, int length)
	throws SAXException
    {
    	switch(currentElement)
    	{
    		case NAME_ELEMENT:
    			name=new String(ch,start,length);
    			break;
    		case CLASS_ELEMENT:
    			if(previousElement==CONFIG_PANEL_ELEMENT)
    			{
    				confPanelClass=new String(ch,start,length);
    			}
    			else
    			{
    				theClass=new String(ch,start,length);
    			}
    			break;
    		case DEPENDENCIES_ELEMENT:
    			dependencies=new ArrayList<String>();
    			break;
    		case JAR_ELEMENT:
    			dependencies.add(new String(ch,start,length));
    			break;
    		case CONFIG_PANEL_ELEMENT:
    			
    			break;
    	}
    }   
    
	public void error(SAXParseException e)
	throws SAXParseException
	{
	    throw e;
	}

	/**
	 * @return the dependencies
	 */
	public java.util.List<String> getDependencies() {
		return dependencies;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the theClass
	 */
	public String getTheClass() {
		return theClass;
	}    
	
	public String getConfPanelClass()
	{
		return confPanelClass;
	}
}
