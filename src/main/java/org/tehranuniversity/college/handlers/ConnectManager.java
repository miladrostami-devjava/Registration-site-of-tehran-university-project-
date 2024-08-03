package org.tehranuniversity.college.handlers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectManager {
    private static final String server ="localhost";
    private static final String username ="postgres";
    private static final String password ="Milad13711992*";
    private static final String dbname ="tehranCollege";
    private static final String port ="5432";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://" + server + ":" + port + "/" + dbname;
        return DriverManager.getConnection(url,username,password);
    }




}
