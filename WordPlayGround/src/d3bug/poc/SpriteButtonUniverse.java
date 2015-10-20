package d3bug.poc;

import java.awt.Font;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JLayeredPane;

import d3bug.kidswing.FontManager;
import d3bug.poc.actions.MoveButtonsToLineupAction;
import d3bug.poc.sounds.SoundFx;
import d3bug.poc.tasks.FlyToFocusPositionTask;
import d3bug.poc.threads.UniverseRunnable;
import d3bug.poc.threads.UniverseRunner;
import d3bug.poc.threads.UserAction;
import d3bug.poc.threads.UserActionQueue;
import d3bug.poc.tools.ToolButton;
import d3bug.poc.tools.ToolSprite;

public class SpriteButtonUniverse {
	
	private JLayeredPane layeredPane;

	private static final int CUTOFF_PRESSURE = 50;
	private static final int WIDTH = 70;
	private static final int HEIGHT = 70;
	private static final Font FONT = FontManager.getInstance().getComicFont(20);
	private static final Random random = new Random();
	
	//private Timer timer = new Timer("DragButtonGroupTimer");
	private static int COLLISION_INTERVAL = 200;
	private static int DOUBLE_CLICK_INTERVAL = 500;
	
	private int clickedX;
	private int clickedY;
	
	private List<SpriteButton> toolButtons = new CopyOnWriteArrayList<SpriteButton>();
	private List<SpriteButton> buttons = new CopyOnWriteArrayList<SpriteButton>();
	private List<SpriteButton> allButtons = new CopyOnWriteArrayList<SpriteButton>();
	private List<UniverseListener> listeners = new CopyOnWriteArrayList<UniverseListener>();
	
	private int jobid;
	private UniverseRunner runner = UniverseRunner.getRunner();

	public SpriteButtonUniverse(JLayeredPane layeredPane) {
		this.layeredPane = layeredPane;
		scheduleUncollideTask();
		addKeyboardListener();
	}
	
	public SpriteButton createButton(String text) {
		SpriteButton b1 = new SpriteButtonButton(this);
		b1.setText(text);
		b1.setSize(WIDTH,HEIGHT);
		ButtonMouseMotionListener l = new ButtonMouseMotionListener(b1);
		b1.setListener(l);
		b1.setFont(FONT);
		//b1.setBorderPainted(false);
		buttons.add(b1);
		allButtons.add(b1);
		layeredPane.add(b1.getComponent(),JLayeredPane.DEFAULT_LAYER);
		for (UniverseListener listener : listeners) {
			listener.onCreated(b1);
		}
		return b1;
	}
	
	public void destroyButton(SpriteButton button) {
		button.setVisible(false);
		button.unregisterListener();
		button.setDestroyed(true);
		buttons.remove(button);
		allButtons.remove(button);
		layeredPane.remove(button.getComponent());
		System.out.println("Destroyed button "+button.getText());
		for (UniverseListener l : listeners) {
			l.onDestroyed(button);
		}
	}
	
	public void undestroyButton(SpriteButton button) {
		button.setVisible(true);
		button.unregisterListener();
		ButtonMouseMotionListener l = new ButtonMouseMotionListener(button);
		button.setListener(l);
		buttons.add(button);
		allButtons.add(button);
		layeredPane.add(button.getComponent(),JLayeredPane.DEFAULT_LAYER);
		button.setDestroyed(false);
		for (UniverseListener listener : listeners) {
			listener.onCreated(button);
		}		
	}

	
	public void registerToolButton(ToolButton button) {
		ButtonMouseMotionListener l = new ButtonMouseMotionListener(button);
		button.setListener(l);
		button.setFont(FONT);
		toolButtons.add(button);
		allButtons.add(button);
		layeredPane.add(button.getComponent(),JLayeredPane.DRAG_LAYER);
		button.setSize(WIDTH,HEIGHT);
		int lpw = layeredPane.getWidth()/3;
		int lph = layeredPane.getHeight()/3;
		if (lpw == 0) lpw = 200;
		if (lph == 0) lph = 200;
		int randomx = lpw + random.nextInt(lpw);
		int randomy = lph + random.nextInt(lph);
		button.setLocation(randomx, randomy);
	}
	
