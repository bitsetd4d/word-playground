package d3bug.poc;


public interface UniverseListener {
	
	void onPressedAgainst(SpriteButton sourceButton, SpriteButton otherButton, int pressure, Edge targeButtonsEdge);
	void onSingleClick(SpriteButton button);
	void onDoubleClick(SpriteButton button);
	void onButtonHorizontalShaked(SpriteButton sourceButton);
	void onKeyPressed(String key);
	
	void onCreated(SpriteButton button);
	void onDestroyed(SpriteButton button);
	
	void onWordCreated(String text);

}
