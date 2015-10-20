package d3bug.os;

/*
 * Information about this computer
 */
public class ThisComputer {
	
	public static boolean isWindows() {
		return !isOSX();
	}
	
	public static boolean isOSX(){
		String lcOSName = System.getProperty("os.name").toLowerCase();
		return lcOSName.startsWith("mac os x");
	}

}
