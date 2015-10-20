package d3bug.poc.tasks;

import d3bug.poc.SpriteButtonButton;
import d3bug.poc.threads.UniverseRunnable;

public class FlyButtonToLocationTask extends UniverseRunnable {

	private SpriteButtonButton button;
	private int x;
	private int y;
	private int steps;
	private int sleep;
	
	private int basex;
	private int basey;
	
	public FlyButtonToLocationTask(SpriteButtonButton button, int x, int y, int steps, int sleep) {
		this.button = button;
		this.x = x;
		this.y = y;
		this.steps = steps;
		this.sleep = sleep;
	}

	@Override
	public String getName() { return "Fly Button"; }
	
	@Override
	public void run() {
		System.out.println("Flying button to "+x+","+y);
		basex = button.getX();
		basey = button.getY();
		final double dx = (x - basex) / (double)steps; 
		final double dy = (y - basey) / (double)steps;
		button.setInMotion(true);
		for (int i = 0; i <= steps; i++) {
			sleep(sleep);
			int newx = (int)(basex + i * dx);
			int newy = (int) (basey + i * dy);
			button.setLocation(newx,newy);
		}
		button.setInMotion(false);
	}

}
