package com.emilio.anabel.minerva.persistence;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que extrae los datos de un archivo CSV.
 *
 * @version 1.0 - 2025-01-15
 * @author Emilio, Anabel, Minerva
 */
@Slf4j
public final class GestorCSV {

    //ejecutar en el constructor

    /**
     * Lee un archivo CSV y devuelve una lista con los valores de cada fila.
     *
     * @param fileName : String
     * @return ArrayList<String [ ]>
     */
    public static ArrayList<String[]> readCSV(String fileName) {
        String filePath = "src/main/resources/csv/" + fileName;

        try (com.opencsv.CSVReader reader = new CSVReaderBuilder(new FileReader(filePath))
                .withCSVParser(new CSVParserBuilder()
                        .withSeparator(';')
                        .build())
                .withSkipLines(1)
                .build()) {

            List<String[]> csvLines = reader.readAll();
            ArrayList<String[]> rowsValues = new ArrayList<>(csvLines);

            return rowsValues;

        } catch (IOException | CsvException e) {
            log.error("Error en la lectura del: {}", fileName, e);
            return null;
        }
    }
}


