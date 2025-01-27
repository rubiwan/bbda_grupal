package config;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import exception.LogicException;
import exception.PersistenceException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class JsonToElasticBulk {

    /**
     * Combina todos los archivos JSON de estaciones en un único archivo JSONL,
     * estructurado según el índice propuesto, e incluye metadatos para Elasticsearch.
     *
     * @throws IOException Si ocurre algún error al leer/escribir archivos.
     */
    public static void combineJsonFiles() throws IOException {
        String inputDir = "src/main/resources/json/estaciones/";
        String outputFile = "src/main/resources/elasticsearch/estaciones_bulk_raw.jsonl";

        Gson gson = new Gson();
        Type mapType = new TypeToken<Map<String, Object>>() {}.getType();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            Files.list(Paths.get(inputDir))
                    .filter(path -> path.toString().endsWith(".json")) // Filtrar archivos JSON
                    .forEach(filePath -> {
                        try (Reader reader = new FileReader(filePath.toFile())) {
                            //Leer el JSON como un objeto
                            Map<String, Object> station = gson.fromJson(reader, mapType);

                            //Transformar el JSON según el índice
                            Map<String, Object> transformedStation = transformToElasticIndex(station);

                            //Escribir metadatos para Elasticsearch
                            writer.write("{\"index\":{\"_index\":\"estaciones\"}}");
                            writer.newLine();

                            //Escribir el JSON resultante como una línea
                            writer.write(gson.toJson(transformedStation));
                            writer.newLine();
                        } catch (IOException e) {
                            log.error("Error al procesar el archivo: " + filePath + " - " + e.getMessage());
                        }
                    });
        }
        log.info("Archivo combinado JSONL creado: " + outputFile);
    }

    /**
     * Transforma un mapa JSON original en la estructura esperada por el índice de Elasticsearch.
     *
     * @param map Mapa JSON original.
     * @return Mapa JSON transformado.
     */
    private static Map<String, Object> transformToElasticIndex(Map<String, Object> map) {
        Map<String, Object> result = new HashMap<>();

        //Asignar campos directamente según el índice
        result.put("tipo", map.get("tipo_estacion"));
        result.put("margen", map.get("margen_estacion"));
        result.put("horario", map.get("horario_estacion"));

        //Ubicación
        Map<String, Object> ubicacion = new HashMap<>();
        Map<String, Object> originalUbicacion = (Map<String, Object>) map.get("ubicacion");
        if (originalUbicacion != null) {
            ubicacion.put("direccion", map.get("direccion_estacion"));
            ubicacion.put("coordenadas", Map.of(
                    "lat", map.get("latitud_estacion"),
                    "lon", map.get("longitud_estacion")
            ));
            ubicacion.put("localidad", originalUbicacion.get("nombre_localidad"));
            ubicacion.put("municipio", originalUbicacion.get("nombre_municipio"));
            ubicacion.put("provincia", originalUbicacion.get("nombre_provincia"));
            ubicacion.put("codigo_postal", originalUbicacion.get("numero_codigo_postal"));
        }
        result.put("ubicacion", ubicacion);

        //Empresa
        Map<String, Object> empresa = new HashMap<>();
        Map<String, Object> originalEmpresa = (Map<String, Object>) map.get("empresa");
        if (originalEmpresa != null) {
            empresa.put("empresa", originalEmpresa.get("nombre_empresa"));
        }
        result.put("empresa", empresa);

        //Carburantes
        List<Map<String, Object>> carburantes = (List<Map<String, Object>>) map.get("combustibles");
        if (carburantes != null) {
            for (Map<String, Object> carburante : carburantes) {
                carburante.remove("id_carburante"); // Eliminar ID
                carburante.put("carburante", carburante.remove("tipo_carburante"));
                carburante.put("precio", carburante.remove("precio_carburante"));
                carburante.put("fecha_actualizacion", carburante.remove("fecha_act_precio_carburante"));
            }
            result.put("carburantes", carburantes);
        }
        return result;
    }
}
