package de.pd.pjc;

import java.awt.Color;
import java.awt.Font;

public class Settings {

	/** COlors */
	public final static Color TABLE_BG = Color.white;
	public final static Color TABLE_ROW_ODD = new Color(238, 246, 255);
	public final static Color TABLE_ROW_FILENAME = new Color(242, 242, 242);
	public final static Color TABLE_ROW_INACTIVE = new Color(242, 242, 242);
//	public final static Color DEFAULT_BG_COLOR = new Color(236,233,216);
	public final static Color DEFAULT_BG_COLOR = new Color(239,239,234);
	public final static Color TAB_INACTIVE_BG = new Color(191,191,191);
	public final static Color BLUE_BG = new Color(167, 198, 255);
	
	
	public static final Font DEFAULT_FONT = new Font("Dialog", Font.TRUETYPE_FONT, 12);
	public static final Font DEFAULT_FONT_BOLD = new Font("Dialog", Font.BOLD, 12);
	
	/** table settings */
	public final static int TABLE_ROW_HEIGHT_NORMAL = 18;
	public final static int TABLE_ROW_HEIGHT_BIG = 72;
	public final static int DEFAULT_TABLE_ROW_HEIGHT = TABLE_ROW_HEIGHT_NORMAL;
	
	public final static int TABLE_ICON_WIDTH = 180;
	public final static int TABLE_ICON_HEIGHT = 140;
	
	public final static int THUMB_WIDTH = 100;
	public final static int THUMB_HEIGHT = 80;
	
	public final static boolean SHOW_IMAGE_PREVIEW = true;
	
}
