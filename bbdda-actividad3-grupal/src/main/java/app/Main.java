package app;

import config.MysqlConnector;
import constants.MongoCollection;
import exception.LogicException;
import exception.PersistenceException;
import persistence.GestorJDBC;

import java.io.File;
import java.sql.Connection;
import java.util.List;
import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    //flujo de ejecucion:
        // 1. Insert data into MySQL --- ver en los constructores de las clases
        // 2. Read data from MySQL y creamos los JSON
        // 3. Insert data into MongoDB
        // 4. Read data from MongoDB ¿? ---- metemos un menu para que el usuario pueda elegir si quiere leer los datos de mongo o no?

    // pendiente
        // ¿ElasticSearch?

    private static GestorJDBC gestorJDBC;
    private static final String FOLDER_PATH = "src/main/resources/json";


    /**
     * Constructor de la clase Main
     *
     * @throws Exception: en caso de error al inicializar el sistema
     */
	public Main(Connection connection) {
            try {
                log.info("Cargar archivos");
                gestorJDBC = new GestorJDBC(connection);
                log.info("Sistema inicializado correctamente");
                System.out.println("Sistema inicializado correctamente");
            } catch (Exception e) {
                log.error("Error al inicializar el sistema", e);
                System.out.println("Error al inicializar el sistema: " + e.getMessage());
            }
        }

        public static void main(String[] args) {
            try (Connection connection = new MysqlConnector().getConnection()) {
                log.info("Inicio del sistema");
                new Main(connection);
//                insertAll();
//                selectAll();
                insertAllJson();
            } catch (Exception e) {
                System.out.println("Error al inicializar el sistema: " + e.getMessage());
                log.error("Error al inicializar el sistema", e);
            }
        }

    /**
     * Metodo que selecciona todos los datos de la base de datos.
     */
    private static void selectAll() {
            try {
                gestorJDBC.selectAll();
            } catch (PersistenceException e) {
                log.error("Error al seleccionar los datos", e);
                System.out.println("Error al seleccionar los datos: " + e.getMessage());
            }
        }

    /**
     * Metodo que inserta todos los datos en la base de datos.
     */
    private static void insertAll() {
        try{
            gestorJDBC.insertAll();
        } catch (PersistenceException | LogicException e) {
            log.error("Error al insertar los datos", e);
            System.out.println("Error al insertar los datos: " + e.getMessage());
        }
    }

    private static void insertAllJson(){
        try {
            //lista de colecciones de enum MongoCollection
            List<MongoCollection> collections = Arrays.asList(MongoCollection.values());
            for (MongoCollection collection : collections) {
                String collectionName = collection.getCollection();
                String folderPath = FOLDER_PATH + File.separator + collectionName;
                gestorJDBC.insertAllJson(folderPath, collectionName);
            }
            /*
                        String collectionName = "empresas";
            String folderPath = FOLDER_PATH + File.separator + collectionName;
            gestorJDBC.insertAllJson(folderPath, collectionName);
             */


        } catch (PersistenceException | LogicException e) {
            log.error("Error al insertar los datos", e);
            System.out.println("Error al insertar los datos: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error al insertar los datos", e);
            System.out.println("Error al insertar los datos: " + e.getMessage());
        }
    }
}

