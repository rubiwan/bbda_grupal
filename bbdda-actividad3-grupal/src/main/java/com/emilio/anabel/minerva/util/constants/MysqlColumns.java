package com.emilio.anabel.minerva.util.constants;

import java.util.List;

public class MysqlColumns {

    // Bases de datos
    public static final String BBDD_NAME = "carburantes";

    // Tablas
    public static final String PROVINCIA_TABLE      = "provincia";
    public static final String MUNICIPIO_TABLE      = "municipio";
    public static final String CODIGO_POSTAL_TABLE  = "codigo_postal";
    public static final String CP_LOCALIDAD_TABLE   = "relacion_cp_localidad";
    public static final String LOCALIDAD_TABLE      = "localidad";
    public static final String ESTACION_TABLE       = "estacion";
    public static final String EMPRESA_TABLE        = "empresa";
    public static final String PRECIO_TABLE         = "precio_carburante";
    public static final String CARBURANTE_TABLE     = "carburante";


    // Atributos de PROVINCIA_TABLE
    public static final String ID_PROVINCIA_COLUMN     = "id_provincia";
    public static final String NOMBRE_PROVINCIA_COLUMN = "nombre_provincia";
    public static final List<String> PROVINCIA_COLUMNS_LIST = List.of(ID_PROVINCIA_COLUMN,
                                                                      NOMBRE_PROVINCIA_COLUMN);

    // Atributos de MUNICIPIO_TABLE
    public static final String ID_MUNICIPIO_COLUMN      = "id_municipio";
    public static final String NOMBRE_MUNICIPIO_COLUMN  = "nombre_municipio";
    public static final List<String> MUNICIPIO_COLUMNS_LIST = List.of(ID_MUNICIPIO_COLUMN,
                                                                      NOMBRE_MUNICIPIO_COLUMN);

    // Atributos de CODIGO_POSTAL_TABLE
    public static final String ID_CODIGO_POSTAL_COLUMN      = "id_codigo_postal";
    public static final String NUMERO_CODIGO_POSTAL_COLUMN  = "numero_codigo_postal";
    public static final List<String> CODIGO_POSTAL_COLUMNS_LIST = List.of(ID_CODIGO_POSTAL_COLUMN,
                                                                          NUMERO_CODIGO_POSTAL_COLUMN);


    // Atributos de LOCALIDAD_TABLE
    public static final String ID_LOCALIDAD_COLUMN      = "id_localidad";
    public static final String NOMBRE_LOCALIDAD_COLUMN  = "nombre_localidad";
    public static final List<String> LOCALIDAD_COLUMNS_LIST = List.of(ID_LOCALIDAD_COLUMN,
                                                                      NOMBRE_LOCALIDAD_COLUMN);

    // Atributos de CP_LOCALIDAD_TABLE
    public static final List<String> CP_LOCALIDAD_COLUMNS_LIST = List.of(ID_CODIGO_POSTAL_COLUMN,
                                                                         ID_LOCALIDAD_COLUMN);

    // Atributos de ESTACION_TABLE
    public static final String ID_ESTACION_COLUMN        = "id_estacion";
    public static final String DIRECCION_ESTACION_COLUMN = "direccion_estacion";
    public static final String MARGEN_ESTACION_COLUMN    = "margen_estacion";
    public static final String TIPO_ESTACION_COLUMN      = "tipo_estacion";
    public static final String HORARIO_ESTACION_COLUMN   = "horario_estacion";
    public static final String LATITUD_ESTACION_COLUMN   = "latitud_estacion";
    public static final String LONGITUD_ESTACION_COLUMN  = "longitud_estacion";
    public static final List<String> ESTACION_COLUMNS_LIST = List.of(ID_ESTACION_COLUMN,
                                                                     DIRECCION_ESTACION_COLUMN,
                                                                     MARGEN_ESTACION_COLUMN,
                                                                     TIPO_ESTACION_COLUMN,
                                                                     HORARIO_ESTACION_COLUMN,
                                                                     LATITUD_ESTACION_COLUMN,
                                                                     LONGITUD_ESTACION_COLUMN);

    // Atributos de EMPRESA_TABLE
    public static final String ID_EMPRESA_COLUMN     = "id_empresa";
    public static final String NOMBRE_EMPRESA_COLUMN = "nombre_empresa";
    public static final List<String> EMPRESA_COLUMNS_LIST = List.of(ID_EMPRESA_COLUMN,
                                                                    NOMBRE_EMPRESA_COLUMN);

    // Atributos de PRECIO_TABLE
    public static final String ID_PRECIO_COLUMN        = "id_precio_carburante";
    public static final String PRECIO_COLUMN           = "precio_carburante";
    public static final String FECHA_ACT_PRECIO_COLUMN = "fecha_act_precio_carburante";
    public static final List<String> PRECIO_COLUMNS_LIST = List.of(ID_PRECIO_COLUMN,
                                                                   PRECIO_COLUMN,
                                                                   FECHA_ACT_PRECIO_COLUMN);

    // Atributos de CARBURANTE_TABLE
    public static final String ID_CARBURANTE_COLUMN   = "id_carburante";
    public static final String TIPO_CARBURANTE_COLUMN = "tipo_carburante";
    public static final List<String> CARBURANTE_COLUMNS_LIST = List.of(ID_CARBURANTE_COLUMN,
                                                                       TIPO_CARBURANTE_COLUMN);
}

