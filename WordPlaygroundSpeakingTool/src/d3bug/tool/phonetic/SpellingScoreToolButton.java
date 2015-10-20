package d3bug.tool.phonetic;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.HashSet;
import java.util.Set;

import d3bug.kidswing.FontManager;
import d3bug.poc.SpriteButtonUniverse;
import d3bug.poc.sounds.SoundFx;
import d3bug.poc.tasks.ShakeButtonsTask;
import d3bug.poc.threads.UniverseRunner;
import d3bug.poc.tools.ToolButton;
import d3bug.util.FireIfNotInterruptedTimer;
import d3bug.util.FireIfNotInterruptedTimer.TimerFiredListener;
import d3bug.words.dictionary.DictionaryLookup;
import d3bug.words.dictionary.DictionaryWord;

public class SpellingScoreToolButton extends ToolButton implements TimerFiredListener {
	
	private boolean disabled = false;
	private int score = 0;
	private Set<String> words = new HashSet<String>();
	private FireIfNotInterruptedTimer fireTimer = new FireIfNotInterruptedTimer(this);

	public SpellingScoreToolButton(SpriteButtonUniverse universe) {
		super(universe);
		showState();
		setForeground(Color.DARK_GRAY);
		getComponent().setBounds(new Rectangle(80,60));
		setFont(FontManager.getInstance().getComicFont(20));
		setVisible(true);
	}
	
	@Override
	public void onWordCreated(String x) {
		if (!disabled && x.length() >= 2) {
			wordToCheck = x;
			fireTimer.fireIfNotInterrupted(750);
		}
	}

	public void onTimerFired() {
		checkWordSpelledCorrectly();
	}
	
	private String wordToCheck;
	private void checkWordSpelledCorrectly() {
		if (wordToCheck == null) return;
		DictionaryWord dw = DictionaryLookup.lookup(wordToCheck);
		if (dw != null) {
			if (words.contains(dw.getWord())) return;
			score++;
			words.add(dw.getWord());
			showState();
			SoundFx.getInstance().soundCorrect();
			UniverseRunner.getRunner().execute(new ShakeButtonsTask(this));
		}		
	}
	
	@Override
	public void onToolHorizontalShake() {
		words.clear();
		score = 0;
		showState();
	}
	
	@Override
	public void onToolDoubleClick() {
		disabled = !disabled;
		showState();
		UniverseRunner.getRunner().execute(new ShakeButtonsTask(this));
	}

	private void showState() {
		if (disabled) {
			setText("X");
			return;
		}
		setText(String.valueOf(score));
	}

}
