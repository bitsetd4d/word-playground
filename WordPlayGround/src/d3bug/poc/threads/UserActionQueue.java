package d3bug.poc.threads;

import java.util.ArrayList;
import java.util.List;

import d3bug.poc.sounds.SoundFx;

public class UserActionQueue {
	
	private static UserActionQueue INSTANCE = new UserActionQueue();
	public static UserActionQueue getInstance() {
		return INSTANCE;
	}
	
	private List<UserAction> userActions = new ArrayList<UserAction>();
	private List<UserAction> redoActions = new ArrayList<UserAction>();
	
	public void run(UserAction action) {
		queue(action);
		action.run();
	}
	
	public void queue(UserAction action) {
		userActions.add(action);
		redoActions.clear();		
	}
	
	public void undo() {
		int size = userActions.size();
		if (size > 0) {
			SoundFx.getInstance().soundUndo();
			UserAction action = userActions.get(size - 1);
			redoActions.add(action);
			userActions.remove(size - 1);
			action.undo();
		}
	}
	
	public void redo() {
		int size = redoActions.size();
		if (size > 0) {
			SoundFx.getInstance().soundRedo();
			UserAction action = redoActions.get(size - 1);
			userActions.add(action);
			redoActions.remove(size - 1);
			action.redo();	
		}
	}
	

}
