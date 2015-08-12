/**
 * PJavaCommander by Petros Dolaschjan
 *
 */ 
package de.pd.pjc.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.JComponent;

import de.pd.pjc.bean.FileElement;
import de.pd.pjc.gui.dialog.JCImageDialog;

public class ImagePanel extends JComponent {

	int mWidth;
	int mHeight;
	
	int mOriginX;
	int mOriginY;
	
	private BufferedImage mSourceFile;
	private BufferedImage mScaleImage;
	
	float mScale;
	
	private boolean mRescale;
	
	private JCImageDialog mMother;
	
	public ImagePanel(JCImageDialog pMother) {
		super();
		mMother = pMother;
	}
	
	@Override
	public final void paint(Graphics pG) {
//		System.out.println("paint");
		pG.setColor(Color.black);
		pG.fillRect(0, 0, mWidth, mHeight);
		
		if(mScaleImage != null) {
			if(mRescale) {
			System.out.println("rescaling...");
				scaleImage();
				mRescale = false;
			}
			int width = mScaleImage.getWidth();
			int height = mScaleImage.getHeight();
			
//			System.out.println("widht " + width + " height " + height);
			
			pG.drawImage(mScaleImage, mOriginX, mOriginY, width, height, this);
		}
		
	}
	
	private void scale(final double adjustment) {
//		System.out.println("scale");
		final double oldScale = mScale;
		mScale += adjustment;
		if (mScale != 1) {
			final int new_width = (int) (mSourceFile.getWidth() * mScale);
			final int old_width = (int) (mSourceFile.getWidth() * oldScale);

			final int new_height = (int) (mSourceFile.getHeight() * mScale);
			final int old_height = (int) (mSourceFile.getHeight() * oldScale);

			mOriginX -= (new_width/2) - (old_width/2);
			mOriginY -= (new_height/2) - (old_height/2);

			mScaleImage = null;
			mScaleImage = new BufferedImage(new_width, new_height, BufferedImage.TYPE_INT_RGB);
			final Graphics2D g2d = mScaleImage.createGraphics();
			g2d.setRenderingHint( RenderingHints.KEY_RENDERING, true ?RenderingHints.VALUE_RENDER_QUALITY:RenderingHints.VALUE_RENDER_SPEED);
			g2d.drawImage( mSourceFile, AffineTransform.getScaleInstance(mScale, mScale), null );
			g2d.dispose();
		} else {
			mScaleImage = mSourceFile;
		}
		repaint();
	}
	
	private void setDimesions() {
//		System.out.println("dimension");
		Insets insets = getInsets();
		Dimension dimScreen = Toolkit.getDefaultToolkit().getScreenSize();
		
		mWidth = dimScreen.width;
		mHeight = dimScreen.height;
		
		mWidth = mMother.getWidth();
		mHeight = mMother.getHeight();
	}
	
	private void scaleImage() {
//		System.out.println("scale image");
		mScale = 1.0f;
		
		setDimesions();
		
		Dimension scaleDim = new Dimension(mWidth, mHeight);
		
		mScale = scaleFactor(scaleDim, mSourceFile.getWidth(), mSourceFile.getHeight());
		
		scale(0);
		
		int w = mScaleImage.getWidth();
		int h = mScaleImage.getHeight();
		
		setBounds(0, 0, w, h);
		mOriginX = 0;
		mOriginY = 0;
	}

   public final static float scaleFactor(final Dimension d1, final int w, final int h) {
      return Math.min((float) d1.width / w, (float) d1.height / h );
   }

   public final static float scaleFactor(final Dimension d1, final Insets i, final int w, final int h) {
      return Math.min((float) (d1.width - (i.left + i.right)) / w,
            (float) (d1.height - (i.top + i.bottom)) / h );
   }
   
   public void setImage(FileElement pFileElement) {
//   	System.out.println("setimage1");
   	try {
			ImageReader reader = getImageReader(pFileElement);
			
			setImage(reader.read(0));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
   	
   }
   
   private void setImage(BufferedImage pImg) {
//   	System.out.println("setimage2");
   	
   	mSourceFile = null;
   	mScaleImage = null;
   	
   	mSourceFile = pImg;
   	
   	scaleImage();
   	
   	repaint();
   }
   
   public static ImageReader getImageReader (final FileElement pFileElement)
   throws FileNotFoundException, IOException {
//   	System.out.println("getreadre");

     ImageInputStream imageInputStream = null;

     try {
        imageInputStream = ImageIO.createImageInputStream(new File(pFileElement.getAbsolutePath()));
        for (final Iterator<ImageReader> iterator = ImageIO.getImageReadersBySuffix(pFileElement.getType()); iterator.hasNext();) {
      	  System.out.println("for");
           final ImageReader reader = iterator.next();
           final String pluginClassName = reader.getOriginatingProvider().getPluginClassName();

           if ((reader != null) 
                 && reader.getOriginatingProvider().canDecodeInput(imageInputStream)) {
              reader.setInput(imageInputStream);
              return reader;
           }
        }
     } catch (final IOException e) {
        e.printStackTrace();
     }

     try {
        if (imageInputStream != null) {
           imageInputStream.close();
        }
     } catch (final IOException e) {
        e.printStackTrace();
     }
     return null;
   }

	@Override
	public void update(Graphics pG) {
//		System.out.println("update");
	}

	/**
	 * @return the rescale
	 */
	public boolean isRescale() {
		return mRescale;
	}

	/**
	 * @param pRescale the rescale to set
	 */
	public void setRescale(boolean pRescale) {
		mRescale = pRescale;
	}

}


/*
 * $Log$
 */