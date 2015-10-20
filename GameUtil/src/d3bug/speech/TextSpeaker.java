package d3bug.speech;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;

import javax.swing.BoundedRangeModel;

public abstract class TextSpeaker {
	
	private static TextSpeaker INSTANCE = createInstance();
	private boolean boyNotGirl;

	public static TextSpeaker getInstance() {
		return INSTANCE;
	}
	
	private static TextSpeaker createInstance() {
		if (isOSX()) {
			return new MacSpeaker();
		}
		return new WindowsSpeaker();
	}
	
	private static boolean isOSX(){
		String lcOSName = System.getProperty("os.name").toLowerCase();
		return lcOSName.startsWith("mac os x");
	}
	
	abstract String getKey();
	
	public void say(String message) {
		System.out.println("Message is "+message);
		String result = substituteWords(message);
		System.out.println("Saying "+result);
		sayImpl(result,boyNotGirl);
	}
	
	abstract void sayImpl(String result,boolean boyVoice);
	
	private String substituteWords(String message) {
		Properties p = readProperties("wordsubstitutions-"+getKey()+".properties");
		if (p == null) {
			System.out.println("No wordsubstitutions file");
			return message;
		}
		String[] words = message.split("[ \\.?,]");
		for (String word: words) {
			String key = word.toLowerCase().trim();
			String substitute = p.getProperty(key);
			if (substitute != null) {
				System.out.println("Replacing "+word+" --> "+substitute);
				message = message.replaceAll(word.trim(),substitute);
			}
		}
		return message;
	}

	Process execute(String cmd) {	
		try {
			System.out.println("EXEC: "+cmd);
			return Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private int MAX_SPEECH_QUEUE = 2;
	private BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(MAX_SPEECH_QUEUE);
	private ThreadPoolExecutor pool = new ThreadPoolExecutor(1,1,0,TimeUnit.MILLISECONDS,workQueue,new DiscardOldestPolicy());
	
	protected void addToExecuteQueue(final String cmd) {
		pool.execute(new Runnable() {public void run() {
			Process p = execute(cmd);
			if (p == null) return;
			try {
				p.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}});
	}
			
	
	// ------------------------
	// Read properties
	// ------------------------
	private Properties readProperties(String fileName) {
		System.out.println("Reading filename "+fileName);
		Properties props = new Properties();
		InputStream is;
		try {
			is = new FileInputStream(fileName);
		} catch (IOException e) {
			System.out.println("File "+fileName+" not found");
			return null;
		}
		try {
			props.load(is);
			return props;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setBoyVoice(boolean boyNotGirl) {
		this.boyNotGirl = boyNotGirl;	
	}
	
}
