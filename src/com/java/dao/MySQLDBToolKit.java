package com.java.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySQLDBToolKit {

    /**
     * 数据库驱动类名称
     */
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    /**
     * 连接字符串
     */
    private static final String URLSTR = "jdbc:mysql://localhost:3306/codecow?useSSL=false&serverTimezone=CST&characterEncoding=utf8";

    /**
     * 用户名
     */
    private static final String USERNAME = "root";

    /**
     * 密码
     */
    private static final String USERPASSWORD = "123456";

    static {
        try {
            // 加载数据库驱动程序
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("=== JDBC驱动加载失败 ===");
            e.printStackTrace();
        }
    }

    /**
     * 建立数据库连接
     * @return Connection对象
     * @throws SQLException 如果连接失败
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URLSTR, USERNAME, USERPASSWORD);
    }

    /**
     * 执行带参数的增删改语句
     * @param sql SQL语句
     * @param params 参数数组
     * @return 受影响的行数
     */
    public static int executeUpdate(String sql, Object[] params) {
        int affectedLine = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            
            affectedLine = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("=== 执行更新语句失败 ===");
            e.printStackTrace();
        } finally {
            close(conn, pstmt, null);
        }
        
        return affectedLine;
    }

    /**
     * 执行不带参数的查询语句
     * @param sql SQL语句
     * @return List结果集
     */
    public static List<Map<String,Object>> executeQuery(String sql) {
        return executeQuery(sql, new Object[0]);
    }

    /**
     * 执行带参数的查询语句
     * @param sql SQL语句
     * @param params 参数数组
     * @return List结果集
     */
    public static List<Map<String,Object>> executeQuery(String sql, Object[] params) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Map<String,Object>> list = new ArrayList<>();
        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            
            rs = pstmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            
            while (rs.next()) {
                Map<String, Object> map = new HashMap<String, Object>();
                for (int i = 1; i <= columnCount; i++) {
                    map.put(rsmd.getColumnLabel(i), rs.getObject(i));
                }
                list.add(map);
            }
        } catch (SQLException e) {
            System.err.println("=== 执行查询语句失败 ===");
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        
        return list;
    }

    /**
     * 关闭数据库资源
     * @param conn 连接对象
     * @param pstmt 语句对象
     * @param rs 结果集对象
     */
    private static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}