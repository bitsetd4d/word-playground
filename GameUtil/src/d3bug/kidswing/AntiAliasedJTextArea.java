package d3bug.kidswing;

import javax.swing.JTextArea;
import javax.swing.text.Document;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class AntiAliasedJTextArea extends JTextArea {

	private boolean antialias = true;

	public AntiAliasedJTextArea() {
		super();
	}

	public AntiAliasedJTextArea(String message)	{
		super(message);
	}

	public AntiAliasedJTextArea(int rows, int columns)	{
		super(rows, columns);
	}

	public AntiAliasedJTextArea(String text, int rows, int columns)	{
		super(text, rows, columns);
	}


	public AntiAliasedJTextArea(Document doc, String text, int rows, int columns) {
		super(doc, text, rows, columns);
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


	public void setAntialias(boolean antialias)	{
		this.antialias = antialias;
		this.repaint();
	}

}
