package com.ccc.dreamfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ccc.dreamlog.LogDream;

/**
 * @author RedSword
 * @date 2011-06-12 18:50:51
 * @version 2.0
 */

public class FileUtil {

    /**
     * @param path
     * @param returnList
     * @return
     */
    public static List<String> getAllFileNamesDept(String path, List<String> returnList) {
        if (returnList == null) {
            return new ArrayList<String>();
        }
        File file = new File(path);
        if (file.isDirectory()) {
            for (File file3 : file.listFiles()) {
                if (file3.isFile()) {
                    returnList.add(file3.getAbsolutePath());
                } else if (file3.isDirectory()) {
                    getAllFileNamesDept(file3.getAbsolutePath(), returnList);
                }
            }
        }
        return returnList;
    }

    /**
     * @param path
     * @return
     */
    public static List<String> getAllFileNames(String path) {
        List<String> returnList = new ArrayList<String>();
        try {
            if (!path.endsWith("/")) {
                path = path + "/";
            }
            File file = new File(path);
            if (!file.isDirectory()) {
                LogDream.error("path is not real path " + path);
                return null;
            }
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isFile()) {
                    returnList.add(file2.getAbsolutePath());
                }
            }
            return returnList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * get all filenames ,not dept find.
     * 
     * @param path
     * @return
     */
    public static List<String> getAllFileNames(String path, List<String> returnList) {
        try {
            if (!path.endsWith("/")) {
                path = path + "/";
            }
            File file = new File(path);
            if (!file.isDirectory()) {
                LogDream.error("file is not real path: " + path);
                return null;
            }
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isFile()) {
                    returnList.add(file2.getAbsolutePath());
                }
            }
            return returnList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * get all file path dept.
     * 
     * @param path
     * @return
     */
    public static List<String> getAllFilePathsDept(String path) {
        List<String> list = new ArrayList<String>();
        getAllFilePathsDept(path, list);
        return list;
    }

    /**
     * get all file path dept.
     * 
     * @param path
     * @param returnList
     */
    public static void getAllFilePathsDept(String path, List<String> returnList) {
        try {
            if (!path.endsWith("/")) {
                path = path + "/";
            }
            File file = new File(path);
            if (!file.isDirectory()) {
                LogDream.error("path is nor real path " + path);
            }
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    returnList.add(file2.getAbsolutePath() + "/");
                    getAllFilePathsDept(file2.getAbsolutePath(), returnList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * copy file to path
     * 
     * @param file1
     * @param file2
     * @return
     */
    public static boolean copyFileToFold(String file1, String file2) {
        File in = new File(file1);
        File out = new File(file2);
        if (!in.exists()) {
            LogDream.info(file1 + " source file is not exist");
            return false;
        }
        if (!out.exists()) {
            out.mkdirs();
        }
        if (in.isFile()) {
            FileInputStream fin = null;
            FileOutputStream fout = null;
            try {
                fin = new FileInputStream(in);
                fout = new FileOutputStream(new File(file2 + "/" + in.getName()));
                int c;
                byte[] b = new byte[1024 * 5];
                while ((c = fin.read(b)) != -1) {
                    fout.write(b, 0, c);
                }
                fin.close();
                fout.flush();
                fout.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;

    }

    /**
     * 拷贝文件夹到文件夹
     * 
     * @param file1
     * @param file2
     * @return
     */
    public static boolean copyFoleToFold(String file1, String file2) {
        File in = new File(file1);
        File out = new File(file2);
        if (!in.exists()) {
            LogDream.info(file1 + " source file is not exist");
            return false;
        }
        if (!out.exists()) {
            out.mkdirs();
        }
        for (File file3 : in.listFiles()) {
            if (file3.isFile()) {
                copyFileToFold(file3.getPath(), file2);
            } else {
                copyFoleToFold(file1 + "/" + file3.getName(), file2 + "/" + file3.getName());
            }
        }
        return true;
    }

    /**
     * @param filePath
     * @return
     */
    public static File getFileFromPath(String filePath) {
        try {
            return new File(filePath);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * get File from class path
     * 
     * @param fileName
     * @return
     */
    public static File getFileFromClass(String fileName) {
        String path = null;
        if (System.getProperties().getProperty("os.name").toLowerCase().indexOf("windows") != -1) {
            path = Thread.currentThread().getContextClassLoader().getResource("").getPath().substring(1);
        } else {
            path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        }
        try {
            return new File(path + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * get File from webroot
     * 
     * @param fileName
     * @return
     */
    public static File getFileFromWebRoot(String fileName) {
        String path = null;
        if (System.getProperties().getProperty("os.name").toLowerCase().indexOf("windows") != -1) {
            path = Thread.currentThread().getContextClassLoader().getResource("").getPath().substring(1);
        } else {
            path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        }
        path = path.replaceAll("WEB-INF", "").replaceAll("classes", "");
        try {
            return new File(path + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * delete file or path
     * 
     * @param fileName
     * @return
     */
    public static boolean deleteFile(File file) {
        try {
            if (file == null || !file.exists()) {
                return false;
            } else {
                if (file.isDirectory()) {
                    for (File child : file.listFiles()) {
                        deleteFile(child);
                    }
                }
                file.delete();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
