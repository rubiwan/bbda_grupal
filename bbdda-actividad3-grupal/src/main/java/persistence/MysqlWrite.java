package persistence;


import constants.MysqlColumns;
import exception.LogicException;
import exception.PersistenceException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Clase que inserta los datos de los archivos CSV en la base de datos MySQL.
 *
 * @version 1.0 - 2025-01-17
 * @autor Emilio, Anabel, Minerva
 */
public class MysqlWrite {

    private final JDBC_WriteDao jdbcWriteDao;

    /**
     * Constructor que recibe un objeto JDBC_WriteDao.
     *
     * @param jdbcWriteDao : JDBC_WriteDao
     */
    public MysqlWrite(JDBC_WriteDao jdbcWriteDao) {
        this.jdbcWriteDao = jdbcWriteDao;
    }

    /**
     * Inserta los datos de las estaciones en la base de datos.
     *
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    public void insertEstaciones() throws PersistenceException, LogicException {
        insertFromCSV("estaciones.csv", MysqlColumns.ESTACION_TABLE, MysqlColumns.ESTACION_COLUMNS_LIST);
    }

    /**
     * Inserta los datos de los archivos CSV en la base de datos.
     *
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    public void insertEmpresas() throws PersistenceException, LogicException {
        insertFromCSV("empresas.csv", MysqlColumns.EMPRESA_TABLE, MysqlColumns.EMPRESA_COLUMNS_LIST);
    }

    /**
     * Inserta los datos de los carburantes en la base de datos.
     *
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    public void insertCarburantes() throws PersistenceException, LogicException {
        insertFromCSV("carburantes.csv", MysqlColumns.CARBURANTE_TABLE, MysqlColumns.CARBURANTE_COLUMNS_LIST);
    }

    /**
     * Inserta los datos de las provincias en la base de datos.
     *
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    public void insertProvincias() throws PersistenceException, LogicException {
        insertFromCSV("provincias.csv", MysqlColumns.PROVINCIA_TABLE, MysqlColumns.PROVINCIA_COLUMNS_LIST);
    }

    /**
     * Inserta los datos de los códigos postales en la base de datos.
     *
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    public void insertCodigosPostales() throws PersistenceException, LogicException {
        insertFromCSV("codigos_postales.csv", MysqlColumns.CODIGO_POSTAL_TABLE, MysqlColumns.CODIGO_POSTAL_COLUMNS_LIST);
    }

    /**
     * Inserta los datos de las localidades en la base de datos.
     *
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    public void insertLocalidades() throws PersistenceException, LogicException {
        Map<String, Integer> provinciasMap = buildIdMap(
                jdbcWriteDao.select(MysqlColumns.PROVINCIA_TABLE, MysqlColumns.PRECIO_COLUMNS_LIST), 1, 0);

        ArrayList<String[]> localidades = readCSV("localidades.csv");
        updateColumnsWithMap(localidades, provinciasMap, 0);
        jdbcWriteDao.insert(localidades, MysqlColumns.LOCALIDAD_TABLE, MysqlColumns.LOCALIDAD_COLUMNS_LIST);
    }

    /**
     * Inserta los datos de las relaciones entre códigos postales y localidades en la base de datos.
     *
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    public void insertRelacionCP_Localidad() throws PersistenceException, LogicException {
        Map<String, Integer> codigosPostalesMap = buildIdMap(
                jdbcWriteDao.select(MysqlColumns.CODIGO_POSTAL_TABLE, MysqlColumns.PRECIO_COLUMNS_LIST), 1, 0);
        Map<String, Integer> localidadesMap = buildIdMap(
                jdbcWriteDao.select(MysqlColumns.LOCALIDAD_TABLE, MysqlColumns.PRECIO_COLUMNS_LIST), 1, 0);

        ArrayList<String[]> cpLocalidad = readCSV("cp_localidad.csv");
        updateColumnsWithMap(cpLocalidad, codigosPostalesMap, 0);
        updateColumnsWithMap(cpLocalidad, localidadesMap, 1);
        jdbcWriteDao.insert(cpLocalidad, MysqlColumns.CP_LOCALIDAD_TABLE, MysqlColumns.CP_LOCALIDAD_COLUMNS_LIST);
    }

    /**
     * Inserta los datos de los precios de los carburantes en la base de datos.
     *
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    public void insertPrecios() throws PersistenceException, LogicException {
        Map<String, Integer> estacionesMap = buildIdMap(
                jdbcWriteDao.select(MysqlColumns.ESTACION_TABLE, MysqlColumns.PRECIO_COLUMNS_LIST), 1, 0);
        Map<String, Integer> carburantesMap = buildIdMap(
                jdbcWriteDao.select(MysqlColumns.CARBURANTE_TABLE, MysqlColumns.PRECIO_COLUMNS_LIST), 1, 0);

        ArrayList<String[]> precios = readCSV("precios_carburantes.csv");
        updateColumnsWithMap(precios, estacionesMap, 0);
        updateColumnsWithMap(precios, carburantesMap, 1);
        jdbcWriteDao.insert(precios, MysqlColumns.PRECIO_TABLE, MysqlColumns.PRECIO_COLUMNS_LIST);
    }

    /**
     * Inserta los datos de los archivos CSV en la base de datos.
     *
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    public void insertMunicipios() throws PersistenceException, LogicException {
        Map<String, Integer> provinciasMap = buildIdMap(
                jdbcWriteDao.select(MysqlColumns.PROVINCIA_TABLE, MysqlColumns.PRECIO_COLUMNS_LIST), 1, 0);

        ArrayList<String[]> municipios = readCSV("municipios.csv");
        updateColumnsWithMap(municipios, provinciasMap, 0);
        jdbcWriteDao.insert(municipios, MysqlColumns.MUNICIPIO_TABLE, MysqlColumns.MUNICIPIO_COLUMNS_LIST);
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

