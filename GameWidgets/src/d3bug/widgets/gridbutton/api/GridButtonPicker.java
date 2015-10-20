package d3bug.widgets.gridbutton.api;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import d3bug.widgets.gridbutton.internal.ConsoleGameCommentator;
import d3bug.widgets.gridbutton.internal.DropSubtleHintsManager;
import d3bug.widgets.gridbutton.internal.GridButtonController;
import d3bug.widgets.gridbutton.internal.ImageManager;
import d3bug.widgets.gridbutton.internal.SoundFx;
import d3bug.widgets.gridbutton.internal.SpeechGameCommentator;
import d3bug.widgets.gridbutton.internal.animation.ButtonAnimationService;


public class GridButtonPicker extends JFrame implements GridButtonListener {
	
	private JLabel imageLabel;
	private ImageIcon masterIcon;
	private double ratio;
	private Random random = new Random();
	private List<GridButtonListener> buttonListeners = new CopyOnWriteArrayList<GridButtonListener>();
	
	private GridButtonController controller = new GridButtonController();
	private ButtonAnimationService buttonAnimator = new ButtonAnimationService();
	
	private int rows = 2;
	private int cols = 4;
	private String[] elements = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11" };
	//private String[] elements = { "Bang", "Hurt", "Play", "Food", "Everyone", "Grab", "Sleep", "Fun" };
	
//	private static int ROWS = 1;
//	private static int COLS = 2;
//	private String[] elements = { "0", "1" };
	
	public GridButtonPicker() {
		ImageIcon icon = ImageManager.getInstance().getBackgroundImage();
		setBackgroundImqge(icon);
		addListeners();
	}
	
	private void addListeners() {
		controller.addListener(buttonAnimator);
		controller.addListener(new ConsoleGameCommentator());
		controller.addListener(new DropSubtleHintsManager(controller));
		controller.addListener(new SpeechGameCommentator());
		controller.addListener(new SoundFx());
		//controller.addListener(new GameOverManager());
	}

	public void setBackgroundImqge(ImageIcon icon) {
		masterIcon = icon;
	}
	
	public void addGridButtonListener(GridButtonListener listener) {
		buttonListeners.add(listener);
	}
	
	public void removeGridButtonListener(GridButtonListener listener) {
		buttonListeners.remove(listener);
	}
	

	public void open() { 
		randomiseElements();
	    Container c = getContentPane();
	    JLayeredPane lp = new JLayeredPane();
	    c.add(lp);
	    lp.setLayout(new GridBagLayout());
	    imageLabel = new JLabel();
	    //masterIcon = ImageManager.getInstance().getBackgroundImage();
		lp.add(imageLabel,getFillAreaConstraints());
		lp.moveToBack(imageLabel);
		int w = masterIcon.getIconWidth();
	    int h = masterIcon.getIconHeight();
	    ratio = (double)w/(double)h;
	    
	    setSize(900, 650);
	    setExtendedState(JFrame.MAXIMIZED_BOTH); 
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    addButtonsOnTop(lp);
	    setVisible(true);
	    fitImageToWindow();
	    controller.start();
	    
	    imageLabel.addComponentListener(new ComponentListener() {
			public void componentHidden(ComponentEvent ev) {
				System.out.println("Component hidden");
			}
			public void componentMoved(ComponentEvent ev) {
				System.out.println("Component moved");
			}
			public void componentResized(ComponentEvent ev) {
				System.out.println("Component resized");
				fitImageToWindow();
			}
			public void componentShown(ComponentEvent ev) {
				System.out.println("Component shown");
				fitImageToWindow();
			}
	    	
	    });
	    System.out.println("Image "+imageLabel.getWidth() + ", "+imageLabel.getHeight());
	    
	}
	
	private void randomiseElements() {
		ArrayList<String> x = new ArrayList<String>();
		for (String e : elements) {
			x.add(e);	
		}
		for (int i=0; i<elements.length; i++) {
			int randomIndex = random.nextInt(x.size());
			elements[i] = x.remove(randomIndex);
		}
	}

	private Object getFillAreaConstraints() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		return gbc;
	}

	private void addButtonsOnTop(JLayeredPane lp) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(rows,cols));
		int length = rows*cols;
		List<GameButton> buttons = controller.getGameButtons();
		int i = 1;
		for (GameButton b : buttons) {
			panel.add(b.getJButton());
			b.addListener(this);
			if (i++ > length) break;
		}
		panel.setOpaque(false);
		lp.add(panel,getFillAreaConstraints());
		lp.moveToFront(panel);
		lp.moveToBack(imageLabel);
	}



	private void fitImageToWindow() {
		Image im = masterIcon.getImage();
		int width = imageLabel.getWidth();
		int height = imageLabel.getHeight();
		if (width == 0 || height == 0) {
			return; 
		}
		Image newImage = newImagePreserveRatio(im,width,height,ratio);
		Icon oldIcon = imageLabel.getIcon();
		ImageIcon icon = new ImageIcon(newImage);
		imageLabel.setIcon(icon);
		if (oldIcon != null && oldIcon instanceof ImageIcon) {
			((ImageIcon)oldIcon).getImage().flush();
		}
		imageLabel.setSize(width, height);
	}
	
	
	private Image newImagePreserveRatio(Image im, int width, int height,double r) {
		int w = width;
		int h = height;
		int widthForHeight = (int)(height * r);
		int heightForWidth = (int)(width / r);
		/* Ensure image will fill area but preserve ratio */
		if (widthForHeight < width) {
			w = width;
			h = heightForWidth;
		} else if (heightForWidth < height) {
			w = widthForHeight;
			h = height;			
		}
		Image newImage = im.getScaledInstance(w, h, Image.SCALE_FAST);
		newImage.setAccelerationPriority(1);
		return newImage;
	}


	public void onButtonPressed(GameButton b) {
		for (GridButtonListener l : buttonListeners) {
			l.onButtonPressed(b);
		}
	}

	public void setElements(String... elements) {
		controller.setElements(elements);
	}

	public void setRowCol(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
	}

}
