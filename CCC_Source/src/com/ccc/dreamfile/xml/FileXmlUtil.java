package com.ccc.dreamfile.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @author RedSword
 * @date 2011-06-09 19:13:02
 * @version 2.0
 */

public class FileXmlUtil {
    /**
     * @param xmlPath
     * @param node
     * @return
     */
    public static Document getDocument(File xmlfile) {
        try {
            return new SAXReader().read(xmlfile);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param xmlPath
     * @param node
     * @return
     */
    public static Document getDocument(InputStream in) {
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(in);
            return document;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param xmlPath
     * @param node
     * @return
     */
    public static String getNodeValue(String xmlPath, String node) {
        String path = null;
        if (System.getProperties().getProperty("os.name").toLowerCase().indexOf("windows") != -1) {
            path = Thread.currentThread().getContextClassLoader().getResource("").getPath().substring(1);
        } else {
            path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        }
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(new File(path + xmlPath));
            return getNodeValue(document, node);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param doc
     * @param node
     * @return
     */
    public static String getNodeValue(Document doc, String node) {
        return doc.selectSingleNode(node).getText();
    }

    /**
     * @param doc
     * @param filaNeme
     * @return
     */
    public static boolean createXml(Document doc, String filaNeme) {
        try {
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("utf-8");
            XMLWriter writer = new XMLWriter(new FileWriter(filaNeme), format);
            writer.write(doc);
            writer.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param doc
     * @param node
     * @return
     */
    public static List<Map<String, String>> getXmlNodeList(Document doc, String node) {
        List<Map<String, String>> returnList = new LinkedList<Map<String, String>>();
        Map<String, String> map = null;
        List<Element> lists11 = doc.selectNodes(node);
        for (Element ele : lists11) {
            map = new HashMap<String, String>();
            map.put("NODENAME", ele.getName());
            map.put("NODEVALUE", ele.getStringValue());
            for (Attribute at : (List<Attribute>) ele.attributes()) {
                map.put(at.getName(), at.getValue());
            }
            returnList.add(map);
        }
        return returnList;
    }

}
