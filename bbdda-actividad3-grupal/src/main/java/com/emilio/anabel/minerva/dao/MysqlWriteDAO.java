package com.emilio.anabel.minerva.dao;

import com.emilio.anabel.minerva.util.MysqlConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MysqlWriteDAO {

    private  MysqlWriteDAO() {};

    public static void insert(ArrayList<String[]> rowsValuesArray, String tableName, List<String> columnsList) {
        MysqlConnector db = new MysqlConnector(columnsList, true);
        db.update("INSERT INTO " + tableName + " " + db.getInsertString(rowsValuesArray));
        db.close();
    }

    public static ArrayList<String[]> select(String tableName, List<String> columnsList) {
        MysqlConnector db = new MysqlConnector(columnsList, true);

        try(ResultSet rs = db.select("SELECT " + String.join(", ", columnsList) + " FROM " + tableName)) {
            ArrayList<String[]> rowsValuesArray = new ArrayList<>();

            while (rs.next()) {
                String[] row = new String[columnsList.size()];

                for (int i = 0 ; i < columnsList.size() ; i++) {
                    row[i] = rs.getString(i+1);
                }
                rowsValuesArray.add(row);
            }
            return rowsValuesArray;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void update(String tableName, List<String> columnsList,
                       ArrayList<String[]> rowsValuesArray, String idRowName,
                       String id) {

        MysqlConnector db = new MysqlConnector(columnsList, true);
        db.update(db.getUpdateString(rowsValuesArray, tableName, idRowName, id));
        db.close();
    }

    public static void delete(String tableName, List<String> columnsList,
                       String idRowName, String idToDelete) {

        MysqlConnector db = new MysqlConnector(columnsList, true);
        db.delete(db.getDeleteString(tableName, idRowName, idToDelete));
        db.close();
    }
}
