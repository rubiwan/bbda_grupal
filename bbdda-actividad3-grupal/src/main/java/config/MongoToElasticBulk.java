package config;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class MongoToElasticBulk {

    public static void main(String[] args) {
        // Ruta del archivo JSON exportado desde MongoDB
        String inputFilePath = "/Users/anabeldiazroig/Desktop/bbdda_grupal/all_raw.json";
        String outputFilePath = "/Users/anabeldiazroig/Desktop/bbdda_grupal/bulk_all_raw.json"; // Archivo de salida en formato bulk

        // Crear el ObjectMapper para procesar JSON
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Leer el archivo JSON
            JsonNode rootNode = objectMapper.readTree(new File(inputFilePath));

            // Crear el archivo de salida
            FileWriter fileWriter = new FileWriter(outputFilePath);

            // Iterar sobre las colecciones en el archivo
            for (JsonNode collectionNode : rootNode) {
                String collectionName = collectionNode.get("collection").asText(); // Nombre de la colección
                JsonNode dataArray = collectionNode.get("data"); // Datos de la colección

                // Iterar sobre cada documento de la colección
                for (JsonNode documentNode : dataArray) {
                    // Obtener el ID del documento
                    String documentId = documentNode.get("_id").get("$oid").asText();

                    // escribir el encabezado
                    fileWriter.write("{ \"index\": { \"_index\": \"" + collectionName + "\", \"_id\": \"" + documentId + "\" } }\n");

                    // Escribir el documento como JSON
                    fileWriter.write(objectMapper.writeValueAsString(documentNode) + "\n");
                }
            }

            // Cerrar el filewriter
            fileWriter.close();

            System.out.println("Archivo bulk generado exitosamente: " + outputFilePath);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error procesando el archivo JSON: " + e.getMessage());
        }
    }
}
