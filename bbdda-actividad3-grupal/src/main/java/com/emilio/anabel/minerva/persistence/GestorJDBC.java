package com.emilio.anabel.minerva.persistence;

import app.write.MongoWrite;
import app.write.MysqlWrite;
import com.emilio.anabel.minerva.config.constants.MongoCollection;
import com.emilio.anabel.minerva.config.constants.MysqlQueries;
import com.emilio.anabel.minerva.exception.LogicException;
import com.emilio.anabel.minerva.exception.PersistenceException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;

/**
 * Clase que gestiona las operaciones de lectura y escritura en la base de datos MySQL.
 *
 * @version 1.0 - 2025-01-17
 * @author Emilio, Anabel, Minerva
 */
@Getter
@Slf4j
public class GestorJDBC {

    private final JDBC_ReadDao jdbcReadDao;
    private final JDBC_WriteDao jdbcWriteDao;
    private final MysqlWrite mysqlWrite;
    private final MongoWrite mongoWrite;

    private final Connection connection;

    /**
     * Constructor de la clase GestorJDBC.
     *
     * @param connection : Connection
     */
    public GestorJDBC(Connection connection) {
        this.connection = connection;
        this.jdbcReadDao = new JDBC_ReadDao(connection);
        this.jdbcWriteDao = new JDBC_WriteDao(connection);

        this.mysqlWrite = new MysqlWrite(jdbcWriteDao);
        this.mongoWrite = new MongoWrite();

        log.info("Sistema inicializado correctamente");
    }


    //todas las operaciones de persistencia deberian hacerse en esta clase

    public void insertAllJson(String folderPath, String collectionName) throws PersistenceException, LogicException {
        try {
            mongoWrite.insertarJsonEnBatches(folderPath, collectionName);
        } catch (PersistenceException e) {
            log.error("Error al insertar los datos", e);
            throw new PersistenceException("Error al insertar los datos", e);
        } catch (LogicException e) {
            log.error("Error al insertar los datos", e);
            throw new LogicException("Error al insertar los datos", e);
        }
    }


    /**
     * Inserta los datos en la base de datos MySQL.
     *
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    public void insertAll() throws PersistenceException, LogicException {
        mysqlWrite.insertEmpresas();
        mysqlWrite.insertCarburantes();
//        mysqlWrite.insertMunicipios();
    }

    /**
     * Selecciona los datos de la base de datos MySQL.
     *
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    public void selectAll() throws PersistenceException {
//        selectEstaciones();
        selectPetroleras();
        selectCarburantes();
        selectPreciosCarburantes();
        selectUbicaciones();
    }

    /**
     * Selecciona las estaciones de la base de datos MySQL.
     *
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    public void selectEstaciones() throws PersistenceException {
        String query = MysqlQueries.SELECT_ESTACIONES.getQuery();
        jdbcReadDao.selectEstaciones(query);
    }

    /**
     * Selecciona las petroleras de la base de datos MySQL.
     *
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    public void selectPetroleras() throws PersistenceException {
        String query = MysqlQueries.SELECT_PETROLERAS.getQuery();
        jdbcReadDao.selectPetroleras(query);
    }

    /**
     * Selecciona los carburantes de la base de datos MySQL.
     *
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    public void selectCarburantes() throws PersistenceException {
        String query = MysqlQueries.SELECT_CARBURANTES.getQuery();
        jdbcReadDao.selectCarburantes(query);
    }

    /**
     * Selecciona los precios de los carburantes de la base de datos MySQL.
     *
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    public void selectPreciosCarburantes() throws PersistenceException {
        String query = MysqlQueries.SELECT_PRECIOS_CARBURANTES.getQuery();
        jdbcReadDao.selectPreciosCarburantes(query);
    }

    /**
     * Selecciona las ubicaciones de la base de datos MySQL.
     *
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    public void selectUbicaciones() throws PersistenceException {
        String query = MysqlQueries.SELECT_UBICACIONES.getQuery();
        jdbcReadDao.selectUbicaciones(query);
    }
}
