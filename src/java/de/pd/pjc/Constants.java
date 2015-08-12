package de.pd.pjc;

import java.awt.Font;
import java.io.File;
import java.text.Format;
import java.text.SimpleDateFormat;


public class Constants {

	public final static String SORT_NAME = "NAME";
	public final static String SORT_TYPE = "TYPE";
	public final static String SORT_TIME = "TIME";
	public final static String SORT_SIZE = "SIZE";
	
	public final static int COLUMN_NAME = 0;
	public final static int COLUMN_TYPE = 1;
	public final static int COLUMN_SIZE = 2;
	public final static int COLUMN_TIME = 3;
	
	public final static String SORT_ASC = "ASC";
	public final static String SORT_DESC = "DESC";

	/** Property prefixes */
	public final static String PROP_SORT = "sort.path.";
	
	public final static Format DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public final static String USER_HOME = System.getProperty("user.home");
	public final static String CONF_DIR = USER_HOME + File.separator + ".pjavacommander" + File.separator;
	
	public final static Font RC_MODE_FONT = new Font("Dialog", 1, 16);
	
}
