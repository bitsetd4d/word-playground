package d3bug.poc.tasks;

import d3bug.poc.SpriteButton;
import d3bug.poc.SpriteButtonUniverse;
import d3bug.poc.threads.UniverseRunnable;

public class MergeOnLeftAndBecomeTask extends UniverseRunnable {

	private SpriteButtonUniverse universe;
	private SpriteButton source;
	private SpriteButton target;
	
	public MergeOnLeftAndBecomeTask(SpriteButtonUniverse universe,SpriteButton source,SpriteButton target) {
		this.universe = universe;
		this.source = source;
		this.target = target;
	}
	
	@Override
	public String getName() {
		return "Merge On Left and Become";
	}

	@Override
	public void run() {
		String x = source.getText() + target.getText(); 		
		target.setText(x);
		int otherRightEdge = target.getX() + target.getWidth();
		int w = 50 + (( x.length() - 1) * 15);
		int h = target.getHeight();
		target.setSize(w,h);
		int by =target.getY();
		universe.destroyButton(source);
		source.setVisible(false);
		target.setLocation(otherRightEdge - target.getWidth(),by);
	}

}
