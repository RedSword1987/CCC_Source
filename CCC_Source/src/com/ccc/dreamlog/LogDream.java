package com.ccc.dreamlog;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;

/**
 * @author RedSword
 * @date 2011-12-14 18:29:28
 */
public class LogDream {
	static Logger logInfo = Logger.getLogger("CCC_INFO");
	static Logger logError = Logger.getLogger("CCC_ERROR");
	static Logger logDebug = Logger.getLogger("CCC_DEBUG");

	public static void info(String info) {
		logInfo.info(getClassPath(getThrowableString(new Throwable())) + info);
	}

	public static void debug(String debug) {
		logDebug.debug(getClassPath(getThrowableString(new Throwable())) + debug);
	}

	public static void error(String error) {
		logError.error(getClassPath(getThrowableString(new Throwable())) + error);
	}

	public static void error(String error, Throwable t) {
		logError.error(getClassPath(getThrowableString(new Throwable())) + error, t);
	}

	public static void info(String info, String logName) {
		Logger log = Logger.getLogger(logName + "_INFO");
		log.info(getClassPath(getThrowableString(new Throwable())) + info);
	}

	public static void debug(String debug, String logName) {
		Logger log = Logger.getLogger(logName + "_DEBUG");
		log.debug(getClassPath(getThrowableString(new Throwable())) + debug);
	}

	public static void error(String error, String logName) {
		Logger log = Logger.getLogger(logName + "_ERROR");
		log.error(getClassPath(getThrowableString(new Throwable())) + error);
	}

	public static void error(String error, String logName, Throwable t) {
		Logger log = Logger.getLogger(logName + "_ERROR");
		log.error(getClassPath(getThrowableString(new Throwable())) + error, t);
	}

	private static String getClassPath(String str) {
		try {
			String s = str;
			int insex = s.indexOf(LogDream.class.getSimpleName() + ".java");
			if (insex != -1) {
				s = s.substring(insex + 18);
			}
			int end = s.indexOf(41);
			s = s.substring(0, end + 1);
			return s;
		} catch (Exception e) {
			return null;
		}
	}

	public static String getThrowableString(Throwable t) {
		try {
			StringWriter sWriter = new StringWriter();
			t.printStackTrace(new PrintWriter(sWriter));
			return sWriter.toString();
		} catch (Exception e) {
			return null;
		}
	}

}
