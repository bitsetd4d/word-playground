package d3bug.tool.phonetic;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingConstants;

import d3bug.kidswing.FontManager;



import d3bug.poc.sounds.SoundFx;
import d3bug.poc.threads.UniverseRunnable;
import d3bug.poc.words.WordUtil;

public class PhoneticSpeakWordTask extends UniverseRunnable {

	private String text;
	private JLayeredPane parent;
	private JLabel jlabel;
	
	public PhoneticSpeakWordTask(JLayeredPane parent,String text) {
		this.parent = parent;
		this.text = text;
	}
	
	@Override
	public String getName() {
		return "Speak word phonetically";
	}

	@Override
	public void run() {
 		String[] elements = WordUtil.splitIntoElements(text);
 		setupElementShower();
 		int count = elements.length;
 		for (String x : elements) {
 			count--;
 			if (count == 0  && x.equalsIgnoreCase("y")) {
 				SoundFx.getInstance().playPhonic("ee");
 			} else {
 				SoundFx.getInstance().playPhonic(x);
 			}
 			showElement(x);
 			sleep(400);
  		}
 		removeElementShower();
	}
	
	
	private void setupElementShower() {
		int x = parent.getWidth() - 350;
		int y = parent.getHeight() - 270;
		jlabel = new JLabel();
		parent.add(jlabel,JLayeredPane.DRAG_LAYER);
		Font f = FontManager.getInstance().getComicFont(150);
		jlabel.setFont(f);
		jlabel.setHorizontalAlignment(SwingConstants.CENTER);
		jlabel.setForeground(Color.GREEN);
		jlabel.setLocation(x, y);
		jlabel.setSize(350, 250);
	}

	private void removeElementShower() {
		jlabel.setText("");
		parent.remove(jlabel);
		parent.repaint();
		jlabel = null;
	}
	
	private void showElement(String x) {
		jlabel.setText(x);
	}

}
