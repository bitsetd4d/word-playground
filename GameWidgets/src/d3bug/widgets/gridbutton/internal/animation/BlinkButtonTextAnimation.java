package d3bug.widgets.gridbutton.internal.animation;

import d3bug.util.Threading;
import d3bug.widgets.gridbutton.api.GameButton;

public class BlinkButtonTextAnimation implements GameAnimation {

	private GameButton gameButton;
	
	public BlinkButtonTextAnimation(GameButton b) {
		this.gameButton = b;
	}
		
	public void run() {
		String t = gameButton.getJButton().getText();
		for (int i=0; i<2; i++) {
			gameButton.getJButton().setText("");
			Threading.sleep(200);
			gameButton.getJButton().setText(t);
			Threading.sleep(500);
		}
	}

}
