package d3bug.widgets.gridbutton.internal.animation;

import java.awt.Color;

import d3bug.util.Threading;
import d3bug.widgets.gridbutton.api.GameButton;

public class ButtonColourTextAnimation implements GameAnimation {

	private GameButton gameButton;
	private static Color BLINK_COLOUR = Color.CYAN;
	
	public ButtonColourTextAnimation(GameButton b) {
		this.gameButton = b;
	}
	
	public void run() {
		Color c = gameButton.getJButton().getForeground();
		for (int i=0; i<2; i++) {
			gameButton.getJButton().setForeground(BLINK_COLOUR);
			Threading.sleep(200);
			gameButton.getJButton().setForeground(c);
			Threading.sleep(500);
		}
	}

}
