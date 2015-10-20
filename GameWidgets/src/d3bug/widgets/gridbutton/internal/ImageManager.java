package d3bug.widgets.gridbutton.internal;

import java.io.File;
import java.io.FilenameFilter;

import javax.swing.ImageIcon;

import d3bug.os.ThisComputer;
import d3bug.util.RandomUtil;

public class ImageManager {
	
	private static ImageManager INSTANCE = new ImageManager();
	
	public static ImageManager getInstance() {
		return INSTANCE;
	}
	
	private String getRootDirectoryPath() {
		return "WordPlayground/images";
	}
	
	public ImageIcon getBackgroundImage() {
		String rootDir = getRootDirectoryPath();
		File dir = new File(rootDir);
		String[] names = dir.list(new GraphicsFilesOnlyFilter());
		String chosen = RandomUtil.chooseRandom(names);
		return new ImageIcon(rootDir + File.separatorChar + chosen);
	}	
	
	
	private class GraphicsFilesOnlyFilter implements FilenameFilter {

		public boolean accept(File dir, String fileName) {
			String name = fileName.toLowerCase();
			if (name.endsWith(".jpg")) return true;
			if (name.endsWith(".png")) return true;
			if (name.endsWith(".gif")) return true;
			return false;
		}
		
	}

}
