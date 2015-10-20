package d3bug.poc.launch;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import d3bug.graphics.FullScreenUtil;
import d3bug.graphics.ImageAccess;
import d3bug.poc.ResizingBackground;
import d3bug.poc.SpriteButtonUniverse;
import d3bug.poc.sounds.SoundFx;
import d3bug.poc.tasks.ShowLogo;
import d3bug.poc.threads.UniverseRunner;
import d3bug.poc.threads.UserActionQueue;
import d3bug.poc.tools.GameToolButton;
import d3bug.poc.tools.LetterCreatorTool;
import d3bug.poc.tools.LogLookTool;
import d3bug.poc.tools.ThreadControlToolButton;
import d3bug.poc.tools.VacuumToolButton;
import d3bug.poc.tools.WordMakerTool;
import d3bug.tool.phonetic.PhoneticToolButton;
import d3bug.tool.phonetic.SpellingScoreToolButton;
import d3bug.words.dictionary.DictionaryLookup;

public class SpriteButtonWindow extends JFrame {

	private JLayeredPane layeredPane;
	private SpriteButtonUniverse universe;
	private ResizingBackground background;
	private ImageIcon backgroundIcon;
	
	private static boolean DEBUG = false;
	
	public static void main(String[] args) {		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		DictionaryLookup.lookup("debug");
		SpriteButtonWindow x = new SpriteButtonWindow();
		x.setExtendedState(MAXIMIZED_BOTH);
//		x.setUndecorated(true);
		x.open();
		FullScreenUtil.fullScreenIfPossible(x);
		x.addStuff();
	}

	public void open() { 
	    Container c = getContentPane();
	    layeredPane = new JLayeredPane();
	    c.add(layeredPane);
	    backgroundIcon = getBackgroundImage();
	    background = new ResizingBackground(backgroundIcon,layeredPane);
	    universe = new SpriteButtonUniverse(layeredPane);
	    SoundFx.getInstance().soundStartup();
	        
	    setSize(900, 650);
	    setExtendedState(JFrame.MAXIMIZED_BOTH); 
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    addControlButtons();
	    setVisible(true);
	    UniverseRunner.getRunner().sheduleOnetime(new TimerTask() {
			@Override
			public void run() {
				universe.moveButtonsToLineUp();
			}	    	
	    }, 3000);
	}
		

	private ImageIcon getBackgroundImage() {
		//return ImageAccess.getInstance().getIcon("background_chipmunk.jpg");
		//return ImageAccess.getInstance().getIcon("background_in_the_news.jpg");
		return ImageAccess.getInstance().getIcon("background_egyptian.jpg");
	}

	private void addControlButtons() {
		try {
			JButton b = new JButton();
			b.setIcon(ImageAccess.getInstance().getIcon("home_button_image.gif"));
			b.setForeground(Color.MAGENTA);
			//b.setBorderPainted(false);
			b.addActionListener(new ActionListener() { 
				public void actionPerformed(ActionEvent e) {
					universe.moveButtonsToLineUp();
				}
			});
			b.setSize(75,75);
			b.setLocation(1,1);
			layeredPane.add(b,JLayeredPane.DRAG_LAYER);
			
			JButton b2 = new JButton();
			b2.setText("Undo");
			b2.setForeground(Color.MAGENTA);
			//b.setBorderPainted(false);
			b2.addActionListener(new ActionListener() { 
				public void actionPerformed(ActionEvent e) {
					UserActionQueue.getInstance().undo();
				}
			});
			b2.setSize(75,75);
			b2.setLocation(85,1);
			layeredPane.add(b2,JLayeredPane.DRAG_LAYER);
			
			JButton b3 = new JButton();
			b3.setText("Redo");
			b3.setForeground(Color.MAGENTA);
			//b.setBorderPainted(false);
			b3.addActionListener(new ActionListener() { 
				public void actionPerformed(ActionEvent e) {
					UserActionQueue.getInstance().redo();
				}
			});
			b3.setSize(75,75);
			b3.setLocation(85 + 85,1);
			layeredPane.add(b3,JLayeredPane.DRAG_LAYER);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addStuff() {		
		new GameToolButton(universe);
		new LetterCreatorTool(universe);
		new WordMakerTool(universe);
		new VacuumToolButton(universe);
		new PhoneticToolButton(layeredPane,universe);
		new SpellingScoreToolButton(universe);
		if (DEBUG) {
			new ThreadControlToolButton(universe,layeredPane);
			new LogLookTool(universe,layeredPane);
		}
		UniverseRunner.getRunner().execute(new ShowLogo(layeredPane));
		
	}
	
}
