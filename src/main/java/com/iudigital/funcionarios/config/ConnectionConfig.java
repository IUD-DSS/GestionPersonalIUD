package com.iudigital.funcionarios.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionConfig {

    private static final String URL = "jdbc:mariadb://localhost:3306/gestion_funcionarios";
    private static final String USER = "root";
    private static final String PASSWORD = "12345";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
