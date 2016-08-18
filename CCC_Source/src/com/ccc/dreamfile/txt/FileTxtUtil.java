package com.ccc.dreamfile.txt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.ccc.dreamfile.handlefile.FileHandleI;
import com.ccc.dreamtag.tag.function.StringUtil;

/**
 * @author RedSword
 * @date 2011-06-08 23:54:20
 * @version 2.0
 */

public class FileTxtUtil {

	/**
	 * @param filePath
	 * @return
	 */
	public static String convertString(File file) {
		List<String> re = convertListString(file);
		StringBuilder sbBuilder=new StringBuilder();
		if (re.size()!=0) {
			for (String string : re) {
				sbBuilder.append(" ").append(string);
			}
		}
		
		return sbBuilder.toString();
	}

    /**
     * @param filePath
     * @return
     */
    public static List<String> convertListString(File file) {
		List<String> lines = new ArrayList<String>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file.getAbsolutePath()), "UTF-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return lines;
    }

    /**
     * @param filePath
     * @return
     */
    public static void convertListString(File file, FileHandleI filehandle) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String r = br.readLine();
            while (r != null) {
                if (!StringUtil.isEmpty(r)) {
                    filehandle.handleObject(r);
                }
                r = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                filehandle.handleOver();
            }
        }
    }

    /**
     * @param filePath
     * @param regex:separtor
     * @return
     */
    public static List<String[]> convertListListString(File file, String regex) {
        List<String[]> returnList = new ArrayList<String[]>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String r = br.readLine();
            while (r != null) {
                if (!StringUtil.isEmpty(r)) {
                    returnList.add(r.split(regex));
                }
                r = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return returnList;
    }

    /**
     * @param filePath
     * @param regex:separtor
     * @return
     */
    public static void convertListListString(File file, String regex, FileHandleI filehandle) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String r = br.readLine();
            while (r != null) {
                if (!StringUtil.isEmpty(r)) {
                    filehandle.handleObject(r.split(regex));
                }
                r = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                filehandle.handleOver();
            }
        }
    }

    /**
     * @param list
     * @param file
     * @param isAppend:false
     *            all rewrite, true：apand it
     * @return
     */
    public static int writeTxtFile(List<String> list, File file, boolean isAppend) {
        BufferedWriter bw = null;
        try {
            int line = 0;
            if (list == null || list.size() == 0) {
                return line;
            }
            FileWriter fw = new FileWriter(file, isAppend);
            bw = new BufferedWriter(fw);
            for (String str : list) {
                if (str == null) {
                    bw.write("");
                } else {
                    bw.write(str);
                }
                bw.write("\n");
                line++;
            }
            return line;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            try {
                if (bw != null) {
                    bw.flush();
                    bw.close();
                }
            } catch (Exception e) {

            }
        }
    }

}
