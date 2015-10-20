package d3bug.poc.actions;

import d3bug.poc.SpriteButton;
import d3bug.poc.SpriteButtonUniverse;
import d3bug.poc.sounds.SoundFx;
import d3bug.poc.threads.UserAction;

public class CreateNewLetterAction extends UserAction {

	private SpriteButtonUniverse universe;
	private String text;
	private SpriteButton b;

	public CreateNewLetterAction(SpriteButtonUniverse universe,String text) {
		this.universe = universe;
		this.text = text;
	}

	@Override
	public void run() {
		SoundFx.getInstance().soundNewLetterButtonCreated();
		b = universe.createButton(text);
		b.setLocation(0,0);
		universe.flyToFocusPosition(b);
	}

	@Override
	public void undo() {
		b.setLocation(0,0);
		b.destroySprite();
		b = null;		
	}

}
