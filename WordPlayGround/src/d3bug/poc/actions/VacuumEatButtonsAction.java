package d3bug.poc.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import d3bug.poc.Memento;
import d3bug.poc.SpriteButton;
import d3bug.poc.tasks.ToolAttacksButtonsTask;
import d3bug.poc.threads.UniverseRunner;
import d3bug.poc.threads.UserAction;
import d3bug.poc.tools.ToolButton;

public class VacuumEatButtonsAction extends UserAction {

	private ToolButton toolButton;
	private List<SpriteButton> buttons;

	private Memento toolMemento;
	private Map<SpriteButton,Memento> mementos = new HashMap<SpriteButton,Memento>();

	public VacuumEatButtonsAction(ToolButton toolButton,List<SpriteButton> buttons) {
		this.toolButton = toolButton;
		this.buttons = buttons;
	}
	
	@Override
	public void run() {
		saveState();
		ToolAttacksButtonsTask task = new ToolAttacksButtonsTask(toolButton,buttons);
		UniverseRunner.getRunner().execute(task);
	}

	@Override
	public void undo() {
		toolButton.setFromMemento(toolMemento);
		for (SpriteButton b : buttons) {
			Memento m = mementos.get(b);
			if (m != null) b.setFromMemento(m);
		}
	}
	
	private void saveState() {
		toolMemento = toolButton.getMemento();
		for (SpriteButton b : buttons) {
			mementos.put(b,b.getMemento());
		}
	}


}
