package d3bug.util;

public class FileNameUtils {
	
	public static String getHumanNameFromFileName(String fileName) {
		String fn = fileName.toLowerCase();
		if (!fn.endsWith(".avi") && !fn.endsWith(".iso")) return fileName;
		String name = fn.substring(0,fn.length()-4);
		name = name.replace('_', ' ');
		return name;
	}
	
	public static String getFileExtensionFromName(String name) {
		int i = name.lastIndexOf('.');
		if (i <= 0 || i == name.length() - 1) return "";
		return name.substring(i+1).toLowerCase();
	}

}
