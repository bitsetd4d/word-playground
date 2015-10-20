package d3bug.testgame;

import d3bug.widgets.gridbutton.api.GridButtonPicker;
import d3bug.widgets.gridbutton.api.Main;

public class NumberGame {
	
	public static void main(String[] args) {
		NumberGame w = new NumberGame();
		w.go();
	}

	private void go() {
		GridButtonPicker window = new GridButtonPicker();
		window.setElements("0","1","2","3","4","5","6","7","8","9","10","11");
		window.setRowCol(3,4);
		window.open();
	}

}
