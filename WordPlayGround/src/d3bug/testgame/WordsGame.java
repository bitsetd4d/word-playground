package d3bug.testgame;

import d3bug.widgets.gridbutton.api.GridButtonPicker;
import d3bug.widgets.gridbutton.api.Main;

public class WordsGame {
	
	public static void main(String[] args) {
		WordsGame w = new WordsGame();
		w.go();
	}

	private void go() {
		GridButtonPicker window = new GridButtonPicker();
		window.setElements("Spelling","Wednesday","Monday","February","Fun","Wheel","Elephant","Market","Potato","Game");
		window.setRowCol(5,2);
		window.open();
	}

}
