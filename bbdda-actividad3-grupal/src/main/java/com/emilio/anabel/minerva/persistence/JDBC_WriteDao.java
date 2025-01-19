package com.emilio.anabel.minerva.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.emilio.anabel.minerva.dao.IWriteDao;
import com.emilio.anabel.minerva.exception.PersistenceException;
import lombok.extern.slf4j.Slf4j;

/**
 * Clase que implementa la interfaz IWriteDao y define los métodos para realizar
 * operaciones de insert en la base de datos.
 *
 * @version 2.0 - 2025-01-17
 * @author Emilio, Anabel, Minerva
 */
@Slf4j
public class JDBC_WriteDao implements IWriteDao {


    private final Connection connection;

    // Constructor que recibe la conexión
    public JDBC_WriteDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Inserta una lista de filas en una tabla de la base de datos.
     *
     * @param rows : List<String []>
     * @param tableName : String
     * @param columns : List<String>
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    @Override
    public void insert(List<String[]> rows, String tableName, List<String> columns)
            throws PersistenceException {

        try (PreparedStatement statement = createInsertStatement(rows, tableName, columns)) {
            for (String[] row : rows) {
                for (int i = 0; i < row.length; i++) {
                    statement.setString(i + 1, row[i]);
                }
                statement.addBatch();
            }
            statement.executeBatch();
            log.info("Inserted {} rows into table {}", rows.size(), tableName);
        } catch (SQLException e) {
            log.error("Error inserting rows into table {}: {}", tableName, e.getMessage());
            throw new PersistenceException("Error insertando tabla " + tableName);
        }
    }

    /**
     * Selecciona todas las filas de una tabla de la base de datos.
     *
     * @param tableName : String
     * @param columns : List<String>
     * @return List<String [ ]>
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    @Override
    public List<String[]> select(String tableName, List<String> columns) throws PersistenceException {
        List<String[]> results = new ArrayList<>();
        String query = buildSelectSQL(tableName, columns);

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String[] row = new String[columns.size()];
                for (int i = 0; i < columns.size(); i++) {
                    row[i] = resultSet.getString(columns.get(i));
                }
                results.add(row);
            }

        } catch (SQLException e) {
            log.error("Error fetching data from table {}: {}", tableName, e.getMessage());
            throw new PersistenceException("Error seleccionando tabla " + tableName);
        }

        return results;
    }

    /**
     * Actualiza una fila de una tabla de la base de datos.
     *
     * @param tableName : String
     * @param columns : List<String>
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    @Override
    public PreparedStatement createInsertStatement(List<String[]> rows, String tableName,
                                                   List<String> columns) throws PersistenceException {
        try {
            String insertSql = buildInsertSQL(tableName, columns);
            return connection.prepareStatement(insertSql);
        } catch (SQLException e) {
            log.error("Error creating insert statement for table {}: {}", tableName, e.getMessage());
            throw new PersistenceException("Error creando sentencia de inserción para tabla " + tableName);
        }

    }

    /**
     * Construye una sentencia SQL para insertar filas en una tabla.
     *
     * @param tableName : String
     * @param columns : List<String>
     * @return String
     */
    @Override
    public String buildInsertSQL(String tableName, List<String> columns) {
        String columnNames = String.join(", ", columns);
        String placeholders = String.join(", ", Collections.nCopies(columns.size(), "?"));
        return "INSERT INTO " + tableName + " (" + columnNames + ") VALUES (" + placeholders + ")";
    }

    /**
     * Construye una sentencia SQL para seleccionar todas las filas de una tabla.
     *
     * @param tableName : String
     * @param columns : List<String>
     * @return String
     */
    @Override
    public String buildSelectSQL(String tableName, List<String> columns) {
        String columnNames = String.join(", ", columns);
        return "SELECT " + columnNames + " FROM " + tableName;
    }
}

