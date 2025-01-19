package app;

import app.read.MysqlRead;
import com.emilio.anabel.minerva.config.MysqlConnector;
import com.emilio.anabel.minerva.exception.LogicException;
import com.emilio.anabel.minerva.exception.PersistenceException;

import java.sql.Connection;

import com.emilio.anabel.minerva.persistence.GestorCSV;
import com.emilio.anabel.minerva.persistence.GestorJDBC;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    //flujo de ejecucion:
        // 1. Insert data into MySQL --- ver en los constructores de las clases
        // 2. Read data from MySQL y creamos los JSON
        // 3. Insert data into MongoDB

    //pendiente
    // ¿Donde carga de datos de mysql? -- en los constructores
    //pendiente

    // modificar el MySqlConnector --- sacar el rs y el ps de la clase
    // e implementarlos en elos metodos de la clase --- mysqlWrite esta

    //reorganizar proyecto y ver implementacion del dao

    // crear conexion con mongo

    // insertar datos en mongo

private static GestorJDBC gestorJDBC;
private static GestorCSV gestorCSV;
        /**
         * Constructor de la clase Main
         *
         * @throws PersistenceException
         * @throws LogicException
         */
	public Main(Connection connection) throws PersistenceException, LogicException {
            try {
                log.info("Cargar archivos");
                //ver carga de datos
                gestorCSV = new GestorCSV();
                gestorJDBC = new GestorJDBC(connection);
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
            } catch (Exception e) {
                System.out.println("Error al inicializar el sistema: " + e.getMessage());
                log.error("Error al inicializar el sistema", e);
            }
        }



/*
        MysqlWrite.insertEmpresas();
        MysqlWrite.insertCarburantes();
        MysqlWrite.insertProvincias();
        MysqlWrite.insertLocalidades();

        MysqlWrite.insertMunicipios();
        MysqlWrite.insertCodigosPostales();
        MysqlWrite.insertRelacionCpLocalidad();
        MysqlWrite.insertEstaciones();
        MysqlWrite.insertPrecioCarburante();
*/

        //MysqlRead.selectEstaciones();

}

