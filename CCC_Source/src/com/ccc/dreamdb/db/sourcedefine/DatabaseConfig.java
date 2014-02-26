package com.ccc.dreamdb.db.sourcedefine;

/**
 * 用来获取数据库连接的bean
 * 
 * @author RedSword
 * @date 2011-05-22 20:15:57
 * @version 2.0
 */

public class DatabaseConfig implements Cloneable {
    // 1：sqlserver 2：oracle 3：mysql 4：db2
    public int databaseType = 1;
    public String driverUrl = null;
    public String driverClass = null;
    public String user = null;
    public String password = null;
    boolean showSql = false;

    /**
     * @return the databaseType
     */
    public int getDatabaseType() {
        return this.databaseType;
    }

    /**
     * @param databaseType
     *            the databaseType to set
     */
    public void setDatabaseType(int databaseType) {
        this.databaseType = databaseType;
    }

    /**
     * @return the driverUrl
     */
    public String getDriverUrl() {
        return this.driverUrl;
    }

    /**
     * @param driverUrl
     *            the driverUrl to set
     */
    public void setDriverUrl(String driverUrl) {
        this.driverUrl = driverUrl;
    }

    /**
     * @return the driverClass
     */
    public String getDriverClass() {
        return this.driverClass;
    }

    /**
     * @param driverClass
     *            the driverClass to set
     */
    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return this.user;
    }

    /**
     * @param user
     *            the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the showSql
     */
    public boolean isShowSql() {
        return this.showSql;
    }

    /**
     * @param showSql
     *            the showSql to set
     */
    public void setShowSql(boolean showSql) {
        this.showSql = showSql;
    }

}
