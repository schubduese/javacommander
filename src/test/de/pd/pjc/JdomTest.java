package de.pd.pjc;

import java.io.FileOutputStream;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class JdomTest {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws JDOMException 
	 */
	public static void main(String[] args) throws JDOMException, IOException {
		
		// writing
		Element root = new Element("mimeSettings");
		Document doc = new Document(root);
		
		Element mime = new Element("mime");
		mime.setAttribute("extension", "jpg");
		
		Element mimeApps = new Element("mimeApps");
		Element mimeApp = new Element("app");
		mimeApp.setAttribute("name", "VideonLAN");
		mimeApp.setAttribute("command", "/usr/bin/vlc");
		mimeApps.addContent(mimeApp);
		
		Element mimeApp2 = new Element("app");
		mimeApp2.setAttribute("name", "qiv");
		mimeApp2.setAttribute("command", "/usr/bin/qiv");
		mimeApps.addContent(mimeApp2);

		mime.addContent(mimeApps);
		
		root.addContent(mime);
		
		
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		outputter.output(doc, new FileOutputStream("/tmp/test.xml"));
		
		
//		// Reading
//		SAXBuilder builder = new SAXBuilder();
//		Document document = builder.build("/tmp/test.xml");
//		outputter.output(document, System.out);
//	
//		Element rootElement = document.getRootElement();
//
//		Element child2 = rootElement.getChild("dir");
//		System.out.println("Child2 " + child2.getAttributeValue("sortOrder"));
//		
//		List<Element> children = rootElement.getChildren();
//		for (Element child : children) {
//			System.out.println("Path " + child.getAttributeValue("path"));
//			
//		}
//		
		
		
		//  --
		System.out.println("Done");
		
	}

}
