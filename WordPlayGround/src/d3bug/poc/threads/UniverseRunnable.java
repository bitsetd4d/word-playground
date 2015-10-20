package d3bug.poc.threads;

import d3bug.poc.log.Logger;

public abstract class UniverseRunnable {
	
	protected boolean universeBusy;
	
	public void setUniverseBusy(boolean b) {
		universeBusy = b;
	}
	
	public boolean isOptional() {
		return false;
	}
  	
	public abstract void run();
	
	public void quickRun() {
		run();
	}
	
	public void sleep(int millis) {
		if (universeBusy) return;
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {}
	}

	public abstract String getName();
	
	public void log(String message) {
		Logger.info(this, message);
	}
}
