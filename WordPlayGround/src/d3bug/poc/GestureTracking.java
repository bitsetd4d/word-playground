package d3bug.poc;

import d3bug.poc.tools.ToolSprite;

public class GestureTracking {
	
	private static final int SHAKE_WINDOW = 300;
	
	private int lastX;
	private int lastY;
	private long lastChangeOfDirection;
	private int xchangedCount = 0;
	private int ychangedCount = 0;
	private int changedCount = 0;
	private boolean movedLeft;
	private boolean movedUp;
	
	private boolean shakeLeftRight;
	private boolean shakeUpDown;
	private boolean shakedAbout;
	
	public void seeMouse(int x,int y) {
		int movedX = x - lastX;
		int movedY = y - lastY;
		lastX = x;
		lastY = y;
		if (lastChangeOfDirection < System.currentTimeMillis() - SHAKE_WINDOW) {
			xchangedCount = 0;
			ychangedCount = 0;
			changedCount = 0;
		}	
		if (movedX > 2) {
			if (movedLeft) {
				movedLeft = false;
				xchangedCount++;
				lastChangeOfDirection = System.currentTimeMillis();
				System.out.println(">>>>");
			}
		} else if (movedX < -2) {
			if (!movedLeft) {
				movedLeft = true;
				xchangedCount++;
				lastChangeOfDirection = System.currentTimeMillis();
				System.out.println("<<<<<");
			}				
		}
		if (movedY > 2) {
			if (movedUp) {
				movedUp = false;
				ychangedCount++;
				lastChangeOfDirection = System.currentTimeMillis();
				System.out.println("VVVV");
			}
		} else if (movedY < -2) {
			if (!movedUp) {
				movedUp = true;
				ychangedCount++;
				lastChangeOfDirection = System.currentTimeMillis();
				System.out.println("^^^^");
			}				
		}

		if (xchangedCount == 5) {
			shakeLeftRight = true;
			xchangedCount++;
			System.out.println("!!!LR!!!");
		} else {
			shakeLeftRight = false;
		}

		if (ychangedCount == 5) {
			shakeUpDown = true;
			ychangedCount++;
			System.out.println("!!!up!!!");
		} else {
			shakeUpDown = false;
		}
		
		if (ychangedCount + xchangedCount == 10) {
			shakedAbout = true;
			ychangedCount++;
			xchangedCount++;
			System.out.println("!!@@!!");
		} else {
			shakedAbout = false;
		}
	}
	
	public boolean isShakeHorizontal() {
		return shakeLeftRight;
	}
	
	public boolean isShakeVertical() {
		return shakeUpDown;
	}
	
	public boolean isShakeAbout() {
		return shakedAbout;
	}


}
