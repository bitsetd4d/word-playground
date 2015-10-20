package d3bug.util;

public class Threading {

	public static void sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {}		
	}

}
