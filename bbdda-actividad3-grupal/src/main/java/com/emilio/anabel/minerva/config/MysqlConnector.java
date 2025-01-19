package com.emilio.anabel.minerva.config;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * Clase que realiza la conexión con la base de datos MySQL.
 *
 * @version 1.0 - 2025-01-15
 * @author Emilio, Anabel, Minerva
 */
@Slf4j
public class MysqlConnector {

    private static final String DB_NAME     = System.getenv("MYSQL_DB_NAME");
    private static final String DB_HOST     = System.getenv("MYSQL_DB_HOST");
    private static final String DB_USER     = System.getenv("MYSQL_DB_USER");
    private static final String DB_PASSWORD = System.getenv("MYSQL_DB_PASSWORD");
    private static final String DB_PORT     = System.getenv("MYSQL_DB_PORT");
    private static final String DB_URL      = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;
    private static final String DB_DRIVER   = "com.mysql.cj.jdbc.Driver";

    private final Connection connection;

    public MysqlConnector() throws SQLException {
        //creamos la conexion a la base de datos
        try {
            Class.forName(DB_DRIVER);
            this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            log.info("Connection established with MySQL database.");
        } catch (SQLException | ClassNotFoundException e) {
            log.error("Error conectando en la base de datos", e);
            throw new RuntimeException("Error conectando en la base de datos", e);
        }
    }

    /**
     * Devuelve la conexión a la base de datos.
     * @return : connection
     */
    public Connection getConnection() {
        return this.connection;
    }
}



