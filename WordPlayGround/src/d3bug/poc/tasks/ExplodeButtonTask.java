package d3bug.poc.tasks;

import java.util.List;
import java.util.Random;

import d3bug.poc.SpriteButton;
import d3bug.poc.threads.UniverseRunnable;

public class ExplodeButtonTask extends UniverseRunnable {

	private SpriteButton button;

	private List<SpriteButton> newButtons;

	private static Random random = new Random();

	public ExplodeButtonTask(SpriteButton button, List<SpriteButton> newButtons) {
		this.button = button;
		this.newButtons = newButtons;
	}
	
	@Override
	public String getName() { return "Expand Button"; }
	
	@Override
	public void run() {
		final int l = newButtons.size();
		final int basex[] = new int[l];
		final int basey[] = new int[l];
		final double dx[] = new double[l];
		final double dy[] = new double[l];
		int i = 0;

		for (SpriteButton b : newButtons) {
			basex[i] = button.getX();
			basey[i] = button.getY();
			double xspeed = i * 2 + random.nextDouble() * 2;
			double yspeed = 2 + random.nextDouble() * 3;
			if (random.nextBoolean())
				xspeed = -xspeed;
			if (random.nextBoolean())
				yspeed = -yspeed;
			dx[i] = xspeed;
			dy[i] = yspeed;
			i++;
			b.touched();
			b.setInMotion(true);
		}
		button.destroySprite();
		for (i = 0; i < 20; i++) {
			sleep(6);
			int k = 0;
			for (SpriteButton b : newButtons) {
				int x = (int) (basex[k] + i * dx[k]);
				int y = (int) (basey[k] + i * dy[k]);
				// Bounds checking
				b.setLocation(x, y);
				k++;
			}

		}
		for (SpriteButton b : newButtons) {
			b.setInMotion(false);
		}
	}
	
}
