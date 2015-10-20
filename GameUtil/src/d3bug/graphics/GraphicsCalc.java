package d3bug.graphics;

import java.awt.Dimension;

public class GraphicsCalc {
	
	public Dimension fillAvailableArea(int width,int height,double ratio) {
		int w = width;
		int h = height;
		int widthForHeight = (int)(height * ratio);
		int heightForWidth = (int)(width / ratio);
		/* Ensure image will fill area but preserve ratio */
		if (widthForHeight < width) {
			w = width;
			h = heightForWidth;
		} else if (heightForWidth < height) {
			w = widthForHeight;
			h = height;			
		}
		return new Dimension(w,h);
	}

}
