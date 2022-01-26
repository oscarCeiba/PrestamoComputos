DROP TABLE IF EXISTS tb_usuario;

CREATE TABLE tb_usuario(
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  cedula BIGINT NOT NULL,
  nombre VARCHAR(100) NOT NULL,
  tipo_usuario INT NOT NULL,
  UNIQUE KEY cedula (cedula)
);

DROP TABLE IF EXISTS tb_solicitud_prestamo;

CREATE TABLE tb_solicitud_prestamo (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  cedula BIGINT NOT NULL,
  equipo_computo VARCHAR(50) NOT NULL,
  fecha_creacion DATE NOT NULL,
  fecha_entrega DATE NOT NULL,
  estado INT NOT NULL
);

DROP TABLE IF EXISTS tb_suspendido;

CREATE TABLE tb_suspendido(
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   cedula BIGINT NOT NULL,
   fecha_suspension DATE NOT NULL,
   fecha_fin_suspension  DATE NOT NULL,
   pago_realizado BIGINT NOT NULL
);

INSERT INTO tb_usuario (cedula,nombre,tipo_usuario)VALUES(1023009035,'Oscar David Cruz Quijano',1);
INSERT INTO tb_usuario (cedula,nombre,tipo_usuario)VALUES(1023009036,'Pepito Perez',1);
INSERT INTO tb_usuario (cedula,nombre,tipo_usuario)VALUES(1023009037,'Andrea Gomez',1);
INSERT INTO tb_usuario (cedula,nombre,tipo_usuario)VALUES(1023009038,'Diego ceiba',1);

INSERT INTO tb_usuario (cedula,nombre,tipo_usuario)VALUES(1023009039,'Juliet Rodriguez',2);
INSERT INTO tb_usuario (cedula,nombre,tipo_usuario)VALUES(1023009040,'Juan Perez',2);
INSERT INTO tb_usuario (cedula,nombre,tipo_usuario)VALUES(1023009041,'Pedro Hernadez',2);
INSERT INTO tb_usuario (cedula,nombre,tipo_usuario)VALUES(1023009042,'Alejandro Gutierrez',2);

INSERT INTO tb_usuario (cedula,nombre,tipo_usuario)VALUES(1023009043,'Martha Pardo',3);

INSERT INTO tb_usuario (cedula,nombre,tipo_usuario)VALUES(1023009044,'Elenea Hernandez',1);

INSERT INTO tb_solicitud_prestamo (cedula,equipo_computo,fecha_creacion,fecha_entrega,estado)
VALUES (1023009044,'Dell','2021-11-01','2021-11-15',2);

INSERT INTO tb_suspendido(cedula,fecha_suspension,fecha_fin_suspension,pago_realizado)
VALUES(1023009044,'2021-11-16','2021-12-14',55500);

INSERT INTO tb_solicitud_prestamo (cedula,equipo_computo,fecha_creacion,fecha_entrega,estado)
VALUES (1023009038,'Dell','2022-01-10','2022-01-24',1);

INSERT INTO tb_solicitud_prestamo (cedula,equipo_computo,fecha_creacion,fecha_entrega,estado)
VALUES (1023009042,'Asus 5','2021-12-27','2022-01-17',1);

INSERT INTO tb_solicitud_prestamo (cedula,equipo_computo,fecha_creacion,fecha_entrega,estado)
VALUES (1023009037,'Asus1','2022-01-25','2022-02-09',1);


