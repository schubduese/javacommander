package de.pd.pjc.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.pd.pjc.bean.MimeApplication;

import junit.framework.TestCase;

public class XMLSettingsServiceTest extends TestCase {

	XMLSettingsService service = null;
	
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		service = (XMLSettingsService) ctx.getBean("XMLSettingsService"); 
	}

	public void testAddFavourite() {
	}

	public void testStoreDirSettings() {
	}

	public void testStoreMimeSettings() {
		MimeApplication app = new MimeApplication();
		app.setName("Mplayer");
		app.setCommand("/usr/bin/mplayer");
//		app.setIcon("display.jpg");
		app.setDefault(true);
		service.storeMimeSettings("wmv", app);
	}

}
