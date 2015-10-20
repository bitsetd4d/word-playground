package d3bug.poc.tasks;

import d3bug.poc.SpriteButton;
import d3bug.poc.SpriteButtonUniverse;
import d3bug.poc.threads.UniverseRunnable;

public class MergeOnRightAndBecomeTask extends UniverseRunnable {

	private SpriteButtonUniverse universe;
	private SpriteButton source;
	private SpriteButton target;
	
	public MergeOnRightAndBecomeTask(SpriteButtonUniverse universe,SpriteButton source,SpriteButton target) {
		this.universe = universe;
		this.source = source;
		this.target = target;
	}
	
	@Override
	public String getName() {
		return "Merge On Right and Become";
	}

	@Override
	public void run() {
		String x = target.getText() + source.getText();		
		target.setText(x);
		int w = 50 + (( x.length() - 1) * 15);
		int h = target.getHeight();
		target.setSize(w,h);
		universe.destroyButton(source);
		source.setVisible(false);
	}

}
