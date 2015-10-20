package d3bug.poc.tasks;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import d3bug.graphics.ImageAccess;
import d3bug.kidswing.FontManager;
import d3bug.poc.threads.UniverseRunnable;

public class ShowLogo extends UniverseRunnable {
	
	private JLayeredPane layeredPane;
	//private ImageIcon im;
	private static final String[] TEXT = {
		"Word Playground",
		"(C) 2007 Paul McGuire",
		"All rights reserved",
		"USE SUBJECT TO A LICENSE AGREEMENT"
	};
	
	private JLabel[] text = new JLabel[TEXT.length];
	private static final Font FONT = FontManager.getInstance().getComicFont(11);

	public ShowLogo(JLayeredPane layeredPane) {
		this.layeredPane = layeredPane;
	}

	@Override
	public String getName() {
		return "Logo Display";
	}

	@Override
	public void run() {
		ImageIcon iconImage = ImageAccess.getInstance().getIcon("W_200pxl_trans.gif");
		JLabel jl = new JLabel(iconImage);
		int lx = layeredPane.getWidth() - 420;
		int ly = layeredPane.getHeight() - (230 + TEXT.length * 15);
		jl.setLocation(lx,ly);
		jl.setSize(400,129);
		layeredPane.add(jl,JLayeredPane.DRAG_LAYER);
		int x = jl.getX();
		int y = jl.getY() + jl.getHeight();
		for (int i=0; i< TEXT.length; i++) {
			text[i] = new JLabel(TEXT[i]);
			text[i].setFont(FONT);
			layeredPane.add(text[i],JLayeredPane.DRAG_LAYER);
			text[i].setLocation(x, y);
			text[i].setSize(400,15);
			y += text[i].getHeight();
		}
		sleep(8000);
		for (JLabel l : text) {
			l.setText("");
		}
		jl.setIcon(null);
		sleep(4000);
		layeredPane.remove(jl);
		for (JLabel l : text) {
			layeredPane.remove(l);
		}
		
	}
}
