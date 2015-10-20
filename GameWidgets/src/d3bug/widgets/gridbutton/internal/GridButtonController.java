package d3bug.widgets.gridbutton.internal;

import java.awt.Dimension;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import d3bug.widgets.gridbutton.api.DefaultGameButton;
import d3bug.widgets.gridbutton.api.GameButton;
import d3bug.widgets.gridbutton.api.GridButtonListener;
import d3bug.widgets.gridbutton.api.PlayerMoveListener;


public class GridButtonController implements GridButtonListener {
	
	//private Map<String,GameButton> gameButtons = new ConcurrentHashMap<String,GameButton>();
	private final List<GameButton> buttonsInPlay = new CopyOnWriteArrayList<GameButton>();
	private final List<GameButton> readOnlyButtonsInPlay = Collections.unmodifiableList(buttonsInPlay);
	private Random random = new Random();
	private GameButton targetButton;
	
	private List<PlayerMoveListener> listeners = new CopyOnWriteArrayList<PlayerMoveListener>();
	
	//private String[] elements = { "Bang", "Hurt", "Play", "Food", "Everyone", "Grab", "Sleep", "Fun" };
	private String[] elements = { "0","1","2","3","4","5","6","7","8" };

	public Dimension getDimension() {
		return new Dimension(4,3);
	}
	
	public void addListener(PlayerMoveListener listener) {
		listeners.add(listener);
	}
	public void removeListener(PlayerMoveListener listener) {
		listeners.remove(listener);
	}

	public List<GameButton> getGameButtons() {
		if (buttonsInPlay.isEmpty()) {
			for (String x : elements) {
				DefaultGameButton b = new DefaultGameButton(x);
				buttonsInPlay.add(b);
				b.addListener(this);
			}
		}
		return buttonsInPlay;
	}

	public void start() {
		chooseNewTargetButton();
	}
	
	public void onButtonPressed(GameButton b) {
		if (isTargetButton(b)) {
			buttonsInPlay.remove(b);
			publishCorrectButtonSelected(b);
			if (buttonsInPlay.isEmpty()) {
				publishPlayerWins();
			} else {
				chooseNewTargetButton();
			}
		} else {
			publishWrongButtonSelected(b);
		}		
	}
	

//	private void setCurrentTargetButton(String key) {
//		targetButton = gameButtons.get(key);
//	}
//	
	private boolean isTargetButton(GameButton b) {
		return b == targetButton;
	}
	
	public List<GameButton> getButtonsInPlay() {
		return readOnlyButtonsInPlay;
	}

	private void chooseNewTargetButton() {
		int size = buttonsInPlay.size();
		if (size == 0) return;
		int i = random.nextInt(size);
		targetButton = buttonsInPlay.get(i);
		publishCurrentTargetSet(targetButton);
	}
	
	private void publishWrongButtonSelected(GameButton b) {
		for (PlayerMoveListener l : listeners) {
			l.onWrongButtonSelected(b);
		}
	}

	private void publishPlayerWins() {
		for (PlayerMoveListener l : listeners) {
			l.onPlayerWins();
		}
	}

	private void publishCorrectButtonSelected(GameButton b) {
		for (PlayerMoveListener l : listeners) {
			l.onCorrectButtonSelected(b);
		}
	}

	private void publishCurrentTargetSet(GameButton b) {
		for (PlayerMoveListener l : listeners) {
			l.onCurrentTargetSet(b);
		}
	}

	public void setElements(String[] elements) {
		this.elements = elements;
	}
}
