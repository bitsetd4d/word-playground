package d3bug.widgets.gridbutton.api;

import javax.swing.JButton;

public interface GameButton {

	JButton getJButton();
	void setVisible(boolean b);
	String getValue();
	
	void resetFont();
	void setWrongHint();
	void adjustFontSize(int i);
	
	void addListener(GridButtonListener listener);	
	void removeListener(GridButtonListener listener);

}
