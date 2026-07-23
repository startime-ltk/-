package com.java.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class DBTest {
    public static void main(String[] args) {
        try {
            Connection conn = MySQLDBToolKit.getConnection();
            System.out.println("数据库连接成功！");
            conn.close();
        } catch (SQLException e) {
            System.err.println("数据库连接失败！");
            e.printStackTrace();
        }
    }
}