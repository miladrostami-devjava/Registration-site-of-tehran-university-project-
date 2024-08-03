package org.tehranuniversity.college.checkdb;

import org.tehranuniversity.college.handlers.ConnectManager;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckDBPostgresql {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Connection connection = ConnectManager.getConnection();
        DatabaseMetaData dbm = connection.getMetaData();
        ResultSet tables = dbm.getTables(null, null, "universityStudent", null);
        if (tables.next()) {
            System.out.println("Table exists.");
        } else {
            System.out.println("Table does not exist.");
        }
        connection.close();


    }
}
