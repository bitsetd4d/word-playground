package d3bug.poc.tools;

import java.awt.Rectangle;

import d3bug.poc.Edge;
import d3bug.poc.SpriteButton;
import d3bug.poc.SpriteButtonUniverse;
import d3bug.poc.actions.ExpandButtonAction;
import d3bug.poc.actions.ExplodeButtonAction;
import d3bug.poc.actions.MergeButtonsTogetherAction;
import d3bug.poc.sounds.SoundFx;
import d3bug.poc.threads.UserActionQueue;

public class GameToolButton extends ToolButton {
	
	public GameToolButton(SpriteButtonUniverse universe) {
		super(universe);
		setText(":-) Physics");
		getComponent().setBounds(new Rectangle(150,60));
		setVisible(false);
	}
	
	@Override
	public void onButtonHorizontalShaked(SpriteButton button) {
		log("Shake "+button.getText());
		UserActionQueue.getInstance().run(new ExplodeButtonAction(universe,button));
	}

	@Override
	public void onPressedAgainst(SpriteButton sourceButton, SpriteButton otherButton, int pressure, Edge targetButtonsEdge) {
		log("PRESSED: "+sourceButton.getText()+" pressed against "+targetButtonsEdge+" edge of "+otherButton.getText());
		if (pressure > 7) {
			if (targetButtonsEdge == Edge.LEFT || targetButtonsEdge == Edge.RIGHT) {
				boolean onLeft = targetButtonsEdge == Edge.LEFT;
				UserActionQueue.getInstance().run(new MergeButtonsTogetherAction(universe,sourceButton,otherButton,onLeft));
			} else {
				SoundFx.getInstance().soundMerge();
			}
		}
	}


	//public void onButtonHorizontalShaked(SpriteButton button) {
	@Override
	public void onDoubleClick(SpriteButton button) {
		log("Double click: "+button.getText());
		UserActionQueue.getInstance().run(new ExpandButtonAction(universe,button));
	}

}
