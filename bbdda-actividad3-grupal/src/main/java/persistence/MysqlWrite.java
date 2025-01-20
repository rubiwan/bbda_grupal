package persistence;


import static constants.MysqlColumns.*;
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
     * Inserta los datos de los archivos CSV en la base de datos.
     *
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    public void insertEmpresas() throws PersistenceException, LogicException {
        insertFromCSV("empresas.csv", EMPRESA_TABLE, List.of(NOMBRE_EMPRESA_COLUMN));
    }

    /**
     * Inserta los datos de los carburantes en la base de datos.
     *
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    public void insertCarburantes() throws PersistenceException, LogicException {
        insertFromCSV("carburantes.csv", CARBURANTE_TABLE, List.of(TIPO_CARBURANTE_COLUMN));
    }

    /**
     * Inserta los datos de las provincias en la base de datos.
     *
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    public void insertProvincias() throws PersistenceException, LogicException {
        insertFromCSV("provincias.csv", PROVINCIA_TABLE, List.of(NOMBRE_PROVINCIA_COLUMN));
    }

    /**
     * Inserta los datos de las localidades en la base de datos.
     *
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    public void insertLocalidades() throws PersistenceException, LogicException {
        insertFromCSV("localidades.csv", LOCALIDAD_TABLE, List.of(NOMBRE_LOCALIDAD_COLUMN));
    }

    /**
     * Inserta los datos de los archivos CSV en la base de datos.
     *
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    public void insertMunicipios() throws PersistenceException, LogicException {
        Map<String, Integer> provinciasMap = buildIdMap(
                jdbcWriteDao.select(PROVINCIA_TABLE, PROVINCIA_COLUMNS_LIST), 1, 0);

        ArrayList<String[]> municipios = readCSV("municipios.csv");
        updateColumnsWithMap(municipios, provinciasMap, 0);
        jdbcWriteDao.insert(municipios, MUNICIPIO_TABLE, List.of(ID_PROVINCIA_COLUMN, NOMBRE_MUNICIPIO_COLUMN));
    }

    /**
     * Inserta los datos de los códigos postales en la base de datos.
     *
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    public void insertCodigosPostales() throws PersistenceException, LogicException {
        Map<String, Integer> municipiosMap = buildIdMap(
                jdbcWriteDao.select(MUNICIPIO_TABLE, MUNICIPIO_COLUMNS_LIST), 1, 0);

        ArrayList<String[]> codigosPostales = readCSV("codigos_postales.csv");
        updateColumnsWithMap(codigosPostales, municipiosMap, 0);
        jdbcWriteDao.insert(codigosPostales, CODIGO_POSTAL_TABLE, List.of(ID_MUNICIPIO_COLUMN, NUMERO_CODIGO_POSTAL_COLUMN));
    }

    /**
     * Inserta los datos de las relaciones entre códigos postales y localidades en la base de datos.
     *
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    public void insertRelacionCP_Localidad() throws PersistenceException, LogicException {
        Map<String, Integer> codigosPostalesMap = buildIdMap(
                jdbcWriteDao.select(CODIGO_POSTAL_TABLE, CODIGO_POSTAL_COLUMNS_LIST), 1, 0);
        Map<String, Integer> localidadesMap = buildIdMap(
                jdbcWriteDao.select(LOCALIDAD_TABLE, LOCALIDAD_COLUMNS_LIST), 1, 0);

        ArrayList<String[]> cpLocalidad = readCSV("relacion_cp_localidad.csv");
        updateColumnsWithMap(cpLocalidad, codigosPostalesMap, 0);
        updateColumnsWithMap(cpLocalidad, localidadesMap, 1);
        jdbcWriteDao.insert(cpLocalidad, CP_LOCALIDAD_TABLE, CP_LOCALIDAD_COLUMNS_LIST);
    }

    /**
     * Inserta los datos de las estaciones en la base de datos.
     *
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    public void insertEstaciones() throws PersistenceException, LogicException {
        Map<String, Integer> localidadesMap = buildIdMap(
                jdbcWriteDao.select(LOCALIDAD_TABLE, LOCALIDAD_COLUMNS_LIST), 1, 0);
        Map<String, Integer> empresasMap = buildIdMap(
                jdbcWriteDao.select(EMPRESA_TABLE, EMPRESA_COLUMNS_LIST), 1, 0);

        ArrayList<String[]> estaciones = readCSV("estaciones.csv");
        updateColumnsWithMap(estaciones, localidadesMap, 6);
        updateColumnsWithMap(estaciones, empresasMap, 7);
        jdbcWriteDao.insert(estaciones, ESTACION_TABLE, List.of(DIRECCION_ESTACION_COLUMN,
                                                                MARGEN_ESTACION_COLUMN,
                                                                TIPO_ESTACION_COLUMN,
                                                                HORARIO_ESTACION_COLUMN,
                                                                LATITUD_ESTACION_COLUMN,
                                                                LONGITUD_ESTACION_COLUMN,
                                                                ID_LOCALIDAD_COLUMN,
                                                                ID_EMPRESA_COLUMN));
    }

    /**
     * Inserta los datos de los precios de los carburantes en la base de datos.
     *
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    public void insertPrecios() throws PersistenceException, LogicException {
        Map<String, Integer> carburantesMap = buildIdMap(
                jdbcWriteDao.select(CARBURANTE_TABLE, CARBURANTE_COLUMNS_LIST), 1, 0);
        Map<String, Integer> estacionesMap = buildIdMap(
                jdbcWriteDao.select(ESTACION_TABLE, ESTACION_COLUMNS_LIST), 1, 0);

        ArrayList<String[]> precios = readCSV("precio_carburantes.csv");
        updateColumnsWithMap(precios, carburantesMap, 2);
        updateColumnsWithMap(precios, estacionesMap, 3);
        jdbcWriteDao.insert(precios, PRECIO_TABLE, List.of( PRECIO_COLUMN,
                                                            FECHA_ACT_PRECIO_COLUMN,
                                                            ID_CARBURANTE_COLUMN,
                                                            ID_ESTACION_COLUMN));
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
    private void updateColumnsWithMap(ArrayList<String[]> rows, Map<String, Integer> map, int columnIndex) {
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

