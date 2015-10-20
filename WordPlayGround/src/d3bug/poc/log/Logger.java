package d3bug.poc.log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Logger {
	
	private static List<LogListener> listeners = new CopyOnWriteArrayList<LogListener>();
	
	public static void info(Object source,String message) {
		for (LogListener l : listeners) {
			l.onInfo(source, message);
		}
		System.out.println("LOG: "+message);
	}
	
	public static void addLogListener(LogListener l) {
		listeners.add(l);
	}
	public static void removeLogListener(LogListener l) {
		listeners.remove(l);
	}

}
