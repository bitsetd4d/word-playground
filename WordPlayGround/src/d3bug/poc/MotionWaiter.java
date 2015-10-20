package d3bug.poc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MotionWaiter implements SpriteStationaryCallback {
	
	private final Lock lock = new ReentrantLock();
	private final Condition finished  = lock.newCondition();
	private final static long MAX_AWAIT_SECS = 10;
	private int count;
	
	public MotionWaiter() {
		count = 1;
	}
	
	public MotionWaiter(int count) {
		this.count = count;
	}

	public void onStationary(SpriteButton button) {
		try {
			lock.lock();
			count--;
			if (count <= 0) finished.signal();
		} finally {
			lock.unlock();
		}
	}
	
	public void await() {
		try {
			System.out.println("Waiting for motion to stop...");
			lock.lock();
			if (count <= 0) return;
			finished.await(MAX_AWAIT_SECS,TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			System.out.println("Interupted");
		} finally {
			System.out.println("... motion stopped");
			lock.unlock();			
		}
	}
	
}
