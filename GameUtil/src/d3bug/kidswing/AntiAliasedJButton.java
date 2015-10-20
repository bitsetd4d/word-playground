package d3bug.kidswing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

public class AntiAliasedJButton extends JButton {

	
	// broken
	
	private boolean antialias = true;
	
	public AntiAliasedJButton() {
		super();
	}

	public AntiAliasedJButton(Action a) {
		super(a);
	}

	public AntiAliasedJButton(Icon icon) {
		super(icon);
	}

	public AntiAliasedJButton(String text, Icon icon) {
		super(text, icon);
	}

	public AntiAliasedJButton(String text) {
		super(text);
	}
	
	public boolean isAntialias() {
		return antialias;
	}

	public void setAntialias(boolean antialias) {
		this.antialias = antialias;
	}

	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if (antialias) {
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
			RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
			RenderingHints.VALUE_RENDER_QUALITY);
		}
		super.paintComponent(g2d);
	}

}
