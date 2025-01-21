# PFU Ingeniería de Software
## Asignatura Bases de Datos Avanzadas
## Actividad grupal: Bases de datos NoSQL y motores de búsqueda

### Emilio Brahim Quechen Romero
### Minerva Barroso Murillo
### Anabel Díaz


## Para establecer conexión con las BBDD correctamente a través del proyecto Java "bbdda-actividad3-grupal" se debe hacer lo siguiente:

	* Crear las variables de entorno:
		MYSQL_DB_NAME=estaciones_de_servicio_mysql;
		MYSQL_DB_HOST=localhost;
		MYSQL_USER=root;
		MYSQL_PASSWORD=mysql;
		MYSQL_DB_PORT=3306;
		
		MONGO_DB_NAME=estaciones_de_servicio_mongodb;
		MONGO_DB_HOST=localhost;
		MONGO_USER=root;
		MONGO_PASSWORD=mongo;
		MONGO_DB_PORT=27017;


	* Crear la BBDD MySQL con el nombre:
		"estaciones_de_servicio_mysql"
	
	* Ejecutar el script SQL que se encuentra en la carpeta "src/main/resources/sql" del proyecto Java:
		"estaciones_de_servicio_mysql.sql"

	* Crear la BBDD MongoDB con el nombre:
		"estaciones_de_servicio_mongodb"

## Flujo de ejecución:
		1. Inyectar datos en la BBDD MySQL desde ficheros CSV.
		2. Descargar los datos de la BBDD Mysql y almacenarlos en archivos JSON. (Directorio dividido por colecciones)
		3. Inyectar datos en la BBDD MongoDB desde los archivos JSON.
		4. Realizar consultas en la BBDD MySQL y MongoDB para comparar los resultados.

