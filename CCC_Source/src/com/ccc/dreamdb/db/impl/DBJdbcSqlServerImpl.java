package com.ccc.dreamdb.db.impl;

import com.ccc.dreamdb.db.DBJdbcSqlServer;
import com.ccc.dreamdb.db.sourcedefine.DatabaseDefine;

/**
 * @author RedSword
 * @date 2011-10-17 22:27:47
 * @version 2.0
 */

public class DBJdbcSqlServerImpl extends ImplAbstract implements DBJdbcSqlServer {

    public DBJdbcSqlServerImpl(String databaseSource, DatabaseDefine databaseDefine) {
        super(databaseSource, databaseDefine);
    }

}
