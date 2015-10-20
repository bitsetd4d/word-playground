package d3bug.poc.actions;

import java.awt.Color;

import d3bug.poc.Memento;
import d3bug.poc.SpriteButton;
import d3bug.poc.SpriteButtonUniverse;
import d3bug.poc.sounds.SoundFx;
import d3bug.poc.threads.UserAction;
import d3bug.poc.words.WordUtil;

public class MergeButtonsTogetherAction extends UserAction {

	private boolean touchedTargetOnLeft;
	private SpriteButton sourceButton;
	private SpriteButton targetButton;	
	private SpriteButtonUniverse universe;
	
	private Memento sourceMemento;
	private Memento targetMemento;
	
	public MergeButtonsTogetherAction(SpriteButtonUniverse universe,SpriteButton sourceButton,SpriteButton targetButton,boolean touchedTargetOnLeft) {
		this.universe = universe;
		this.sourceButton = sourceButton;
		this.targetButton = targetButton;
		this.touchedTargetOnLeft = touchedTargetOnLeft;
	}

	@Override
	public void run() {
		sourceMemento = sourceButton.getMemento();
		targetMemento = targetButton.getMemento();
		String word = null;
		if (touchedTargetOnLeft) {
			word = sourceButton.getText() + targetButton.getText();
			if (WordUtil.isSyllable(word)) {
				sourceButton.setForeground(Color.BLUE);
			}
			targetButton.mergeOnRightAndBecome(sourceButton);
		} else {
			word = targetButton.getText() + sourceButton.getText();
			if (WordUtil.isSyllable(word)) {
				targetButton.setForeground(Color.BLUE);
			}
			targetButton.mergeOnLeftAndBecome(sourceButton);
		}
		SoundFx.getInstance().soundMerge();
		universe.notifyWordCreated(word);
	}

	@Override
	public void undo() {
		sourceButton.setFromMemento(sourceMemento);
		targetButton.setFromMemento(targetMemento);
	}

}
