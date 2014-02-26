package com.ccc.dreamdb.db.sourcedefine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

import com.ccc.dreamdb.db.DBUtil;
import com.ccc.dreamfile.xml.FileXmlUtil;
import com.ccc.dreamlog.LogDream;
import com.ccc.dreamtag.tag.function.StringUtil;

/**
 * @author RedSword
 * @date 2011-05-22 20:18:04
 * @version 2.0
 */

public class DatabaseSources {

    private Map<String, DatabaseDefine> databaseDefines = new HashMap<String, DatabaseDefine>();
    private Map<String, DatabaseDefine> linkDatabaseDefines = new HashMap<String, DatabaseDefine>();

    List<Element> listlinktos = new ArrayList<Element>();
    List<Element> listdatabases = new ArrayList<Element>();

    public DatabaseSources(String filePath) {
        HashMap<String, String> pathMap1 = new HashMap<String, String>();
        initDatabaseImports(filePath, pathMap1);
        for (String key : pathMap1.keySet()) {
            handleFile(key);
        }
        for (Element element : this.listdatabases) {
            DatabaseDefine dd = initDatabaseDefine(element);
            if (dd != null) {
                this.databaseDefines.put(dd.getName(), dd);
            }
        }
        Attribute name;
        Attribute linkto;
        for (Element element : this.listlinktos) {
            name = element.attribute("name");
            linkto = element.attribute("linkto");
            if (name == null || name.getValue() == null) {
                LogDream.error("datasource name can not empty ");

                continue;
            }
            if (linkto == null || linkto.getValue() == null) {
                LogDream.error("datasource linkto name can not empty ");
                continue;
            }
            if (this.databaseDefines.get(linkto.getValue()) == null) {
                LogDream.error("datasource linkto name is not exist ");
                continue;
            }
            if (this.databaseDefines.get(name.getValue()) != null) {
                LogDream.error("datasource name has regist ");
                continue;
            }
            if (this.linkDatabaseDefines.get(name.getValue()) != null) {
                LogDream.error("datasource linkto name has regist ");
                continue;
            }

            DatabaseDefine dd = this.databaseDefines.get(linkto.getValue()).clone();
            dd.setName(name.getValue());
            dd.setLinkto(linkto.getValue());
            if (element.element("constantMap") != null) {
                Map<String, String> conMap = new HashMap<String, String>();
                Element elementP = element.element("constantMap");
                for (Attribute at : (List<Attribute>) elementP.attributes()) {
                    if ("showSql".equals(at.getName())) {
                        dd.setShowSql(Boolean.valueOf(at.getValue()));
                    } else if ("logName".equals(at.getName())) {
                        dd.setLogName(at.getValue());
                    }
                }
                List<Element> keyValue = elementP.elements("param");
                for (Element element2 : keyValue) {
                    if (element2.attribute("key") != null) {
                        if (element2.attribute("value") != null) {
                            conMap.put(element2.attribute("key").getStringValue(), element2.attribute("value").getStringValue());
                        } else {
                            conMap.put(element2.attribute("key").getStringValue(), "");
                        }
                    }
                }
                dd.setConstantMap(conMap);
            }
            this.linkDatabaseDefines.put(name.getValue(), dd);
        }
    }

    public void initDatabaseImports(String filePath, HashMap<String, String> map) {
        map.put(filePath, "1");
        Document document = DBUtil.getDocument(filePath);
        List<Map<String, String>> lists = FileXmlUtil.getXmlNodeList(document, "database-sources/database-import");
        for (Map<String, String> tempmap : lists) {
            if (tempmap.get("path") != null) {
                if (StringUtil.isEmpty(map.get(tempmap.get("path")))) {
                    initDatabaseImports(tempmap.get("path"), map);
                }
            }
        }
    }

    public void handleFile(String filePath) {

        Document document = DBUtil.getDocument(filePath);
        Element e = document.getRootElement();
        List<Element> databaseimports = e.elements("database-source");
        for (Element element : databaseimports) {
            this.listdatabases.add(element);
        }
        List<Element> listlinktoss = e.elements("database-mapping");
        for (Element element : listlinktoss) {
            this.listlinktos.add(element);
        }
    }

