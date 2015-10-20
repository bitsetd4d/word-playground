package d3bug.poc.tasks;

import java.util.List;

import d3bug.poc.SpriteButton;
import d3bug.poc.SpriteButtonButton;
import d3bug.poc.threads.UniverseRunnable;

public class MoveButtonsTowardsButtonTask extends UniverseRunnable {
	
	private SpriteButton target;
	private List<SpriteButton> followers;
	private int speed;
	private double speedDecay;
	private int interval;
	private int maxrun;
	private boolean done;
	
	private static final int MAX_ALLOWED_RUN = 60 * 1000;

	public MoveButtonsTowardsButtonTask(SpriteButton target, List<SpriteButton> followers,int speed,int slowSpeed,int interval) {
		this.target = target;
		this.followers = followers;
		this.speed = speed;
		this.speedDecay = (double)(speed - slowSpeed) / followers.size();
		this.interval = interval;
		maxrun = MAX_ALLOWED_RUN / interval;
	}

	public String getName() {
		return "Move Towards Destination";
	}
	
	public void done() {
		done = true;
	}

	public void run() {
		while (!done && !universeBusy && maxrun-- > 0) {
			int targetx = target.getX() - 15;
			int targety = target.getY();
			int i = 0;
			for (SpriteButton b : followers) {
				targetx -= (b.getWidth() + 3);
				double deltax = targetx - b.getX();
				double deltay = targety - b.getY();
				int currentMax = (int)(speed - (i * speedDecay));
				if (deltax > currentMax) deltax = currentMax;
				if (deltax < -currentMax) deltax = -currentMax;
				if (deltay > currentMax) deltay = currentMax;
				if (deltay < -currentMax) deltay = -currentMax;
				int newx = (int)(b.getX() + deltax);
				int newy = (int)(b.getY() + deltay);
				b.setLocation(newx,newy);				
				i++;
			}
			sleep(interval);
		}
	}
	
}
