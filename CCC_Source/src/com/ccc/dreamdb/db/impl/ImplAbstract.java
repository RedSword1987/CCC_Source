package com.ccc.dreamdb.db.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.KeyedHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.ccc.dreamdb.db.DBJdbc;
import com.ccc.dreamdb.db.DBUtil;
import com.ccc.dreamdb.db.sourcedefine.DatabaseDefine;
import com.ccc.dreamlog.LogDream;
import com.ccc.dreamtag.tag.function.StringUtil;

public class ImplAbstract implements DBJdbc {

	protected String databaseSource = "";
	protected boolean showSql = false;
	protected DatabaseDefine databaseDefine;
	protected static QueryRunner queryRunner = new QueryRunner();

	public ImplAbstract(String databaseSource, DatabaseDefine databaseDefine) {
		if ("".equals(databaseDefine.getLinkto())) {
			this.databaseSource = databaseSource;
		} else {
			this.databaseSource = databaseDefine.getLinkto();
		}
		this.databaseDefine = databaseDefine;
		this.showSql = databaseDefine.isShowSql();
	}

	/*
	 * (non-Javadoc)
	 * @see com.jhplatform.dreamdb.db.DBJdbc#executeBoolean(java.lang.String,
	 * java.lang.Object[])
	 */
	@Override
	public boolean executeBoolean(String updateSql, Object... objects) {
		Connection conn = getConnection(this.databaseSource);
		try {
			if (this.showSql) {
				if (objects == null || objects.length == 0) {
					LogDream.info(updateSql);
				} else {
					LogDream.info(updateSql + "   " + StringUtil.convertObjectsTostring(objects));
				}
			}
			queryRunner.update(conn, updateSql, objects);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DbUtils.close(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public int execute(String updateSql, Object... objects) {
		int result = -1;
		Connection conn = getConnection(this.databaseSource);
		try {
			if (this.showSql) {
				if (objects == null || objects.length == 0) {
					LogDream.info(updateSql);
				} else {
					LogDream.info(updateSql + "   " + StringUtil.convertObjectsTostring(objects));
				}
			}
			result = queryRunner.update(conn, updateSql, objects);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DbUtils.close(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public boolean executeBatchParas(String sql, Object[][] objects, boolean roolBack) {
		Connection conn = getConnection(this.databaseSource);
		boolean autosubmit = true;
		PreparedStatement ptmt = null;
		try {
			autosubmit = conn.getAutoCommit();
			if (roolBack) {
				conn.setAutoCommit(false);
			} else {
				conn.setAutoCommit(true);
			}
			ptmt = conn.prepareStatement(sql);
			if (this.showSql) {
				if (objects == null || objects.length == 0) {
					LogDream.info(sql);
				} else {
					LogDream.info(sql + "   " + StringUtil.convertObjectsTostring(objects));
				}
			}

			for (Object[] objects2 : objects) {
				int i = 1;
				for (Object object : objects2) {
					ptmt.setObject(i++, object);
				}
				ptmt.addBatch();
			}
			ptmt.executeBatch();
			if (roolBack) {
				conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				if (roolBack) {
					conn.rollback();
				}
			} catch (Exception ee) {
			}
			return false;
		} finally {
			try {
				if (ptmt != null) {
					DbUtils.close(ptmt);
				}
				conn.setAutoCommit(autosubmit);
				DbUtils.close(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	@Override
	public boolean executeBatchSqls(String[] sql, boolean roolback) {
		Connection conn = getConnection(this.databaseSource);
		boolean autosubmit = true;
		Statement smtpStatement = null;
		try {
			autosubmit = conn.getAutoCommit();
			if (roolback) {
				conn.setAutoCommit(false);
			} else {
				conn.setAutoCommit(true);
			}
			smtpStatement = conn.createStatement();
			for (String sqlS : sql) {
				if (this.showSql) {
					LogDream.info(sqlS);
					LogDream.info(StringUtil.convertObjectsTostring(sqlS));
				}
				smtpStatement.addBatch(sqlS);
			}
			smtpStatement.executeBatch();
			if (roolback) {
				conn.commit();
			}
		} catch (SQLException e) {
			try {
				if (roolback) {
					conn.rollback();
				}
			} catch (Exception ee) {
			}
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (smtpStatement != null) {
					DbUtils.close(smtpStatement);
				}
				conn.setAutoCommit(autosubmit);
				DbUtils.close(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	@Override
	public <T> T queryBean(String sql, Class<T> cls, Object... objects) {
		T tObj;
		Connection conn = getConnection(this.databaseSource);
		try {
			if (this.showSql) {
				if (objects == null || objects.length == 0) {
					LogDream.info(sql);
				} else {
					LogDream.info(sql + "   " + StringUtil.convertObjectsTostring(objects));
				}
			}
			tObj = queryRunner.query(conn, sql, new BeanHandler<T>(cls), objects);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				DbUtils.close(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return tObj;
	}

	@Override
	public Map<String, Object> queryBeanPage(String sql, Integer pageNo, Integer PageSize, Class<?> cls, Object... objects) {
		if (this.showSql) {
			if (objects == null || objects.length == 0) {
				LogDream.info(sql);
			} else {
				LogDream.info(sql + "   " + StringUtil.convertObjectsTostring(objects));
			}
		}
		Map<String, Object> map = new HashMap<String, Object>() {
			private static final long serialVersionUID = 0L;
			{
				put("ALLCOUNT", 0);
				put("PAGELIST", new ArrayList<Map<String, Object>>());
			}
		};
		StringBuilder sBuilder = new StringBuilder("select count(*) as ALLCOUNT from (");
		sBuilder.append(sql).append(")");
		Map<String, Object> mapTemp = queryMap(sBuilder.toString(), objects);
		sBuilder.setLength(0);
		sBuilder.append("select * from (select *,rownum row_num from (").append(sql).append(")  where rownum<=").append(pageNo * PageSize).append(") where   row_num>=").append((pageNo - 1) * PageSize);
		List<?> lists = queryListBean(sql, cls, objects);
		map.put("ALLCOUNT", mapTemp.get("ALLCOUNT"));
		map.put("PAGELIST", lists);
		return map;
	}

	@Override
	public Map<Object, Map<String, Object>> queryKeyMap(String sql, String keyName, Object... objects) {

		Map<Object, Map<String, Object>> map;
		Connection conn = getConnection(this.databaseSource);
		try {
			if (this.showSql) {
				if (objects == null || objects.length == 0) {
					LogDream.info(sql);
				} else {
					LogDream.info(sql + "   " + StringUtil.convertObjectsTostring(objects));
				}
			}
			KeyedHandler<Object> sssHandler = new KeyedHandler(keyName);
			map = queryRunner.query(conn, sql, sssHandler, objects);
		} catch (SQLException e) {
			e.printStackTrace();
			return new HashMap<Object, Map<String, Object>>();
		} finally {
			try {
				DbUtils.close(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return map;
	}

	@Override
	public <T> List<T> queryListBean(String sql, Class<T> cls, Object... objects) {
		List<T> tObj;
		Connection conn = getConnection(this.databaseSource);
		try {
			if (this.showSql) {
				if (objects == null || objects.length == 0) {
					LogDream.info(sql);
				} else {
					LogDream.info(sql + "   " + StringUtil.convertObjectsTostring(objects));
				}
			}
			tObj = queryRunner.query(conn, sql, new BeanListHandler<T>(cls), objects);
		} catch (SQLException e) {
			e.printStackTrace();
			return new LinkedList<T>();
		} finally {
			try {
				DbUtils.close(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return tObj;
	}

	@Override
	public List<Map<String, Object>> queryListMap(String sql, Object... objects) {
		List<Map<String, Object>> results = null;
		Connection conn = getConnection(this.databaseSource);
		try {
			if (this.showSql) {
				if (objects == null || objects.length == 0) {
					LogDream.info(sql);
				} else {
					LogDream.info(sql + "   " + StringUtil.convertObjectsTostring(objects));
				}
			}
			results = queryRunner.query(conn, sql, new MapListHandler(), objects);
		} catch (SQLException e) {
			e.printStackTrace();
			results = new LinkedList<Map<String, Object>>();
		} finally {
			try {
				DbUtils.close(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return results;
	}

	@Override
	public Map<String, Object> queryMap(String sql, Object... objects) {
		Map<String, Object> map;
		Connection conn = getConnection(this.databaseSource);
		try {
			if (this.showSql) {
				if (objects == null || objects.length == 0) {
					LogDream.info(sql);
				} else {
					LogDream.info(sql + "   " + StringUtil.convertObjectsTostring(objects));
				}
			}
			map = queryRunner.query(conn, sql, new MapHandler(), objects);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				DbUtils.close(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}

	@Override
	public Map<String, Object> queryMapPage(String sql, Integer pageNo, Integer PageSize, Object... objects) {
		if (this.showSql) {
			if (objects == null || objects.length == 0) {
				LogDream.info(sql);
			} else {
				LogDream.info(sql + "   " + StringUtil.convertObjectsTostring(objects));
			}
		}
		Map<String, Object> map = new HashMap<String, Object>() {
			private static final long serialVersionUID = 0L;
			{
				put("ALLCOUNT", 0);
				put("PAGELIST", new ArrayList<Map<String, Object>>());
			}
		};
		StringBuilder sBuilder = new StringBuilder("select count(*) as ALLCOUNT from (");
		sBuilder.append(sql).append(")");
		Map<String, Object> mapTemp = queryMap(sBuilder.toString(), objects);
		sBuilder.setLength(0);
		sBuilder.append("select * from (select *,rownum row_num from (").append(sql).append(")  where rownum<=").append(pageNo * PageSize).append(") where   row_num>=").append((pageNo - 1) * PageSize);
		List<Map<String, Object>> lists = queryListMap(sql, objects);
		map.put("ALLCOUNT", mapTemp.get("ALLCOUNT"));
		map.put("PAGELIST", lists);
		return map;
	}

	@Override
	public DatabaseDefine getDatabaseDefine() {
		return this.databaseDefine;
	}

	@Override
	public Connection getConnection(String databaseSource) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("proxool." + databaseSource);
			if (conn == null) {
				System.out.println("con is wrong");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	@Override
	public boolean callProc(String procName, Object... objects) {
		boolean result = false;
		if (this.showSql) {
			if (objects == null || objects.length == 0) {
				LogDream.info(procName);
			} else {
				LogDream.info(procName + "   " + StringUtil.convertObjectsTostring(objects));
			}
		}
		Connection conn = getConnection(this.databaseSource);
		try {
			CallableStatement proc = conn.prepareCall(DBUtil.getProcCallDesc(procName, objects));
			if (objects != null && objects.length > 0) {
				int l = objects.length;
				while (l > 0) {
					proc.setObject(l, objects[l - 1]);
					l--;
				}
			}
			result = proc.execute();
			proc.close();
			proc = null;
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		} finally {
			try {
				DbUtils.close(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see com.jhplatform.dreamdb.db.DBJdbc#insertForId(java.lang.String,
	 * java.lang.Object[])
	 */
	@Override
	public Long insertForId(String insertSql, Object... objects) {
		Long generate = null;
		if (this.databaseDefine.getDatabaseType() == 1) {
			if (this.showSql) {
				LogDream.info(insertSql);
				LogDream.info(StringUtil.convertObjectsTostring(objects));
			}
			Connection conn = getConnection(this.databaseSource);
			PreparedStatement prement = null;
			try {
				prement = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
				if (objects != null && objects.length != 0) {
					for (int i = 0; i < objects.length; i++) {
						prement.setObject(i + 1, objects[i]);
					}
				}
				prement.executeUpdate();
				ResultSet rs = prement.getGeneratedKeys();
				rs.next();
				generate = rs.getLong(1);
				DbUtils.close(rs);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					DbUtils.close(prement);
					DbUtils.close(conn);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else if (this.databaseDefine.getDatabaseType() == 2) {
		} else if (this.databaseDefine.getDatabaseType() == 3) {
		} else {
		}
		return generate;
	}

}
