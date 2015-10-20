package d3bug.poc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.Icon;
import javax.swing.JButton;

import d3bug.poc.SpriteButtonUniverse.ButtonMouseMotionListener;
import d3bug.poc.tasks.ExpandButtonTask;
import d3bug.poc.tasks.ExplodeButtonTask;
import d3bug.poc.tasks.FlyButtonToLocationTask;
import d3bug.poc.tasks.MergeOnLeftAndBecomeTask;
import d3bug.poc.tasks.MergeOnRightAndBecomeTask;

public class SpriteButtonButton implements SpriteButton {

	private String name;
	protected SpriteButtonUniverse universe;
	private SpriteMouseListener listener;
	private long lastClicked;
	private long created;
	private long lastTouched;
	private boolean inMotion;
	private JButton jbutton;
	private boolean destroyed;

	public SpriteButtonButton(SpriteButtonUniverse group) {
		super();
		created = System.currentTimeMillis();
		this.universe = group;
		jbutton = new JButton();
	}
	
	public void setListener(SpriteMouseListener listener) {
		unregisterListener();
		this.listener = listener;
		jbutton.addMouseListener(listener);
		jbutton.addMouseMotionListener(listener);
	}
	
	public void unregisterListener() {
		if (listener != null) {
			jbutton.removeMouseListener(listener);
			jbutton.removeMouseMotionListener(listener);
		}
	}
	
	public void clicked() {
		lastClicked = System.currentTimeMillis();
		lastTouched = lastClicked;
	}
	
	public long getLastClicked() {
		return lastClicked;
	}
	
	public long getCreated() {
		return created;
	}
	
	public void touched() {
		lastTouched = System.currentTimeMillis();
	}

	public long getLastTouched() {
		return lastTouched;
	}
	
	public synchronized boolean inMotion() {
		return inMotion;
	}
	
	public synchronized void setInMotion(boolean newInMotion) {
		if (inMotion && !newInMotion) {
			for (SpriteStationaryCallback c : callbacks) {
				c.onStationary(this);
			}
			callbacks.clear();
		}
		this.inMotion = newInMotion;
	}
	
	private List<SpriteStationaryCallback> callbacks = new CopyOnWriteArrayList<SpriteStationaryCallback>();
	public synchronized void addCallback(SpriteStationaryCallback c) {
		if (inMotion) {
			callbacks.add(c);
		} else {
			c.onStationary(this);
		}
	}

	// otherButton --> (this button)
	public void mergeOnRightAndBecome(SpriteButton otherButton) {
		universe.executeNow(new MergeOnRightAndBecomeTask(universe,this,otherButton));
	}
	//
	// (this button) <-- otherButton
	public void mergeOnLeftAndBecome(SpriteButton otherButton) {
		universe.executeNow(new MergeOnLeftAndBecomeTask(universe,this,otherButton));
	}
	
	public void flyTo(int x,int y) {
		flyTo(x,y,25);
	}
	
	public void flyTo(int x,int y,final int steps) {
		System.out.println("Flying "+getText()+" to "+x+","+y);
		universe.execute(new FlyButtonToLocationTask(this,x,y,steps,10));		
	}
	
	public void expand(final List<SpriteButton> newButtons) {
		universe.execute(new ExpandButtonTask(this,newButtons));
	}
		
	public void explode(final List<SpriteButton> newButtons) {
		universe.execute(new ExplodeButtonTask(this,newButtons));
	}

	public void destroySprite() {
		universe.destroyButton(this);
	}
	
	public void unDestroySprite() {
		universe.undestroyButton(this);
	}
	
	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}
	
	public String toString() {
		return "SpriteButton "+getText();
	}

	public Component getComponent() {
		return jbutton;
	}

	public Rectangle getBounds() {
		return jbutton.getBounds();
	}

	public int getHeight() {
		return jbutton.getHeight();
	}

	public String getText() {
		return jbutton.getText();
	}

	public int getWidth() {
		return jbutton.getWidth();
	}

	public int getX() {
		return jbutton.getX();
	}

	public int getY() {
		return jbutton.getY();
	}

	public boolean isVisible() {
		return jbutton.isVisible();
	}

	public void setFont(Font font) {
		jbutton.setFont(font);
	}

	public void setForeground(Color c) {
		jbutton.setForeground(c);
	}

	public void setLocation(int x, int y) {
		jbutton.setLocation(x,y);
	}

	public void setSize(int width, int height) {
		jbutton.setSize(width, height);
	}

	public void setText(String text) {
		jbutton.setText(text);
	}

	public void setVisible(boolean b) {
		jbutton.setVisible(b);
	}
	
	public void setIcon(Icon i) {
		jbutton.setIcon(i);
	}

	public Memento getMemento() {
		ButtonMemento bm = new ButtonMemento();
		bm.bounds = jbutton.getBounds();
		bm.text = jbutton.getText();
		bm.visible = jbutton.isVisible();
		bm.destroyed = destroyed;
		return bm;
	}

	public void setFromMemento(Memento m) {
		ButtonMemento bm = (ButtonMemento)m;
		jbutton.setBounds(bm.bounds);
		jbutton.setText(bm.text);
		jbutton.setVisible(bm.visible);
		if (bm.destroyed && !destroyed) {
			destroySprite();
		} else if (!bm.destroyed && destroyed) {
			unDestroySprite();
		}
	}
	
	private class ButtonMemento implements Memento {
		private Rectangle bounds;
		private String text;
		private boolean visible;
		private boolean destroyed;
	}
	

	
}
