package d3bug.widgets.gridbutton.api;


public interface PlayerMoveListener {

	void onWrongButtonSelected(GameButton b);
	void onPlayerWins();
	void onCorrectButtonSelected(GameButton b);
	void onCurrentTargetSet(GameButton b);

}
