package d3bug.testgame;

import d3bug.widgets.gridbutton.api.GridButtonPicker;

public class Amy20070512 {

	public static void main(String[] args) {
		Amy20070512 w = new Amy20070512();
		w.go();
	}

	private void go() {
		GridButtonPicker window = new GridButtonPicker();
		window.setElements("one first two second three third four fourth five fifth six sixth seven seventh eight eighth nine ninth ten tenth".split(" "));
		window.setRowCol(10,2);
		window.open();
	}
}
