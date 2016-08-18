package com.ccc.dreamdb.db;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;

import com.ccc.dreamfile.FileUtil;
import com.ccc.dreamfile.xml.FileXmlUtil;

/**
 * @author RedSword
 * @date 2011-09-20 23:11:37
 * @version 2.0
 */

public final class DBUtil {
	public static int getFileType = 0;

	// 1： webroot 2：jar
	/**
	 * @deprecated
	 */
	@Deprecated
	public static void setFileType(int type) {
		getFileType = type;
	}

	/**
	 * 获取存储过程调用的串
	 * 
	 * @param procName
	 * @param objects
	 * @return
	 */
	public static String getProcCallDesc(String procName, Object... objects) {
		StringBuilder sBuilder = new StringBuilder("{call ");
		sBuilder.append(procName).append("(");
		if (objects != null && objects.length != 0) {
			int len = objects.length;
			while (len > 0) {
				len--;
				sBuilder.append("?,");
			}
		}
		if (',' == (sBuilder.charAt(sBuilder.length() - 1))) {
			sBuilder.deleteCharAt(sBuilder.length() - 1);
		}
		return sBuilder.append(")}").toString();
	}

	public static Document getDocument(String filePathp) {
		try {
			if (getFileType == 0) {
				File file = null;
				try {
					file = FileUtil.getFileFromClass(filePathp);
				} catch (Exception e) {
				}
				if (file != null && file.exists()) {
					getFileType = 1;
				} else {
					getFileType = 2;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (2 == getFileType) {
			java.net.URL url = DBUtil.class.getProtectionDomain().getCodeSource().getLocation();
			String filename = null;
			try {
				filename = java.net.URLDecoder.decode(url.getPath(), "utf-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (filename.endsWith(".jar")) {
				filename = filename.substring(0, filename.lastIndexOf("/") + 1);
				File file = new java.io.File(filename + filePathp);
				return FileXmlUtil.getDocument(file);
			}
		} else {
			return FileXmlUtil.getDocument(FileUtil.getFileFromClass(filePathp));
		}
		return null;
	}

	public static String getPrepareS(int size) {
		StringBuilder sBuilde = new StringBuilder();
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				sBuilde.append(",").append("?");
			}
			sBuilde.deleteCharAt(0);
		}
		return sBuilde.toString();
	}

	/**
	 * oracle special char
	 * 
	 * @param fieldValue
	 * @param type
	 * <br/>
	 *            1:字符拼接 = 2：字符拼接 like sql需要 escape '\\' 3:注入，like sql需要 escape
	 *            '\\'
	 * @return
	 */
	public static String replaceForSqlOracle(String fieldValue, int type) {
		if (type == 1) {
			return fieldValue.replaceAll("'", "'||chr(39)||'").replaceAll("&", "'||chr(38)||'").replaceAll("\\\\", "'||chr(92)||'");
		} else if (type == 1) {
			return fieldValue.replaceAll("'", "'||chr(39)||'").replaceAll("&", "'||chr(38)||'").replaceAll("\\\\", "'||chr(92)||chr(92)||'").replaceAll("%", "\\\\%").replaceAll("_", "\\\\_");
		} else if (type == 1) {
			return fieldValue.replaceAll("'", "'||chr(39)||'").replaceAll("&", "'||chr(38)||'").replaceAll("\\\\", "'||chr(92)||'").replaceAll("%", "\\%").replaceAll("_", "\\_");
		}
		return fieldValue;

	}

	private static Map<String, String> referencesMap = new HashMap<String, String>();

	static {
		referencesMap.put("'", "\\'");
		referencesMap.put("\"", "\\\"");
		referencesMap.put("\\", "\\\\");
		referencesMap.put("%", "\\%");
		referencesMap.put("_", "\\_");

		referencesMap.put("\n", "\\\n");
		referencesMap.put("\0", "\\\0");
		referencesMap.put("\b", "\\\b");
		referencesMap.put("\r", "\\\r");
		referencesMap.put("\t", "\\\t");
		referencesMap.put("\f", "\\\f");
	}

	public static String replaceForSqlMySql(String fieldValue) {
		if (fieldValue == null)
			return "";

		StringBuffer sbuffer = new StringBuffer(fieldValue.length());

		for (int i = 0; i < fieldValue.length(); i++) {
			String c = fieldValue.substring(i, i + 1);

			if (referencesMap.get(c) != null) {
				sbuffer.append(referencesMap.get(c));
			} else {
				sbuffer.append(c);
			}
		}
		return sbuffer.toString();

	}

}
