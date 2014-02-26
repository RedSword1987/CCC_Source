package com.ccc.log4j.extend;

import java.io.File;
import java.util.List;

import com.ccc.dreamfile.txt.FileTxtNew;

/**
 * @author RedSword
 * @date 2013-05-14 22:29:22
 * @version 1.0
 */

public class CreateLog4jFile {
    public static boolean createLog4jFile(File file, File logFile, List<String> ll) {
        try {
            FileTxtNew fileTxtNew = new FileTxtNew(file.getAbsolutePath(), false);
            fileTxtNew.writeLine("log4j.addivity.org.apache=true");
            fileTxtNew.writeLine("log4j.appender.CONSOLE=org.apache.log4j.ConsoleAppender");
            fileTxtNew.writeLine("log4j.appender.CONSOLE.Target=System.out");
            fileTxtNew.writeLine("log4j.appender.CONSOLE.layout=org.apache.log4j.PatternLayout");
            fileTxtNew.writeLine("log4j.appender.CONSOLE.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss SSS}%x %p:%m   %n");
            fileTxtNew.writeLine("log4j.appender.FILE=org.apache.log4j.DailyRollingFileAppender");
            fileTxtNew.writeLine("log4j.appender.FILE.File=" + logFile.getAbsolutePath() + "/SendMagPlatForm.log");
            fileTxtNew.writeLine("log4j.appender.FILE.DatePattern='.'yyyy-MM-dd");
            fileTxtNew.writeLine("log4j.appender.FILE.layout=org.apache.log4j.PatternLayout");
            fileTxtNew.writeLine("log4j.appender.FILE.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss SSS}%x %l %p:%m   %n");
            fileTxtNew.writeLine("log4j.appender.Threshold=INFO");
            fileTxtNew.writeLine("log4j.rootLogger=info,FILE,CONSOLE");
            fileTxtNew.writeLine("");

            ll.add(0, "CCC");
            for (String str : ll) {
                String tempS = str + "_DEBUG";
                fileTxtNew.writeLine("log4j.logger." + tempS + " =info," + tempS + "_");
                fileTxtNew.writeLine("log4j.appender." + tempS + "_=org.apache.log4j.DailyRollingFileAppender");
                fileTxtNew.writeLine("log4j.appender." + tempS + "_.File=" + logFile.getAbsolutePath() + "/" + str + "/" + tempS + ".log");
                fileTxtNew.writeLine("log4j.appender." + tempS + "_.layout=org.apache.log4j.PatternLayout");
                fileTxtNew.writeLine("log4j.appender." + tempS + "_.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss SSS}%x %l %p:%m  %n");

                tempS = str + "_INFO";
                fileTxtNew.writeLine("log4j.logger." + tempS + " =info," + tempS + "_");
                fileTxtNew.writeLine("log4j.appender." + tempS + "_=org.apache.log4j.DailyRollingFileAppender");
                fileTxtNew.writeLine("log4j.appender." + tempS + "_.File=" + logFile.getAbsolutePath() + "/" + str + "/" + tempS + ".log");
                fileTxtNew.writeLine("log4j.appender." + tempS + "_.layout=org.apache.log4j.PatternLayout");
                fileTxtNew.writeLine("log4j.appender." + tempS + "_.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss SSS}%x %l %p:%m  %n");

                tempS = str + "_ERROR";
                fileTxtNew.writeLine("log4j.logger." + tempS + "_ERROR=info," + tempS + "_");
                fileTxtNew.writeLine("log4j.appender." + tempS + "_=org.apache.log4j.DailyRollingFileAppender");
                fileTxtNew.writeLine("log4j.appender." + tempS + "_.File=" + logFile.getAbsolutePath() + "/" + str + "/" + tempS + ".log");
                fileTxtNew.writeLine("log4j.appender." + tempS + "_.layout=org.apache.log4j.PatternLayout");
                fileTxtNew.writeLine("log4j.appender." + tempS + "_.layout.ConversionPattern=%d{yyyy-MM-dd HH:mm:ss SSS}%x %l %p:%m  %n");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
}
