package com.xgh.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionGenerator {
    private final  static String driver = "com.mysql.jdbc.Driver";
    //URL指向要访问的数据库名mydata
    private final static String url = "jdbc:mysql://localhost:3306/94train";
    //MySQL配置时的用户名
    private final static String user = "root";
    //MySQL配置时的密码
    //不同自己改!!!
    private static String password = "Ezio1234";

    public static Connection GetConnetct() throws SQLException, ClassNotFoundException {
        //加载驱动程序
        Class.forName(driver);
        //1.getConnection()方法，连接MySQL数据库！！
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;

    }

}
