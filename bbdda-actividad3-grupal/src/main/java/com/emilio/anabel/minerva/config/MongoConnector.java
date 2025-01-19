package com.emilio.anabel.minerva.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MongoConnector {

    private static final String MONGO_URI     = System.getenv("MONGO_URI");
    private static final String DATABASE_NAME = System.getenv("MONGO_DB_NAME");
    private static final String DB_NAME       = System.getenv("MONGO_DB_NAME");
    private static final String DB_HOST       = System.getenv("MONGO_DB_HOST");
    private static final String DB_USER       = System.getenv("MONGO_DB_USER");
    private static final String DB_PASSWORD   = System.getenv("MONGO_DB_PASSWORD");
    private static final String DB_PORT       = System.getenv("MONGO_DB_PORT");
    private static final String DB_URL        = "mongodb://" + DB_USER + ":" + DB_PASSWORD
                                                + "@" + DB_HOST + ":" + DB_PORT;

    private final MongoClient mongoClient;
    private final MongoDatabase database;

    public MongoConnector() {
        try {
            // Crear cliente MongoDB usando la URI de conexión
            mongoClient = MongoClients.create(DB_URL);
            // Obtener la base de datos
            database = mongoClient.getDatabase(DB_NAME);
            log.info("Connection established with MongoDB.");
        } catch (Exception e) {
            log.error("Error conectando en la base de datos", e);
            throw new RuntimeException("Error conectando en la base de datos", e);
        }
    }

    /**
     * Devuelve el objeto base de datos conectada.
     *
     * @return MongoDatabase (objeto de la base de datos).
     */
    public MongoDatabase getDatabase() {
        return database;
    }

    /**
     * Cierra la conexión con la BBDD.
     */
    public void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
            log.info("Gestión de MongoDB finalizada.");
        }
    }

    //TEST CODE:
/*
    MongoConnector mongoConnector = new MongoConnector();
    MongoDatabase MongoDatabase = mongoConnector.getDatabase();
    System.out.println(MongoDatabase.getName() + " CONECTADA!! TIRII TIRIIIIII");
    mongoConnector.closeConnection();
*/

}
