package com.ccc.dreamfile.compress;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import com.ccc.dreamlog.LogDream;
import com.ccc.dreamtag.tag.function.StringUtil;

/**
 * @author RedSword
 * @date 2011-06-13 21:29:14
 * @version 2.0
 */

public class FileRarUtil {

    private FileRarUtil() {
    }

    /**
     * unrar rar file,will not unrar contains unrar file. <br>
     * 
     * @param cmd：
     *            like C:\\Program Files\\WinRAR\\winrar.exe
     * @param rarFileName：rar
     *            fullpath
     * @param toPath：if
     *            toPath is null,then unrar to this path.
     */
    public static void unRar(String cmd, String rarFileName, String toPath) {
        try {
            if (!rarFileName.endsWith(".rar") && !rarFileName.endsWith(".RAR")) {
                LogDream.info("file is no rar file");
                return;
            }
            File file = new File(rarFileName);
            if (!file.exists()) {
                LogDream.info("file is no exist");
                return;
            }
            if (StringUtil.isEmpty(toPath)) {
                toPath = rarFileName.replaceAll(".rar", "").replace(".rar", "");
            }
            File file2 = new File(toPath);
            if (!file2.exists()) {
                file2.mkdir();
            }
            String unrarCmd = cmd + " x -r -p- -o+ " + rarFileName + " " + toPath;
            Runtime rt = Runtime.getRuntime();
            Process pre = rt.exec(unrarCmd);
            InputStreamReader isr = new InputStreamReader(pre.getInputStream());
            BufferedReader bf = new BufferedReader(isr);
            String line = null;
            while ((line = bf.readLine()) != null) {
                line = line.trim();
                if ("".equals(line)) {
                    continue;
                }
                System.out.println(line);
            }
            bf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * unrar rar file,will not unrar contains unrar file. <br>
     * 
     * @param cmd：
     *            like C:\\Program Files\\WinRAR\\winrar.exe
     * @param rarFileName：rar
     *            fullpath
     */
    public static void unRarPathDept(String cmd, String rarFileName) {
        try {
            File file = new File(rarFileName);
            if (!file.exists() || !file.isDirectory()) {
                LogDream.info("file is no exist");
                return;
            }
            for (File file2 : file.listFiles()) {
                if ((file2.getName().endsWith(".rar") || file2.getName().endsWith(".RAR")) && file2.isFile()) {
                    unRarDept(cmd, file2.getAbsolutePath(), null);
                } else if (file2.isDirectory()) {
                    unRarPathDept(cmd, file2.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * unrar rar file,will not unrar contains unrar file. <br>
     * 
     * @param cmd：
     *            like C:\\Program Files\\WinRAR\\winrar.exe
     * @param rarFileName：rar
     *            fullpath
     * @param toPath：if
     *            toPath is null,then unrar to this path.
     */
    public static void unRarDept(String cmd, String rarFileName, String toPath) {
        try {
            if (!rarFileName.endsWith(".rar") && !rarFileName.endsWith(".RAR")) {
                LogDream.info("file is not rar file");
                return;
            }
            File file = new File(rarFileName);
            if (!file.exists()) {
                LogDream.info("file is no exist");
                return;
            }
            if (StringUtil.isEmpty(toPath)) {
                toPath = rarFileName.replaceAll(".rar", "").replace(".RAR", "");
            }
            File file2 = new File(toPath);
            if (!file2.exists()) {
                file2.mkdir();
            }
            String unrarCmd = cmd + " x -r -p- -o+ " + rarFileName + " " + toPath;
            Runtime rt = Runtime.getRuntime();
            Process pre = rt.exec(unrarCmd);
            InputStreamReader isr = new InputStreamReader(pre.getInputStream());
            BufferedReader bf = new BufferedReader(isr);
            String line = null;
            while ((line = bf.readLine()) != null) {
                line = line.trim();
                if ("".equals(line)) {
                    continue;
                }
                System.out.println(line);
            }
            bf.close();
            file.delete();
            File file3 = new File(toPath);
            for (File file4 : file3.listFiles()) {
                if ((file4.getName().endsWith(".rar") || file4.getName().endsWith(".RAR")) && file4.isFile()) {
                    unRarDept(cmd, file4.getAbsolutePath(), null);
                } else if (file4.isDirectory()) {
                    unRarPathDept(cmd, file4.getAbsolutePath());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
