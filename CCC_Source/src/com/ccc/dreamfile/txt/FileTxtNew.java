package com.ccc.dreamfile.txt;

import java.io.File;
import java.io.FileWriter;

/**
 * @author RedSword
 * @date 2011-06-08 23:54:20
 * @version 2.0
 */

public class FileTxtNew {
    File file = null;
    FileWriter fw = null;

    public FileTxtNew(String path, boolean isAppend) {
        try {
            file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file, isAppend);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeLine(String str) {
        try {
            fw.append(str + "\n");
            fw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeLine(String[] str) {
        try {
            for (String string : str) {
                fw.append(string + "\n");
            }
            fw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (fw!=null) {
                fw.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void flush() {
        try {
            if (fw!=null) {
                fw.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
