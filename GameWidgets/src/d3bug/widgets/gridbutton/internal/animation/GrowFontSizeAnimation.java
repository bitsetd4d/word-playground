package d3bug.widgets.gridbutton.internal.animation;

import java.awt.Color;
import java.awt.Font;

import d3bug.kidswing.FontManager;
import d3bug.util.Threading;
import d3bug.widgets.gridbutton.api.GameButton;

public class GrowFontSizeAnimation implements GameAnimation {

	private GameButton gameButton;
	private int[] STEPS = { 70, 80, 90, 100, 110, 130, 150, 170 };
	private Color C1 = Color.BLUE;
	private Color C2 = Color.ORANGE;

	public GrowFontSizeAnimation(GameButton b) {
		this.gameButton = b;
	}
	
	public void run() {
		for (int i = 0; i<10; i++) {
			Color c = (i % 2 == 0) ? C1 : C2;
			gameButton.getJButton().setForeground(c);
			Threading.sleep(50);
		}
		gameButton.getJButton().setForeground(C1);
		for (int sz : STEPS) {
			Font f = FontManager.getInstance().getComicFont(sz);
			gameButton.getJButton().setFont(f);
			Threading.sleep(100);			
		}
		Threading.sleep(500);
		gameButton.setVisible(false);
	}

}
