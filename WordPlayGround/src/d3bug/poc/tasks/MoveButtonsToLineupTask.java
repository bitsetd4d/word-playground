package d3bug.poc.tasks;

import java.awt.Component;
import java.util.List;

import d3bug.poc.SpriteButton;
import d3bug.poc.threads.UniverseRunnable;

public class MoveButtonsToLineupTask extends UniverseRunnable {

	private List<SpriteButton> allButtons;
	private List<SpriteButton> toolButtons;
	private Component parent;

	private int[] basex;
	private int[] basey;
	private double[] dx;
	private double[] dy;
	
	public MoveButtonsToLineupTask(List<SpriteButton> allButtons,List<SpriteButton> toolButtons,Component parent) {
		this.allButtons = allButtons;
		this.toolButtons = toolButtons;
		this.parent = parent;
		int l = allButtons.size();
		basex = new int[l];
		basey = new int[l];
		dx = new double[l];
		dy = new double[l];
	}


	@Override
	public String getName() { return "Move Buttons to Lineup"; }

	@Override
	public void run() {
		setup();
		for (int i=0;i<=50; i++) {  // Change to <= to make line up perfect
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {}
			int k = 0;
			for (SpriteButton b : allButtons) {
				b.setLocation((int)(basex[k] - i * dx[k]), (int)(basey[k] - i * dy[k]));
				k++;
			}
		}
		for (SpriteButton b : allButtons) {
			b.setInMotion(false);
		}
	}
	
	@Override
	public void quickRun() {
		setup();
		int k = 0;
		for (SpriteButton b : allButtons) {
			b.setLocation((int)(basex[k] - 49 * dx[k]), (int)(basey[k] - 49 * dy[k]));
			k++;
		}				
	}

	private void setup() {
		int targetx = 50;		
		int numberButtons = allButtons.size();
		int buttonsPerRow = parent.getWidth() / 100;
		int targety = parent.getHeight() - (1 + numberButtons / buttonsPerRow) * 90;
		if (targety < 60) targety = 60;
		int toolTargetX = 120;
		int toolTargetY = 1;
		int i = 0;
		for (SpriteButton b : allButtons) {
			basex[i] = b.getX();
			basey[i] = b.getY();
			int xx = targetx;
			int yy = targety;
			if (toolButtons.contains(b)) {
				xx = toolTargetX;
				yy = toolTargetY;
				toolTargetX += b.getWidth() + 10;
			} else {
				targetx += b.getWidth() + 10;
				if (targetx > parent.getWidth() - 60) {
					targetx = 50;
					targety += 90;
				}
			}
			dx[i] = (b.getX() - xx) / 50.0;
			dy[i] = (b.getY() - yy) / 50.0;
			i++;
			b.setInMotion(true);
		}
	}
	
}
