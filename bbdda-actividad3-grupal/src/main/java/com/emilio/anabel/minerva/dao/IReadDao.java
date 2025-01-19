package com.emilio.anabel.minerva.dao;

import com.emilio.anabel.minerva.config.MysqlConnector;
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


public interface IReadDao {


    /**
     * Realiza una consulta a la base de datos para obtener todas las estaciones de servicio.
     *
     * @param query : String
     */
    public void selectEstaciones(String query);

    /**
     * Realiza una consulta a la base de datos para obtener todas las empresas.
     *
     * @param query : String
     */
    public void selectPetroleras(String query);

    /**
     * Realiza una consulta a la base de datos para obtener todos los carburantes.
     *
     * @param carburantesQuery : String
     */
    public void selectCarburantes(String carburantesQuery);

    public void selectPreciosCarburantes(String preciosCarburantesQuery);

    public void selectUbicaciones(String ubicacionesQuery);

    /**
     * Construye un objeto JSON con los datos de una estación de servicio.
     *
     * @param rs : ResultSet
     * @param gson : Gson
     * @return JsonObject
     * @throws SQLException : cuando hay un error en el acceso a la base de datos
     */
    public JsonObject buildStationJson(ResultSet rs, Gson gson) throws SQLException;

    /**
     * Construye un objeto JSON con los datos de una empresa.
     *
     * @param rs : ResultSet
     * @param gson : Gson
     * @return JsonObject
     * @throws SQLException : cuando hay un error en el acceso a la base de datos
     */
    public JsonObject buildPetroleraJson(ResultSet rs, Gson gson) throws SQLException;

    public JsonObject buildCarburanteJson(ResultSet rs, Gson gson);
    // precio_carburante
    public JsonObject buildPrecioCarburanteJson(ResultSet rs, Gson gson);

    public JsonObject  buildUbicacionJson(ResultSet rs, Gson gson);

    /**
     * Escribe un objeto JSON en un archivo.
     *
     * @param jsonObject : JsonObject
     * @param fileName : String
     */
    public void writeJsonToFile(JsonObject jsonObject, String fileName);
}