package persistence;


import dao.IReadDao;
import exception.PersistenceException;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Clase que realiza consultas a la base de datos MySQL.
 *
 * @version 1.0 - 2025-01-15
 * @author Emilio, Anabel, Minerva
 */
@Slf4j
public class JDBC_ReadDao implements IReadDao {

    private final Connection connection;

    public JDBC_ReadDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Realiza una consulta a la base de datos para obtener todas las estaciones de servicio.
     *
     * @param query : String
     */
    @Override
    public void selectEstaciones(String query) throws PersistenceException {

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            Gson gson = new Gson();

            while (rs.next()) {
                if (rs.getObject("id_estacion") == null || rs.getObject("tipo_estacion") == null) {
                    log.warn("Salta la estación con id_estacion nulo o tipo_estacion nulo" + rs.getInt("id_estacion"));
                    continue;
                }

                JsonObject estacion = buildStationJson(rs, gson);

                String filePath = "src/main/resources/json/estaciones/";
                String fileName = filePath + "estacion_" + rs.getInt("id_estacion") + ".json";

                writeJsonToFile(estacion, fileName);

                log.info("JSON creado: {}", fileName);
            }

        } catch (SQLException e) {
            log.error("Error en acceso a la base de datos: {}", query, e);
            throw new PersistenceException("Error en acceso a la base de datos", e);
        }
    }

    public void selectEmpresas(String query) throws PersistenceException {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            Map<String, JsonObject> empresasMap = new HashMap<>();

            while (rs.next()) {
                buildEmpresaJson(rs, empresasMap);
            }

            // ver si escribe las emppresas
            String filePath = "src/main/resources/json/empresas/";
            for (JsonObject petrolera : empresasMap.values()) {
                String idEmpresa = petrolera.get("id_empresa").getAsString();
                String fileName = filePath + "empresa_" + idEmpresa + ".json";
                writeJsonToFile(petrolera, fileName);
                log.info("JSON creado: {}", fileName);
            }
        } catch (SQLException e) {
            log.error("Error en acceso a la base de datos: {}", query, e);
            throw new PersistenceException("Error en acceso a la base de datos", e);
        }
    }


    /*

    @Override
    public void selectEmpresas(String query) throws PersistenceException {

       try (Statement stmt = connection.createStatement();
                  ResultSet rs = stmt.executeQuery(query))  {

            Gson gson = new Gson();

            while (rs.next()) {
                if (rs.getObject("id_empresa") == null) {
                    log.warn("Salta la petrolera con id_empresa nulo o nombre_empresa nulo"
                            + rs.getInt("id_empresa"));
                    continue;
                }

                JsonObject petrolera = buildPetroleraJson(rs, gson);

                String filePath = "src/main/resources/json/empresas/";
                String fileName = filePath + "empresa_" + rs.getInt("id_empresa") + ".json";
                writeJsonToFile(petrolera, fileName);

                log.info("JSON creado: {}", fileName);
            }
        } catch (SQLException e) {
           log.error("Error en acceso a la base de datos: {}", query, e);
           throw new PersistenceException("Error en acceso a la base de datos", e);
       }
    }
*/
    /**
     * Realiza una consulta a la base de datos para obtener todos los carburantes.
     *
     * @param carburantesQuery : String
     */
    @Override
    public void selectCarburantes(String carburantesQuery) throws PersistenceException {

        try (Statement stmt = connection.createStatement();
              ResultSet rs = stmt.executeQuery(carburantesQuery)) {

            Gson gson = new Gson();

            while (rs.next()) {
                if (rs.getObject("id_carburante") == null) {
                    log.warn("Salta el carburante con id_carburante nulo"
                            + rs.getString("id_carburante"));
                    continue;
                }

                JsonObject carburante = buildCarburanteJson(rs, gson);

                String filePath = "src/main/resources/json/carburantes/";
                String fileName = filePath + "carburante_" + rs.getString("id_carburante") + ".json";
                writeJsonToFile(carburante, fileName);

                log.info("JSON creado: {}", fileName);
            }
        } catch (SQLException e) {
            log.error("Error en acceso a la base de datos: {}", carburantesQuery, e);
            throw new PersistenceException("Error en acceso a la base de datos", e);
        }
    }

    @Override
    public void selectPreciosCarburantes(String preciosCarburantesQuery) throws PersistenceException {

        try(Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(preciosCarburantesQuery)) {

            Gson gson = new Gson();

            while (rs.next()) {
                if (rs.getObject("id_precio_carburante") == null) {
                    log.warn("Salta el precio del carburante con id_precio_carburante nulo o precio_carburante nulo"
                            + rs.getString("id_precio_carburante"));
                    continue;
                }

                JsonObject precioCarburante = buildPrecioCarburanteJson(rs, gson);

                String filePath = "src/main/resources/json/precios/";
                String fileName = filePath + "precio_" + rs.getString("id_precio_carburante") + ".json";
                writeJsonToFile(precioCarburante, fileName);

                log.info("JSON creado: {}", fileName);
            }
        } catch (SQLException e) {
            log.error("Error en acceso a la base de datos: {}", preciosCarburantesQuery, e);
            throw new PersistenceException("Error en acceso a la base de datos", e);
        }
    }

    @Override
    public void selectUbicaciones(String ubicacionesQuery) throws PersistenceException {

        try(Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(ubicacionesQuery)) {

            Gson gson = new Gson();

            while (rs.next()) {
//                if (rs.getObject("id_localidad") == null) {
//                    log.warn("Salta la ubicación con id_localidad nulo"
//                            + rs.getString("id_localidad"));
//                    continue;
//                }
                int idLocalidadTemporal = rs.getInt("id_localidad");

                if(idLocalidadTemporal >= 4247) {
                    System.out.println(rs.getObject("nombre_localidad"));
                }

                JsonObject ubicacion = buildUbicacionJson(rs, gson);

                String filePath = "src/main/resources/json/ubicaciones/";
                String fileName = filePath + "ubicacion_" + rs.getString("id_localidad") + ".json";
                writeJsonToFile(ubicacion, fileName);

                log.info("JSON creado: {}", fileName);
            }
        } catch (SQLException e) {
            log.error("Error en acceso a la base de datos: {}", ubicacionesQuery, e);
            throw new PersistenceException("Error en acceso a la base de datos", e);
        }
    }

    /**
     * Construye un objeto JSON con los datos de una estación de servicio.
     *
     * @param rs : ResultSet
     * @param gson : Gson
     * @return JsonObject
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    @Override
    public JsonObject buildStationJson(ResultSet rs, Gson gson)  throws PersistenceException{
        try {
            JsonObject estacion = new JsonObject();
            estacion.addProperty("tipo", rs.getString("tipo_estacion"));
            estacion.addProperty("margen", rs.getString("margen_estacion"));
            estacion.addProperty("horario", rs.getString("horario_estacion"));

            JsonObject ubicacion = new JsonObject();
            ubicacion.addProperty("direccion", rs.getString("direccion_estacion"));
            ubicacion.addProperty("latitud", rs.getDouble("latitud_estacion"));
            ubicacion.addProperty("longitud", rs.getDouble("longitud_estacion"));
            ubicacion.addProperty("localidad", rs.getString("nombre_localidad"));
            ubicacion.addProperty("municipio", rs.getString("nombre_municipio"));
            ubicacion.addProperty("provincia", rs.getString("nombre_provincia"));
            ubicacion.addProperty("codigo_postal", rs.getString("numero_codigo_postal"));
            estacion.add("ubicacion", ubicacion);

            JsonObject empresa = new JsonObject();
            empresa.addProperty("empresa", rs.getString("nombre_empresa"));
            estacion.add("empresa", empresa);







            JsonObject carburante = new JsonObject();
            carburante.addProperty("carburante", rs.getString("tipo_carburante"));
            carburante.addProperty("precio", rs.getDouble("precio_carburante"));
            carburante.addProperty("fecha_actualizacion",
                                    rs.getString("fecha_act_precio_carburante"));

            JsonArray carburantes = new JsonArray();
            carburantes.add(carburante);
            estacion.add("carburantes", carburantes);



/*              //probaaaaar
            JsonArray carburantesRaw = gson.fromJson(rs.getString("tipo_carburante"), JsonArray.class);
            JsonArray carburantes    = new JsonArray();


            for (int i = 0; i < carburantesRaw.size(); i++) {
                JsonObject carburante = carburantesRaw.get(i).getAsJsonObject();

                if (carburante.has("precio_carburante") && !carburante.get("precio_carburante").isJsonNull())
                    carburantes.add(carburante); // Añadir solo combustibles válidos
            }
            estacion.add("carburantes", carburantes);
*/







            return estacion;
        } catch (SQLException e) {
            log.error("Error en acceso a la base de datos", e);
            throw new PersistenceException("Error en acceso a la base de datos", e);
        }


    }

    /**
     * Construye un objeto JSON con los datos de una empresa.
     *
     * @param rs : ResultSet
     * @return JsonObject
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    @Override
    public JsonObject buildEmpresaJson(ResultSet rs, Map<String, JsonObject> empresasMap) throws PersistenceException {
        try {
            String idEmpresa = rs.getString("id_empresa");
            String nombreEmpresa = rs.getString("nombre_empresa");
            String idEstacion = rs.getString("id_estacion");

            // Fetch or create the empresa JSON object
            JsonObject empresa = empresasMap.getOrDefault(idEmpresa, new JsonObject());
            empresa.addProperty("id_empresa", idEmpresa);
            empresa.addProperty("nombre_empresa", nombreEmpresa);

            // Fetch or create the estaciones array
            JsonArray estaciones = empresa.has("estaciones") ? empresa.getAsJsonArray("estaciones") : new JsonArray();

            // Add the current estacion to the estaciones array if not null
            if (idEstacion != null) {
                JsonObject estacion = new JsonObject();
                estacion.addProperty("id_estacion", idEstacion);
                estaciones.add(estacion);
            }

            // Update the estaciones array in the empresa object
            empresa.add("estaciones", estaciones);

            // Save the updated object back to the map
            empresasMap.put(idEmpresa, empresa);

            return empresa;
        } catch (SQLException e) {
            log.error("Error en acceso a la base de datos", e);
            throw new PersistenceException("Error en acceso a la base de datos", e);
        }
    }


    /**
     * Construye un objeto JSON con los datos de un carburante.
     *
     * @param rs : ResultSet
     * @param gson : Gson
     * @return JsonObject
     * @throws PersistenceException : cuando hay un error en el acceso a la base de datos
     */
    @Override
    public JsonObject buildCarburanteJson(ResultSet rs, Gson gson) throws PersistenceException {
        try {
            JsonObject carburante = new JsonObject();
            carburante.addProperty("id_carburante", rs.getString("id_carburante"));
            carburante.addProperty("tipo_carburante", rs.getString("tipo_carburante"));
            return carburante;
        } catch (SQLException e) {
            log.error("Error en acceso a la base de datos", e);
            throw new PersistenceException("Error en acceso a la base de datos", e);
        }
    }

    @Override
    // precio_carburante
    public JsonObject buildPrecioCarburanteJson(ResultSet rs, Gson gson) throws PersistenceException {
        try{
            JsonObject precioCarburante = new JsonObject();
            precioCarburante.addProperty("id_precio_carburante", rs.getString("id_precio_carburante"));
            precioCarburante.addProperty("precio_carburante", rs.getDouble("precio_carburante"));
            precioCarburante.addProperty("fecha_act_precio_carburante", rs.getString("fecha_act_precio_carburante"));
            return precioCarburante;
        } catch (SQLException e) {
            log.error("Error en acceso a la base de datos", e);
            throw new PersistenceException("Error en acceso a la base de datos", e);
        }
    }

    @Override
    public JsonObject buildUbicacionJson(ResultSet rs, Gson gson) throws PersistenceException {

        try {
            JsonObject ubicacion = new JsonObject();
            ubicacion.addProperty("id_localidad", rs.getString("id_localidad"));
            ubicacion.addProperty("nombre_localidad", rs.getString("nombre_localidad"));
            ubicacion.addProperty("id_municipio", rs.getInt("id_municipio"));
            ubicacion.addProperty("nombre_municipio", rs.getString("nombre_municipio"));
            ubicacion.addProperty("id_provincia", rs.getInt("id_provincia"));
            ubicacion.addProperty("nombre_provincia", rs.getString("nombre_provincia"));
            ubicacion.addProperty("id_codigo_postal", rs.getInt("id_codigo_postal"));
            ubicacion.addProperty("numero_codigo_postal", rs.getString("numero_codigo_postal"));
            return ubicacion;
        } catch (SQLException e) {
            log.error("Error en acceso a la base de datos", e);
            throw new PersistenceException("Error en acceso a la base de datos", e);
        }
    }

    /**
     * Escribe un objeto JSON en un archivo.
     *
     * @param jsonObject : JsonObject
     * @param fileName : String
     */
    @Override
    public void writeJsonToFile(JsonObject jsonObject, String fileName) throws PersistenceException {
        try (FileWriter writer = new FileWriter(fileName)) {
            new Gson().toJson(jsonObject, writer);
        } catch (IOException e) {
            log.error("Error de escritura: {}", fileName, e);
            throw new PersistenceException("Error de escritura", e);
        }
    }
}