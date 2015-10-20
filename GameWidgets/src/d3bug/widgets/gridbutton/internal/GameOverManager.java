package d3bug.widgets.gridbutton.internal;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;

import javax.swing.JFrame;
import javax.swing.JPanel;

import d3bug.widgets.gridbutton.api.GameButton;
import d3bug.widgets.gridbutton.api.PlayerMoveListener;

public class GameOverManager implements PlayerMoveListener {
	
	private JFrame window;
	
	public GameOverManager(JFrame window) {
		this.window = window;
	}

	public void onCorrectButtonSelected(GameButton b) {}
	public void onCurrentTargetSet(GameButton b) {}
	public void onWrongButtonSelected(GameButton b) {}


	public void onPlayerWins() {
		doGameOver();
		
	}

	private void doGameOver() {
		// TODO
		System.out.println("Game Over Manager says: GAME OVER!");
	}
	
	private class NewGamePanel extends JPanel {

		private Composite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.6f);
		
		public NewGamePanel() {
			super(false);
		}

		public NewGamePanel(boolean isDoubleBuffered) {
			super(isDoubleBuffered);
		}

		public NewGamePanel(LayoutManager layout, boolean isDoubleBuffered) {
			super(layout, isDoubleBuffered);
		}

		public NewGamePanel(LayoutManager layout) {
			super(layout);
		}
		
		protected void paintComponent(Graphics g) {
			Graphics scratchGraphics = (g == null) ? null : g.create();
			Graphics2D g2d = (Graphics2D) scratchGraphics;
			g2d.setComposite(alphaComposite);
			super.paintComponent(scratchGraphics);
		}				
		
	}

}
