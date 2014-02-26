package com.ccc.dreamfile.csv;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ccc.dreamfile.handlefile.FileHandleI;

/**
 * @author RedSword
 * @date 2011-06-26 18:59:35
 * @version 2.0
 */

public class FileCsvUtil {
    static Pattern pCells = Pattern.compile("(\"[^\"]*(\"{2})*[^\"]*\")*[^,]*,");

    /**
     * @param fileName:fullpath
     * @return
     */
    public static List<List<String>> convertListListString(String fileName) {
        List<List<String>> listFile = new ArrayList<List<String>>();
        try {
            InputStreamReader fr = new InputStreamReader(new FileInputStream(fileName));
            BufferedReader br = new BufferedReader(fr);
            String rec = null;
            String str;

            try {
                while ((rec = br.readLine()) != null) {
                    Matcher mCells = pCells.matcher(rec);
                    List<String> cells = new ArrayList<String>();
                    while (mCells.find()) {
                        str = mCells.group();
                        str = str.replaceAll("(?sm)\"?([^\"]*(\"{2})*[^\"]*)\"?.*,", "$1");
                        str = str.replaceAll("(?sm)(\"(\"))", "$2");
                        cells.add(str);
                    }
                    listFile.add(cells);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                fr.close();
                br.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return listFile;
    }

    /**
     * @param fileName
     * @param fh
     */
    public static void convertListListString(String fileName, FileHandleI fh) {
        try {
            InputStreamReader fr = new InputStreamReader(new FileInputStream(fileName));
            BufferedReader br = new BufferedReader(fr);
            String rec = null;
            String str;

            try {
                while ((rec = br.readLine()) != null) {
                    Matcher mCells = pCells.matcher(rec);
                    List<String> cells = new ArrayList<String>();
                    while (mCells.find()) {
                        str = mCells.group();
                        str = str.replaceAll("(?sm)\"?([^\"]*(\"{2})*[^\"]*)\"?.*,", "$1");
                        str = str.replaceAll("(?sm)(\"(\"))", "$2");
                        cells.add(str);
                    }
                    fh.handleObject(cells);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                fr.close();
                br.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
