package d3bug.widgets.gridbutton.internal.animation;

import java.awt.Dimension;

import javax.swing.JButton;

import d3bug.widgets.gridbutton.api.GameButton;

/* 
 * This is pretty lame!
 */
public class ShrinkButtonAnimation implements GameAnimation {
		
	private GameButton gameButton;
	private int interval = 50;
	private int steps = 10;

	public ShrinkButtonAnimation(GameButton b) {
		this.gameButton = b;
	}
	
	public int getInterval() {
		return interval;
	}
	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getSteps() {
		return steps;
	}
	public void setSteps(int steps) {
		this.steps = steps;
	}

	public void run() {
		JButton jb = gameButton.getJButton();
		Dimension d = jb.getSize();
		for (float i=1.0f; i>0; i -= 0.1) {
			int w = (int)(d.getWidth() * i);
			int h = (int)(d.getHeight() * i);
			jb.setSize(w,h);
			try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		jb.setVisible(false);
	}

}
