package de.pd.pjc.util;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.pd.pjc.bean.FileElement;
import de.pd.pjc.service.ServiceFactory;

public class IconFactory {

	private final static Log mLog = LogFactory.getLog(IconFactory.class
			.getName());

	public static String ROOT_IMG_PATH = "resources" + File.separator + "icons"
			+ File.separator;

	public static String EXT_IMG_PATH_NORMAL = "resources" + File.separator
			+ "icons" + File.separator + "mime" + File.separator + "normal"
			+ File.separator;

	public static String EXT_IMG_PATH_BIG = "resources" + File.separator
			+ "icons" + File.separator + "mime" + File.separator + "big"
			+ File.separator;

	private static String APP_IMG_PATH = "resources" + File.separator + "icons"
			+ File.separator + "apps" + File.separator;

	public final static int ICON_SIZE_NORMAL = 0;

	public final static int ICON_SIZE_BIG = 1;

	/**
	 * Returns an image icon by file name.
	 * 
	 * @param pName
	 * @return
	 */
	public static ImageIcon getIconByName(String pName) {
		return getIconByName(pName, ROOT_IMG_PATH);
	}

	/**
	 * @param pName
	 * @return
	 */
	public static ImageIcon getAppIconByName(String pName) {
		return getIconByName(pName, APP_IMG_PATH);
	}

	/**
	 * returns an image icon by a file extension.
	 * 
	 * @param pFileName
	 * @return
	 */
	public static ImageIcon getIconByFileExt(String pFileName, int pIconSize) {

		String mime = ServiceFactory.getFileService().getFileMimeType(
				pFileName);

		return getIconByName(mime + ".png",
				(pIconSize == ICON_SIZE_NORMAL ? EXT_IMG_PATH_NORMAL
						: EXT_IMG_PATH_BIG));
	}

	/**
	 * Returns an image icon by file name.
	 * 
	 * @param pName
	 * @param pImgPath
	 * @return
	 */
	public static ImageIcon getIconByName(String pName, String pImgPath) {
		URL iconsUrl = ClassLoader.getSystemResource(pImgPath + pName);
		if (iconsUrl == null) {
//			mLog.warn("Icons resource " + pImgPath + pName + " not found");
			return null;
		}
		return new ImageIcon(iconsUrl);
	}

	public static ImageIcon getPreviewImage(FileElement element, int width, int height) {
		ImageIcon icon = null;
		try {
			InputStream in = new FileInputStream(new File(element
					.getAbsolutePath()));
			if (in == null)
				return null;
			Image img = getScaledImg(ImageIO.read(in), width, height);
//			Image img = getScaledImg(element.getAbsolutePath(), width, height);
			if (img == null)
				return null;
			icon = new ImageIcon(img);
			in.close();
		} catch (IOException ignored) {
		}
		return icon;
	}
	
	/** Returns the image resolution as formatted string.
	 * @param pElement
	 * @return
	 */
	public static String getImageResolutionFormatted(FileElement pElement) {
		Dimension imageResolution = getImageResolution(pElement);
		return imageResolution.width + "x" + imageResolution.height;
	}
	
	/** Returns the image resolution as dimension.
	 * @param element
	 * @return
	 */
	public static Dimension getImageResolution(FileElement element) {
		InputStream in;
		Image img = null;
		try {
			in = new FileInputStream(new File(element
					.getAbsolutePath()));
			img = ImageIO.read(in);
			in.close();
		} catch (Exception e) {
		}
		if(img == null) {
			return null;
		}
		return new Dimension(img.getWidth(null), img.getHeight(null));
	}
	
//
//	public static Image getScaledImg(String image, int width, int height) {
//		
//		MagickImage scaled = null;
//		try {
//			ImageInfo info = new ImageInfo(image);
//			MagickImage img = new MagickImage(info);
//			
//			int w = img.getDimension().width;
//			int h = img.getDimension().height;
//			
//			double scale = (double) height / (double)h;
//			
//			int scaleWidth = (int) (scale * w);
//			if(scaleWidth == 0) scaleWidth = 1;
//			int scaleHeight = (int) (scale * h);
//			if(scaleHeight == 0) scaleHeight = 1;			
//			
//			scaled = img.scaleImage(scaleWidth, scaleHeight);
//			
//			scaled.writeImage(new ImageInfo("/tmp/tmppic.jpg"));
//		} catch (MagickException e) {
//			mLog.error(e);
//		}
//		InputStream in = null;
//		try {
//			in = new FileInputStream(new File("/tmp/tmppic.jpg"));
//		} catch (FileNotFoundException e) {
//			mLog.error(e);
//		}
//		try {
//			return ImageIO.read(in);
//		} catch (IOException e) {
//			mLog.error(e);
//			return null;
//		}
//		
//	}
//	
	public static Image getScaledImg(Image image, int width, int height) {
		if (image == null)
			return null;
		int w = image.getWidth(null);
		int h = image.getHeight(null);

//		double scale = (double) (w > h ? width : height) / (double) (w > h ? w : h);
		double scale = (double) height / (double)h;
		
//		mLog.info("ww " + width + " hh " + height + " W: " + w + " H: " + h + " scale " + scale);

		// If the image is smaller than the desired image size, don't bother
		// scaling.
		if (scale >= 1.0d)
			return image;

		AffineTransform tx = new AffineTransform();
		tx.scale(scale, scale);

		int scaleWidth = (int) (scale * w);
		if(scaleWidth == 0) scaleWidth = 1;
		int scaleHeight = (int) (scale * h);
		if(scaleHeight == 0) scaleHeight = 1;
		BufferedImage outImage = new BufferedImage(scaleWidth, scaleHeight, BufferedImage.TYPE_INT_RGB);

		// Paint image.
		Graphics2D g2d = outImage.createGraphics();
		g2d.drawImage(image, tx, null);
		g2d.dispose();

		return outImage;
	}

}
