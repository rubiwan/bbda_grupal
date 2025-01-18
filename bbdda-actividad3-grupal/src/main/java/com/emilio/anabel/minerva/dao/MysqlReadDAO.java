package com.emilio.anabel.minerva.dao;

import com.emilio.anabel.minerva.util.MysqlConnector;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;


import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase que realiza consultas a la base de datos MySQL.
 *
 * @version 1.0 - 2025-01-15
 * @author Emilio, Anabel, Minerva
 */
@Slf4j
public class MysqlReadDAO {


    /**
     * Realiza una consulta a la base de datos para obtener todas las estaciones de servicio.
     *
     * @param query : String
     */
    public static void selectEstaciones(String query) {
        MysqlConnector db = new MysqlConnector(true);

        try (Connection connection = db.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            Gson gson = new Gson();

            while (rs.next()) {
                if (rs.getObject("id_estacion") == null || rs.getObject("tipo_estacion") == null) {
                    log.warn("Salta la estación con id_estacion nulo o tipo_estacion nulo" + rs.getInt("id_estacion"));
                    continue;
                }

                JsonObject estacion = buildStationJson(rs, gson);

                String filePath = "src/main/resources/json/";
                String fileName = filePath + "estacion_" + rs.getInt("id_estacion") + ".json";

                writeJsonToFile(estacion, fileName);

                log.info("JSON creado: {}", fileName);
            }

        } catch (SQLException e) {
            log.error("Error en acceso a la base de datos: {}", query, e);
        } finally {
            db.close();
        }
    }

    /**
     * Realiza una consulta a la base de datos para obtener todas las empresas.
     *
     * @param query : String
     */
    public static void selectPetroleras(String query) {
        MysqlConnector db = new MysqlConnector(true);

        try (ResultSet rs = db.select(query)) {

            Gson gson = new Gson();

            while (rs.next()) {
                if (rs.getObject("id_empresa") == null || rs.getObject("nombre_empresa") == null) {
                    log.warn("Salta la petrolera con id_empresa nulo o nombre_empresa nulo" + rs.getInt("id_empresa"));
                    continue;
                }

                JsonObject petrolera = buildPetroleraJson(rs, gson);

                String filePath = "src/main/resources/json/";
                String fileName = filePath + "petrolera_" + rs.getInt("id_empresa") + ".json";
                writeJsonToFile(petrolera, fileName);

                log.info("JSON creado: {}", fileName);
            }
        } catch (SQLException e) {
            log.error("Error en acceso a la base de datos: {}", query, e);
        } finally {
            db.close();
        }
    }

    /**
     * Realiza una consulta a la base de datos para obtener todos los carburantes.
     *
     * @param carburantesQuery : String
     */
    public static void selectCarburantes(String carburantesQuery) {

        MysqlConnector db = new MysqlConnector(true);

        try (ResultSet rs = db.select(carburantesQuery)) {

            Gson gson = new Gson();

            while (rs.next()) {
                if (rs.getObject("id_carburante") == null || rs.getObject("tipo_carburante") == null) {
                    log.warn("Salta el carburante con id_carburante nulo o tipo_carburante nulo" + rs.getInt("id_carburante"));
                    continue;
                }

                JsonObject carburante = buildCarburanteJson(rs, gson);

                String filePath = "src/main/resources/json/";
                String fileName = filePath + "carburante_" + rs.getInt("id_carburante") + ".json";
                writeJsonToFile(carburante, fileName);

                log.info("JSON creado: {}", fileName);
            }
        } catch (SQLException e) {
            log.error("Error en acceso a la base de datos: {}", carburantesQuery, e);
        } finally {
            db.close();
        }
    }

    public static void selectPreciosCarburantes(String preciosCarburantesQuery) {
    }

    public static void selectUbicaciones(String ubicacionesQuery) {
    }

