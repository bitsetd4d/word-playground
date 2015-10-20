package net.didion.jwnl.util;

import net.didion.jwnl.JWNL;

public class MessageLog {

	public MessageLog(Class clazz) {
		//_log = LogFactory.getLog(clazz);
	}

	public void log(MessageLogLevel level, String messageKey) {
		doLog(level, JWNL.resolveMessage(messageKey));
	}

	public void log(MessageLogLevel level, String messageKey, Object param) {
		doLog(level, JWNL.resolveMessage(messageKey, param));
	}

	public void log(MessageLogLevel level, String messageKey, Object[] params) {
		doLog(level, JWNL.resolveMessage(messageKey, params));
	}

	public void log(MessageLogLevel level, String messageKey, Throwable t) {
		doLog(level, JWNL.resolveMessage(messageKey), t);
	}

	public void log(MessageLogLevel level, String messageKey, Object param, Throwable t) {
		doLog(level, JWNL.resolveMessage(messageKey, param), t);
	}

	public void log(MessageLogLevel level, String messageKey, Object[] params, Throwable t) {
		doLog(level, JWNL.resolveMessage(messageKey, params), t);
	}

	public boolean isLevelEnabled(MessageLogLevel level) {
		if (level == MessageLogLevel.TRACE) {
			return false;
		} else if (level == MessageLogLevel.DEBUG) {
			return false;
		} else if (level == MessageLogLevel.INFO) {
			return true;
		} else if (level == MessageLogLevel.WARN) {
			return true;
		} else if (level == MessageLogLevel.ERROR) {
			return true;
		} else if (level == MessageLogLevel.FATAL) {
			return true;
		}
		return false;
	}

	private void doLog(MessageLogLevel level, String message) {
		if (isLevelEnabled(level)) {
			if (level == MessageLogLevel.TRACE) {
				//_log.trace(message);
			} else if (level == MessageLogLevel.DEBUG) {
				//_log.debug(message);
			} else if (level == MessageLogLevel.INFO) {
				System.out.println("INFO "+message);
			} else if (level == MessageLogLevel.WARN) {
				System.out.println("WARN "+message);;
			} else if (level == MessageLogLevel.ERROR) {
				System.err.println("ERROR "+message);
			} else if (level == MessageLogLevel.FATAL) {
				System.err.println("FATAL "+message);
			}
		}
	}

	private void doLog(MessageLogLevel level, String message, Throwable t) {
		if (isLevelEnabled(level)) {
			if (level == MessageLogLevel.TRACE) {
				//_log.trace(message);
			} else if (level == MessageLogLevel.DEBUG) {
				//_log.debug(message);
			} else if (level == MessageLogLevel.INFO) {
				System.out.println("INFO "+message);
				t.printStackTrace();
			} else if (level == MessageLogLevel.WARN) {
				System.out.println("WARN "+message);
				t.printStackTrace();
			} else if (level == MessageLogLevel.ERROR) {
				System.err.println("ERROR "+message);
				t.printStackTrace();
			} else if (level == MessageLogLevel.FATAL) {
				System.err.println("FATAL "+message);
				t.printStackTrace();
			}
		}
	}
}