package d3bug.widgets.gridbutton.internal.animation;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import d3bug.widgets.gridbutton.api.GameButton;
import d3bug.widgets.gridbutton.api.PlayerMoveListener;

public class ButtonAnimationService implements PlayerMoveListener {

	private int ANIMATION_THREADS = 3;
	private int KEEP_POOL_THREADS_MS = 5000;
	private BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
	private ThreadPoolExecutor pool = new ThreadPoolExecutor(ANIMATION_THREADS,ANIMATION_THREADS,KEEP_POOL_THREADS_MS,TimeUnit.MILLISECONDS,workQueue);
	private GameButton correctAnswer;
	private int wrongGuesses;

	public void onCorrectButtonSelected(GameButton b) {
		GameAnimation ga = new GrowFontSizeAnimation(b);
		pool.execute(ga);
	}

	public void onWrongButtonSelected(GameButton b) {
		wrongGuesses++;
		if (wrongGuesses == 2 || wrongGuesses == 4 || wrongGuesses == 7) {
			GameAnimation r = new BlinkButtonTextAnimation(correctAnswer);
			pool.execute(r);
		} else if (wrongGuesses == 3) {
			GameAnimation r = new ButtonColourTextAnimation(correctAnswer);
			pool.execute(r);			
		}
	}

	public void onCurrentTargetSet(GameButton b) {
		correctAnswer = b;
		wrongGuesses = 0;
	}
	
	public void onPlayerWins() {}	

}
