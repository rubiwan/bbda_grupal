package com.emilio.anabel.minerva.util;

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

    private static final String DB_NAME     = System.getenv("BBDD_NAME");
    private static final String DB_HOST     = System.getenv("DB_HOST");
    private static final String DB_USER     = System.getenv("MYSQL_USER");
    private static final String DB_PASSWORD = System.getenv("MYSQL_PASSWORD");
    private static final String DB_PORT     = System.getenv("DB_PORT");
    private static final String DB_URL      = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME;
    private static final String DB_DRIVER   = "com.mysql.cj.jdbc.Driver";

    private Connection connection;

    /**
     * Constructor de la clase. Se conecta a la base de datos.
     *
     * @param setConnection : boolean
     */
    public MysqlConnector(boolean setConnection) {
        if (setConnection) connect();
    }

    /**
     * Constructor de la clase. Se conecta a la base de datos.
     *
     * @param columnsList : List<String>
     * @param setConnection : boolean
     */
    public MysqlConnector(List<String> columnsList, boolean setConnection) {
        if (setConnection) connect();
    }

    /**
     * Constructor de la clase. Se conecta a la base de datos.
     */
    public void connect() {
        try {
            Class.forName(DB_DRIVER);
            this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            log.info("Connection established with MySQL database.");
        } catch (SQLException | ClassNotFoundException e) {
            log.error("Error connecting to the database", e);
        }
    }

   /**
     * Method for executing a SELECT query
     *
     * @param query : String
     * @return ResultSet
     */
    public ResultSet select(String query) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            return statement.executeQuery();
        } catch (SQLException e) {
            log.error("Error executing SELECT query", e);
            return null;
        }
    }

    /**
     * Metodo sobrecargado para ejecutar una consulta SELECT con parametros
     *
     * @param query : String
     * @param parameters : List<Object>
     * @return ResultSet
     */
    public ResultSet select(String query, List<Object> parameters) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }
            return statement.executeQuery();
        } catch (SQLException e) {
            log.error("Error executing parameterized SELECT query", e);
            return null;
        }
    }

    /**
     * Metodo que ejecuta una consulta UPDATE
     *
     * @param query : String
     * @return int
     */
    public int update(String query) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            return statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error executing UPDATE query", e);
            return -1;
        }
    }

    /**
     * Metodo sobrecargado para ejecutar una consulta UPDATE con parametros
     *
     * @param query : String
     * @param parameters : List<Object>
     * @return int
     */
    public int update(String query, List<Object> parameters) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));  // Bind parameters to the prepared statement
            }
            return statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error executing parameterized UPDATE query", e);
            return -1;
        }
    }

    /**
     * Metodo que ejecuta una consulta DELETE
     *
     * @param query : String
     * @return int
     */
    public int delete(String query) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            return statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error executing DELETE query", e);
            return -1;
        }
    }

    /**
     * Metodo sobrecargado para ejecutar una consulta DELETE con parametros
     *
     * @param query : String
     * @param parameters : List<Object>
     * @return int
     */
    public int delete(String query, List<Object> parameters) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));  // Bind parameters to the prepared statement
            }
            return statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Error executing parameterized DELETE query", e);
            return -1;
        }
    }

    /**
     * Metodo que cierra la conexion con la base de datos
     */
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                log.info("Connection closed successfully.");
            }
        } catch (SQLException e) {
            log.error("Error closing the database connection", e);
        }
    }

    /**
     * Metodo que construye una consulta UPDATE
     *
     * @param rowsValuesArray : ArrayList<String[]>
     * @param tableName : String
     * @param idRowName : String
     * @param id : String
     * @return String
     */
    public String getUpdateString(ArrayList<String[]> rowsValuesArray, String tableName, String idRowName, String id) {
        StringBuilder query = new StringBuilder("UPDATE " + tableName + " SET ");

        for (int i = 0; i < rowsValuesArray.size(); i++) {
            String[] row = rowsValuesArray.get(i);
            query.append(row[0] + " = '" + row[1] + "'");

            if (i < rowsValuesArray.size() - 1) {
                query.append(", ");
            }
        }

        query.append(" WHERE " + idRowName + " = '" + id + "'");

        return query.toString();
    }

    /**
     * Metodo que construye una consulta DELETE
     *
     * @param tableName : String
     * @param idRowName : String
     * @param idToDelete : String
     * @return String
     */
    public String getDeleteString(String tableName, String idRowName, String idToDelete) {
        return "DELETE FROM " + tableName + " WHERE " + idRowName + " = '" + idToDelete + "'";
    }

    /**
     * Metodo que construye una consulta INSERT
     *
     * @param rowsValuesArray : ArrayList<String[]>
     * @return String
     */
    public String getInsertString(ArrayList<String[]> rowsValuesArray) {
        StringBuilder query = new StringBuilder("INSERT INTO table_name (");
        StringBuilder values = new StringBuilder("VALUES (");

        // itera sobre las filas de la matriz
        for (int i = 0; i < rowsValuesArray.size(); i++) {
            String[] row = rowsValuesArray.get(i);
            query.append(row[0]);

            values.append("'" + row[1] + "'");

            if (i < rowsValuesArray.size() - 1) {
                query.append(", ");
                values.append(", ");
            }
        }

        query.append(") ");
        values.append(")");

        query.append(values);

        return query.toString();
    }

    public Connection getConnection() {
        return connection;
    }
}