    /**
     * Construye un objeto JSON con los datos de una estación de servicio.
     *
     * @param rs : ResultSet
     * @param gson : Gson
     * @return JsonObject
     * @throws SQLException : cuando hay un error en el acceso a la base de datos
     */
    private static JsonObject buildStationJson(ResultSet rs, Gson gson) throws SQLException {
        JsonObject estacion = new JsonObject();
        estacion.addProperty("id_estacion", rs.getString("id_estacion"));
        estacion.addProperty("tipo_estacion", rs.getString("tipo_estacion"));
        estacion.addProperty("margen_estacion", rs.getString("margen_estacion"));
        estacion.addProperty("horario_estacion", rs.getString("horario_estacion"));
        estacion.addProperty("direccion_estacion", rs.getString("direccion_estacion"));
        estacion.addProperty("latitud_estacion", rs.getDouble("latitud_estacion"));
        estacion.addProperty("longitud_estacion", rs.getDouble("longitud_estacion"));

        JsonObject ubicacion = new JsonObject();
        ubicacion.addProperty("id_localidad", rs.getString("id_localidad"));
        ubicacion.addProperty("nombre_localidad", rs.getString("nombre_localidad"));
        ubicacion.addProperty("id_municipio", rs.getInt("id_municipio"));
        ubicacion.addProperty("nombre_municipio", rs.getString("nombre_municipio"));
        ubicacion.addProperty("id_provincia", rs.getInt("id_provincia"));
        ubicacion.addProperty("nombre_provincia", rs.getString("nombre_provincia"));
        ubicacion.addProperty("id_codigo_postal", rs.getInt("id_codigo_postal"));
        ubicacion.addProperty("numero_codigo_postal", rs.getString("numero_codigo_postal"));
        estacion.add("ubicacion", ubicacion);

        JsonObject empresa = new JsonObject();
        empresa.addProperty("id_empresa", rs.getString("id_empresa"));
        empresa.addProperty("nombre_empresa", rs.getString("nombre_empresa"));
        estacion.add("empresa", empresa);

        JsonObject carburante = new JsonObject();
        carburante.addProperty("id_carburante", rs.getString("id_carburante"));
        carburante.addProperty("tipo_carburante", rs.getString("tipo_carburante"));
        carburante.addProperty("precio_carburante", rs.getDouble("precio_carburante"));
        carburante.addProperty("fecha_act_precio_carburante", rs.getString("fecha_act_precio_carburante"));

        JsonArray combustibles = new JsonArray();
        combustibles.add(carburante);
        estacion.add("combustibles", combustibles);

        return estacion;
    }

    /**
     * Construye un objeto JSON con los datos de una empresa.
     *
     * @param rs : ResultSet
     * @param gson : Gson
     * @return JsonObject
     * @throws SQLException : cuando hay un error en el acceso a la base de datos
     */
    private static JsonObject buildPetroleraJson(ResultSet rs, Gson gson) throws SQLException {
        JsonObject petrolera = new JsonObject();
        petrolera.addProperty("id_empresa", rs.getString("id_empresa"));
        petrolera.addProperty("nombre_empresa", rs.getString("nombre_empresa"));

        JsonArray estaciones = new JsonArray();

        JsonObject estacion = new JsonObject();
        estacion.addProperty("id_estacion", rs.getString("id_estacion"));
        estaciones.add(estacion);

        petrolera.add("estaciones", estaciones);

        return petrolera;
    }

    private static JsonObject buildCarburanteJson(ResultSet rs, Gson gson) {
        return null;
    }

    // precio_carburante
    private static JsonObject buildPrecioCarburanteJson(ResultSet rs, Gson gson) {
        return null;
    }

    private static JsonObject buildUbicacionJson(ResultSet rs, Gson gson) {
        return null;
    }

    /**
     * Escribe un objeto JSON en un archivo.
     *
     * @param jsonObject : JsonObject
     * @param fileName : String
     */
    private static void writeJsonToFile(JsonObject jsonObject, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            new Gson().toJson(jsonObject, writer);
        } catch (IOException e) {
            log.error("Error de escritura: {}", fileName, e);
        }
    }
}