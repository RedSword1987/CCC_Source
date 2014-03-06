package com.ccc.dreamlog;

import java.io.File;
import java.util.List;

import com.ccc.dreamfile.txt.FileTxtNew;

/**
 * @author RedSword
 * @date 2013-05-14 22:29:22
 * @version 1.0
 */

public class CreateLog4jFile {
	/**
	 * @param log4jFile
	 *            : create log4j file
	 * @param logFilePath
	 *            :log path
	 * @param logNames
	 *            :log module name
	 * @return
	 */
	public static boolean createLog4jFile(File log4jFile, File logFilePath, List<String> logNames) {
		try {
			String logPath = logFilePath.getAbsolutePath();
			logPath = logPath.replaceAll("\\\\", "/");

			FileTxtNew fileTxtNew = new FileTxtNew(log4jFile.getAbsolutePath(), false);
			fileTxtNew.writeLine("log4j.addivity.org.apache=true");
			fileTxtNew.writeLine("log4j.appender.CONSOLE=org.apache.log4j.ConsoleAppender");
			fileTxtNew.writeLine("log4j.appender.CONSOLE.Target=System.out");
			fileTxtNew.writeLine("log4j.appender.CONSOLE.layout=org.apache.log4j.PatternLayout");
			fileTxtNew.writeLine("log4j.appender.CONSOLE.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss SSS}%x %p:%m   %n");
			fileTxtNew.writeLine("log4j.appender.FILE=org.apache.log4j.DailyRollingFileAppender");
			fileTxtNew.writeLine("log4j.appender.FILE.File=" + logPath + "/ALL.log");
			fileTxtNew.writeLine("log4j.appender.FILE.DatePattern='.'yyyy-MM-dd");
			fileTxtNew.writeLine("log4j.appender.FILE.layout=org.apache.log4j.PatternLayout");
			fileTxtNew.writeLine("log4j.appender.FILE.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss SSS}%x %l %p:%m   %n");
			fileTxtNew.writeLine("log4j.appender.Threshold=INFO");
			fileTxtNew.writeLine("log4j.rootLogger=info,FILE,CONSOLE");
			fileTxtNew.writeLine("");

			logNames.add(0, "CCC");
			for (String str : logNames) {
				String[] arr = { "INFO", "DEBUG", "ERROR" };
				for (String string : arr) {
					String tempS = str + "_" + string;
					fileTxtNew.writeLine("log4j.logger." + tempS + " =" + string + "," + tempS + "_");
					fileTxtNew.writeLine("log4j.appender." + tempS + "_=org.apache.log4j.DailyRollingFileAppender");
					fileTxtNew.writeLine("log4j.appender." + tempS + "_.File=" + logPath + "/" + str + "/" + tempS + ".log");
					fileTxtNew.writeLine("log4j.appender." + tempS + "_.layout=org.apache.log4j.PatternLayout");
					fileTxtNew.writeLine("log4j.appender." + tempS + "_.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss SSS}%x %l %p:%m  %n");
				}
				fileTxtNew.writeLine("\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
}
