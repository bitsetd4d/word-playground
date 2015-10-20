package d3bug.widgets.gridbutton.api;

import javax.swing.ImageIcon;

import d3bug.widgets.gridbutton.internal.ImageManager;

public class Main {
	
//	private JLabel imageLabel;
//	private ImageIcon masterIcon;
//	private double ratio;
	private GridButtonPicker window;
	
//	private GameButtonContainer gameButtonContainer = new GameButtonContainer();
	
//	private static int ROWS = 2;
//	private static int COLS = 4;
	//private String[] elements = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11" };
//	private String[] elements = { "Bang", "Hurt", "Play", "Food", "Everyone", "Grab", "Sleep", "Fun" };
	
//	private static int ROWS = 1;
//	private static int COLS = 2;
//	private String[] elements = { "0", "1" };

	public static void main(String[] args) {
		Main w = new Main();
		w.go();
	}

	private void go() {
		window = new GridButtonPicker();
		window.open();
	}

//	private void registerObjects() {
//		Bus.registerConsumer(new ConsoleGameCommentator());
//		Bus.registerConsumer(new GameOverManager(this));
//		Bus.registerConsumer(new PlayerHintsManager(gameButtonContainer));
//		Bus.registerConsumer(new CorrectAnswerManager());
//		Bus.registerConsumer(new SpeechGameCommentator());
//		Bus.registerConsumer(new ButtonAnimationService());
//		Bus.registerConsumer(new SoundFx());
//	}
	
//	private Object getFillAreaConstraints() {
//		GridBagConstraints gbc = new GridBagConstraints();
//		gbc.anchor = GridBagConstraints.NORTH;
//		gbc.fill = GridBagConstraints.BOTH;
//		gbc.gridx = 0;
//		gbc.gridy = 0;
//		gbc.weightx = 1.0;
//		gbc.weighty = 1.0;
//		return gbc;
//	}
//
//	private void addButtonsOnTop(JLayeredPane lp) {
//		JPanel panel = new JPanel();
//		panel.setLayout(new GridLayout(ROWS,COLS));
//		int length = ROWS*COLS;
//		for (int i=0; i<length; i++) {
//			String key = elements[i];
//			final GameButton b = gameButtonContainer.createGameButton(key);
//			panel.add(b.getJButton());
//		}
//		panel.setOpaque(false);
//		lp.add(panel,getFillAreaConstraints());
//		lp.moveToFront(panel);
//		lp.moveToBack(imageLabel);
//	}



//	private void fitImageToWindow() {
//		Image im = masterIcon.getImage();
//		int width = imageLabel.getWidth();
//		int height = imageLabel.getHeight();
//		if (width == 0 || height == 0) {
//			return; 
//		}
//		Image newImage = newImagePreserveRatio(im,width,height,ratio);
//		Icon oldIcon = imageLabel.getIcon();
//		ImageIcon icon = new ImageIcon(newImage);
//		imageLabel.setIcon(icon);
//		if (oldIcon != null && oldIcon instanceof ImageIcon) {
//		 ((ImageIcon)oldIcon).getImage().flush();
//		}
//		imageLabel.setSize(width, height);
//	}
	
	
//	private Image newImagePreserveRatio(Image im, int width, int height,double r) {
//		int w = width;
//		int h = height;
//		int widthForHeight = (int)(height * r);
//		int heightForWidth = (int)(width / r);
//		/* Ensure image will fill area but preserve ratio */
//		if (widthForHeight < width) {
//			w = width;
//			h = heightForWidth;
//		} else if (heightForWidth < height) {
//			w = widthForHeight;
//			h = height;			
//		}
//		Image newImage = im.getScaledInstance(w, h, Image.SCALE_FAST);
//		newImage.setAccelerationPriority(1);
//		return newImage;
//	}
	
//	private ImageIcon loadImage() {
//		String imageFileName = "/Documents and Settings/Paul/My Documents/ETS/performance/workspace/KidsHearAndClickTrainer/images/P1010490.JPG";
//		if (ThisComputer.isOSX()) {
//			imageFileName = "/Users/paul/Documents/workspace/KidsHearAndClickTrainer/images/P1010490.JPG";
//		}
//		return new ImageIcon(imageFileName);
//	}
	
}
