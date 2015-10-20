package d3bug.poc.threads;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class UniverseRunner {
	
	private static UniverseRunner INSTANCE = new UniverseRunner();
	
	public static UniverseRunner getRunner() {
		return INSTANCE;
	}
	
	private BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
	private ThreadPoolExecutor pool = new ThreadPoolExecutor(2,12,1000,TimeUnit.MILLISECONDS,workQueue);
//	private ExecutorService priorityExecutor = Executors.newCachedThreadPool();
	private AtomicInteger jobCount = new AtomicInteger();
	private Timer timer = new Timer("UniverseTimer");
	
	private List<UniverseRunningListener> listeners = new CopyOnWriteArrayList<UniverseRunningListener>();
	
	private static final int QUICK_THRESHOLD = 4;
	private static final int BACKPRESSURE_THRESHOLD = 20;
	
	private UniverseRunnable currentRunnable;
	
	private UniverseRunner() {}
	
	public void addListener(UniverseRunningListener l) { listeners.add(l); }
	public void removeListener(UniverseRunningListener l) { listeners.remove(l); }
	
	public void executeNow(final UniverseRunnable ur) {
		if (isUniverseBusy()) {
			if (ur.isOptional()) return;
			ur.setUniverseBusy(true);
			ur.quickRun();
			return;
		}
		ur.run();
	}
	
	public void execute(final UniverseRunnable ur) {
		if (jobCount.get() > BACKPRESSURE_THRESHOLD) {
			try {
				if (currentRunnable != null) {
					currentRunnable.setUniverseBusy(true);
				}
				Thread.sleep(10);
			} catch (InterruptedException e) {}
		}
		final String name = ur.getName();
		Runnable r = new Runnable() { public void run() {
			try {
				for (UniverseRunningListener l : listeners) { l.onExecute(name); }
				currentRunnable = ur;
				if (isUniverseBusy()) {
					if (ur.isOptional()) return;
					ur.setUniverseBusy(true);
					ur.quickRun();
					return;
				}
				long start = System.currentTimeMillis();
				ur.run();
				long timeTaken = System.currentTimeMillis() - start;
				for (UniverseRunningListener l : listeners) { l.onExecuted(name, timeTaken); }
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				int q = jobCount.decrementAndGet();
				for (UniverseRunningListener l : listeners) { l.onQueueLength(q); }
			}
		}};
		int position = jobCount.incrementAndGet();
		for (UniverseRunningListener l : listeners) {
			l.onQueueLength(position);
			l.onScheduled(ur.getName(), position); 
		}
		System.out.println("+==============> "+position);
		pool.execute(r);
	}
	
	private boolean isUniverseBusy() {
		return jobCount.get() > QUICK_THRESHOLD;
	}

	public void sheduleWhenNotBusy(final TimerTask t, int start, int interval) {
		TimerTask t2 = new TimerTask() {
			@Override
			public void run() {
				if (isUniverseBusy()) return;
				try {
					t.run();
				} catch (Exception e) {}
			}			
		};
		timer.schedule(t2,start,interval);
	}
	
	public void sechedule(final TimerTask t,int start, int interval) {
		timer.schedule(t,start,interval);		
	}
	
	public void sheduleOnetime(final TimerTask t, int startFromNow) {
		TimerTask t2 = new TimerTask() {
			@Override
			public void run() {
				try {
					t.run();
				} catch (Exception e) {}
			}			
		};
		timer.schedule(t2,startFromNow);
	}

//	public void undo() {
//		if (currentRunnable instanceof UndoableUniverseRunnable) {
//			((UndoableUniverseRunnable)currentRunnable).runUndo();
//			currentRunnable = null;
//		}
//	}

}
