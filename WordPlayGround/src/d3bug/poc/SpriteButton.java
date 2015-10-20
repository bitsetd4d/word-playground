package d3bug.poc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.List;

public interface SpriteButton {

	String getText();
	void setText(String text);

	int getX();
	int getY();
	int getWidth();
	int getHeight();
	void setSize(int width, int height);
	Rectangle getBounds();
	void setLocation(int x, int y);
	
	void setForeground(Color blue);
	void setFont(Font font);
	boolean isVisible();

	void addCallback(SpriteStationaryCallback waiter);
	boolean inMotion();
	void setInMotion(boolean b);

	Component getComponent();
	void setListener(SpriteMouseListener listener);
	
	void mergeOnRightAndBecome(SpriteButton sourceButton);
	void mergeOnLeftAndBecome(SpriteButton sourceButton);

	void expand(List<SpriteButton> newButtons);
	void explode(List<SpriteButton> newButtons);
	void flyTo(int i, int y, int j);

	void touched();
	long getCreated();
	void destroySprite();
	void unDestroySprite();
	void setDestroyed(boolean b);
	
	void setVisible(boolean b);
	void unregisterListener();
	
	Memento getMemento();
	void setFromMemento(Memento m);
	long getLastClicked();
	void clicked();
	
}
