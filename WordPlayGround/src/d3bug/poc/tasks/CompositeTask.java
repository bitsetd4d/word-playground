package d3bug.poc.tasks;

import java.util.ArrayList;
import java.util.List;

import d3bug.poc.threads.UniverseRunnable;

public class CompositeTask extends UniverseRunnable {

	private List<UniverseRunnable> tasks = new ArrayList<UniverseRunnable>();
	
	public CompositeTask(UniverseRunnable... runnables) {
		for (UniverseRunnable r : runnables) {
			tasks.add(r);
		}
	}
	
	public void add(UniverseRunnable r) {
		tasks.add(r);
	}
	
	@Override
	public String getName() {
		return "Composite";
	}
	
	public void setUniverseBusy(boolean b) {
		for (UniverseRunnable r : tasks) {
			r.setUniverseBusy(b);
		}
	}

	@Override
	public void run() {
		for (UniverseRunnable r : tasks) {
			r.run();
		}
	}
	
	@Override
	public void quickRun() {
		for (UniverseRunnable r : tasks) {
			r.quickRun();
		}
	}

}