    public DatabaseDefine initDatabaseDefine(Element element) {
        DatabaseDefine dataDefine = new DatabaseDefine();
        dataDefine.setName(element.elementTextTrim("name"));
        dataDefine.setDatabaseType(Integer.valueOf(element.elementTextTrim("databaseType")));
        if (this.databaseDefines.get(element.elementTextTrim("name")) != null) {
            LogDream.error("datasource " + element.elementTextTrim("name") + " has been registed");
            return null;
        }
        dataDefine.setDriverUrl(element.elementTextTrim("driverUrl"));
        dataDefine.setDriverClass(element.elementTextTrim("driverClass"));
        dataDefine.setUser(element.elementTextTrim("user"));
        dataDefine.setPassword(element.elementTextTrim("password"));

        if (!StringUtil.isEmpty(element.elementTextTrim("connectSizeMax"))) {
            dataDefine.setConnectSizeMax(element.elementTextTrim("connectSizeMax"));
        }

        if (!StringUtil.isEmpty(element.elementTextTrim("connectSizeMin"))) {
            dataDefine.setConnectSizeMin(element.elementTextTrim("connectSizeMin"));
        }
        if (!StringUtil.isEmpty(element.elementTextTrim("connectSizeIncreament"))) {
            dataDefine.setConnectSizeIncreament(element.elementTextTrim("connectSizeIncreament"));
        }
        if (!StringUtil.isEmpty(element.elementTextTrim("connectActiveTimeMax"))) {
            dataDefine.setConnectActiveTimeMax(element.elementTextTrim("connectActiveTimeMax"));
        }
        if (!StringUtil.isEmpty(element.elementTextTrim("connectFreeTimeMax"))) {
            dataDefine.setConnectFreeTimeMax(element.elementTextTrim("connectFreeTimeMax"));
        }
        if (!StringUtil.isEmpty(element.elementTextTrim("connectSizeAvailable"))) {
            dataDefine.setConnectSizeAvailable(element.elementTextTrim("connectSizeAvailable"));
        }
        if (!StringUtil.isEmpty(element.elementTextTrim("poolDriverClass"))) {
            dataDefine.setPoolDriverClass(element.elementTextTrim("poolDriverClass"));
        }
        if (!StringUtil.isEmpty(element.elementTextTrim("showSql"))) {
            dataDefine.setShowSql(Boolean.valueOf(element.elementTextTrim("showSql")));
        }
        if (!StringUtil.isEmpty(element.elementTextTrim("logName"))) {
            dataDefine.setLogName(element.elementTextTrim("logName"));
        }
        if (!StringUtil.isEmpty(element.elementTextTrim("testSql"))) {
            dataDefine.setTestSql(element.elementTextTrim("testSql"));
        }

        Map<String, String> conMap = new HashMap<String, String>();
        if (element.element("constantMap") != null) {
            Element elementP = element.element("constantMap");
            for (Attribute at : (List<Attribute>) elementP.attributes()) {
                if ("showSql".equals(at.getName())) {
                    dataDefine.setShowSql(Boolean.valueOf(at.getValue()));
                } else if ("logName".equals(at.getName())) {
                    dataDefine.setLogName(at.getValue());
                }
            }
            List<Element> keyValue = elementP.elements("param");
            for (Element element2 : keyValue) {
                if (element2.attribute("key") != null) {
                    if (element2.attribute("value") != null) {
                        conMap.put(element2.attribute("key").getStringValue(), element2.attribute("value").getStringValue());
                    } else {
                        conMap.put(element2.attribute("key").getStringValue(), "");
                    }
                }
            }
        }
        dataDefine.setConstantMap(conMap);
        return dataDefine;
    }

    public Map<String, DatabaseDefine> getDatabaseDefines() {
        return this.databaseDefines;
    }

    public Map<String, DatabaseDefine> getLinkDatabaseDefines() {
        return linkDatabaseDefines;
    }

    public void setLinkDatabaseDefines(Map<String, DatabaseDefine> linkDatabaseDefines) {
        this.linkDatabaseDefines = linkDatabaseDefines;
    }

}
