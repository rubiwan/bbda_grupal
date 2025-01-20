package config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;

/**
 * Clase que realiza la conexión con la base de datos MongoDB.
 *
 * @version 1.0 - 2025-01-19
 * @author Emilio, Anabel, Minerva
 */
@Slf4j
public class MongoConnector {

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
            mongoClient = MongoClients.create(DB_URL);
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
}
