package d3bug.poc.tasks;

import java.util.List;
import java.util.Random;

import d3bug.poc.SpriteButton;
import d3bug.poc.threads.UniverseRunnable;

/*
 * Exand the button into the newButtons, destorying button
 */
public class ExpandButtonTask extends UniverseRunnable {

	private SpriteButton button;
	private List<SpriteButton> newButtons;

	private static Random random = new Random();

	public ExpandButtonTask(SpriteButton button, List<SpriteButton> newButtons) {
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

		int totalWidth = 0;
		for (SpriteButton b : newButtons) {
			totalWidth += b.getWidth();
			totalWidth += 7;
		}

		int i = 0;
		double xoffset = -totalWidth / 2;
		for (SpriteButton b : newButtons) {
			basex[i] = button.getX() + button.getWidth() / 2;
			basey[i] = button.getY();
			double xspeed = xoffset / 20;
			xoffset += b.getWidth() + 7;
			double yspeed = 0.3 - (random.nextDouble() * 0.6);
			dx[i] = xspeed;
			dy[i] = yspeed;
			b.touched();
			b.setInMotion(true);
			i++;
		}

		button.destroySprite();
		
		for (i = 0; i <= 20; i++) {
			sleep(10);
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
