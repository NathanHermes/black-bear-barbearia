package br.ifsp.edu.blackbearbarbearia.application.repository.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionFactory {
    private static Connection CONNECTION;

    public static Connection createConnection() throws SQLException {
        if(CONNECTION == null)
            CONNECTION = DriverManager.getConnection("jdbc:sqlite:blackbearbarbearia.db");
        return CONNECTION;
    }

    public static PreparedStatement createPreparedStatement(String sql) throws SQLException {
        return createConnection().prepareStatement(sql);
    }
}
