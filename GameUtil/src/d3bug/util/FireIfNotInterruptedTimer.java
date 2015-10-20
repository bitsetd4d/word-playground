package d3bug.util;

import java.util.Timer;
import java.util.TimerTask;

public class FireIfNotInterruptedTimer {
	
	public interface TimerFiredListener {
		void onTimerFired();
	}

	private static Timer timer = new Timer("DelayedAction-Timer");
	private TimerFiredListener listener;
	private TimerTask fireTask;
	
	public FireIfNotInterruptedTimer(TimerFiredListener listener) {
		this.listener = listener;
	}
	
	public synchronized void fireIfNotInterrupted(int ms) {
		if (fireTask != null) {
			fireTask.cancel();
		}
		fireTask = new TimerTask() { public void run() {
			fireCallback();
		}};
		timer.schedule(fireTask, ms);
		
	}
	
	private synchronized void fireCallback() {
		listener.onTimerFired();
		fireTask = null;
	}

}
