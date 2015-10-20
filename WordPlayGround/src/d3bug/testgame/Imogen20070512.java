package d3bug.testgame;

import d3bug.widgets.gridbutton.api.GridButtonPicker;

public class Imogen20070512 {

	public static void main(String[] args) {
		Imogen20070512 w = new Imogen20070512();
		w.go();
	}

	private void go() {
		GridButtonPicker window = new GridButtonPicker();
		window.setElements("Chip wanted some sugar he went to the supermarket got crisps shop comic market ball forgot".split(" "));
		window.setRowCol(8,2);
		window.open();
	}
}
