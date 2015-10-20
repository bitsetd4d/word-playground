package d3bug.widgets.gridbutton.api;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JButton;

import d3bug.kidswing.AntiAliasedJButton;
import d3bug.kidswing.FontManager;

public class DefaultGameButton implements GameButton, ActionListener {
	
	private String value;
	private JButton button;
	private static int DEFAULT_FONT_SIZE = 56;
	private static int WRONG_FONT_SIZE = 26;
	private static Font BUTTON_FONT = FontManager.getInstance().getComicFont(DEFAULT_FONT_SIZE);
	private static Font WRONG_FONT = FontManager.getInstance().getComicFont(WRONG_FONT_SIZE);
	
	private List<GridButtonListener> buttonListeners = new CopyOnWriteArrayList<GridButtonListener>();
		
	public DefaultGameButton(String value) {
		this.value = value;
		button = new AntiAliasedJButton(value);
		button.setDefaultCapable(false);
		button.setFocusable(false);
		button.setFont(BUTTON_FONT);
		button.addActionListener(this);
	}
	
	public String getValue() {
		return value;
	}

//	public void addActionListener(ActionListener listener) {
//		button.addActionListener(listener);
//	}
//	
//	public void removeActionListener(ActionListener listener) {
//		button.removeActionListener(listener);
//	}

	public JButton getJButton() {
		return button;
	}

	public void setVisible(boolean visible) {
		button.setVisible(visible);
	}

	public void setWrongHint() {
		button.setFont(WRONG_FONT);
	}

	public void resetFont() {
		button.setFont(BUTTON_FONT);
	}

	public void adjustFontSize(int adjustment) {
		int newSize = DEFAULT_FONT_SIZE + adjustment;
		if (newSize < 1) return;
		Font f = FontManager.getInstance().getComicFont(newSize);
		button.setFont(f);
	}

	public void addListener(GridButtonListener listener) {
		buttonListeners.add(listener);
	}

	public void removeListener(GridButtonListener listener) {
		buttonListeners.remove(listener);
	}

	public void actionPerformed(ActionEvent e) {
		for (GridButtonListener l : buttonListeners) {
			l.onButtonPressed(this);
		}
	}


}
