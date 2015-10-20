package d3bug.poc.tasks;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TimerTask;

import d3bug.poc.SpriteButton;
import d3bug.poc.SpriteButtonButton;
import d3bug.poc.threads.UniverseRunnable;
import d3bug.poc.threads.UniverseRunner;

public class HighlightSpritesTask extends UniverseRunnable {
	
	private List<SpriteButton> buttons = new ArrayList<SpriteButton>();
	private int duration;
	private Color colour;

	public HighlightSpritesTask(Collection<SpriteButton> buttonSet, int duration,Color colour) {
		for (SpriteButton b : buttonSet) {
			buttons.add(b);
		}
		this.duration = duration;
		this.colour = colour;
	}

	@Override
	public String getName() { return "Highlight Sprites"; }
	
	@Override
	public void run() {
		for (SpriteButton b : buttons) {
			b.setForeground(colour);
		}
		TimerTask t = new TimerTask() {
			@Override
			public void run() {
				for (SpriteButton b : buttons) {
					b.setForeground(null);
				}
			}
		};
		UniverseRunner.getRunner().sheduleOnetime(t, duration);
	}

}
