package d3bug.poc.tools;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import d3bug.graphics.ImageAccess;
import d3bug.poc.Edge;
import d3bug.poc.SpriteButton;
import d3bug.poc.SpriteButtonButton;
import d3bug.poc.SpriteButtonUniverse;
import d3bug.poc.sounds.SoundFx;
import d3bug.poc.tasks.HighlightSpritesTask;
import d3bug.poc.tasks.MakeWordFromLettersTask;
import d3bug.poc.tasks.MoveButtonsTowardsButtonTask;
import d3bug.poc.threads.UniverseRunner;

public class WordMakerTool extends ToolButton {
	
	private static UniverseRunner runner = UniverseRunner.getRunner();
	private List<SpriteButton> buttons = new CopyOnWriteArrayList<SpriteButton>();
	private List<SpriteButton> lastHighlighted = Collections.EMPTY_LIST;
	private boolean dragging;
	
	public WordMakerTool(SpriteButtonUniverse universe) {
		super(universe);
		//setText("-><-");
		setIcon(ImageAccess.getInstance().getIcon("50s_robot_head_ty_clr.gif"));
		setSize(85,80);
	}

	@Override
	public void onCreated(SpriteButton button) {
		buttons.add(button);
	}

	@Override
	public void onDestroyed(SpriteButton button) {
		buttons.remove(button);
	}
	
	@Override
	public void onKeyPressed(String key) {
//		if (key.equals(" ")) {  NOT WORKING PROPERLY
//			doWordMake();
//		}
	}
	
	@Override
	public void onToolDoubleClick() {
		if (lastHighlighted.size() >= 2) {
			log("Making letters");
			runner.execute(new MakeWordFromLettersTask(lastHighlighted));
			lastHighlighted.clear();
		}
	}
	
	@Override
	public void onToolPressed() { dragging = true;}
	
	@Override
	public void onToolReleased() { 
		dragging = false; 
		stopFollowTask();
	}
	
	@Override
	public void onToolPressedAgainst(SpriteButton otherButton, int pressure, Edge targetButtonsEdge) {
		if (pressure > 5) {
			if (followTask == null) {
				boolean touchedRight = targetButtonsEdge == Edge.RIGHT;
				highlightHookedSprites(touchedRight);
				runFollowTask();
			}
		}
	}
	
	private MoveButtonsTowardsButtonTask followTask;
	private synchronized void runFollowTask() {
		if (followTask == null) {
			SoundFx.getInstance().soundWordPickedUp();
			followTask = new MoveButtonsTowardsButtonTask(this,lastHighlighted,7,2,5);
			log("STARTING Follow task");
			UniverseRunner.getRunner().execute(followTask);
		}
	}
	
	private synchronized void stopFollowTask() {
		if (followTask != null) {
			log("STOPPING Follow task");
			followTask.done();
			followTask = null;
		}		
	}
	
	private List<SpriteButton> findSprites(boolean goLeft) {
		List<SpriteButton> list = new ArrayList<SpriteButton>();
		int direction = goLeft ? -20 : 20;
		int x = getX();
		int y = getY() + getHeight()/2;
		Rectangle r = universe.getVisibleArea();
		for (int searchx=x; (searchx > r.getX() && searchx < r.getX() + r.getWidth()); searchx += direction) {
			SpriteButton b = universe.getButtonAt(searchx,y);
			if (b != null && !list.contains(b)) {
				list.add(b);
			}
		}
		return list;
	}
	
	private void highlightHookedSprites(boolean goLeft) {
		List<SpriteButton> list = findSprites(goLeft);
		lastHighlighted = list;
		if (list.size() > 0) {
			runner.execute(new HighlightSpritesTask(list,3000,Color.GREEN));
		}	
	}
	
}
