package antClass;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.ccc.dreamfile.FileUtil;
import com.ccc.dreamfile.txt.FileTxtNew;
import com.ccc.dreamfile.txt.FileTxtUtil;
import com.ccc.dreamtag.tag.function.TagUtil;

/**
 * @author RedSword
 * @date 2013-11-05 22:53:38
 * @version 1.0
 */

public class Ant_createTag {

    public static void main(String[] args) {
        // 获取META-INF的路径
        String path = FileUtil.getFileFromClass("").getAbsolutePath();
        path = path.substring(0, path.length() - 4) + "\\META-INF";
        System.out.println(path);

        List<String> list = FileTxtUtil.convertListString(new File(path + "\\ccc_fn_bak.tld"));
        FileTxtNew fileTxtNew = new FileTxtNew(path + "\\ccc_fn.tld", false);
        //
        List<String> list_targetList = new ArrayList<String>();
        list_targetList.addAll(list);
        list_targetList.remove(list.size() - 1);
        //
        Class class1 = TagUtil.class;
        for (Method method : class1.getDeclaredMethods()) {
            list_targetList.add("\n");
            list_targetList.add("\t<function>");
            list_targetList.add("\t\t<description>" + method.getName() + "</description>");
            list_targetList.add("\t\t<name>" + method.getName() + "</name>");
            list_targetList.add("\t\t<function-class>" + class1.getName() + "</function-class>");
            StringBuilder sBuilder = new StringBuilder();
            for (Class child : method.getParameterTypes()) {
                sBuilder.append(",").append(child.getName());
            }
            if (sBuilder.length() != 0) {
                sBuilder.deleteCharAt(0);
            }

            list_targetList.add("\t\t<function-signature>" + method.getReturnType().getName() + " " + method.getName() + "(" + sBuilder + ")</function-signature>");
            list_targetList.add("\t\t<example>Product name: ${ccc_fn." + method.getName() + "(" + sBuilder + ")}</example>");
            list_targetList.add("\t</function>");
        }
        list_targetList.add(list.get(list.size() - 1));
        for (String string : list_targetList) {
            fileTxtNew.writeLine(string);
        }

    }
}
