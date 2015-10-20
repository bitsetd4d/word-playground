package d3bug.widgets.gridbutton.internal;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import d3bug.widgets.gridbutton.api.GameButton;
import d3bug.widgets.gridbutton.api.PlayerMoveListener;

public class DropSubtleHintsManager implements PlayerMoveListener {
	
	private GridButtonController controller;
	private GameButton currentButton;
	private int wrongGuesses;
	private Set<GameButton> pressedThisRound = new CopyOnWriteArraySet<GameButton>();
	
	public DropSubtleHintsManager(GridButtonController controller) {
		this.controller = controller;
	}
	
	public void onCorrectButtonSelected(GameButton b) {
		System.out.println("Hint: Correct Button is "+b.getValue());
		wrongGuesses = 0;
	}
	
	public void onCurrentTargetSet(GameButton b) {
		System.out.println("Hint: Current Target. Button is "+b.getValue());
		currentButton = b;
		pressedThisRound.clear();
		List<GameButton> buttonsInPlay = controller.getButtonsInPlay();
		for (GameButton bt : buttonsInPlay) {
			bt.resetFont();
		}
	}
	
	public void onWrongButtonSelected(GameButton b) {
		System.out.println("Hint: Wrong Button - User chose "+b.getValue());
		b.setWrongHint();
		if (!pressedThisRound.contains(b)) {
	 		wrongGuesses++;
			if (wrongGuesses > 1) {
				currentButton.adjustFontSize(wrongGuesses*wrongGuesses);
			}
			pressedThisRound.add(b);
		}
	}
	
	public void onPlayerWins() {
		System.out.println("Hint: Player wins");
	}

}
