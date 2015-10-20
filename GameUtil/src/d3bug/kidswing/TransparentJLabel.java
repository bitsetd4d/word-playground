package d3bug.kidswing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;

import javax.swing.Icon;
import javax.swing.JLabel;

public class TransparentJLabel extends JLabel {

	public TransparentJLabel() {
		super();
	}

	public TransparentJLabel(Icon image, int horizontalAlignment) {
		super(image, horizontalAlignment);
	}

	public TransparentJLabel(Icon image) {
		super(image);
	}

	public TransparentJLabel(String text, Icon icon, int horizontalAlignment) {
		super(text, icon, horizontalAlignment);
	}

	public TransparentJLabel(String text, int horizontalAlignment) {
		super(text, horizontalAlignment);
	}

	public TransparentJLabel(String text) {
		super(text);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setXORMode(new Color(255,255,255,255));
		super.paintComponent(g2d);
	}

}
