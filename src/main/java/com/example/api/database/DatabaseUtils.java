package com.example.api.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtils {
    public static ResultSet executeQuery(String query) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public static void executeUpdate(String query) throws SQLException {
        Connection connection = DatabaseConnector.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }
}
