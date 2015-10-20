package d3bug.poc.tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import d3bug.poc.MotionWaiter;
import d3bug.poc.SpriteButton;
import d3bug.poc.sounds.SoundFx;
import d3bug.poc.threads.UniverseRunnable;
import d3bug.poc.words.WordUtil;

public class MakeWordFromLettersTask extends UniverseRunnable {

	private List<SpriteButton> potentialWord;
	private Random random = new Random();
	
	public MakeWordFromLettersTask(List<SpriteButton> potentialWord) {
		this.potentialWord = new ArrayList<SpriteButton>(potentialWord);
	}
	
	@Override
	public String getName() { return "Make Words"; }

	@Override
	public void run() {
		sortPotentialWordByX();
		String word = getWord(potentialWord);
		log("Will make word: "+word);
		String[] elements = WordUtil.splitIntoElements(word);
		log("Word has elements: "+Arrays.toString(elements));
		// Break apart buttons that are capturing syllables
		int i=0;
		log("Current buttons --> "+getWordBits(potentialWord));
		List<SpriteButton> revisedButtons = new ArrayList<SpriteButton>();
		//SpriteButton pushedButton = null
		boolean merged = false;
		int cb = 0;
		int ce = 0;
		while (cb < potentialWord.size() && ce < elements.length) {
			SpriteButton currentButton = potentialWord.get(cb);
			String currentText = currentButton.getText();
			String currentElement = elements[ce];
			log("Current Button: "+currentText+", Current Element: "+currentElement);
			if (currentText.length() < currentElement.length()) {
				List<SpriteButton> potentialMerge = new ArrayList<SpriteButton>();
				String merge = currentText;
				INNER:
				for (int j = 1; j<currentElement.length(); j++) {
					int indx = cb + j;
					if (indx < potentialWord.size()) {
						SpriteButton b = potentialWord.get(indx);
						potentialMerge.add(b);
						merge += b.getText();
						if (merge.equals(currentElement)) {
							log("Found buttons "+getWordBits(potentialMerge)+ " that merge to "+currentElement);
							mergeButtons(currentButton,potentialMerge); 
							revisedButtons.add(currentButton);
							merged = true;
							cb += potentialMerge.size();
							break INNER;
						}
					}
				}
				cb++;
				ce++;
			} else {
				cb++;
				ce++;
				revisedButtons.add(currentButton);
			}
		}
		
		if (merged) sleep(400);

		List<SpriteButton> newButtons = new ArrayList<SpriteButton>(revisedButtons);
		Iterator<SpriteButton> it = newButtons.iterator();
		SpriteButton base = it.next();
		while (it.hasNext()) {
			SpriteButton b = it.next();
			shakeButtonsNow(3, 2, 5, 25, base, b);
			SoundFx.getInstance().soundMerge();
			flyTogetherNow(newButtons);
			b.mergeOnRightAndBecome(base);
		}
	}
	
	private void mergeButtons(SpriteButton currentButton, List<SpriteButton> merges) {
		for (SpriteButton b : merges) {
			shakeButtonsNow(4, 2, 20, 25, currentButton, b);
			log("Making syllable button "+currentButton.getText()+" + "+b.getText());
			b.mergeOnRightAndBecome(currentButton);
		}
		log("Made button "+currentButton.getText());
		SoundFx.getInstance().soundMerge();
	}

	private void flyTogetherNow(List<SpriteButton> buttons) {
		Iterator<SpriteButton> it = buttons.iterator();
		if (!it.hasNext()) return;
		SpriteButton firstButton = it.next();
		int delta = firstButton.getWidth();
		SpriteButton lastButton = null;
		if (!it.hasNext()) return;
		while (it.hasNext()) {
			SpriteButton b = it.next();
			if (b.isVisible()) {
				b.flyTo(firstButton.getX() + delta, firstButton.getY(),10);
				delta += b.getWidth();
				lastButton = b;
			}
		}
		if (lastButton != null) {
			MotionWaiter waiter = new MotionWaiter();
			lastButton.addCallback(waiter);
			waiter.await();
		}
	}

	public void shakeButtonsNow(int xshake, int yshake, int iterations, int pause,SpriteButton...buttons) {
		int[] x = new int[buttons.length];
		int[] y = new int[buttons.length];
		int i=0;
		for (SpriteButton b : buttons) {
			x[i] = b.getX();
			y[i] = b.getY();
			i++;
		}
		for (i=0; i<iterations; i++) {
			int j = 0;
			for (SpriteButton b : buttons) {
				int dx = random.nextInt(xshake);
				int dy = random.nextInt(yshake);
				if (random.nextBoolean()) dx = -dx;
				if (random.nextBoolean()) dy = -dy;
				b.setLocation(x[j] + dx, y[j] + dy);
				j++;
			}
			sleep(pause);
		}		
	}
	private void sortPotentialWordByX() {
		Collections.sort(potentialWord,new Comparator<SpriteButton>() {
			public int compare(SpriteButton o1, SpriteButton o2) {
				return o1.getX() - o2.getX();
			}
		});
	}
	
	private String getWord(List<SpriteButton> list) {
		StringBuilder sb = new StringBuilder();
		for (SpriteButton b : list) {
			sb.append(b.getText());
		}		
		return sb.toString();
	}
	
	private String getWordBits(List<SpriteButton> list) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (SpriteButton b : list) {
			if (first) {
				first = false;
			} else {
				sb.append("-");
			}
			sb.append(b.getText());
		}		
		return sb.toString();
	}
	
}

