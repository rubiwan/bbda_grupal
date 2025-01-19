package com.emilio.anabel.minerva.config;

import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
public class GestorConnectors {


        private final Connection mysqlConnection;
        private final MongoDatabase mongoDatabase;

        public GestorConnectors() {
            try {
                // Inicializar MySQL
                this.mysqlConnection = new MysqlConnector().getConnection();

                // Inicializar MongoDB
                this.mongoDatabase = MongoConnector.getInstance().getDatabase();

                log.info("Conexiones a MySQL y MongoDB inicializadas correctamente.");
            } catch (Exception e) {
                log.error("Error inicializando bases de datos", e);
                throw new RuntimeException("Error inicializando bases de datos", e);
            }
        }

        public Connection getMysqlConnection() {
            return mysqlConnection;
        }

        public MongoDatabase getMongoDatabase() {
            return mongoDatabase;
        }

        public void closeConnections() {
            try {
                if (mysqlConnection != null && !mysqlConnection.isClosed()) {
                    mysqlConnection.close();
                }
                log.info("Conexión a MySQL cerrada.");
            } catch (SQLException e) {
                log.error("Error cerrando conexión a MySQL", e);
            }

            // MongoDB se gestiona automáticamente por el cliente.
            log.info("Gestión de MongoDB finalizada.");
        }
    }


