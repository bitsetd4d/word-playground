package d3bug.poc.tools;

import java.awt.Color;

import d3bug.poc.Edge;
import d3bug.poc.SpriteButton;
import d3bug.poc.SpriteButtonButton;
import d3bug.poc.SpriteButtonUniverse;
import d3bug.poc.UniverseListener;
import d3bug.poc.log.Logger;

public class ToolButton extends SpriteButtonButton implements ToolSprite, UniverseListener {

	public ToolButton(SpriteButtonUniverse universe) {
		super(universe);
		setForeground(Color.RED);
		universe.registerToolButton(this);
		universe.addListener(this);
	}

	// ToolSprite
	public void onToolHorizontalShake() {}
	public void onToolDoubleClick() {}	
	public void onToolPressedAgainst(SpriteButton otherButton, int pressure, Edge targeButtonsEdge) {}
	public void onToolTouchedBy(SpriteButton sourceButton, int pressure, Edge edge) {}
	public void onToolPressed() {}
	public void onToolReleased() {}
	
	// SpriteButtonListener
	public void onButtonHorizontalShaked(SpriteButton sourceButton) {}
	public void onSingleClick(SpriteButton button) {}
	public void onDoubleClick(SpriteButton button) {}
	public void onPressedAgainst(SpriteButton sourceButton, SpriteButton otherButton, int pressure, Edge targeButtonsEdge) {}

	public void onKeyPressed(String key) {}
	
	public void onCreated(SpriteButton button) {}
	public void onDestroyed(SpriteButton button) {}

	public void onWordCreated(String text) {}
	
	public void log(String message) {
		Logger.info(this, message);
	}


}
