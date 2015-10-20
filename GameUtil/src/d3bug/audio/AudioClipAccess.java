package d3bug.audio;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import d3bug.util.VM;


public class AudioClipAccess {
	
	private static AudioClipAccess INSTANCE = new AudioClipAccess();
	private Map<String,AudioClip> clips = new HashMap<String,AudioClip>();
	private static final String MEDIA_LOCATION = System.getProperty("media.location");
	
	public static AudioClipAccess getInstance() {
		return INSTANCE;
	}

	public AudioClip getAudioClip(String name) {
		URL url = getSoundUrl(name);
		AudioClip clip = clips.get(name);
		if (clip == null) {
			clip = Applet.newAudioClip(url);
			clips.put(name,clip);
		}
		return clip;
	}

	private URL getSoundUrl(String name) {
		Class cls = VM.getCaller(AudioClipAccess.class);
		URL url = cls.getResource("/sounds/" + name);
		if (url == null) {
			try {
				if (MEDIA_LOCATION == null) {
					return new URL("file:sounds" + File.separator + name);
				}
				return new URL("file:"+MEDIA_LOCATION+File.separator+"sounds" + File.separator + name);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		return url;		
	}
}