	public void addListener(UniverseListener l) {
		listeners.add(l);
	}
	
	public void removeListener(UniverseListener l) {
		listeners.remove(l);
	}
	
	private void addKeyboardListener() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
			public boolean dispatchKeyEvent(KeyEvent e) {
				String key = String.valueOf(e.getKeyChar());
				if (e.getID() ==  KeyEvent.KEY_RELEASED) {
// Doesn't work on OSX
//					boolean capslock = java.awt.Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
//					if (capslock) {
//						//java.awt.Toolkit.getDefaultToolkit().setLockingKeyState(KeyEvent.VK_CAPS_LOCK,false);
//						key = key.toLowerCase();
//					}
					for (UniverseListener l : listeners) {
						l.onKeyPressed(key);
					}
				}
				if (key.equals(" ")) return true;
				return false;
			}
		});
	}

	
	// ----------------------------------------------------------------
	// Manage buttons
	// ----------------------------------------------------------------
	public class ButtonMouseMotionListener implements SpriteMouseListener {
		
		private boolean down;
		private boolean in;
		private SpriteButton myButton;
		private Memento lastMoved;
		
		public ButtonMouseMotionListener(SpriteButton button) {
			this.myButton = button;
		}
		
		private boolean doubleClickCheck() {
			long lastClicked = myButton.getLastClicked();
			myButton.clicked();
			long now = System.currentTimeMillis();
			boolean doubleClick = (now - lastClicked < DOUBLE_CLICK_INTERVAL);
			return doubleClick;
		}

		public void mouseClicked(MouseEvent e) {
			System.out.println("Mouse Clicked");
			down = true;
			lastMoved = myButton.getMemento();
		}

		public void mouseEntered(MouseEvent e) {
			System.out.println("Mouse Entered");
			in = true;
		}

		public void mouseExited(MouseEvent e) {
			System.out.println("Mouse Exited");
			in = false;
		}

		public void mousePressed(MouseEvent e) {
			System.out.println("Mouse Pressed");
			in = true;
			down = true;
			lastMoved = myButton.getMemento();
			clickedX = e.getX();
			clickedY = e.getY();
			myButton.touched();
			boolean doubleClick = doubleClickCheck();
			if (doubleClick) {
				if (myButton instanceof ToolSprite) {
					((ToolSprite)myButton).onToolDoubleClick();
				} else {
					SoundFx.getInstance().soundDoubleClick();
					for (UniverseListener l : listeners) {
						l.onDoubleClick(myButton);
					}
				}
				return;
			}
			SoundFx.getInstance().soundClick();
			if (myButton instanceof ToolSprite) {
				((ToolSprite)myButton).onToolPressed();
			} else {
				for (UniverseListener l : listeners) {
					l.onSingleClick(myButton);
				}				
			}
		}

		public void mouseReleased(MouseEvent e) {
			System.out.println("Mouse Released");
			in = false;
			down = false;
			if (myButton instanceof ToolSprite) {
				((ToolSprite)myButton).onToolReleased();
			}
//			final int x = myButton.getX();
//			final int y = myButton.getY();
//			UserActionQueue.getInstance().queue(new UserAction() {
//				@Override
//				public void run() {
//					myButton.setLocation(x, y);
//				}
//
//				@Override
//				public void undo() {
//					myButton.setFromMemento(lastMoved);
//				}
//				
//			});

		}
		
		// MouseMotionListener
		
		private GestureTracking dragGestureTracking = new GestureTracking();
		
		public void mouseDragged(MouseEvent e) {
			int deltax = e.getX() - clickedX;
			int deltay = e.getY() - clickedY;
			int newX1 = myButton.getX() + deltax;
			int newY1 = myButton.getY() + deltay;
			dragGestureTracking.seeMouse(newX1,newY1);
			if (dragGestureTracking.isShakeHorizontal()) {
				if (myButton instanceof ToolSprite) {
					((ToolSprite)myButton).onToolHorizontalShake();
				} else {	
					for (UniverseListener l : listeners) {
						l.onButtonHorizontalShaked(myButton);
					}
				}
				return;
			}
			Rectangle sourceRect = new Rectangle(newX1,newY1,myButton.getWidth(),myButton.getHeight());
			int LR = 0,RL = 0,TB = 0,BT = 0;
			int adjust_x = 0;
			int adjust_y = 0;
			int pressure = 0;
			double faceContact = 0;
			Edge edge = null;
			for (SpriteButton otherButton : buttons) {
				if (myButton == otherButton) continue;
				if (!otherButton.isVisible()) continue;
 				Rectangle otherRect = otherButton.getBounds();
 				if (otherRect.intersects(sourceRect)) {
					LR = otherRect.x + otherRect.width - sourceRect.x;
					TB = otherRect.y + otherRect.height - sourceRect.y;
					RL = otherRect.x - (sourceRect.x + sourceRect.width);
					BT = otherRect.y - (sourceRect.y + sourceRect.height);
					int smallest = calculateSmallest(LR,TB,RL,BT);
					if (Math.abs(LR) == smallest) {
						adjust_x = LR;
						edge = Edge.RIGHT;
						faceContact = calcOverlapPercentage(sourceRect.y,otherRect.y,sourceRect.height,otherRect.height);
					} else if (Math.abs(RL) == smallest) {
						adjust_x = RL;
						edge = Edge.LEFT;
						faceContact = calcOverlapPercentage(sourceRect.y,otherRect.y,sourceRect.height,otherRect.height);
					} else if (Math.abs(TB) == smallest) {
						adjust_y = TB;
						edge = Edge.BOTTOM;
						faceContact = calcOverlapPercentage(sourceRect.x,otherRect.x,sourceRect.width,otherRect.width);
					} else {
						adjust_y = BT;
						edge = Edge.TOP;
						faceContact = calcOverlapPercentage(sourceRect.x,otherRect.x,sourceRect.width,otherRect.width);
					}
					if (LR < TB) {
						TB = 0;
					} else {
						LR = 0;
					}
					pressure = smallest;
					if (myButton.isVisible()) {
						System.out.println("Pressure: "+pressure+" ... contact: "+faceContact);
						boolean sourceTool = myButton instanceof ToolSprite;
						boolean otherTool = otherButton instanceof ToolSprite;
						
						if (sourceTool && otherTool) {
							
						} else if (sourceTool) {
							((ToolSprite)myButton).onToolPressedAgainst(otherButton, pressure, edge);
						} else if (otherTool) {
							((ToolSprite)myButton).onToolTouchedBy(myButton, pressure, edge);
						} else {
							for (UniverseListener l : listeners) {
								l.onPressedAgainst(myButton, otherButton, pressure, edge);
							}							
						}
					}
				}
			}
			if (pressure < CUTOFF_PRESSURE) {
				myButton.setLocation(newX1 + adjust_x, newY1 + adjust_y); 
			}
		}

		private double calcOverlapPercentage(int p1, int p2, int height1,int height2) {
//			int top = 0;
//			int bottom = 0;
//			if (p1 > p2) {
//				top = p1;
//				bottom = p2 + height1;
//			} else  {
//				top = p2;
//				bottom = p1 + height2;							
//			}
//			return (double)(bottom - top) / height1 ;
			return 1.0; // TODO
		}

		private int calculateSmallest(int a, int b, int c, int d) {
			int a0 = Math.abs(a);
			int b0 = Math.abs(b);
			int c0 = Math.abs(c);
			int d0 = Math.abs(d);
			int x = Math.min(a0, b0);
			int y = Math.min(c0, d0);
			return Math.min(x,y);
		}

		private GestureTracking moveGestureTracking = new GestureTracking();
		public void mouseMoved(MouseEvent e) {
			moveGestureTracking.seeMouse(e.getX(), e.getY());
		}

	}
	
	public void moveButtonsToLineUp() {
		UserActionQueue.getInstance().run(new MoveButtonsToLineupAction(allButtons,toolButtons,layeredPane));
	}
	
	private void scheduleUncollideTask() {
		TimerTask t = new TimerTask() {
			@Override
			public void run() {
				final List<SpriteButton> tweakedButtons = new ArrayList<SpriteButton>();
				final List<Integer> xdrift = new ArrayList<Integer>();
				final List<Integer> ydrift = new ArrayList<Integer>();
				for (SpriteButton thisButton : buttons) {
					if (tweakedButtons.contains(thisButton)) continue;
					for (SpriteButton thatButton : buttons) {
						if (thisButton == thatButton) continue;
						if (tweakedButtons.contains(thatButton)) continue;
						Rectangle thisRect = thisButton.getBounds();
						Rectangle thatRect = thatButton.getBounds();
						
						if (thisRect.intersects(thatRect)) {
							int Z = 1;
							SpriteButton current;
							if (random.nextBoolean()) {
								tweakedButtons.add(thisButton);
								current = thisButton;
							} else {
								tweakedButtons.add(thatButton);
								current = thatButton;
								Z = -1;
							}
							int dx=0,dy=0;
							if (thatRect.contains(thisRect.x,thisRect.y) || thatRect.contains(thisRect.x,thisRect.y + thisRect.height)) {
								dx = Z;
							} else {
								dx = -Z;
							}					

							if (thatRect.contains(thisRect.x,thisRect.y) || thatRect.contains(thisRect.x + thisRect.width,thisRect.y)) {
								dy = Z;
							} else {
								dy = -Z;
							}
							if (current.getX() < 0) {
								dx = 1;
							}
							if (current.getX() > layeredPane.getWidth()) {
								dx = -1;
							}
							if (current.getY() < 0) {
								dy = 1;
							}
							if (current.getY() > layeredPane.getHeight()) {
								dy = -1;
							}
							xdrift.add(dx);
							ydrift.add(dy);
						}
					}
				}
				for (int i=0;i<10; i++) {
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {}
					int k = 0;
					for (SpriteButton b : tweakedButtons) {
						if (!b.inMotion()) {
							int x = b.getX() + random.nextInt(4) * xdrift.get(k);
							int y = b.getY() + random.nextInt(3) * ydrift.get(k);
							b.setLocation(x,y);
						}
						k++;
					}
				}
			}
		};
		runner.sheduleWhenNotBusy(t,0,COLLISION_INTERVAL);
	}
	
	public void shakeButtonNow(SpriteButton b, int xshake, int yshake, int iterations, int pause) {
		int x = b.getX();
		int y = b.getY();
		for (int i=0; i<iterations; i++) {
			int dx = random.nextInt(xshake);
			int dy = random.nextInt(yshake);
			if (random.nextBoolean()) dx = -dx;
			if (random.nextBoolean()) dy = -dy;
			b.setLocation(x + dx, y + dy);
			try {
				Thread.sleep(pause);
			} catch (InterruptedException e) {}
		}		
	}
	
	public void shakeButtonsNow(int xshake, int yshake, int iterations, int pause,SpriteButtonButton...buttons) {
		int[] x = new int[buttons.length];
		int[] y = new int[buttons.length];
		int i=0;
		for (SpriteButtonButton b : buttons) {
			x[i] = b.getX();
			y[i] = b.getY();
			i++;
		}
		for (i=0; i<iterations; i++) {
			int j = 0;
			for (SpriteButtonButton b : buttons) {
				int dx = random.nextInt(xshake);
				int dy = random.nextInt(yshake);
				if (random.nextBoolean()) dx = -dx;
				if (random.nextBoolean()) dy = -dy;
				b.setLocation(x[j] + dx, y[j] + dy);
				try {
					Thread.sleep(pause);
				} catch (InterruptedException e) {}
				j++;
			}
		}		
	}

	public void flyToFocusPosition(final SpriteButton b) {
		execute(new FlyToFocusPositionTask(b,allButtons,layeredPane));
	}
	
	void execute(UniverseRunnable r) {
		runner.execute(r);
	}
	void executeNow(UniverseRunnable r) {
		runner.executeNow(r);
	}

	public List<SpriteButton> getAllSpriteButtons() {
		return new ArrayList<SpriteButton>(buttons);
	}

	public Rectangle getVisibleArea() {
		return layeredPane.getBounds();
	}

	public SpriteButton getButtonAt(int x, int y) {
		for (SpriteButton b : buttons) {
			if (b.getBounds().contains(x,y)) return b;
		}
		return null;
	}
	
	public void notifyWordCreated(String word) {
		for (UniverseListener listener : listeners) {
			listener.onWordCreated(word);
		}		
	}

}
