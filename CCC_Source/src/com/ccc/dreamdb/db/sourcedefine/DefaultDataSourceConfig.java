package com.ccc.dreamdb.db.sourcedefine;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;

import com.ccc.dreamfile.xml.FileXmlUtil;

/**
 * @author RedSword
 * @date 2011-07-26 20:26:55
 * @version 2.0
 */

public class DefaultDataSourceConfig {
	public final Map<String, String> configMap = getConfigMap();
	
	public Map<String, String> getConfigMap() {
		InputStream in = this.getClass().getResourceAsStream("/com/jhplatform/config/DefaultDataBaseConfig.xml");
		Map<String, String> returnMap = new HashMap<String, String>();
		try {
			Document doc = FileXmlUtil.getDocument(in);
			returnMap.put("connectSizeMax", FileXmlUtil.getNodeValue(doc, "database-source/connectSizeMax"));
			returnMap.put("connectSizeMin", FileXmlUtil.getNodeValue(doc, "database-source/connectSizeMin"));
			returnMap.put("connectSizeIncreament", FileXmlUtil.getNodeValue(doc, "database-source/connectSizeIncreament"));
			returnMap.put("connectSizeAvailable", FileXmlUtil.getNodeValue(doc, "database-source/connectSizeAvailable"));
			returnMap.put("connectFreeTimeMax", FileXmlUtil.getNodeValue(doc, "database-source/connectFreeTimeMax"));
			returnMap.put("connectActiveTimeMax", FileXmlUtil.getNodeValue(doc, "database-source/connectActiveTimeMax"));
			returnMap.put("poolDriverClass", FileXmlUtil.getNodeValue(doc, "database-source/poolDriverClass"));
			returnMap.put("testSql", FileXmlUtil.getNodeValue(doc, "database-source/testSql"));
			List<Map<String, String>> lists = FileXmlUtil.getXmlNodeList(doc, "database-source/constantMap");
			for (Map<String, String> map : lists) {
				returnMap.put("showSql", map.get("showSql"));
				returnMap.put("logName", map.get("logName"));
			}
			return returnMap;
		}
		catch (Exception e) {
			e.printStackTrace();
			return returnMap;
		}
	}
	
}
