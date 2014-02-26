package com.ccc.dreamfile.txt;

import java.io.File;
import java.io.FileWriter;
import java.util.Timer;
import java.util.TimerTask;

import com.ccc.dreamtag.tag.function.DateUtil;

/**
 * @author RedSword
 * @date 2011-06-08 23:54:20
 * @version 2.0
 */

public class FileTxtTimerNew {
    String path = null;
    boolean isAppend = true;
    int index=0;
//    long periodTime = 10000;

    File file = null;
    FileWriter fw = null;
    long row= 0;
    long count=1000000;
    Timer timer=null;
    public FileTxtTimerNew(String path, boolean isAppend,Integer periodTime,Integer count) {
        try {
            this.path = path;
            this.isAppend = isAppend;
//            this.periodTime = periodTime;
            if (count!=null&&count.intValue()>0) {
                this.count=count;
            }
            file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file, isAppend);
              timer=new Timer();
            if (periodTime!=null&&periodTime.intValue()>0) {
                timer.schedule(new FileTxtTimerNew_TimerTask(), 5000, periodTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reInit() {
        try {
            file = new File(this.path);
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file, isAppend);
            row=0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param str
     * @param type
     *            1: 换文件
     */
    public synchronized void write(String str, int type) {
        try {
            if (type == 1) {
                if (fw != null) {
                    fw.close();
                    fw=null;
                }
                file.renameTo(new File(file.getAbsolutePath().replace(".", "_"+DateUtil.getTimeString_Now("yyyyMMddHHmmss")+"_"+index+++".")));
                reInit();
            } else {
                fw.append(str + "\n");
                fw.flush();
                row++;
                if (row>=count) {
                    if (fw != null) {
                        fw.close();
                        fw=null;
                    }
                    file.renameTo(new File(file.getAbsolutePath().replace(".", "_"+DateUtil.getTimeString_Now("yyyyMMddHHmmss")+"_"+index+++".")));
                    reInit();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void writeLine(String str) {
        write(str, 0);
    }

    public void writeLine(String[] str) {
        try {
            for (String string : str) {
                write(string, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (fw != null) {
                fw.close();
                fw=null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            this.timer.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    class FileTxtTimerNew_TimerTask extends TimerTask {
        public void run() {
            write(null, 1);
        }

    }

}
