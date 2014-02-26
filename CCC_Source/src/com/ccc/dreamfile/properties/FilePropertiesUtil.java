package com.ccc.dreamfile.properties;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.Map.Entry;

/**
 * @author RedSword
 * @date 2011-12-13 22:43:06
 */
public class FilePropertiesUtil {
    /**
     * read like proprrties file
     * 
     * @param file
     * @return
     */
    public static HashMap<String, String> readProperties(File file) {
        try {
            HashMap<String, String> resultMap = new HashMap<String, String>();
            Properties pProperties = new Properties();
            InputStream in = new BufferedInputStream(new FileInputStream(file));
            pProperties.load(in);
            for (Entry<Object, Object> en : pProperties.entrySet()) {
                resultMap.put(String.valueOf(en.getKey()), String.valueOf(en.getValue()));
            }
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * read like proprrties file
     * 
     * @param file
     * @return
     */
    public static boolean createProperties(HashMap<String, String> propertiesMap, File file, boolean isAppend) {
        BufferedWriter bw = null;
        try {
            FileWriter fw = new FileWriter(file, isAppend);
            bw = new BufferedWriter(fw);
            for (Entry<String, String> en : propertiesMap.entrySet()) {
                bw.append(en.getKey() + "=" + en.getValue() + "\n");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (bw != null) {
                    bw.flush();
                    bw.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
