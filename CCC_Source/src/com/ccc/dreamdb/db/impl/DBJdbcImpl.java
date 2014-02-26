package com.ccc.dreamdb.db.impl;

import java.sql.Connection;
import java.sql.DriverManager;

import com.ccc.dreamdb.db.sourcedefine.DatabaseDefine;

public class DBJdbcImpl extends ImplAbstract {

    /**
     * @param databaseSource
     * @param databaseDefine
     */
    public DBJdbcImpl(String databaseSource, DatabaseDefine databaseDefine) {
        super(databaseSource, databaseDefine);
    }

    @Override
    public Connection getConnection(String databaseSource) {
        Connection conn = null;
        try {
            Class.forName(this.databaseDefine.getDriverClass());
            conn = DriverManager.getConnection(this.databaseDefine.getDriverUrl(), this.databaseDefine.getUser(), this.databaseDefine.getPassword());
            if (conn == null) {
                System.out.println("conn is wrong");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
