package com.ccc.dreamfile.access;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;

import com.ccc.dreamfile.handlefile.FileHandleI;

/**
 * @author RedSword
 * @date 2011-06-13 23:33:46
 * @version 2.0
 */

public class FileAccessUtil {

    /**
     * convert access 2003 file to List<List<Object[]>>:just like table<tr<td[]>>
     * 
     * @param filePath
     * @return l
     */
    public static List<List<Object[]>> convertListListStringArray2003(String filePath, String userName, String pwd) {
        QueryRunner queryRunner = new QueryRunner();
        List<List<Object[]>> list = new ArrayList<List<Object[]>>();
        List<String> tableNames = new LinkedList<String>();
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String url = "jdbc:odbc:Driver={MicroSoft Access Driver (*.mdb)};DBQ=" + filePath;
            Connection conn = DriverManager.getConnection(url, userName, pwd);
            DatabaseMetaData dbmd = conn.getMetaData();
            String[] type = { "TABLE" };
            ResultSet rs1 = dbmd.getTables(null, null, "%", type);
            while (rs1.next()) {
                tableNames.add(rs1.getString(3));
            }
            rs1.close();
            List<Object[]> lists;
            for (String string : tableNames) {
                lists = queryRunner.query(conn, "select * from " + string, new ArrayListHandler());
                list.add(lists);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * convert access 2003 file to List<List<Object[]>>:just like table<tr<td[]>>,then handle List<Object[]>
     * 
     * @param filePath
     * @return
     */
    public static void convertListListStringArray2003(String filePath, String userName, String pwd, FileHandleI filehandle) {
        QueryRunner queryRunner = new QueryRunner();
        List<String> tableNames = new LinkedList<String>();
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String url = "jdbc:odbc:Driver={MicroSoft Access Driver (*.mdb)};DBQ=" + filePath;
            Connection conn = DriverManager.getConnection(url, userName, pwd);
            DatabaseMetaData dbmd = conn.getMetaData();
            String[] type = { "TABLE" };
            ResultSet rs1 = dbmd.getTables(null, null, "%", type);
            while (rs1.next()) {
                tableNames.add(rs1.getString(3));
            }
            rs1.close();
            List<Object[]> lists;
            for (String string : tableNames) {
                lists = queryRunner.query(conn, "select * from " + string, new ArrayListHandler());
                filehandle.handleObject(lists);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * convert access 2007 file to List<List<Object[]>>:just like table<tr<td[]>>
     * 
     * @param filePath
     * @return
     */
    public static List<List<Object[]>> convertListListStringArray2007(String filePath, String userName, String pwd) {
        QueryRunner queryRunner = new QueryRunner();
        List<List<Object[]>> list = new ArrayList<List<Object[]>>();
        List<String> tableNames = new LinkedList<String>();
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String url = "jdbc:odbc:Driver={MicroSoft Access Driver (*.mdb, *.accdb)};DBQ=" + filePath;
            Connection conn = DriverManager.getConnection(url, userName, pwd);
            DatabaseMetaData dbmd = conn.getMetaData();
            String[] type = { "TABLE" };
            ResultSet rs1 = dbmd.getTables(null, null, "%", type);
            while (rs1.next()) {
                tableNames.add(rs1.getString(3));
            }
            rs1.close();
            List<Object[]> lists;
            for (String string : tableNames) {
                lists = queryRunner.query(conn, "select * from " + string, new ArrayListHandler());
                list.add(lists);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * convert access 2007 file to List<List<Object[]>>:just like table<tr<td[]>>,then handle List<Object[]>
     * 
     * @param filePath
     * @return
     */
    public static void convertListListStringArray2007(String filePath, String userName, String pwd, FileHandleI filehandle) {
        QueryRunner queryRunner = new QueryRunner();
        List<String> tableNames = new LinkedList<String>();
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            String url = "jdbc:odbc:Driver={MicroSoft Access Driver (*.mdb, *.accdb)};DBQ=" + filePath;
            Connection conn = DriverManager.getConnection(url, userName, pwd);
            DatabaseMetaData dbmd = conn.getMetaData();
            String[] type = { "TABLE" };
            ResultSet rs1 = dbmd.getTables(null, null, "%", type);
            while (rs1.next()) {
                tableNames.add(rs1.getString(3));
            }
            rs1.close();
            List<Object[]> lists;
            for (String string : tableNames) {
                lists = queryRunner.query(conn, "select * from " + string, new ArrayListHandler());
                filehandle.handleObject(lists);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
