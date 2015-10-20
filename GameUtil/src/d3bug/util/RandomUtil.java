package d3bug.util;

import java.util.Random;

public class RandomUtil {
	
	private static Random random = new Random();
	
	public static String chooseRandom(String[] list) {
		if (list.length == 0) return null;
		int chosen = random.nextInt(list.length);
		return list[chosen];
	}

}
