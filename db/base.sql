--roles de los usuarios pueden ser profesor, profesor comite, administrador, estudiante
create table rol(
    id_rol SERIAL primary key,
    nombre varchar(30)
);

--usuarios creados por el administrador
create table usuario(
    id_usuario serial primary key,
    nombre varchar(30),
    nombre_usuario varchar(30) unique, --para que no se repita el nombre de usuario en la tabla
    contrasena text,
    correo text unique check (correo ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+(\.[A-Za-z]{2,})+$'),
    activo boolean --para que tenga la estructura de un correo
);

--se guarda el registro de que tipo de rol a tenido el usuario en el tiempo 
create table presenta(
    id_rol integer,
    id_usuario integer,
    periodo integer check (periodo >= 1 and periodo <=2), --se restringe que el periodo solo puede ser 1 o 2
    anio integer,
    primary key (id_rol, id_usuario, periodo, anio),
    foreign key (id_rol) references rol(id_rol),
    foreign key (id_usuario) references usuario(id_usuario)
);

--donde se guarda el microcurriculo creado por los profesores esperando que sea revisado por los profesores comite
create table microcurriculo(
    id_microcurriculo serial primary key,
    contenido bytea,
    estado varchar(20),
    nombre varchar(20),
    anio_creacion integer,
    id_usuario integer references usuario(id_usuario)
);

--donde se guarda el material pertinente a los laboratorios que se anexan con el microcurriculo
create table laboratorio(
    id_lab serial primary key,
    nombre varchar(20),
    descripcion text,
    archivo bytea,
    id_microcurriculo integer references microcurriculo(id_microcurriculo)
);

--se almacenan los comentarios que son realizados por los usuarios
create table comentario(
    id_comentario serial primary key,
    fecha date,
    autor varchar(30),
    relevancia varchar(15),
    contenido text,
    id_usuario integer references usuario(id_usuario),
    id_microcurriculo integer references microcurriculo(id_microcurriculo)
);

--se guardan los cursos que son creados con el plan de estudio
create table curso(
    id_curso serial primary key,
    nombre varchar(20),
    creditos integer check (creditos > 0 and creditos <= 4), --no permite que los créditos sean mayores a 4 ni menores que 1
    semestre integer check (semestre > 0 and semestre <= 10), --no permite que el semestre pase de 10 ni que baje de 1
    tipo varchar(2)
);

--se relacionan los microcurrículos con los cursos
create table tiene(
    id_curso integer references curso(id_curso),
    id_microcurriculo integer references microcurriculo(id_microcurriculo),
    anio integer,
    periodo integer check (periodo >= 1 and periodo <= 2), --solo permite que sea 1 o 2
    primary key (id_curso, id_microcurriculo)
);

--se almacenan los planes de estudio
create table plan_estudio(
    id_plan_estudio serial primary key,
    archivo bytea,
    descripcion text,
    nombre varchar(20)
);

--se relacionan los planes de estudio con los cursos
create table pertenece(
    id_plan_estudio integer references plan_estudio(id_plan_estudio),
    id_curso integer references curso(id_curso),
    anio integer,
    primary key (id_plan_estudio, id_curso)
);

insert into rol (nombre) values ('Administrador'), ('Profesor de Comite'), ('Profesor'), ('Estudiante');


-- Inserta el usuario
INSERT INTO usuario (nombre, nombre_usuario, contrasena, correo, activo)
VALUES ('Administrador', 'admin', '$2a$10$rRudbKUhoXlyylTFaBpOAuk6F/TIYfDp.NQ40f/fQXXZpNIXb3k9O', 'admin@unillanos.edu.co', TRUE);

-- Inserta el rol del usuario
INSERT INTO presenta (id_rol, id_usuario, periodo, anio)
VALUES ((SELECT id_rol FROM rol WHERE nombre = 'Administrador'), 
        (SELECT id_usuario FROM usuario WHERE nombre_usuario = 'admin'), 
        1, 2024);