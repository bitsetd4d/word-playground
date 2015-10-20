package d3bug.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;

import javax.swing.ImageIcon;

public class Imaging {
	
	public static ImageIcon newImagePreserveRatio(ImageIcon imIcon, int maxWidth, int maxHeight,boolean fillArea) {	
		Dimension d = sizeToFitArea(imIcon.getIconWidth(), imIcon.getIconHeight(), maxWidth, maxHeight, fillArea);
		Image newImage = imIcon.getImage().getScaledInstance(d.width, d.height, Image.SCALE_FAST);
		return new ImageIcon(newImage);
	}
	
	public static BufferedImage newBufferredImagePreserveRatio(BufferedImage image,int maxWidth,int maxHeight,boolean fillArea) {
		Dimension d = sizeToFitArea(image.getWidth(), image.getHeight(), maxWidth, maxHeight, fillArea);
		return resize(image,d.width,d.height);
	}
	
	public static Dimension sizeToFitArea(int imageWidth,int imageHeight,int areaWidth,int areaHeight,boolean fillArea) {
		int w = imageWidth;
		int h = imageHeight;
		double r = (double)imageWidth/(double)imageHeight;
		int widthForHeight = (int)(areaHeight * r);
		int heightForWidth = (int)(areaWidth / r);
		/* Ensure image will fill area but preserve ratio */
		if (fillArea) {
			if (widthForHeight < areaWidth) {
				w = areaWidth;
				h = heightForWidth;
			} else /* if (heightForWidth < areaHeight) */ {
				w = widthForHeight;
				h = areaHeight;			
			}
		} else {
			if (w > areaWidth) {
				double scale = (double)areaWidth / (double)w;
				w = (int)(w * scale);
				h = (int)(h * scale);
			}
			if (h > areaHeight) {
				double scale = (double)areaHeight / (double)h;
				w = (int)(w * scale);				
				h = (int)(h * scale);				
			}
		}
		return new Dimension(w,h);
	}

	public static BufferedImage resize(BufferedImage image,int w,int h) {
        AffineTransform tx = new AffineTransform();
        int cw = image.getWidth();
        int ch = image.getHeight();
        double sx = (double)w/(double)cw;
        double sy = (double)h/(double)ch;
        tx.scale(sx, sy);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BICUBIC);
        return op.filter(image, null);
    }
 
	public static Image makeColorTransparent(Image im, final Color color) {
	    ImageFilter filter = new RGBImageFilter() {
	      // the color we are looking for... Alpha bits are set to opaque
	      public int markerRGB = color.getRGB() | 0xFF000000;
	      public final int filterRGB(int x, int y, int rgb) {
	        if ((rgb | 0xFF000000) == markerRGB) {
	          // Mark the alpha bits as zero - transparent
	          return 0x00FFFFFF & rgb;
	        }
            // nothing to do
	        return rgb;
	      }}; 

	    ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
	    return Toolkit.getDefaultToolkit().createImage(ip);
	}
	
	public static Image makeImageTransparent(Image im, final int trans) {
	    ImageFilter filter = new RGBImageFilter() {
	      public final int filterRGB(int x, int y, int rgb) {
	    	  int t = rgb >> 24;
	    	  if (t == 0) return rgb;
	    	  int t2 = trans << 24;
	    	  return (0x00FFFFFF & rgb) | t2;
	      }};
	    ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
	    return Toolkit.getDefaultToolkit().createImage(ip);
	}

}
