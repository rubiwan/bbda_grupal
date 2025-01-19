package app;

import com.emilio.anabel.minerva.config.MysqlConnector;
import com.emilio.anabel.minerva.exception.LogicException;
import com.emilio.anabel.minerva.exception.PersistenceException;
import com.emilio.anabel.minerva.persistence.GestorJDBC;

import java.sql.Connection;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    //flujo de ejecucion:
        // 1. Insert data into MySQL --- ver en los constructores de las clases
        // 2. Read data from MySQL y creamos los JSON
        // 3. Insert data into MongoDB
        // 4. Read data from MongoDB ¿? ---- metemos un menu para que el usuario pueda elegir si quiere leer los datos de mongo o no?

    // pendiente
        // 1.crear conexion con mongo

        // 2.insertar datos en mongo

        // 3.leer datos de mongo?

        // 4.pruebas

    private static GestorJDBC gestorJDBC;


    /**
     * Constructor de la clase Main
     *
     * @throws PersistenceException
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
                insertAll();
                selectAll();
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
}

