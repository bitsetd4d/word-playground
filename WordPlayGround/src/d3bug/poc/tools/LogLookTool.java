package d3bug.poc.tools;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import d3bug.kidswing.FontManager;
import d3bug.poc.Edge;
import d3bug.poc.SpriteButtonButton;
import d3bug.poc.SpriteButtonUniverse;
import d3bug.poc.log.LogListener;
import d3bug.poc.log.Logger;
import d3bug.poc.threads.UniverseRunner;
import d3bug.poc.threads.UniverseRunningListener;

public class LogLookTool extends ToolButton implements LogListener {
	
	private static final Font FONT = FontManager.getInstance().getComicFont(10);
	
	private Object focus;
	
	private JLayeredPane layeredPane;
	private static final int CONSOLE_SIZE = 60;
	private static final int CONSOLE_STEP = 12;
	private JLabel[] console = new JLabel[CONSOLE_SIZE];
	private boolean hud;
	
	public LogLookTool(SpriteButtonUniverse universe,JLayeredPane layeredPane) {
		super(universe);
		setFont(FONT);
		setText("??");
		setSize(50,50);
		this.layeredPane = layeredPane;
	}
	
	public void onToolDoubleClick() {
		if (!hud) {
			createHud();
			Logger.addLogListener(this);
			console("HUD Created.");
		} else {
			Logger.removeLogListener(this);
			destroyHud();
		}
		focus = null;
		hud = !hud;
	}
	
	private void createHud() {
		for (int i=0; i<CONSOLE_SIZE; i++) {
			JLabel l = new JLabel();
			l.setSize(400,20);
			l.setLocation(getX() + getWidth() + 10,getY() + CONSOLE_STEP * (i+1));
			l.setForeground(Color.RED);
			l.setFont(FONT);
			layeredPane.add(l,JLayeredPane.DRAG_LAYER);
			console[i] = l;
		}
	}
	
	private void destroyHud() {
		for (int i=0; i<CONSOLE_SIZE; i++) {
			if (console[i] != null) {
				layeredPane.remove(console[i]);
			}
		}
		layeredPane.repaint();
	}
	
	public void onToolPressedAgainst(SpriteButtonButton otherButton, int pressure, Edge targeButtonsEdge) {
		focus = otherButton;
		console("Focus: "+otherButton);
	}
	public void onToolTouchedBy(SpriteButtonButton sourceButton, int pressure, Edge edge) {
		focus = sourceButton;
		console("Focus: "+sourceButton);
	}

	private void console(String msg) {
		for (int i=0; i<CONSOLE_SIZE-1; i++) {
			console[i].setText(console[i+1].getText());
		}
		console[CONSOLE_SIZE-1].setText(msg);
	}

	public void onInfo(Object source, String message) {
		if (focus == null || source == focus) {
			console(message);
		}
	}

}
