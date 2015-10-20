package d3bug.poc.actions;

import java.awt.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import d3bug.poc.Memento;
import d3bug.poc.SpriteButton;
import d3bug.poc.tasks.MoveButtonsToLineupTask;
import d3bug.poc.threads.UniverseRunner;
import d3bug.poc.threads.UserAction;

public class MoveButtonsToLineupAction extends UserAction {
	
	private List<SpriteButton> allButtons;
	private List<SpriteButton> toolButtons;
	private Component parent;
	
	private Map<SpriteButton,Memento> mementos = new HashMap<SpriteButton,Memento>();
	
	public MoveButtonsToLineupAction(List<SpriteButton> allButtons, List<SpriteButton> toolButtons, Component parent) {
		this.allButtons = allButtons;
		this.toolButtons = toolButtons;
		this.parent = parent;
	}

	@Override
	public void run() {
		saveState();
		MoveButtonsToLineupTask task = new MoveButtonsToLineupTask(allButtons,toolButtons,parent);
		UniverseRunner.getRunner().execute(task);
	}

	@Override
	public void undo() {
		for (SpriteButton b : allButtons) {
			Memento m = mementos.get(b);
			if (m != null) b.setFromMemento(m);	
		}
	}
	
	private void saveState() {
		for (SpriteButton b : allButtons) {
			mementos.put(b,b.getMemento());			
		}
	}

}
