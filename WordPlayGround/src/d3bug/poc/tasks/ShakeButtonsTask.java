package d3bug.poc.tasks;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import d3bug.poc.SpriteButton;
import d3bug.poc.SpriteButtonButton;
import d3bug.poc.threads.UniverseRunnable;

public class ShakeButtonsTask extends UniverseRunnable {

	private List<SpriteButton> buttons;
	private Random random = new Random();
	
	public ShakeButtonsTask(List<SpriteButton> buttons) {
		this.buttons = buttons;
	}
	
	public ShakeButtonsTask(SpriteButton button) {
		buttons = Collections.singletonList(button);
	}

	@Override
	public String getName() { return "Shake"; }
	
	@Override
	public void run() {
		shakeButtonsNow(5,5,50,10);		
	}
	
	public void quickRun() {
		shakeButtonsNow(5,5,10,10);
	}
	
	public void shakeButtonsNow(int xshake, int yshake, int iterations, int pause) {
		int[] x = new int[buttons.size()];
		int[] y = new int[buttons.size()];
		int i=0;
		for (SpriteButton b : buttons) {
			x[i] = b.getX();
			y[i] = b.getY();
			i++;
		}
		for (i=0; i<iterations; i++) {
			int j = 0;
			for (SpriteButton b : buttons) {
				if (!b.inMotion()) { 
					int dx = random.nextInt(xshake);
					int dy = random.nextInt(yshake);
					if (random.nextBoolean()) dx = -dx;
					if (random.nextBoolean()) dy = -dy;
					b.setLocation(x[j] + dx, y[j] + dy);
				}
				if (universeBusy) return;
				j++;
			}
			sleep(pause);
		}		
	}
}
