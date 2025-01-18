package app;

import app.read.MysqlRead;
import app.write.MysqlWrite;

public class Main {
    public static void main(String[] args) {

        // 1. Insert data into MySQL
        // 2. Read data from MySQL y creamos los JSON
        // 3. Insert data into MongoDB


        //pendiente

        // modificar el MySqlConnector --- sacar el rs y el ps de la clase
        // e implementarlos en elos metodos de la clase

        //reorganizar proyecto y ver implementacion del dao

        // crear conexion con mongo

        // insertar datos en mongo

/*
        MysqlWrite.insertEmpresas();
        MysqlWrite.insertCarburantes();
        MysqlWrite.insertProvincias();
        MysqlWrite.insertLocalidades();

        MysqlWrite.insertMunicipios();
        MysqlWrite.insertCodigosPostales();
        MysqlWrite.insertRelacionCpLocalidad();
        MysqlWrite.insertEstaciones();
        MysqlWrite.insertPrecioCarburante();
*/

        MysqlRead.selectEstaciones();
    }
}
