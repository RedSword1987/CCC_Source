package com.ccc.dreamdb.db;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.logicalcobwebs.proxool.ProxoolFacade;

import com.ccc.dreamdb.db.impl.DBJdbcOracleImpl;
import com.ccc.dreamdb.db.impl.DBJdbcSqlServerImpl;
import com.ccc.dreamdb.db.impl.DefaultImpl;
import com.ccc.dreamdb.db.sourcedefine.DatabaseDefine;
import com.ccc.dreamdb.db.sourcedefine.DatabaseSources;
import com.ccc.dreamlog.LogDream;

public class DBFactory {
    static final String PARAM_DATABASESOOURCE = "DataBase.xml";
    static DatabaseSources databasesources = new DatabaseSources(PARAM_DATABASESOOURCE);
    static Map<String, DBJdbc> DbJdbcMap = new HashMap<String, DBJdbc>();
    static Map<String, DBJdbcOracle> DbJdbcOracleMap = new HashMap<String, DBJdbcOracle>();
    static Map<String, DBJdbcSqlServer> DbJdbcSqlServer = new HashMap<String, DBJdbcSqlServer>();
    static {
        try {
            Map<String, DatabaseDefine> lists = databasesources.getDatabaseDefines();
            for (Entry<String, DatabaseDefine> en : lists.entrySet()) {
                Class.forName(en.getValue().getPoolDriverClass());
                Properties info = new Properties();
                info.setProperty("proxool.minimum-connection-count", en.getValue().getConnectSizeMin());
                info.setProperty("proxool.maximum-connection-count", en.getValue().getConnectSizeMax());
                info.setProperty("proxool.maximum-active-time", en.getValue().getConnectActiveTimeMax());
                info.setProperty("proxool.house-keeping-test-sql", en.getValue().getTestSql());

                info.setProperty("user", en.getValue().getUser());
                info.setProperty("password", en.getValue().getPassword());
                String url = "proxool." + en.getValue().getName() + ":" + en.getValue().getDriverClass() + ":" + en.getValue().getDriverUrl();
                ProxoolFacade.registerConnectionPool(url, info);
            }

            for (Entry<String, DatabaseDefine> en : lists.entrySet()) {
                if (1 == (en.getValue().getDatabaseType())) {
                    DBJdbcSqlServer dbJdbcSqlServer = createFactoryDBJdbcSqlServer(en.getValue());
                    DbJdbcSqlServer.put(en.getKey(), dbJdbcSqlServer);
                    DbJdbcMap.put(en.getKey(), dbJdbcSqlServer);
                } else if (2 == (en.getValue().getDatabaseType())) {
                    DBJdbcOracle dbJdbcSqlServer = createFactoryDBJdbcOracle(en.getValue());
                    DbJdbcOracleMap.put(en.getKey(), dbJdbcSqlServer);
                    DbJdbcMap.put(en.getKey(), dbJdbcSqlServer);
                } else {
                    DbJdbcMap.put(en.getKey(), createFactoryDbJdbc(en.getValue()));
                }
            }
            for (Entry<String, DatabaseDefine> en : databasesources.getLinkDatabaseDefines().entrySet()) {
                if (1 == (en.getValue().getDatabaseType())) {
                    DBJdbcSqlServer dbJdbcSqlServer = createFactoryDBJdbcSqlServer(en.getValue());
                    DbJdbcSqlServer.put(en.getKey(), dbJdbcSqlServer);
                    DbJdbcMap.put(en.getKey(), dbJdbcSqlServer);
                } else if (2 == (en.getValue().getDatabaseType())) {
                    DBJdbcOracle dbJdbcSqlServer = createFactoryDBJdbcOracle(en.getValue());
                    DbJdbcOracleMap.put(en.getKey(), dbJdbcSqlServer);
                    DbJdbcMap.put(en.getKey(), dbJdbcSqlServer);
                } else {
                    DbJdbcMap.put(en.getKey(), createFactoryDbJdbc(en.getValue()));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            LogDream.error("datawource load error");
        }

    }

    protected DBFactory() {
        //
    }

    public static DBJdbc getDBJdbc() {
        return getDBJdbc("default");
    }

    public static DBJdbc getDBJdbc(String sourName) {
        return DbJdbcMap.get(sourName);
    }

    public static DBJdbcOracle getDBJdbcOracle(String sourName) {
        return DbJdbcOracleMap.get(sourName);
    }

    public static DBJdbcSqlServer getDBJdbcSqlServer(String sourName) {
        return DbJdbcSqlServer.get(sourName);
    }

    private static DBJdbc createFactoryDbJdbc(DatabaseDefine dd) {
        DBJdbc dbJdbc = new DefaultImpl(dd.getName(), dd);
        return dbJdbc;
    }

    private static DBJdbcOracle createFactoryDBJdbcOracle(DatabaseDefine dd) {
        DBJdbcOracle dbJdbc = new DBJdbcOracleImpl(dd.getName(), dd);
        return dbJdbc;
    }

    private static DBJdbcSqlServer createFactoryDBJdbcSqlServer(DatabaseDefine dd) {
        DBJdbcSqlServer dbJdbc = new DBJdbcSqlServerImpl(dd.getName(), dd);
        return dbJdbc;
    }

}
