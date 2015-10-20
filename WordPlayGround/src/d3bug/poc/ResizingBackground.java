package d3bug.poc;

import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class ResizingBackground {

	private JLabel imageLabel;
	private ImageIcon backgroundIcon;
	private ImageIcon oldIcon;
	private JLayeredPane layeredPane;
	private double imageRatio;

	public ResizingBackground(ImageIcon background,JLayeredPane layeredPane) {
		this.backgroundIcon = background;
		this.layeredPane = layeredPane;
		setupBackground();
	}

	private void setupBackground() {
		imageLabel = new JLabel();
		layeredPane.add(imageLabel,new Integer(-1000));
		layeredPane.moveToBack(imageLabel);
		layeredPane.addComponentListener(new ComponentListener() {
			public void componentHidden(ComponentEvent ev) {}
			public void componentMoved(ComponentEvent ev) {}
			public void componentResized(ComponentEvent ev) { fitImageToWindow(); }
			public void componentShown(ComponentEvent ev) {	fitImageToWindow(); }
	    });
		int w = backgroundIcon.getIconWidth();
	    int h = backgroundIcon.getIconHeight();
	    imageRatio = (double)w/(double)h;
	}

	private void fitImageToWindow() {
		Image im = backgroundIcon.getImage();
		int width = layeredPane.getWidth();
		int height = layeredPane.getHeight();
		if (width == 0 || height == 0) {
			return; 
		}
		imageLabel.setSize(width, height);
		Image newImage = newImagePreserveRatio(im,width,height);
		Icon oldIcon = imageLabel.getIcon();
		ImageIcon icon = new ImageIcon(newImage);
		imageLabel.setIcon(icon);
		if (oldIcon != null && oldIcon instanceof ImageIcon) {
			((ImageIcon)oldIcon).getImage().flush();
		}
		imageLabel.setSize(width, height);
	}

	private Image newImagePreserveRatio(Image im, int width, int height) {
		int w = width;
		int h = height;
		double r = imageRatio;
		int widthForHeight = (int)(height * r);
		int heightForWidth = (int)(width / r);
		/* Ensure image will fill area but preserve ratio */
		if (widthForHeight < width) {
			w = width;
			h = heightForWidth;
		} else if (heightForWidth < height) {
			w = widthForHeight;
			h = height;			
		}
		Image newImage = im.getScaledInstance(w, h, Image.SCALE_FAST);
		newImage.setAccelerationPriority(1);
		return newImage;
	}
	
}
