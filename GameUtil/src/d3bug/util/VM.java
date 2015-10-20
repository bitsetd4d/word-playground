package d3bug.util;

public class VM {
	
	/*
	 * Return class belonging to whoever called the callers
	 * class
	 */
	public static Class getCaller(Class caller) {
		String callerName = caller.getCanonicalName();
		StackTraceElement[] stack = (new Throwable()).getStackTrace();
		boolean seenCaller = false;
		for (StackTraceElement e : stack) {
			if (e.getClassName().equals(callerName)) {
				seenCaller = true;
				continue;
			}
			if (!seenCaller) continue;
			try {
				return Class.forName(e.getClassName());
			} catch (ClassNotFoundException e1) {}
		}
		return Object.class;
	}

}
