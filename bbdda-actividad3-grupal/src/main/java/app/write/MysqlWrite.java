package app.write;

import com.emilio.anabel.minerva.dao.IWriteDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.emilio.anabel.minerva.persistence.GestorCSV.readCSV;
import static com.emilio.anabel.minerva.config.constants.MysqlColumns.*;

public class MysqlWrite {

    private MysqlWrite() {}

    public static void insertEmpresas() {
        IWriteDao.insert(readCSV("empresas.csv"), EMPRESA_TABLE, List.of(NOMBRE_EMPRESA_COLUMN));
    }

    public static void insertCarburantes() {
        IWriteDao.insert(readCSV("carburantes.csv"), CARBURANTE_TABLE, List.of(TIPO_CARBURANTE_COLUMN));
    }

    public static void insertProvincias() {
        IWriteDao.insert(readCSV("provincias.csv"), PROVINCIA_TABLE, List.of(NOMBRE_PROVINCIA_COLUMN));
    }

    public static void insertLocalidades() {
        IWriteDao.insert(readCSV("localidades.csv"), LOCALIDAD_TABLE, List.of(NOMBRE_LOCALIDAD_COLUMN));
    }

    public static void insertMunicipios() {
        ArrayList<String[]> provinciasList = IWriteDao.select(PROVINCIA_TABLE, PROVINCIA_COLUMNS_LIST);
        ArrayList<String[]> municipiosList = readCSV("municipios.csv");
        Map<String, Integer> provinciasMap = new HashMap<>();

        for (String[] provincia : provinciasList) {
            provinciasMap.put(provincia[1], Integer.parseInt(provincia[0]));
        }
        assert municipiosList != null;
        for (String[] valuesAtColumn : municipiosList) {
            valuesAtColumn[0] = provinciasMap.get(valuesAtColumn[0]).toString();
        }
        IWriteDao.insert(municipiosList, MUNICIPIO_TABLE, List.of(ID_PROVINCIA_COLUMN,
                                                            NOMBRE_MUNICIPIO_COLUMN));
    }

    public static void insertCodigosPostales() {
        ArrayList<String[]> municipiosList = IWriteDao.select(MUNICIPIO_TABLE, MUNICIPIO_COLUMNS_LIST);
        ArrayList<String[]> codigosPostalesList = readCSV("codigos_postales.csv");
        Map<String, Integer> municipiosMap = new HashMap<>();

        for (String[] municipio : municipiosList) {
            municipiosMap.put(municipio[1], Integer.parseInt(municipio[0]));
        }
        assert codigosPostalesList != null;
        for (String[] valuesAtColumn : codigosPostalesList) {
            valuesAtColumn[0] = municipiosMap.get(valuesAtColumn[0]).toString();
        }
        IWriteDao.insert(codigosPostalesList, CODIGO_POSTAL_TABLE, List.of(ID_MUNICIPIO_COLUMN,
                                                                     NUMERO_CODIGO_POSTAL_COLUMN));
    }

    public static void insertRelacionCpLocalidad() {
        ArrayList<String[]> codigosPostalesList = IWriteDao.select(CODIGO_POSTAL_TABLE, CODIGO_POSTAL_COLUMNS_LIST);
        ArrayList<String[]> localidadesList = IWriteDao.select(LOCALIDAD_TABLE, LOCALIDAD_COLUMNS_LIST);
        ArrayList<String[]> cpLocalidadList = readCSV("relacion_cp_localidad.csv");

        Map<String, Integer> codigosPostalesMap = new HashMap<>();
        Map<String, Integer> localidadesMap = new HashMap<>();

        for (String[] codigoPostal : codigosPostalesList) {
            codigosPostalesMap.put(codigoPostal[1], Integer.parseInt(codigoPostal[0]));
        }

        for (String[] localidad : localidadesList) {
            localidadesMap.put(localidad[1], Integer.parseInt(localidad[0]));
        }

        assert cpLocalidadList != null;
        for (String[] valuesAtColumn : cpLocalidadList) {
            valuesAtColumn[0] = codigosPostalesMap.get(valuesAtColumn[0]).toString();
            valuesAtColumn[1] = localidadesMap.get(valuesAtColumn[1]).toString();
        }
        IWriteDao.insert(cpLocalidadList, CP_LOCALIDAD_TABLE, CP_LOCALIDAD_COLUMNS_LIST);
    }

    public static void insertEstaciones() {
        ArrayList<String[]> localidadesList = IWriteDao.select(LOCALIDAD_TABLE, LOCALIDAD_COLUMNS_LIST);
        ArrayList<String[]> empresasList = IWriteDao.select(EMPRESA_TABLE, EMPRESA_COLUMNS_LIST);
        ArrayList<String[]> estacionesList = readCSV("estaciones.csv");

        Map<String, Integer> localidadesMap = new HashMap<>();
        Map<String, Integer> empresasMap = new HashMap<>();

        for (String[] localidad : localidadesList) {
            localidadesMap.put(localidad[1], Integer.parseInt(localidad[0]));
        }

        for (String[] empresa : empresasList) {
            empresasMap.put(empresa[1], Integer.parseInt(empresa[0]));
        }

        assert estacionesList != null;
        for (String[] valuesAtColumn : estacionesList) {
            valuesAtColumn[6] = localidadesMap.get(valuesAtColumn[6]).toString();
            valuesAtColumn[7] = empresasMap.get(valuesAtColumn[7]).toString();
        }
        IWriteDao.insert(estacionesList, ESTACION_TABLE, List.of( DIRECCION_ESTACION_COLUMN,
                                                            MARGEN_ESTACION_COLUMN,
                                                            TIPO_ESTACION_COLUMN,
                                                            HORARIO_ESTACION_COLUMN,
                                                            LATITUD_ESTACION_COLUMN,
                                                            LONGITUD_ESTACION_COLUMN,
                                                            ID_LOCALIDAD_COLUMN,
                                                            ID_EMPRESA_COLUMN));
    }

    public static void insertPrecioCarburante() {

        ArrayList<String[]> carburantesList = IWriteDao.select(CARBURANTE_TABLE, CARBURANTE_COLUMNS_LIST);
        ArrayList<String[]> estacionesList = IWriteDao.select(ESTACION_TABLE, ESTACION_COLUMNS_LIST);
        ArrayList<String[]> precioCarburantesList = readCSV("precio_carburantes.csv");

        Map<String, Integer> carburantesMap = new HashMap<>();
        Map<String, Integer> estacionesMap = new HashMap<>();

        for (String[] carburantes : carburantesList) {
            carburantesMap.put(carburantes[1], Integer.parseInt(carburantes[0]));
        }

        for (String[] estaciones : estacionesList) {
            estacionesMap.put(estaciones[1], Integer.parseInt(estaciones[0]));
        }

        assert precioCarburantesList != null;
        for (String[] valuesAtColumn : precioCarburantesList) {
            valuesAtColumn[2] = carburantesMap.get(valuesAtColumn[2]).toString();
            valuesAtColumn[3] = estacionesMap.get(valuesAtColumn[3]).toString();
        }
        IWriteDao.insert(precioCarburantesList, PRECIO_TABLE, List.of(PRECIO_COLUMN,
                                                                FECHA_ACT_PRECIO_COLUMN,
                                                                ID_CARBURANTE_COLUMN,
                                                                ID_ESTACION_COLUMN));
    }
}
