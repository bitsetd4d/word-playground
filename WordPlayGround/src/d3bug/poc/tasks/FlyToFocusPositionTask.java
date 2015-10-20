package d3bug.poc.tasks;

import java.awt.Component;
import java.awt.Rectangle;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import d3bug.poc.SpriteButton;
import d3bug.poc.threads.UniverseRunnable;

public class FlyToFocusPositionTask extends UniverseRunnable {

	private Component parent;
	private SpriteButton target;
	private List<SpriteButton> allButtons;
	
	private int basex;
	private int basey;
	private double dx;
	private double dy;
	
	private static Map<SpriteButton,Rectangle> inFlightTargets = new ConcurrentHashMap<SpriteButton,Rectangle>();
	
	public FlyToFocusPositionTask(SpriteButton target,List<SpriteButton> allButtons,Component parent) {
		this.target = target;
		this.allButtons = allButtons;
		this.parent = parent;
	}
	
	@Override
	public String getName() { return "Fly button to focus pos"; }

	@Override
	public void run() {
		setup();
		for (int i=0;i<50; i++) {  // Change to <= to make line up perfectly
			try {
				Thread.sleep(3);
			} catch (InterruptedException e) {}
			target.setLocation((int)(basex - i * dx), (int)(basey - i * dy));
		}
		target.setInMotion(false);
		inFlightTargets.remove(target);
	}

	@Override
	public void quickRun() {
		target.setLocation((int)(basex - 49 * dx), (int)(basey - 49 * dy));
		target.setInMotion(false);
		inFlightTargets.remove(target);
	}

	private void setup() {
		basex = target.getX();
		basey = target.getY();
		int focusx = 20;
		int focusy = parent.getHeight() - 300;
		
		LOOP:
		for (int i=20; i<1000; i+=20) {
			Rectangle r = new Rectangle(i,focusy,target.getWidth(),target.getHeight());
			for (SpriteButton z : allButtons) {
				Rectangle zr = z.getBounds();
				if (zr.intersects(r)) {
					continue LOOP;
				}
			}
			for (Rectangle rt : inFlightTargets.values()) {
				if (rt.intersects(r)) continue LOOP;
			}
			focusx = i;
			break;
		}
		dx = (basex - focusx) / 50.0;
		dy = (basey - focusy) / 50.0;
		Rectangle rt = new Rectangle(focusx,focusy,target.getWidth(),target.getHeight());
		target.setInMotion(true);
		inFlightTargets.put(target, rt);
	}


}
