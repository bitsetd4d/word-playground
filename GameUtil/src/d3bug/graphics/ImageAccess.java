package d3bug.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import d3bug.util.VM;

public class ImageAccess {
	
	private static ImageAccess INSTANCE = new ImageAccess();
	private Map<String,ImageIcon> icons = new HashMap<String,ImageIcon>();
	
	private static final String MEDIA_LOCATION = System.getProperty("media.location");
	
	public static ImageAccess getInstance() {
		return INSTANCE;
	}
	
	public ImageIcon getIcon(String name) {
		ImageIcon i = icons.get(name);
		if (i == null) {
			i = createIcon(name);
			if (i != null) icons.put(name, i);
		}
		return i;
	}

	private ImageIcon createIcon(String name) {
		try {
			URL url = getImageUrl(name);
			InputStream is = url.openStream();
			BufferedImage i = ImageIO.read(is);
			is.close();
			ImageIcon icon = new ImageIcon(i);
			return icon;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private URL getImageUrl(String name) {
		Class cls = VM.getCaller(ImageAccess.class);
		URL url = cls.getResource("/images/" + name);
		if (url == null) {
			try {
				if (MEDIA_LOCATION == null) {
					return new URL("file:images" + File.separator + name);
				}
				return new URL("file:"+MEDIA_LOCATION+File.separator+"images" + File.separator + name);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		return url;		
	}

}
