package d3bug.poc.actions;

import java.util.ArrayList;
import java.util.List;

import d3bug.poc.Memento;
import d3bug.poc.SpriteButton;
import d3bug.poc.SpriteButtonUniverse;
import d3bug.poc.sounds.SoundFx;
import d3bug.poc.threads.UserAction;
import d3bug.poc.words.WordUtil;

public class ExplodeButtonAction extends UserAction {

	private SpriteButtonUniverse universe;
	private SpriteButton button;
	private Memento buttonMemento;
	
	private List<SpriteButton> newButtons;
	
	public ExplodeButtonAction(SpriteButtonUniverse universe,SpriteButton button) {
		this.universe = universe;
		this.button = button;
	}

	@Override
	public void run() {
		buttonMemento = button.getMemento();
		String x = button.getText();
		if (x.length() > 1) {
			newButtons = new ArrayList<SpriteButton>();
			String[] elements = WordUtil.splitIntoElements(x);
			for (int i=0; i<elements.length; i++) {
				String c = elements[i];
				SpriteButton newButton = universe.createButton(c);
				newButtons.add(newButton);
			}
			button.explode(newButtons);
			SoundFx.getInstance().soundNewLetterButtonCreated();
		}
	}

	@Override
	public void undo() {
		button.setFromMemento(buttonMemento);
		for (SpriteButton b : newButtons) {
			b.destroySprite();
		}
	}

}
