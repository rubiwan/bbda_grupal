package com.emilio.anabel.minerva.persistence;

import app.write.MysqlWrite;

import java.sql.Connection;

public class GestorJDBC {

    private final JDBC_ReadDao jdbcReadDao;
    private final JDBC_WriteDao jdbcWriteDao;

    private final Connection connection;

    public GestorJDBC(Connection connection) {
        this.connection = connection;
        this.jdbcReadDao = new JDBC_ReadDao(connection);
        this.jdbcWriteDao = new JDBC_WriteDao(connection);

        MysqlWrite mysqlWrite = new MysqlWrite(jdbcWriteDao);
    }

    private void cargarDatos(){

    }
}
