package d3bug.poc.tools;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import d3bug.graphics.ImageAccess;
import d3bug.poc.Edge;
import d3bug.poc.SpriteButton;
import d3bug.poc.SpriteButtonUniverse;
import d3bug.poc.actions.VacuumEatButtonsAction;
import d3bug.poc.sounds.SoundFx;
import d3bug.poc.tasks.CompositeTask;
import d3bug.poc.tasks.FlyButtonToLocationTask;
import d3bug.poc.tasks.ShakeButtonsTask;
import d3bug.poc.tasks.ToolAttacksButtonsTask;
import d3bug.poc.threads.UniverseRunnable;
import d3bug.poc.threads.UniverseRunner;
import d3bug.poc.threads.UserAction;
import d3bug.poc.threads.UserActionQueue;

public class VacuumToolButton extends ToolButton {
	
	private static final Object MARKER = new Object();
	private WeakHashMap<SpriteButton, Object> inProgress = new WeakHashMap<SpriteButton,Object>();

	public VacuumToolButton(SpriteButtonUniverse universe) {
		super(universe);
		//setText("(**)");
		setIcon(ImageAccess.getInstance().getIcon("frog_freddy_fork_knife_ty_clr_st.gif"));
		hookKeyboard();
	}
	
	private void hookKeyboard() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
			public boolean dispatchKeyEvent(KeyEvent e) {
				if (e.getID() ==  KeyEvent.KEY_RELEASED) {
					if (e.getKeyCode() == '\b' || e.getKeyCode() == KeyEvent.VK_DELETE) {
						onDeletePressed();
					}
				}
				return false;
			}
		});
	}

	private void onDeletePressed() {
		log("Deleted Pressed");
		final int x = getX();
		final int y = getY();
		final SpriteButton b = getLastCreatedSingleLetterButton();
		final ToolButton tool = this;
		if (b != null) {
			UserActionQueue.getInstance().run(new UserAction() {
				@Override
				public void run() { 
					inProgress.put(b,MARKER);
					UniverseRunnable r1 = new ToolAttacksButtonsTask(tool,b);
					UniverseRunnable r2 = new FlyButtonToLocationTask(tool,x,y,10,10);
					UniverseRunner.getRunner().execute(new CompositeTask(r1,r2));				
				}
				@Override
				public void undo() { 
					b.unDestroySprite(); 
				}
			});
		}
	}

	private SpriteButton getLastCreatedSingleLetterButton() {
		List<SpriteButton> allButtons = universe.getAllSpriteButtons();
		SpriteButton youngest = null;
		long baseline = 0;
		for (SpriteButton b : allButtons) {
			if (b.getCreated() > baseline && b.getText().length() == 1 && inProgress.get(b) == null) {
				youngest = b;
				baseline = b.getCreated();
			}
		}
		return youngest;
	}

	@Override
	public void onToolPressedAgainst(final SpriteButton otherButton, int pressure, Edge targeButtonsEdge) {
		SoundFx.getInstance().soundEatButton();
		UserActionQueue.getInstance().run(new UserAction() {
			@Override
			public void run() { otherButton.destroySprite(); }
			@Override
			public void undo() { otherButton.unDestroySprite(); }
		});
	}
	
	@Override
	public void onToolHorizontalShake() {
		log("Vacuum shaked");
		SoundFx.getInstance().soundVacuumAttack();
		List<SpriteButton> all = getEligibleButtons();
		UserActionQueue.getInstance().run(new VacuumEatButtonsAction(this,all));
//		UniverseRunner.getRunner().execute(new ToolAttacksButtonsTask(this,all));
	}
	
	@Override
	public void onToolDoubleClick() {
		log("Vacuum double click");
		SoundFx.getInstance().soundVacuumAttack();
		UniverseRunner.getRunner().execute(new ShakeButtonsTask(getEligibleButtons()));
	}
	
	private List<SpriteButton> getEligibleButtons() {
		 List<SpriteButton> buttons = universe.getAllSpriteButtons();
		 List<SpriteButton> eligibleButtons = new ArrayList<SpriteButton>();
		 for (SpriteButton b : buttons) {
			 if (b.isVisible() && b.getText().length() <= 1) {
				 eligibleButtons.add(b);
			 }
		 }
		 return eligibleButtons;
	}
	
	@Override
	public void onDoubleClick(final SpriteButton button) {
		if (button.getText().length() == 1) {
			log("Deleting single letter button");
			CompositeTask t = new CompositeTask();
			t.add(new ShakeButtonsTask(button));
			t.add(new UniverseRunnable() {
				public String getName() { return "Kill Button"; }
				public void run() { button.destroySprite(); }
			});
			UniverseRunner.getRunner().execute(t);
		}
	}

}
