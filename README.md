# PFU Ingeniería de Software
## Asignatura Bases de Datos Avanzadas
## Actividad grupal: Bases de datos NoSQL y motores de búsqueda

### Emilio Brahim Quechen Romero
### Minerva Barroso Murillo
### Anabel Díaz


## Para establecer conexión con las BBDD correctamente a través del proyecto Java "bbdda-actividad3-grupal" se debe hacer lo siguiente:

### Configuración Java
	1A. Crear las variables de entorno:
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

	1B. Copiar y pegar en run/debug configurations si se usa Inteliij:
		MONGO_DB_HOST=localhost;MONGO_DB_NAME=estaciones_de_servicio_mongodb;MONGO_DB_PASSWORD=mongo;MONGO_DB_PORT=27017;MONGO_DB_USER=root;MYSQL_DB_HOST=localhost;MYSQL_DB_NAME=estaciones_de_servicio_mysql;MYSQL_DB_PASSWORD=mysql;MYSQL_DB_PORT=3306;MYSQL_DB_USER=root



### Configuración MySQL
	2. Instalar y ejecutar un contenedor de Docker con una imagen de MySQL:
		docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=mysql -d -p 3306:3306 mysql:latest

	3. Crear la conexión con MySQL en DataGrip y crear la BBDD con el nombre:
		"estaciones_de_servicio_mysql"
	
	4. Ejecutar en DataGrip el script SQL (DDL) que se encuentra en la ruta del proyecto "src/main/resources/sql":
		"estaciones_de_servicio_mysql.sql"



### Configuración MongoDB
	5. Instalar y ejecutar un contenedor de Docker con una imagen de MongoDB:
		docker run --name mongo-container -e MONGO_INITDB_ROOT_USERNAME=root -e MONGO_INITDB_ROOT_PASSWORD=mongo -d -p 27017:27017 mongo:latest

	6. Crear la conexión con MongoDB en DataGrip

	7. La BBDD MongoDB se creará automáticamente con la primera inyección de datos en el
	siguiente paso, si decides crearla antes debes hacerlo con el nombre:
		"estaciones_de_servicio_mongodb"


### Finalmente
	8. Ejecutar el programa Java.

## Flujo de ejecución:
	8.1. Inyectar datos en la BBDD MySQL desde ficheros CSV.
	8.2. Descargar los datos de la BBDD Mysql y almacenarlos en archivos JSON.
	(Directorio dividido por colecciones)
	8.3. Inyectar datos en la BBDD MongoDB desde los archivos JSON.
	8.4. Generar el archivo "estaciones_bulk_raw.jsonl" con todas las estaciones
	para su implementación en EslasticSearch

### Uso de ElasticSearch:
	* Se ha creado un clúster en bonsai.io a través de una cuenta compartida por el grupo
	de trabajo
	* En dicho clúster se ha cargado el índice propuesto por este grupo, se encuentra
	almacenado en un archivo JSON en la ruta del proyecto 
	"src/main/resources/elasticsearch/bonsai_io":
		"elasticsearch_index.json"
	* Se han cargado los registros en el índice usando el archivo
	"estaciones_bulk_raw.jsonl" (generado a traves del programa en el punto 8.4).
	

##	Debido a la necesidad de credenciales, se enviarán para su prueba los comandos:
#### 	curl -X POST
	(Carga todos los registros en el índice propuesto para ElasticSearch usando
	el archivo generado en el programa).
#### 	curl -X GET
	(Devuleve todos los registros del índice para su comprobación).

