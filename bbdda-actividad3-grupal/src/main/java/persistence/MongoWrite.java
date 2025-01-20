package persistence;
import config.MongoConnector;
import exception.LogicException;
import exception.PersistenceException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * Clase que inserta archivos JSON en MongoDB.
 *
 * @version 1.0 - 2025-01-17
 * @author Emilio, Anabel, Minerva
 */
@Slf4j
public class MongoWrite {

    // Metodo para insertar archivos JSON en MongoDB en lotes
    public static void insertarJsonEnBatches(String folderPath, String collectionName) throws LogicException, PersistenceException {
        // conexion a singleton

        MongoConnector mongoConnector = new MongoConnector();
        MongoDatabase database = mongoConnector.getDatabase();
        MongoCollection<Document> collection = database.getCollection(collectionName);

        File folder = new File(folderPath);
        //funcion lambda para filtrar los archivos json
        String[] jsonFiles = folder.list((dir, name) -> name.endsWith(".json"));

        if (jsonFiles == null || jsonFiles.length == 0) {
            log.warn("No se encontraron archivos JSON en la carpeta: " + folderPath);
            throw new LogicException("No se encontraron archivos JSON en la carpeta: " + folderPath);
        }

        int batchSize = 100; // Tamanio del lote
        List<Document> batch = new ArrayList<>();

        for (String jsonFile : jsonFiles) {
            String filePath = folderPath + File.separator + jsonFile;

            try (FileReader reader = new FileReader(filePath)) {
                // Leer el archivo JSON y convertirlo a un Document de MongoDB
                JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
                Document document = Document.parse(jsonObject.toString());
                batch.add(document);

                // Cuando el lote se llena, se inserta en MongoDB
                if (batch.size() == batchSize) {
                    collection.insertMany(batch);
                    batch.clear(); // Limpia el lote para la siguiente inserción
                }
            } catch (IOException e) {
                log.error("Error al leer el archivo JSON: " + filePath, e);
                throw new PersistenceException("Error al leer el archivo JSON: " + filePath);
            }
        }

        // Inserta el resto
        if (!batch.isEmpty()) {
            collection.insertMany(batch);
        }
        log.info("Se insertaron todos los documentos JSON en la coleccion: " + collectionName);
    }
}

