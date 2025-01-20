create table if not exists carburante
(
    id_carburante   int auto_increment
        primary key,
    tipo_carburante varchar(50) not null,
    constraint tipo_carburante
        unique (tipo_carburante)
);

create table if not exists empresa
(
    id_empresa     int auto_increment
        primary key,
    nombre_empresa varchar(100) not null
);

create table if not exists localidad
(
    id_localidad     int auto_increment
        primary key,
    nombre_localidad varchar(150) not null
);

create table if not exists estacion
(
    id_estacion        int auto_increment
        primary key,
    direccion_estacion varchar(150)                   not null,
    margen_estacion    char                           null,
    tipo_estacion      enum ('Terrestre', 'Marítima') not null,
    horario_estacion   varchar(100)                   not null,
    latitud_estacion   decimal(10, 6)                 not null,
    longitud_estacion  decimal(11, 6)                 not null,
    id_localidad       int                            not null,
    id_empresa         int                            not null,
    constraint estacion_ibfk_1
        foreign key (id_localidad) references localidad (id_localidad),
    constraint estacion_ibfk_2
        foreign key (id_empresa) references empresa (id_empresa)
);

create table if not exists precio_carburante
(
    id_precio_carburante        int auto_increment
        primary key,
    precio_carburante           decimal(8, 3) not null,
    fecha_act_precio_carburante varchar(25)   null,
    id_carburante               int           not null,
    id_estacion                 int           not null,
    constraint precio_carburante_ibfk_1
        foreign key (id_carburante) references carburante (id_carburante),
    constraint precio_carburante_ibfk_2
        foreign key (id_estacion) references estacion (id_estacion)
);

create table if not exists provincia
(
    id_provincia     int auto_increment
        primary key,
    nombre_provincia varchar(50) not null
);

create table if not exists municipio
(
    id_municipio     int auto_increment
        primary key,
    nombre_municipio varchar(100) not null,
    id_provincia     int          not null,
    constraint municipio_ibfk_1
        foreign key (id_provincia) references provincia (id_provincia)
);

create table if not exists codigo_postal
(
    id_codigo_postal     int auto_increment
        primary key,
    numero_codigo_postal char(5) not null,
    id_municipio         int     not null,
    constraint numero_codigo_postal
        unique (numero_codigo_postal, id_municipio),
    constraint codigo_postal_ibfk_1
        foreign key (id_municipio) references municipio (id_municipio)
);

create table if not exists relacion_cp_localidad
(
    id_codigo_postal int not null,
    id_localidad     int not null,
    primary key (id_codigo_postal, id_localidad),
    constraint relacion_cp_localidad_ibfk_1
        foreign key (id_codigo_postal) references codigo_postal (id_codigo_postal),
    constraint relacion_cp_localidad_ibfk_2
        foreign key (id_localidad) references localidad (id_localidad)
);

create index idx_tipo_estacion_empresa_estacion
    on estacion (tipo_estacion, id_empresa);

create index idx_carburante_precio_carburante
    on precio_carburante (id_carburante, precio_carburante);

create index idx_latitud_longitud
    on estacion (latitud_estacion, longitud_estacion);
