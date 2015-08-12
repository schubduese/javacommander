package de.pd.pjc;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

import de.pd.pjc.gui.MainWindow;
import de.pd.pjc.gui.MainWindowPanelController;
import de.pd.pjc.gui.MainWindowWrapper;
import de.pd.pjc.service.FileService;
import de.pd.pjc.service.MainWindowService;
import de.pd.pjc.service.ServiceFactory;
import de.pd.pjc.service.XMLSettingsService;


public class Main {

	final static Log mLog = LogFactory.getLog(Main.class.getName());
	
	private static String mLpath = null;
	private static String mRpath = null;
	private static boolean mLightMode;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {


		for (int ii = 0; ii < args.length; ii++) {
			String arg = args[ii];
			if(arg.equals("-l")) {
				mLightMode = true;
				continue;
			}
			if(mLpath == null && StringUtils.hasText(arg)) {
				mLpath = arg;
				if(mLpath.equals(".")) {
					mLpath = System.getProperty("user.dir");
				}
			} else if( StringUtils.hasText(arg)) {
				mRpath = arg;
			}
		}
		
		mLog.info("Running PJavaCommander...");
      java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {
         	long start = new Date().getTime();
         	
         	ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
         	MainWindowPanelController panelController = (MainWindowPanelController) ctx.getBean("MainWindowPanelController");
         	panelController.setLeftPath(mLpath);
         	panelController.setRightPath(mRpath);
         	initServices(ctx, panelController);
         	MainWindow mainWindow = new MainWindowWrapper(panelController, mLightMode);
            mainWindow.setVisible(true);
         	
         	long end = new Date().getTime();
            System.out.println(end-start + " ms");
         }

			private void initServices(ApplicationContext ctx, MainWindowPanelController panelController) {
				FileService fileService = (FileService) ctx.getBean("FileService");
      		XMLSettingsService XMLSettingsService = (XMLSettingsService) ctx
      				.getBean("XMLSettingsService");

      		MainWindowService mainWindowService = (MainWindowService) ctx.getBean("MainWindowService");
      		ServiceFactory.setFileService(fileService);
      		ServiceFactory.setMainWindowService(mainWindowService);
      		ServiceFactory.setXMLSettingsService(XMLSettingsService);
      		ServiceFactory.setMainWindowController(panelController);
			}
     });
	}

}
