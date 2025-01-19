package com.emilio.anabel.minerva.persistence;

import java.sql.Connection;

public class GestorJDBC {

    private final JDBC_ReadDao jdbcReadDao;
    private final JDBC_WriteDao jdbcWriteDao;
    private final Connection connection;

    public GestorJDBC(Connection connection) {
        this.connection = connection;
        this.jdbcReadDao = new JDBC_ReadDao(connection);
        this.jdbcWriteDao = new JDBC_WriteDao(connection);
    }

    private void cargarDatos(){

    }
}
