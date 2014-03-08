package com.ccc.dreamdb.db.impl;

import java.sql.Connection;
import java.sql.DriverManager;

import com.ccc.dreamdb.db.sourcedefine.DatabaseConfig;
import com.ccc.dreamdb.db.sourcedefine.DatabaseDefine;
import com.ccc.dreamlog.LogDream;

public class DBJdbcImpl extends ImplAbstract {

	/**
	 * @param databaseDefine
	 */
	public DBJdbcImpl(DatabaseConfig databaseconfig) {
		super("", new DatabaseDefine());
		DatabaseDefine databaseDefine = new DatabaseDefine();
		databaseDefine.setDatabaseType(databaseconfig.getDatabaseType());
		databaseDefine.setDriverClass(databaseconfig.getDriverClass());
		databaseDefine.setDriverUrl(databaseconfig.getDriverUrl());
		databaseDefine.setUser(databaseconfig.getUser());
		databaseDefine.setPassword(databaseconfig.getPassword());
		this.databaseDefine = databaseDefine;
	}

	@Override
	public Connection getConnection(String databaseSource) {
		Connection conn = null;
		try {
			Class.forName(this.databaseDefine.getDriverClass());
			conn = DriverManager.getConnection(this.databaseDefine.getDriverUrl(), this.databaseDefine.getUser(), this.databaseDefine.getPassword());
			if (conn == null) {
				LogDream.error("Can not get connection");
			}
		} catch (Exception e) {
			LogDream.error("获取连接错误", e);
		}
		return conn;
	}
}
