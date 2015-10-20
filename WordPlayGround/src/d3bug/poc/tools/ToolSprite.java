package d3bug.poc.tools;

import d3bug.poc.Edge;
import d3bug.poc.SpriteButton;

public interface ToolSprite {
	
	void onToolHorizontalShake();
	void onToolDoubleClick();
	void onToolPressedAgainst(SpriteButton otherButton, int pressure, Edge targeButtonsEdge);
	void onToolTouchedBy(SpriteButton sourceButton, int pressure, Edge edge);
	void onToolPressed();
	void onToolReleased();

}
