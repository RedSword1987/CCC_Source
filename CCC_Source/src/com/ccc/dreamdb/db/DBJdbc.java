package com.ccc.dreamdb.db;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.ccc.dreamdb.db.sourcedefine.DatabaseDefine;

/**
 * @author RedSword(416448382@qq.com)
 * @date 2011-11-14 21:59:22
 */
public interface DBJdbc {
    public DatabaseDefine getDatabaseDefine();

    /**
     * @param sql
     * @param objects
     * @return
     */
    public List<Map<String, Object>> queryListMap(String sql, Object... objects);

    /**
     * @param sql
     * @param objects
     * @return
     */
    public <T> List<T> queryListBean(String sql, Class<T> cls, Object... objects);

    /**
     * just for oracle
     * 
     * @param sql
     * @param objects
     * @return <br/>
     *         <ul>
     *         <li>put("ALLCOUNT","all count")</li>
     *         <li>put("PAGELIST","List Bean")</li>
     *         </ul>
     * @deprecated
     */
    @Deprecated
    public Map<String, Object> queryBeanPage(String sql, Integer pageNo, Integer PageSize, Class<?> cls, Object... objects);

    /**
     * @param sql
     * @param objects
     * @return <ul>
     *         <li>put("ALLCOUNT","all count")</li>
     *         <li>put("PAGELIST","List Map")</li>
     *         </ul>
     * @deprecated
     */
    @Deprecated
    public Map<String, Object> queryMapPage(String sql, Integer pageNo, Integer PageSize, Object... objects);

    /**
     * @param sql
     * @param keyName
     * @param objects
     * @return
     * @author RedSword
     * @date 2011-9-5 8:01:31
     */
    public Map<Object, Map<String, Object>> queryKeyMap(String sql, String keyName, Object... objects);

    /**
     * @param sql
     * @param objects
     * @return
     * @author RedSword
     * @date 2011-9-5 8:01:24
     */
    public Map<String, Object> queryMap(String sql, Object... objects);

    /**
     * @param sql
     * @param cls
     * @param objects
     * @return
     * @author RedSword
     * @date 2011-9-5 8:01:18
     */
    public <T> T queryBean(String sql, Class<T> cls, Object... objects);

    /**
     * @param updateSql
     * @param objects
     * @return
     * @author RedSword
     * @date 2011-9-5 8:01:13
     */
    public int execute(String updateSql, Object... objects);

    /**
     * @param updateSql
     * @param objects
     * @return
     * @author RedSword
     * @date 2011-9-5 8:01:13
     */
    public boolean executeBoolean(String updateSql, Object... objects);

    /**
     * @param sql
     * @param objects
     * @return
     * @author RedSword
     * @date 2011-9-5 8:01:07
     */
    public boolean executeBatchParas(String sql, Object[][] objects, boolean roolBack);

    /**
     * @param sql
     * @return
     * @author RedSword
     * @date 2011-9-5 8:00:59
     */
    public boolean executeBatchSqls(String[] sql, boolean roolBack);

    /**
     * @param sql
     * @return
     * @author RedSword
     * @date 2011-9-5 8:00:59
     */
    public Long insertForId(String insertSql, Object... objects);

    /**
     * @param databaseSource
     * @return
     * @author RedSword
     * @date 2011-9-5 8:00:46
     */
    public Connection getConnection(String databaseSource);

    /**
     * @param procName
     * @param objects
     * @author RedSword
     * @date 2011-9-20 10:37:51
     */
    public boolean callProc(String procName, Object... objects);

}
