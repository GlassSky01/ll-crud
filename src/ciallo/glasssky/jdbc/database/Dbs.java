package ciallo.glasssky.jdbc.database;

import java.sql.*;

public class Dbs {
    private static String user;
    private static String password;
    private static final String url = "jdbc:mysql://localhost:3306/";
    private static Connection conn = null;
    private static Statement state = null;

    public static void setInfo(String user, String password) {
        Dbs.user = user;
        Dbs.password = password;
    }

    public static Connection getConnection() throws SQLException {
        if (conn != null)
            return conn;
//        System.out.println(user + " " + password);
        conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    public static Statement getStatement() throws SQLException {
        if (state != null)
            return state;
        conn = getConnection();
        state = conn.createStatement();
        return state;
    }

    public static void initDb() throws SQLException {
        DbOperates.setState(getStatement());

        //创建数据库
        state.execute("create database if not exists glassskydb;");

        //用户表
        state.execute("create table if not exists glassskydb.users(\n" +
                "    name varchar(20),\n" +
                "    user varchar(20) primary key,\n" +
                "    password varchar(20)\n" +
                ");");

        //账户
        state.execute("create table if not exists glassskydb.account(\n" +
                "    account varchar(20) ,\n" +
                "    user varchar(20),\n" +
                "    money int,\n" +
                "    enable int ,\n" +
                "    foreign key (user) references glassskydb.users(user) ON DELETE CASCADE,\n" +
                "    primary key(account , user )\n" +
                ");");


        //科目
        state.execute("create table if not exists glassskydb.subject(\n" +
                "    subject varchar(20) ,\n" +
                "    class varchar(20) ,\n" +
                "    user varchar(20),\n" +
                "    enable int,\n" +
                "    foreign key (user) references glassskydb.users(user) ON DELETE CASCADE,\n" +
                "    primary key(subject , user  )\n" +
                ");");


        //记账
        state.execute("create table if not exists glassskydb.billing(\n" +
                "    money int,\n" +
                "    class varchar(20),\n" +
                "    account varchar(20),\n" +
                "    subject varchar(20),\n" +
                "    date date,\n" +
                "    remark varchar(20),\n" +
                "    user varchar(20),\n" +
                "    foreign key (account) references glassskydb.account(account) ON DELETE CASCADE,\n" +
                "    foreign key (subject ) references glassskydb.subject(subject) ON DELETE CASCADE,\n" +
                "    foreign key (user) references glassskydb.users(user) ON DELETE CASCADE\n" +
                ");");
    }


}
