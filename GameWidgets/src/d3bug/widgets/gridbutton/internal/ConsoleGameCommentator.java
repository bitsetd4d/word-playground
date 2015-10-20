package d3bug.widgets.gridbutton.internal;

import d3bug.widgets.gridbutton.api.GameButton;
import d3bug.widgets.gridbutton.api.PlayerMoveListener;

public class ConsoleGameCommentator implements PlayerMoveListener {

	public void onCorrectButtonSelected(GameButton b) {
		System.out.println("Console Commentator: Correct Button Selected "+b.getValue());
	}

	public void onCurrentTargetSet(GameButton b) {
		System.out.println("Console Commentator: Current Target Set: "+b.getValue());		
	}

	public void onWrongButtonSelected(GameButton b) {
		System.out.println("Console Commentator: Wrong Button Selected "+b.getValue());
	}

	public void onPlayerWins() {
		System.out.println("Console Commentator: Player wins!!");
	}

}
