package com.ccc.dreamtag.tag.function;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * @author RedSword(416448382@qq.com)
 * @date 2013-11-10 22:21:38
 */
public class StringUtil {
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input.trim()) || "null".equals(input.trim())) {
            return true;
        }
        return false;
    }

    public static String toUpperCase(String input) {
        return input.toUpperCase();
    }

    public static String toLowerCase(String input) {
        return input.toLowerCase();
    }

    public static int indexOf(String input, String substring) {
        if (input == null)
            input = "";
        if (substring == null)
            substring = "";
        return input.indexOf(substring);
    }

    public static boolean contains(String input, String substring) {
        return indexOf(input, substring) != -1;
    }

    public static boolean containsIgnoreCase(String input, String substring) {
        if (input == null)
            input = "";
        if (substring == null)
            substring = "";
        String inputUC = input.toUpperCase();
        String substringUC = substring.toUpperCase();
        return indexOf(inputUC, substringUC) != -1;
    }

    public static boolean startsWith(String input, String substring) {
        if (input == null)
            input = "";
        if (substring == null)
            substring = "";
        return input.startsWith(substring);
    }

    public static boolean endsWith(String input, String substring) {
        if (input == null)
            input = "";
        if (substring == null)
            substring = "";
        int index = input.indexOf(substring);
        if (index == -1)
            return false;
        if (index == 0 && substring.length() == 0)
            return true;
        return (index == input.length() - substring.length());
    }

    public static String substring(String input, int beginIndex, int endIndex) {
        if (input == null)
            input = "";
        if (beginIndex >= input.length())
            return "";
        if (beginIndex < 0)
            beginIndex = 0;
        if (endIndex < 0 || endIndex > input.length())
            endIndex = input.length();
        if (endIndex < beginIndex)
            return "";
        return input.substring(beginIndex, endIndex);
    }

    public static String substringAfter(String input, String substring) {
        if (input == null)
            input = "";
        if (input.length() == 0)
            return "";
        if (substring == null)
            substring = "";
        if (substring.length() == 0)
            return input;

        int index = input.indexOf(substring);
        if (index == -1) {
            return "";
        } else {
            return input.substring(index + substring.length());
        }
    }

    public static String substringBefore(String input, String substring) {
        if (input == null)
            input = "";
        if (input.length() == 0)
            return "";
        if (substring == null)
            substring = "";
        if (substring.length() == 0)
            return "";

        int index = input.indexOf(substring);
        if (index == -1) {
            return "";
        } else {
            return input.substring(0, index);
        }
    }

    public static String trim(String input) {
        if (input == null)
            return "";
        return input.trim();
    }

    public static String replace(String input, String substringBefore, String substringAfter) {
        if (input == null)
            input = "";
        if (input.length() == 0)
            return "";
        if (substringBefore == null)
            substringBefore = "";
        if (substringBefore.length() == 0)
            return input;

        StringBuffer buf = new StringBuffer(input.length());
        int startIndex = 0;
        int index;
        while ((index = input.indexOf(substringBefore, startIndex)) != -1) {
            buf.append(input.substring(startIndex, index)).append(substringAfter);
            startIndex = index + substringBefore.length();
        }
        return buf.append(input.substring(startIndex)).toString();
    }

    public static String[] split(String input, String delimiters) {
        String[] array;
        if (input == null)
            input = "";
        if (input.length() == 0) {
            array = new String[1];
            array[0] = "";
            return array;
        }

        if (delimiters == null)
            delimiters = "";

        StringTokenizer tok = new StringTokenizer(input, delimiters);
        int count = tok.countTokens();
        array = new String[count];
        int i = 0;
        while (tok.hasMoreTokens()) {
            array[i++] = tok.nextToken();
        }
        return array;
    }

    public static int length(Object obj) {
        if (obj == null)
            return 0;

        if (obj instanceof String)
            return ((String) obj).length();
        if (obj instanceof Collection)
            return ((Collection) obj).size();
        if (obj instanceof Map)
            return ((Map) obj).size();

        int count = 0;
        if (obj instanceof Iterator) {
            Iterator iter = (Iterator) obj;
            count = 0;
            while (iter.hasNext()) {
                count++;
                iter.next();
            }
            return count;
        }
        if (obj instanceof Enumeration) {
            Enumeration enum_ = (Enumeration) obj;
            count = 0;
            while (enum_.hasMoreElements()) {
                count++;
                enum_.nextElement();
            }
            return count;
        }
        try {
            count = Array.getLength(obj);
            return count;
        } catch (Exception ex) {
            return 0;
        }
    }

    public static String join(String[] array, String separator) {
        if (array == null)
            return "";
        if (separator == null)
            separator = "";

        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            buf.append(array[i]);
            if (i < array.length - 1)
                buf.append(separator);
        }
        return buf.toString();
    }

    public static List<String> cutOff(String content, int length) {
        List<String> lists = new ArrayList<String>();
        content = trim(content);
        if ("".equals(content) || length <= 0) {
            return lists;
        }

        int allL = content.length() / length;
        if (allL * length < content.length()) {
            allL = allL + 1;
        }
        int start = 0;
        int max = 0;
        while (allL-- > 0) {
            max = start + length > content.length() ? content.length() : start + length;
            lists.add(content.substring(start, max));
            start = max;
        }
        return lists;
    }

    /**
     * 将参数转换为String
     * 
     * @param obj
     * @return
     */
    public static String convertObjectsTostring(Object... obj) {
        if (obj == null || obj.length == 0) {
            return "";
        } else {
            StringBuilder sBuilder = new StringBuilder("[");
            for (Object object : obj) {
                sBuilder.append(object).append(",");
            }
            sBuilder.deleteCharAt(sBuilder.length() - 1).append("]");
            return sBuilder.toString();
        }
    }

    /**
     * 将参数转换为String
     * 
     * @param obj
     * @return
     */
    public static String convertObjectArrayTostring(Object[][] obj) {
        if (obj == null || obj.length == 0) {
            return "";
        } else {
            StringBuilder sBuilder = new StringBuilder("[");
            for (Object[] object : obj) {
                for (Object object2 : object) {
                    sBuilder.append(object2).append(",");
                }
                sBuilder.append(";").append("\n");
            }
            sBuilder.deleteCharAt(sBuilder.length() - 1).append("]");
            return sBuilder.toString();
        }
    }

    /**
     * 验证是不是IP
     * 
     * @param ip
     * @return
     */
    public static boolean isIp(String ip) {

        return false;
    }

    /**
     * 验证是不是MAC
     * 
     * @param ip
     * @return
     */
    public static boolean isMac(String ip) {
        return false;
    }

    /**
     * 验证是不是身份证
     * 
     * @param ip
     * @return
     */
    public static boolean isIdCard(String ip) {
        return false;
    }

    /**
     * 15位身份证转换为18位身份证
     * 
     * @param ip
     * @return
     */
    public static String convertIdCard15_IdCard15(String ip) {
        return null;
    }

    public static String joinListMap(List<Map<String, Object>> param, String fieldName, String separator) {
        StringBuilder sBuilder = new StringBuilder();
        if (param != null && param.size() != 0) {
            for (Map<String, Object> map : param) {

                sBuilder.append(separator).append(map.get(fieldName));
            }
            if (separator.length() != 0) {
                sBuilder.delete(0, separator.length());
            }
        }
        return sBuilder.toString();
    }

    public static String joinListString(List<String> param, String separator) {
        StringBuilder sBuilder = new StringBuilder();
        if (param != null && param.size() != 0) {
            for (String str : param) {
                sBuilder.append(separator).append(str);
            }
            if (separator.length() != 0) {
                sBuilder.delete(0, separator.length());
            }
        }
        return sBuilder.toString();
    }

}
