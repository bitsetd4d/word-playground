package d3bug.poc.tasks;

import java.util.ArrayList;
import java.util.List;

import d3bug.poc.SpriteButton;
import d3bug.poc.sounds.SoundFx;
import d3bug.poc.threads.UniverseRunnable;
import d3bug.poc.tools.ToolButton;

public class ToolAttacksButtonsTask extends UniverseRunnable {

	private ToolButton toolButton;
	private List<SpriteButton> buttons;
	private boolean first = true;
	
	public ToolAttacksButtonsTask(ToolButton toolButton,SpriteButton... buttons) {
		this.toolButton = toolButton;
		this.buttons = new ArrayList<SpriteButton>();
		for (SpriteButton b : buttons) {
			this.buttons.add(b);
		}
	}
	
	public ToolAttacksButtonsTask(ToolButton toolButton,List<SpriteButton> buttons) {
		this.toolButton = toolButton;
		this.buttons = new ArrayList<SpriteButton>(buttons);
	}
	
	@Override
	public String getName() { return "Tool Attack"; }

	@Override
	public void run() {
		UniverseRunnable r = new ShakeButtonsTask(buttons);
		r.run();
		while (!buttons.isEmpty()) {
			SpriteButton b = findClosestButton();
			attackButton(b);
			buttons.remove(b);
			sleepSome();
			first = false;
		}
	}
	
	private void sleepSome() {
		int sleep = 100;
		if (buttons.size() > 20) sleep = 50;
		sleep(sleep);
		
	}

	private void attackButton(SpriteButton b) {
		int x = b.getX();
		int y = b.getY();
		int steps = first ? 200 : 50;
		if (buttons.size() > 20) steps = 10;
		UniverseRunnable r = new FlyButtonToLocationTask(toolButton,x,y,30,1);
		r.run();
		SoundFx.getInstance().soundEatButton();
		b.destroySprite();
	}

	private SpriteButton findClosestButton() {
		SpriteButton best = buttons.get(0);
		int bestDistance = computeDistance(best);
		for (SpriteButton b : buttons) {
			int currentDistance = computeDistance(b);
			if (currentDistance < bestDistance) {
				bestDistance = currentDistance;
				best = b;
			}
		}
		return best;
	}

	private int computeDistance(SpriteButton button) {
		int midx1 = toolButton.getX() + toolButton.getWidth() / 2;
		int midy1 = toolButton.getY() + toolButton.getHeight() / 2;

		int midx2 = button.getX() + button.getWidth() / 2;
		int midy2 = button.getY() + button.getHeight() / 2;
		
		int dx = Math.abs(midx1 - midx2);
		int dy = Math.abs(midy1 - midy2);
		int distance = (int)Math.sqrt(dx*dx + dy*dy);
		return distance;
	}

}
