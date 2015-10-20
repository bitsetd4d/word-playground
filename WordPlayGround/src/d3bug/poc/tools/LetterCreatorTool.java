package d3bug.poc.tools;

import java.awt.Rectangle;

import d3bug.poc.SpriteButton;
import d3bug.poc.SpriteButtonUniverse;
import d3bug.poc.actions.CreateNewLetterAction;
import d3bug.poc.sounds.SoundFx;
import d3bug.poc.threads.UserActionQueue;

public class LetterCreatorTool extends ToolButton {
	
	private static final int MAX = 100;
	private static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ01234567890+=-";

	public LetterCreatorTool(SpriteButtonUniverse universe) {
		super(universe);
		setText(">ABC Creator>");
		getComponent().setBounds(new Rectangle(200,60));
		setVisible(false);
	}
	
	@Override
	public void onKeyPressed(String x) {
		if (LETTERS.indexOf(x) >= 0 && universe.getAllSpriteButtons().size() < MAX) {
			UserActionQueue.getInstance().run(new CreateNewLetterAction(universe,x));
			log("Created letter: "+x);
		}
	}

}
