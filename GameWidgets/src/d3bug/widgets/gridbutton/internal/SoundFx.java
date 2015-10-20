package d3bug.widgets.gridbutton.internal;

import java.applet.AudioClip;

import d3bug.audio.AudioClipAccess;
import d3bug.util.RandomUtil;
import d3bug.widgets.gridbutton.api.GameButton;
import d3bug.widgets.gridbutton.api.PlayerMoveListener;

public class SoundFx implements PlayerMoveListener {
	
	private String[] correctSounds = { 
			"chime04.wav",
			"chime01.wav",
			"goodmorning.wav",
			"chime03.wav",
			"chime05.wav",
			"fredrogers.wav",
			"chime02.wav"
	};
	
	private String[] wrongSounds = {
			"mudfart.wav",
			"ohno.wav"
	};
	
	private String PLAYER_WINS = "awaken07.wav";
	
	public void onCorrectButtonSelected(GameButton b) {
		String chosen = RandomUtil.chooseRandom(correctSounds);
		AudioClip clip = AudioClipAccess.getInstance().getAudioClip(chosen);
		clip.play();
	}

	public void onCurrentTargetSet(GameButton b) {}
	public void onPlayerWins() {
		AudioClip clip = AudioClipAccess.getInstance().getAudioClip(PLAYER_WINS);
		clip.play();
	}

	public void onWrongButtonSelected(GameButton b) {
		String chosen = RandomUtil.chooseRandom(wrongSounds);
		AudioClip clip = AudioClipAccess.getInstance().getAudioClip(chosen);
		clip.play();
	}

}
