package com.emilio.anabel.minerva.dao;

import com.emilio.anabel.minerva.exception.PersistenceException;

import java.sql.PreparedStatement;
import java.util.List;

/**
 * Interfaz que define los métodos para realizar operaciones de escritura en la base de datos.
 *
 * @version 1.0 - 2025-01-17
 * @autor Emilio, Anabel, Minerva
 */
public interface IWriteDao {

    /**
     * Inserta una lista de filas en una tabla de la base de datos.
     *
     * @param rows : List<String []>
     * @param tableName : String
     * @param columns : List<String>
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    void insert(List<String[]> rows, String tableName, List<String> columns)
            throws PersistenceException;

    /**
     * Selecciona todas las filas de una tabla de la base de datos.
     *
     * @param tableName : String
     * @param columns : List<String>
     * @return List<String [ ]> : lista con los valores de cada fila
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    List<String[]> select(String tableName, List<String> columns) throws PersistenceException;

    /**
     * Crea una sentencia de inserción para una lista de filas en una tabla de la base de datos.
     *
     * @param rows : List<String []>
     * @param tableName : String
     * @param columns : List<String>
     * @return PreparedStatement : sentencia de inserción
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    PreparedStatement createInsertStatement(List<String[]> rows, String tableName,
                                            List<String> columns) throws PersistenceException;

    /**
     * Construye una sentencia SQL de inserción para una tabla de la base de datos.
     *
     * @param tableName : String
     * @param columns : List<String>
     * @return String : sentencia SQL de inserción
     */
    String buildInsertSQL(String tableName, List<String> columns);

    /**
     * Construye una sentencia SQL de selección para una tabla de la base de datos.
     *
     * @param tableName : String
     * @param columns : List<String>
     * @return String : sentencia SQL de selección
     */
    String buildSelectSQL(String tableName, List<String> columns);
}
