package com.ccc.dreamdb.db.sourcedefine;

import java.util.Map;

/**
 * 用来获取数据库连接的bean
 * 
 * @author RedSword
 * @date 2011-05-22 20:15:57
 * @version 2.0
 */

public class DatabaseDefine implements Cloneable {
    static DefaultDataSourceConfig ddsConfig = new DefaultDataSourceConfig();
    private String name;
    private String linkto = "";
    // 1：sqlserver 2：oracle 3：mysql 4：db2
    private int databaseType;
    private String driverUrl;
    private String driverClass;
    private String user;
    private String password;
    private String connectSizeMax = ddsConfig.getConfigMap().get("connectSizeMax");
    private String connectSizeMin = ddsConfig.getConfigMap().get("connectSizeMin");
    private String connectSizeIncreament = ddsConfig.getConfigMap().get("connectSizeIncreament");
    private String connectSizeAvailable = ddsConfig.getConfigMap().get("connectSizeAvailable");
    private String connectFreeTimeMax = ddsConfig.getConfigMap().get("connectFreeTimeMax");
    private String connectActiveTimeMax = ddsConfig.getConfigMap().get("connectActiveTimeMax");
    private String poolDriverClass = ddsConfig.getConfigMap().get("poolDriverClass");
    private String testSql = ddsConfig.getConfigMap().get("poolDriverClass");
    private boolean showSql = Boolean.valueOf(ddsConfig.getConfigMap().get("showSql"));
    private String logName = ddsConfig.getConfigMap().get("showSql");
    private Map<String, String> constantMap = null;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDriverUrl() {
        return this.driverUrl;
    }

    public void setDriverUrl(String driverUrl) {
        this.driverUrl = driverUrl;
    }

    public String getDriverClass() {
        return this.driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConnectSizeMax() {
        return this.connectSizeMax;
    }

    public void setConnectSizeMax(String connectSizeMax) {
        this.connectSizeMax = connectSizeMax;
    }

    public String getConnectSizeMin() {
        return this.connectSizeMin;
    }

    public void setConnectSizeMin(String connectSizeMin) {
        this.connectSizeMin = connectSizeMin;
    }

    public String getConnectSizeIncreament() {
        return this.connectSizeIncreament;
    }

    public void setConnectSizeIncreament(String connectSizeIncreament) {
        this.connectSizeIncreament = connectSizeIncreament;
    }

    public String getConnectSizeAvailable() {
        return this.connectSizeAvailable;
    }

    public void setConnectSizeAvailable(String connectSizeAvailable) {
        this.connectSizeAvailable = connectSizeAvailable;
    }

    public String getConnectFreeTimeMax() {
        return this.connectFreeTimeMax;
    }

    public void setConnectFreeTimeMax(String connectFreeTimeMax) {
        this.connectFreeTimeMax = connectFreeTimeMax;
    }

    public String getConnectActiveTimeMax() {
        return this.connectActiveTimeMax;
    }

    public void setConnectActiveTimeMax(String connectActiveTimeMax) {
        this.connectActiveTimeMax = connectActiveTimeMax;
    }

    public boolean isShowSql() {
        return this.showSql;
    }

    public void setShowSql(boolean showSql) {
        this.showSql = showSql;
    }

    public String getLogName() {
        return this.logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public String getPoolDriverClass() {
        return this.poolDriverClass;
    }

    public void setPoolDriverClass(String poolDriverClass) {
        this.poolDriverClass = poolDriverClass;
    }

    public String getTestSql() {
        return this.testSql;
    }

    public void setTestSql(String testSql) {
        this.testSql = testSql;
    }

    public int getDatabaseType() {
        return this.databaseType;
    }

    public void setDatabaseType(int databaseType) {
        this.databaseType = databaseType;
    }

    public Map<String, String> getConstantMap() {
        return this.constantMap;
    }

    public void setConstantMap(Map<String, String> constantMap) {
        this.constantMap = constantMap;
    }

    public String getLinkto() {
        return linkto;
    }

    public void setLinkto(String linkto) {
        this.linkto = linkto;
    }

    public DatabaseDefine clone() {
        try {
            DatabaseDefine dDatabaseDefine = (DatabaseDefine) super.clone();
            return dDatabaseDefine;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
