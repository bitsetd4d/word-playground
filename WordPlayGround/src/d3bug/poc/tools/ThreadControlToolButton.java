package d3bug.poc.tools;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import d3bug.kidswing.FontManager;
import d3bug.poc.SpriteButtonUniverse;
import d3bug.poc.threads.UniverseRunner;
import d3bug.poc.threads.UniverseRunningListener;

public class ThreadControlToolButton extends ToolButton implements UniverseRunningListener {
	
	private static final Font FONT = FontManager.getInstance().getComicFont(10);
	
	private JLayeredPane layeredPane;
	private JLabel titleLabel;
	private static final int CONSOLE_SIZE = 30;
	private static final int CONSOLE_STEP = 12;
	private JLabel[] console = new JLabel[CONSOLE_SIZE];
	private boolean hud;
	
	public ThreadControlToolButton(SpriteButtonUniverse universe,JLayeredPane layeredPane) {
		super(universe);
		setFont(FONT);
		setText("?>");
		setSize(100,50);
		this.layeredPane = layeredPane;
		UniverseRunner.getRunner().addListener(this);
	}
	
	public void onToolDoubleClick() {
		if (!hud) {
			createHud();
		} else {
			destroyHud();
		}
		hud = !hud;
	}
	
	private void createHud() {
		titleLabel = new JLabel("Jobs");
		titleLabel.setForeground(Color.RED);
		titleLabel.setSize(250,20);
		titleLabel.setLocation(getX() + getWidth() + 10,getY());
		titleLabel.setFont(FONT);
		layeredPane.add(titleLabel,JLayeredPane.DRAG_LAYER);
		
		for (int i=0; i<CONSOLE_SIZE; i++) {
			JLabel l = new JLabel();
			l.setSize(400,20);
			l.setLocation(getX() + getWidth() + 10,getY() + CONSOLE_STEP * (i+1));
			l.setForeground(Color.GREEN);
			l.setFont(FONT);
			layeredPane.add(l,JLayeredPane.DRAG_LAYER);
			console[i] = l;
		}
	}
	
	private void destroyHud() {
		layeredPane.remove(titleLabel);
		for (int i=0; i<CONSOLE_SIZE; i++) {
			if (console[i] != null) {
				layeredPane.remove(console[i]);
			}
		}
		layeredPane.repaint();
		titleLabel = null;
	}
	
	// *******************************************
	// UniversRunningListener callbascks
	// *******************************************
	public void onExecute(String job) {
		console("--> "+job);
	}

	public void onExecuted(String job, long timetaken) {
		console("<-- "+job+" ("+timetaken+")");
	}

	public void onQueueLength(int size) {
		//JLabel l = queueLabel;
		String bar = "##########";
		int bl = size;
		if (bl > bar.length()) {
			bl = bar.length();
		}
		//if (l != null) {
			setText(""+size+" "+(bar.substring(0,bl)));
		//}
	}

	public void onScheduled(String job, int position) {
		console("Job "+job+" scheduled ("+position+")");
	}

	private void console(String msg) {
		if (hud) {
			for (int i=0; i<CONSOLE_SIZE-1; i++) {
				console[i].setText(console[i+1].getText());
			}
			console[CONSOLE_SIZE-1].setText(msg);
		}
	}




	

}
