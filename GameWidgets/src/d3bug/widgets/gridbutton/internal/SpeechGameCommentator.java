package d3bug.widgets.gridbutton.internal;

import d3bug.speech.TextSpeaker;
import d3bug.widgets.gridbutton.api.GameButton;
import d3bug.widgets.gridbutton.api.PlayerMoveListener;

public class SpeechGameCommentator implements PlayerMoveListener {
	
	private TextSpeaker speaker = TextSpeaker.getInstance();
	private GameButton currentTarget;

	public void onCorrectButtonSelected(GameButton b) {
		speaker.say("Well Done! "+b.getValue());
	}

	public void onCurrentTargetSet(GameButton b) {
		currentTarget = b;
		speaker.say("Find "+b.getValue());		
	}

	public void onWrongButtonSelected(GameButton b) {
		speaker.say("Wrong! Not "+b.getValue()+".");		
		if (currentTarget != null) {
			speaker.say("Find "+currentTarget.getValue());		
		}
	}

	public void onPlayerWins() {
		speaker.say("Well Done - you win!! Well done!");
	}

}
