package com.ccc.dreamfile.compress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import com.ccc.dreamlog.LogDream;
import com.ccc.dreamtag.tag.function.StringUtil;

/**
 * @author RedSword
 * @date 2011-06-13 21:29:00
 * @version 2.0
 */

public class FileZipUtil {

    private FileZipUtil() {
    }

    /**
     * unzip zip file,will not unzip contains zip file. <br>
     * 
     * @param zipFileName：zip
     *            full path
     * @param toPath:if
     *            toPath is null,then unzip to this path.
     */
    public static void unZip(String zipFileName, String toPath) {
        File file = new File(zipFileName);
        if ((!zipFileName.endsWith(".zip") && !zipFileName.endsWith(".ZIP")) || !file.exists()) {
            LogDream.info("zip file unexist");
            return;
        }
        File outFile;
        if (StringUtil.isEmpty(toPath)) {
            toPath = zipFileName.replaceAll(".zip", "").replaceAll(".ZIP", "");
            outFile = new File(zipFileName.replaceAll(".zip", "").replaceAll(".ZIP", ""));
            if (!outFile.exists()) {
                outFile.mkdir();
            }
        } else {
            outFile = new File(toPath);
            if (!outFile.exists()) {
                outFile.mkdir();
            }
        }
        try {
            ZipFile zipFile = new ZipFile(zipFileName);
            Enumeration<ZipEntry> e = zipFile.getEntries();
            ZipEntry zipEntry = null;
            while (e.hasMoreElements()) {
                zipEntry = e.nextElement();
                if (zipEntry.isDirectory()) {
                    String name = zipEntry.getName();
                    name = name.substring(0, name.length() - 1);
                    File f = new File(toPath + File.separator + name);
                    f.mkdir();
                } else {
                    String fileName = zipEntry.getName();
                    fileName = fileName.replace('\\', '/');
                    if (fileName.indexOf("/") != -1) {
                        createDirectory(toPath, fileName.substring(0, fileName.lastIndexOf("/")));
                        fileName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length());
                    }

                    File f = new File(toPath + File.separator + zipEntry.getName());
                    f.createNewFile();
                    InputStream in = zipFile.getInputStream(zipEntry);
                    FileOutputStream out = new FileOutputStream(f);
                    byte[] by = new byte[1024];
                    int c;
                    while ((c = in.read(by)) != -1) {
                        out.write(by, 0, c);
                    }
                    out.close();
                    in.close();
                }
            }
            zipFile.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * unzip zip file,will unzip contains zip file. <br>
     * 
     * @param zipFileName：zip
     *            full path
     * @param toPath:if
     *            toPath is null,then unzip to this path.
     */
    public static void unZipDept(String zipFileName, String toPath) {
        File file = new File(zipFileName);
        if (!file.exists() || (!zipFileName.endsWith(".zip") && !zipFileName.endsWith(".ZIP"))) {
            LogDream.info("zip file unexist");
            return;
        }
        File outFile;
        if (StringUtil.isEmpty(toPath)) {
            toPath = zipFileName.replaceAll(".zip", "").replaceAll(".ZIP", "");
            outFile = new File(zipFileName.replaceAll(".zip", "").replaceAll(".ZIP", ""));
            if (!outFile.exists()) {
                outFile.mkdir();
            }
        } else {
            outFile = new File(toPath);
            if (!outFile.exists()) {
                outFile.mkdir();
            }
        }
        try {
            ZipFile zipFile = new ZipFile(zipFileName);
            Enumeration e = zipFile.getEntries();
            ZipEntry zipEntry = null;
            while (e.hasMoreElements()) {
                zipEntry = (ZipEntry) e.nextElement();
                if (zipEntry.isDirectory()) {
                    String name = zipEntry.getName();
                    name = name.substring(0, name.length() - 1);
                    File f = new File(toPath + File.separator + name);
                    f.mkdir();
                } else {
                    String fileName = zipEntry.getName();
                    fileName = fileName.replace('\\', '/');
                    if (fileName.indexOf("/") != -1) {
                        createDirectory(toPath, fileName.substring(0, fileName.lastIndexOf("/")));
                        fileName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length());
                    }

                    File f = new File(toPath + File.separator + zipEntry.getName());
                    f.createNewFile();
                    InputStream in = zipFile.getInputStream(zipEntry);
                    FileOutputStream out = new FileOutputStream(f);
                    byte[] by = new byte[1024];
                    int c;
                    while ((c = in.read(by)) != -1) {
                        out.write(by, 0, c);
                    }
                    out.close();
                    in.close();
                }
            }
            zipFile.close();
            File file3 = new File(zipFileName);
            file3.deleteOnExit();
            for (File file4 : outFile.listFiles()) {
                if ((file4.getName().endsWith(".zip") || file4.getName().endsWith(".ZIP")) && file4.isFile()) {
                    unZipDept(file4.getAbsolutePath(), null);
                } else if (file4.isDirectory()) {
                    unZipPathDept(file4.getAbsolutePath());
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * unzip zip file,will unzip contains zip file. <br>
     * 
     * @param zipFileName：zip
     *            full path
     * @param toPath:if
     *            toPath is null,then unzip to this path.
     */
    public static void unZipPathDept(String zipFilePath) {

        try {
            File file = new File(zipFilePath);
            if (!file.exists() || !file.isDirectory()) {
                LogDream.info("file is not exist");
                return;
            }
            for (File file2 : file.listFiles()) {
                if ((file2.getName().endsWith(".zip") || file2.getName().endsWith(".ZIP")) && file2.isFile()) {
                    unZipDept(file2.getAbsolutePath(), null);
                } else if (file2.isDirectory()) {
                    unZipPathDept(file2.getAbsolutePath());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // create file path
    private static void createDirectory(String directory, String subDirectory) {
        String dir[];
        File fl = new File(directory);
        try {
            if ("".equals(subDirectory) && fl.exists() != true)
                fl.mkdirs();
            else if (!"".equals(subDirectory)) {
                dir = subDirectory.replace("\\", "/").split("/");
                for (int i = 0; i < dir.length; i++) {
                    File subFile = new File(directory + File.separator + dir[i]);
                    if (!subFile.exists())
                        subFile.mkdir();
                    directory += File.separator + dir[i];
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * zip file
     * 
     * @param sourceFile
     * @param targetFile
     * @return
     */
    public static boolean zip(File sourceFile, File targetFile) {
        try {
            Project prj = new Project();
            Zip zip = new Zip();
            zip.setProject(prj);
            zip.setDestFile(targetFile);
            FileSet fileSet = new FileSet();
            fileSet.setProject(prj);
            if (sourceFile.isFile()) {
                fileSet.setFile(sourceFile);
            } else {

                fileSet.setDir(sourceFile);
            }
            zip.addFileset(fileSet);
            zip.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
