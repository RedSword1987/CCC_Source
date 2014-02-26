package com.ccc.dreamdb.db.impl;

import com.ccc.dreamdb.db.DBJdbcOracle;
import com.ccc.dreamdb.db.sourcedefine.DatabaseDefine;

public class DBJdbcOracleImpl extends ImplAbstract implements DBJdbcOracle {

    public DBJdbcOracleImpl(String databaseSource, DatabaseDefine databaseDefine) {
        super(databaseSource, databaseDefine);
    }

}
