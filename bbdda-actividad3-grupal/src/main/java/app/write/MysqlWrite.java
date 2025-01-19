package app.write;


import com.emilio.anabel.minerva.exception.LogicException;
import com.emilio.anabel.minerva.exception.PersistenceException;
import com.emilio.anabel.minerva.persistence.GestorCSV;
import com.emilio.anabel.minerva.persistence.JDBC_WriteDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;


/**
 * Clase que inserta los datos de los archivos CSV en la base de datos MySQL.
 *
 * @version 1.0 - 2025-01-17
 * @autor Emilio, Anabel, Minerva
 */
@Slf4j
public class MysqlWrite {

    private final JDBC_WriteDao jdbcWriteDao;

    private static final String EMPRESA_TABLE = "empresa";
    private static final String NOMBRE_EMPRESA_COLUMN = "nombre_empresa";
    private static final String CARBURANTE_TABLE = "carburante";
    private static final String TIPO_CARBURANTE_COLUMN = "tipo_carburante";
    private static final String MUNICIPIO_TABLE = "municipio";
    private static final List<String> MUNICIPIO_COLUMNS = List.of("id_provincia", "nombre_municipio");
    private static final String PROVINCIA_TABLE = "provincia";
    private static final List<String> PROVINCIA_COLUMNS = List.of("id", "nombre_provincia");

    /**
     * Constructor que recibe un objeto JDBC_WriteDao.
     *
     * @param jdbcWriteDao : JDBC_WriteDao
     */
    public MysqlWrite(JDBC_WriteDao jdbcWriteDao) {
        this.jdbcWriteDao = jdbcWriteDao;
    }



    /**
     * Inserta los datos de los archivos CSV en la base de datos.
     *
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    public void insertEmpresas() throws PersistenceException, LogicException {
        insertFromCSV("empresas.csv", EMPRESA_TABLE, List.of(NOMBRE_EMPRESA_COLUMN));
    }

    /**
     * Inserta los datos de los archivos CSV en la base de datos.
     *
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    public void insertCarburantes() throws PersistenceException, LogicException {
        insertFromCSV("carburantes.csv", CARBURANTE_TABLE, List.of(TIPO_CARBURANTE_COLUMN));
    }

    /**
     * Inserta los datos de los archivos CSV en la base de datos.
     *
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    public void insertMunicipios() throws PersistenceException, LogicException {
        Map<String, Integer> provinciasMap = buildIdMap(
                jdbcWriteDao.select(PROVINCIA_TABLE, PROVINCIA_COLUMNS), 1, 0);

        ArrayList<String[]> municipios = readCSV("municipios.csv");
        updateColumnsWithMap(municipios, provinciasMap, 0);
        jdbcWriteDao.insert(municipios, MUNICIPIO_TABLE, MUNICIPIO_COLUMNS);
    }

    /**
     * Inserta los datos de los archivos CSV en la base de datos.
     *
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    private void insertFromCSV(String csvFile, String table, List<String> columns)
            throws PersistenceException, LogicException {
        ArrayList<String[]> rows = readCSV(csvFile);
        jdbcWriteDao.insert(rows, table, columns);
    }

    /**
     * Construye un mapa con los datos de una lista.
     *
     * @param dataList : List<String [ ]>
     * @param keyIndex : int
     * @param valueIndex : int
     * @return Map<String, Integer>
     */
    private Map<String, Integer> buildIdMap(List<String[]> dataList, int keyIndex, int valueIndex) {
        Map<String, Integer> map = new HashMap<>();
        for (String[] data : dataList) {
            map.put(data[keyIndex], Integer.parseInt(data[valueIndex]));
        }
        return map;
    }

    /**
     * Actualiza las columnas de una lista con los valores de un mapa.
     *
     * @param rows : List<String [ ]>
     * @param map : Map<String, Integer>
     * @param columnIndex : int
     */
    private void updateColumnsWithMap(List<String[]> rows, Map<String, Integer> map, int columnIndex) {
        for (String[] row : rows) {
            row[columnIndex] = map.getOrDefault(row[columnIndex], -1).toString();
        }
    }

    /**
     * Lee un archivo CSV y devuelve una lista con los valores de cada fila.
     *
     * @param fileName : String
     * @return ArrayList<String [ ]>
     */
    private ArrayList<String[]> readCSV(String fileName) throws LogicException {
        return GestorCSV.readCSV(fileName);
    }
}

