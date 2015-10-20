package d3bug.tool.phonetic;

import java.awt.Rectangle;
import java.util.Arrays;

import javax.swing.JLayeredPane;

import d3bug.poc.SpriteButton;
import d3bug.poc.SpriteButtonUniverse;
import d3bug.poc.sounds.SoundFx;
import d3bug.poc.tasks.ShakeButtonsTask;
import d3bug.poc.threads.UniverseRunner;
import d3bug.poc.tools.ToolButton;
import d3bug.poc.words.WordUtil;
import d3bug.util.FireIfNotInterruptedTimer;
import d3bug.util.FireIfNotInterruptedTimer.TimerFiredListener;
import d3bug.words.dictionary.DictionaryLookup;
import d3bug.words.dictionary.DictionaryWord;
import d3bug.words.dictionary.Phoneme;

public class PhoneticToolButton extends ToolButton implements TimerFiredListener {

	private JLayeredPane pane;
	private boolean toolEnabled = true;
	private FireIfNotInterruptedTimer fireTimer = new FireIfNotInterruptedTimer(this);
	private DictionaryWord wordToSay;
	
	public PhoneticToolButton(JLayeredPane pane,SpriteButtonUniverse universe) {
		super(universe);		
		this.pane = pane;
		setVisibleState();
		getComponent().setBounds(new Rectangle(60,60));
		setVisible(true);
	}
	
	@Override
	public void onWordCreated(String x) {
		if (toolEnabled && x.length() >= 2) {
			System.out.println("Word "+x);
			DictionaryWord dw = DictionaryLookup.lookup(x);
			if (dw != null) {
				System.out.println("SAY: "+dw.getWord()+" --> "+Arrays.toString(dw.getPhonemes()));
				for (Phoneme p : dw.getPhonemes()) {
					System.out.println(" --->>"+p.getEquivalent());
				}
			} else {
				System.out.println("Not found");
			}
			queueWordSay(dw);
		}
	}

	private void setVisibleState() {
		setText(toolEnabled ? ":-)" : ":-o");
	}
	
	@Override
	public void onToolDoubleClick() {
		toolEnabled = !toolEnabled;
		setVisibleState();
	}
	
	@Override
	public void onSingleClick(SpriteButton button) {
		if (toolEnabled) {
	 		String text = button.getText();
	 		DictionaryWord dw = DictionaryLookup.lookup(text);
	 		if (dw != null) {
	 			queueWordSay(dw);
	 			return;
	 		}
	 		if (WordUtil.isSyllable(text)) {
	 			SoundFx.getInstance().playPhonic(text);
	 			return;
	 		}
	 		//wordSpeak(text);
		}
	}
	
//	private void wordSpeak(final String text) {
//		UniverseRunner.getRunner().execute(new PhoneticSpeakWordTask(pane,text));		
//	}
			
	private void queueWordSay(DictionaryWord dw) {
		wordToSay = dw;
		fireTimer.fireIfNotInterrupted(750);
	}
	
	public void onTimerFired() {
		if (wordToSay != null) {
			UniverseRunner.getRunner().execute(new ShakeButtonsTask(this));
			UniverseRunner.getRunner().execute(new PhoneticSpeakDictionaryWordTask(wordToSay));				
		} else {
			UniverseRunner.getRunner().execute(new ShakeButtonsTask(this));
			SoundFx.getInstance().soundDontKnown();
		}
	}

}
