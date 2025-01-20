package dao;

import exception.PersistenceException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.sql.ResultSet;
import java.util.Map;


/**
 * Interfaz que define los métodos para realizar consultas a la base de datos.
 *
 * @version 1.0 - 2025-01-15
 * @author Emilio, Anabel, Minerva
 */
public interface IReadDao {


    /**
     * Realiza una consulta a la base de datos para obtener todas las estaciones de servicio.
     *
     * @param query : String
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    void selectEstaciones(String query) throws PersistenceException;

    /**
     * Realiza una consulta a la base de datos para obtener todas las empresas.
     *
     * @param query : String
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    void selectEmpresas(String query)throws PersistenceException;

    /**
     * Realiza una consulta a la base de datos para obtener todos los carburantes.
     *
     * @param carburantesQuery : String
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    void selectCarburantes(String carburantesQuery) throws PersistenceException;

    /**
     * Realiza una consulta a la base de datos para obtener todos los precios de los carburantes.
     *
     * @param preciosCarburantesQuery : String
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    void selectPreciosCarburantes(String preciosCarburantesQuery) throws PersistenceException;

    /**
     * Realiza una consulta a la base de datos para obtener todas las ubicaciones.
     *
     * @param ubicacionesQuery : String
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    void selectUbicaciones(String ubicacionesQuery) throws PersistenceException;

    /**
     * Construye un objeto JSON con los datos de una estación de servicio.
     *
     * @param rs : ResultSet
     * @param gson : Gson
     * @return JsonObject
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    JsonObject buildStationJson(ResultSet rs, Gson gson) throws PersistenceException;

    /**
     * Construye un objeto JSON con los datos de una empresa.
     *
     * @param rs : ResultSet
     * @return JsonObject
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    JsonObject buildPetroleraJson(ResultSet rs, Map<Integer, JsonObject> empresasMap) throws PersistenceException;

    /**
     * Construye un objeto JSON con los datos de un carburante.
     *
     * @param rs : ResultSet
     * @param gson : Gson
     * @return JsonObject
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    JsonObject buildCarburanteJson(ResultSet rs, Gson gson) throws PersistenceException;

    /**
     * Construye un objeto JSON con los datos de un precio de carburante.
     *
     * @param rs : ResultSet
     * @param gson : Gson
     * @return JsonObject
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    JsonObject buildPrecioCarburanteJson(ResultSet rs, Gson gson) throws PersistenceException;

    /**
     * Construye un objeto JSON con los datos de una ubicación.
     *
     * @param rs : ResultSet
     * @param gson : Gson
     * @return JsonObject
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    JsonObject  buildUbicacionJson(ResultSet rs, Gson gson) throws PersistenceException;

    /**
     * Escribe un objeto JSON en un archivo.
     *
     * @param jsonObject : JsonObject
     * @param fileName : String
     */
    void writeJsonToFile(JsonObject jsonObject, String fileName)throws PersistenceException;
}