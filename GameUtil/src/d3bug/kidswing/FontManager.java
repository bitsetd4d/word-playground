package d3bug.kidswing;

import java.awt.Font;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FontManager {
	
	private static FontManager INSTANCE = new FontManager();
	
	private static String FONT_FACE = "Comic Sans MS";	
	private Map<Integer,Font> fontsInUse = new ConcurrentHashMap<Integer, Font>();
	
	public static FontManager getInstance() {
		return INSTANCE;
	}
	
	public Font getComicFont(int size) {
		Font f = fontsInUse.get(size);
		if (f == null) {
			f = new Font(FONT_FACE,Font.TRUETYPE_FONT,size);
			fontsInUse.put(size, f);
		}
		return f;
	}
	

}
