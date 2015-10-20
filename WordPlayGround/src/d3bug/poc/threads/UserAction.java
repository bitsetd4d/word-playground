package d3bug.poc.threads;

public abstract class UserAction {

	public abstract void run();
	public abstract void undo();
	public void redo() {
		run();
	}
	
}
