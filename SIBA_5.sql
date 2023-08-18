create database SIBA;
use SIBA;

CREATE TABLE Usuarios(
    id_usuario INT auto_increment,
    nombre VARCHAR(50) NOT NULL,
    apellido_paterno VARCHAR(50) NOT NULL,
	apellido_materno VARCHAR(50) NOT NULL,
    correo VARCHAR(100) NOT NULL,
    contrasenia varbinary(128) not null,
    telefono VARCHAR(12) NOT NULL,
    rol int NOT NULL,
    PRIMARY KEY(id_usuario)
);

CREATE TABLE Docentes(
	no_trabajador varchar(30) primary key,
    division varchar(50) not null,
    id_usuario int not null, 
    foreign key (id_usuario) references usuarios(id_usuario)
);

CREATE TABLE Alumnos(
	matricula varchar(30) primary key,
	carrera varchar(200) not null,
    grado int not null,
    grupo varchar(2) not null,
    id_usuario int not null,
    foreign key (id_usuario) references usuarios(id_usuario)
);

CREATE TABLE Ubicaciones_libros(
	id INTEGER AUTO_INCREMENT,
    pasillo INT NOT NULL,
    seccion INT NOT NULL,
    estante varchar(4) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE Libros (
	id_libro INTEGER AUTO_INCREMENT,
	titulo VARCHAR(50) NOT NULL,
    isbn VARCHAR(17) NOT NULL,
	editorial VARCHAR(50) NOT NULL,
	id_ub  integer NOT NULL,
	PRIMARY KEY (id_libro),
    FOREIGN KEY (id_ub) references ubicaciones_libros(id)
  );

CREATE TABLE libros_img(
    id INTEGER AUTO_INCREMENT,
    id_libro INT,
    file_name VARCHAR(200),
    file MEDIUMBLOB,
    primary key (id),
    foreign key (id_libro) references libros(id_libro)
);
  
CREATE TABLE Autores (
  id_autor INT AUTO_INCREMENT,
  nombre VARCHAR(50) NOT NULL,
  apellido_paterno VARCHAR(50),
  apellido_materno VARCHAR(50),
  PRIMARY KEY (id_autor)
  );

CREATE TABLE libros_autores(
	id_libro INT  NOT NULL,
	id_autor INT NOT NULL,
	PRIMARY KEY (id_libro, id_autor),
    FOREIGN KEY (id_libro)
    REFERENCES Libros (id_libro),
    FOREIGN KEY (id_autor)
    REFERENCES Autores (id_autor)
);

CREATE TABLE Ejemplares (
  id_ejemplar INT,
  observaciones VARCHAR(255) NULL,
  id_libro INT NOT NULL,
  PRIMARY KEY (id_ejemplar),
  FOREIGN KEY (id_libro) REFERENCES Libros (id_libro)
);

CREATE TABLE Salas(
    id_sala INT auto_increment,
    nombre VARCHAR(50) NOT NULL,
    capacidad int NOT NULL,
    descripcion varchar(100)not null,
    PRIMARY KEY(id_sala)
);

CREATE TABLE Prestamos_Libros(
    id_prestamo_libro INT AUTO_INCREMENT,
    id_ejemplar INT NOT NULL,
    id_usuario INT NOT NULL,
    fecha_inicio TIMESTAMP NOT NULL,
    fecha_devolucion TIMESTAMP NOT NULL,
    estatus ENUM('Activo', 'Finalizado') NOT NULL DEFAULT 'Activo',
    PRIMARY KEY(id_prestamo_libro),
    FOREIGN KEY (id_ejemplar) REFERENCES Ejemplares (id_ejemplar),
    FOREIGN KEY (id_usuario) REFERENCES Usuarios (id_usuario)
);

CREATE TABLE Prestamos_Salas(
    id_prestamo_sala INT AUTO_INCREMENT,
    id_sala INT NOT NULL,
    id_usuario INT NOT NULL,
    fecha_inicio TIMESTAMP NOT NULL,
    fecha_devolucion TIMESTAMP NOT NULL,
    estatus ENUM('Activo', 'Finalizado') NOT NULL DEFAULT 'Activo',
    PRIMARY KEY(id_prestamo_sala),
    FOREIGN KEY (id_sala) REFERENCES Salas (id_sala),
    FOREIGN KEY (id_usuario) REFERENCES Usuarios (id_usuario)
);

CREATE TABLE Change_Log (
    id_log integer auto_increment primary key,
    tabla varchar(20) not null,
    informe varchar(255) not null,
    usuario varchar(15),
    fecha timestamp not null
);

CREATE VIEW Prestamos_Libros_Usuario AS
SELECT u.id_usuario, 
	concat(u.nombre, ' ', u.apellido_paterno, ' ',apellido_materno ) as nombre,
    u.correo as correo,
	CASE 
        WHEN u.rol = 3 THEN 'Docente'
        wHEN u.rol = 4 THEN 'Alumno'
    END AS rol,
    p.id_prestamo_libro, p.id_ejemplar, p.fecha_inicio, p.fecha_devolucion
FROM Usuarios u
INNER JOIN Prestamos_Libros p ON u.id_usuario = p.id_usuario;

CREATE VIEW Prestamos_Libros_docentes AS
SELECT u.id_usuario, 
	concat(u.nombre, ' ', u.apellido_paterno, ' ',apellido_materno ) as nombre,
    u.correo as correo,
    d.no_trabajador,
    d.division,
    p.id_prestamo_libro, p.id_ejemplar, p.fecha_inicio, p.fecha_devolucion, estatus
FROM docentes d
INNER JOIN Usuarios u on u.id_usuario=d.id_usuario
INNER JOIN Prestamos_Libros p ON u.id_usuario = p.id_usuario;

CREATE VIEW Prestamos_Libros_Alumnos AS
SELECT u.id_usuario, 
	concat(u.nombre, ' ', u.apellido_paterno, ' ',apellido_materno ) as nombre,
    u.correo as correo,
    a.matricula,
    concat(a.grado, '-',a.grupo) as grado_grupo,
    p.id_prestamo_libro, p.id_ejemplar, p.fecha_inicio, p.fecha_devolucion, estatus
FROM alumnos a
INNER JOIN Usuarios u on u.id_usuario=a.id_usuario
INNER JOIN Prestamos_Libros p ON u.id_usuario = p.id_usuario;

CREATE VIEW Prestamos_Salas_Usuario AS
SELECT u.id_usuario, 
	concat(u.nombre, ' ', u.apellido_paterno, ' ',apellido_materno ) as nombre,
    u.correo as correo,
	CASE 
        WHEN u.rol = 3 THEN 'Docente'
        wHEN u.rol = 4 THEN 'Alumno'
    END AS rol,
    p.id_prestamo_sala, p.id_sala, p.fecha_inicio, p.fecha_devolucion, estatus
FROM Usuarios u
INNER JOIN Prestamos_salas p ON u.id_usuario = p.id_usuario;

CREATE VIEW Prestamos_salas_docentes AS
SELECT u.id_usuario, 
	concat(u.nombre, ' ', u.apellido_paterno, ' ',apellido_materno ) as nombre,
    u.correo as correo,
    d.no_trabajador,
    d.division,
    p.id_prestamo_sala, p.id_sala, p.fecha_inicio, p.fecha_devolucion, estatus
FROM docentes d
INNER JOIN Usuarios u on u.id_usuario=d.id_usuario
INNER JOIN Prestamos_salas p ON u.id_usuario = p.id_usuario;

CREATE VIEW Prestamos_salas_Alumnos AS
SELECT u.id_usuario, 
	concat(u.nombre, ' ', u.apellido_paterno, ' ',apellido_materno ) as nombre,
    u.correo as correo,
    a.matricula,
    concat(a.grado, '-',a.grupo) as grado_grupo,
    p.id_prestamo_sala, p.id_sala, p.fecha_inicio, p.fecha_devolucion, estatus
FROM alumnos a
INNER JOIN Usuarios u on u.id_usuario=a.id_usuario
INNER JOIN Prestamos_salas p ON u.id_usuario = p.id_usuario;

create view Datos_Libros as
select 
	l.titulo as titulo,
	group_concat(concat(a.nombre,' ', a.apellido_paterno,' ', a.apellido_materno)) As autor_es,
    l.editorial as Editorial,
    l.isbn as ISBN,
    (select count(*) from ejemplares e where e.id_libro=l.id_libro) as Unidades
from libros_autores la 
INNER join autores a on a.id_autor=la.id_autor
INNER join libros l on l.id_libro= la.id_libro 
group by l.id_libro;

CREATE VIEW Disponibilidad_de_Ejemplares AS
SELECT 
    l.titulo AS titulo,
    COUNT(CASE WHEN p.estatus = 'Activo' THEN 1 END) AS disponibles,
    COUNT(CASE WHEN p.estatus = 'Finalizado' THEN 1 END) AS en_prestamo
FROM libros l
INNER JOIN ejemplares e ON l.id_libro = e.id_libro
INNER JOIN prestamos_libros p ON p.id_ejemplar=e.id_ejemplar
GROUP BY p.id_ejemplar;

create view Cantidad_de_libros_por_estante as
SELECT 
    estante,
	Count(*) as libros 
From ubicaciones_libros u
join libros l on l.id_ub=u.id
group by estante order by u.estante;

CREATE UNIQUE INDEX idx_id_usuario_docentes on docentes(id_usuario);

CREATE UNIQUE INDEX idx_id_usuario_alumnos on alumnos(id_usuario);

CREATE INDEX idx_pasillo_ubicaciones_libros on Ubicaciones_libros(pasillo);

CREATE INDEX idx_seccion_estante_ubicaciones_libros on Ubicaciones_libros(seccion, estante);

CREATE INDEX idx_titulo_libros on Libros(titulo);

CREATE INDEX idx_editorial_titulo_libros on Libros(editorial, titulo);

CREATE INDEX idx_nombre_autores on autores(nombre);

CREATE INDEX idx_nombre_completo_autores on autores(nombre, apellido_paterno, apellido_materno);

CREATE INDEX idx_estatatus_prestamos_libros ON prestamos_libros(estatus);

CREATE INDEX idx_estatatus_prestamos_salas ON prestamos_salas(estatus);

CREATE INDEX idx_tabla_change_log ON change_log(tabla);

CREATE INDEX idx_usuario_nombre ON USUARIOS (nombre);

CREATE INDEX idx_prestamoLibro_inicio_fin ON PRESTAMOS_LIBROS (fecha_inicio, fecha_devolucion);

CREATE INDEX idx_prestamoSala_inicio_fin ON PRESTAMOS_SALAS (fecha_inicio, fecha_devolucion);

CREATE INDEX idx_carrera_alumno ON alumnos (carrera);

DELIMITER $$
CREATE TRIGGER after_insert_ubicaciones_libros AFTER INSERT ON Ubicaciones_libros
FOR EACH ROW 
BEGIN
    INSERT INTO change_log(id_log, tabla, informe, usuario, fecha) values (0,'Ubicaciones_libros',
    concat('Se ha insertado la ubicación con id: ', NEW.id,
    ', Pasillo: ', NEW.pasillo,', Sección: ',NEW.seccion, ', Estante: ', NEW.estante)
    , current_user(),
    sysdate());
END;
$$

DELIMITER $$
CREATE TRIGGER after_update_ubicaciones_libros AFTER UPDATE ON Ubicaciones_libros
FOR EACH ROW
BEGIN
    INSERT INTO change_log(id_log, tabla, informe, usuario, fecha) VALUES (0, 'Ubicaciones_libros',
    concat('Se ha actualizado la ubicación con el id: ', OLD.id, 
    ', Ubicación Pasada: Pasillo: ', OLD.pasillo,', Sección: ', OLD.seccion,', Estante: ',OLD.estante,', Ubicación Nueva: Pasillo: ', NEW.pasillo,', Sección: ', NEW.seccion,', Estante: ',NEW.estante),
    current_user(),
    sysdate());
END;
$$

DELIMITER $$
CREATE TRIGGER after_delete_ubicaciones_libros AFTER DELETE ON Ubicaciones_libros
FOR EACH ROW
BEGIN
    INSERT INTO change_log (id_log, tabla, informe, usuario, fecha) VALUES (0, 'Ubicaciones_libros',
    concat('Se ha borrado la Ubicación con id: ', OLD.id,
    ', Pasillo: ', OLD.pasillo, ', Sección: ',OLD.seccion, ',Estante',OlD.estante),
    current_user(),
    sysdate());
END;
$$

DELIMITER $$
CREATE TRIGGER after_insert_libros AFTER INSERT ON Libros
FOR EACH ROW
BEGIN
    INSERT INTO change_log (id_log, tabla, informe, usuario, fecha) VALUES (0, 'Libros', 
    CONCAT('Se ha insertado un nuevo libro con id: ', NEW.id_libro, 
           ', titulo: ', NEW.titulo, ', ISBN: ', NEW.isbn,
           ', editorial: ', NEW.editorial, ' y localización: ', NEW.id_ub), current_user(),
	SYSDATE());
END;$$

DELIMITER $$
CREATE TRIGGER after_update_libros AFTER UPDATE ON Libros
FOR EACH ROW
BEGIN
    INSERT INTO change_log (id_log, tabla, informe, usuario, fecha) VALUES (0, 'Libros', 
    CONCAT('Se ha actualizado un libro con id: ', OLD.id_libro, 
           'Datos anteriores, titulo: ', OLD.titulo, ', ISBN: ', OLD.isbn, ', editorial: ', OLD.editorial, 
           ', id_ubicacion: ', OLD.id_ub, ' Datos nuevos, titulo: ',new.titulo,', ISBN: ',new.isbn,', Editorial: ',new.editorial,', id_ubicacion: ', NEW.id_ub), current_user(),
	SYSDATE());
END;$$

DELIMITER $$
CREATE TRIGGER after_delete_libros AFTER DELETE ON Libros
FOR EACH ROW
BEGIN
    INSERT INTO change_log (id_log, tabla, informe, usuario, fecha) VALUES (0, 'Libros', 
    CONCAT('Se ha borrado un libro con id: ', OLD.id_libro, 
           ', titulo: ', OLD.titulo, ', ISBN: ', OLD.isbn, 
           ', editorial: ', OLD.editorial, ' y localización : ', OLD.id_ub), current_user(),
	SYSDATE());
END;$$

DELIMITER $$
CREATE TRIGGER validar_eliminacion_libros BEFORE DELETE ON Libros
FOR EACH ROW
BEGIN
    IF (select count(*) from ejemplares e where e.id_libro=OLD.id_libro) > 0 then
		SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'Para eliminar un libro tienes que borrar todos lo ejemplares relacionados a este';
	END IF;
END;$$

DELIMITER $$
CREATE TRIGGER after_insert_autores AFTER INSERT ON Autores
FOR EACH ROW
BEGIN
    INSERT INTO change_log (id_log, tabla, informe, usuario, fecha) VALUES (0, 'Autores', 
    CONCAT('Se ha insertado un nuevo autor con id: ', NEW.id_autor, 
           ' y nombre: ', NEW.nombre, NEW.apellido_paterno, NEW.apellido_materno), current_user(),
	SYSDATE());
END;$$

DELIMITER $$
CREATE TRIGGER after_update_autores AFTER UPDATE ON Autores
FOR EACH ROW
BEGIN
    INSERT INTO change_log (id_log, tabla, informe, usuario, fecha) VALUES (0, 'Autores', 
    CONCAT('Se ha actualizado un autor con id: ', OLD.id_autor, 
           ', nombre anterior: ', OLD.nombre, OLD.apellido_paterno, OLD.apellido_materno,
           ' y nombre nuevo: ', NEW.nombre, NEW.apellido_paterno, NEW.apellido_materno), current_user(),
	SYSDATE());
END;$$

DELIMITER $$
CREATE TRIGGER after_delete_autores AFTER DELETE ON Autores
FOR EACH ROW
BEGIN
    INSERT INTO change_log (id_log, tabla, informe, usuario, fecha) VALUES (0, 'Autores', 
    CONCAT('Se ha borrado un autor con id: ', OLD.id_autor, 
           ' y nombre: ', OLD.nombre, OLD.apellido_paterno, OLD.apellido_materno), current_user(),
	SYSDATE());
END;$$

DELIMITER $$
CREATE TRIGGER after_insert_ejemplares AFTER INSERT ON Ejemplares
FOR EACH ROW
BEGIN
    INSERT INTO change_log (id_log, tabla, informe, usuario, fecha) VALUES (0, 'Ejemplares', 
    CONCAT('Se ha insertado un nuevo ejemplar con id: ', NEW.id_ejemplar, 
           ', observaciones: ', COALESCE(NEW.observaciones, 'N/A'), 
           ', del libro con id: ', NEW.id_libro), current_user(),
    SYSDATE());
END;
$$

DELIMITER $$
CREATE TRIGGER after_update_ejemplares AFTER UPDATE ON Ejemplares
FOR EACH ROW
BEGIN
    INSERT INTO change_log (id_log, tabla, informe, usuario, fecha) VALUES (0, 'Ejemplares', 
    CONCAT('Se ha actualizado un ejemplar con id: ', OLD.id_ejemplar, 
           ', observaciones anteriores: ', COALESCE(OLD.observaciones, 'N/A'), 
           ', observaciones nuevas: ', NEW.observaciones,
           ', del libro con id: ', OLD.id_libro), current_user(),
    SYSDATE());
END;$$

DELIMITER $$
CREATE TRIGGER validar_eliminacion_ejemplares BEFORE DELETE ON EJEMPLARES
FOR EACH ROW
BEGIN
	IF (SELECT estatus FROM prestamos_libros p WHERE p.id_ejemplar=OLD.id_ejemplar) = 'Finalizado' OR NOT (EXISTS(SELECT * FROM prestamos_libros p WHERE p.id_ejemplar=OLD.id_ejemplar)) THEN
		DELETE FROM prestamos_libros WHERE id_ejemplar=OLD.id_ejemplar;
	ELSE 
		SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'El ejemplar no se puede eliminar porque tiene un prestamo en curso';
    END IF;
END;$$

DELIMITER $$
create trigger validar_eliminacion_sala before delete on salas
for each row
begin
    if(select estatus from prestamos_salas p where p.id_sala=OLD.id_sala) = 'Finalizado' OR NOT (EXISTS(select * from prestamos_salas p where p.id_sala=OLD.id_sala)) then
        delete from prestamos_salas p where id_sala=OLD.id_sala;
        else
        SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'La sala no se puede eliminar porque tiene un prestamo en curso';
    end if;
end;$$

DELIMITER $$
CREATE TRIGGER after_delete_ejemplares AFTER DELETE ON Ejemplares
FOR EACH ROW
BEGIN
    INSERT INTO change_log (id_log, tabla, informe, usuario, fecha) VALUES (0, 'Ejemplares', 
    CONCAT('Se ha borrado un ejemplar con id: ', OLD.id_ejemplar, 
           ', observaciones: ', COALESCE(OLD.observaciones, 'N/A'), 
           ', del libro con id: ', OLD.id_libro), current_user(),
    SYSDATE());
END;
$$

DELIMITER $$
CREATE TRIGGER after_insert_usuarios AFTER INSERT ON usuarios
FOR EACH ROW
BEGIN
	declare rol_ varchar(20);
	case
	    when NEW.rol = 1 then set rol_ = 'Administrador';
        when NEW.rol = 2 then set rol_ = 'Bibliotecario';
		when NEW.rol = 3 then set rol_ = 'Docente';
		when NEW.rol = 4 then set rol_ = 'Alumno';
        else
            signal sqlstate '45000' set  message_text  = 'El rol de usuario no es valido';
	end case;
    INSERT INTO change_log (id_log, tabla, informe, usuario, fecha) VALUES (0, 'Usuarios', 
    CONCAT('Se ha insertado un nuevo usuario con id: ', NEW.id_usuario, 
           ',Nombre: ',new.nombre, 'Apellido: ',new.apellido_paterno,' ', new.apellido_materno, ', correo: ', NEW.correo,
           ', Rol:', rol_)
           , current_user(),
	SYSDATE());
END;$$

DELIMITER $$
CREATE TRIGGER after_update_usuarios AFTER UPDATE ON Usuarios
FOR EACH ROW
BEGIN
	declare rol_ varchar(20);
	case
	     when rol = 0 then set rol_ = 'Desactivado';
	    when rol = 1 then set rol_ = 'Administrador';
        when rol = 2 then set rol_ = 'Bibliotecario';
		when rol = 3 then set rol_ = 'Docente';
		when rol = 4 then set rol_ = 'Alumno';
	end case;
    INSERT INTO change_log (id_log, tabla, informe, usuario, fecha) VALUES (0, 'Usuarios', 
    CONCAT('Se ha actualizado un usuario con id: ', OLD.id_usuario, 
           ',Nombre anterior: ', OLD.Nombre,' ',old.apellido_paterno,' ',old.apellido_materno,
           ', Nombre actual: ', new.Nombre,' ',new.apellido_paterno,' ',new.apellido_materno,
           ', Rol: ', rol_),
            current_user(),
	SYSDATE());
END;$$

CREATE TRIGGER validar_eliminacion_usuario BEFORE DELETE ON usuarios
FOR EACH ROW
BEGIN
	IF (select estatus from prestamos_libros p WHERE p.id_usuario=OLD.id_usuario) = 'Finalizado' OR NOT(EXISTS(select * from prestamos_libros p WHERE p.id_usuario=OLD.id_usuario))
		AND (select estatus from prestamos_salas p WHERE p.id_usuario=id_usuario) = 'Finalizado' OR NOT(EXISTS(select estatus from prestamos_salas p WHERE p.id_usuario=OLD.id_usuario)) THEN
        DELETE FROM prestamos_salas WHERE id_usuario=OLD.id_usuario;
        DELETE FROM prestamos_libros WHERE id_usuario=OLD.id_usuario;
	ELSE
		SIGNAL sqlstate '45000' SET MESSAGE_TEXT = 'El usuario tiene prestamos en curso';
    END IF;
END;

DELIMITER $$
CREATE TRIGGER after_delete_Usuarios AFTER DELETE ON Usuarios
FOR EACH ROW
BEGIN
	declare rol_ varchar(20);
    case 
        when OLD.rol = 1 then set rol_ = 'Administrador';
        when OLD.rol = 2 then set rol_ = 'Bibliotecario';
		when OLD.rol = 3 then set rol_ = 'Docente';
		when OLD.rol = 4 then set rol_ = 'Alumno';
	end case;
    INSERT INTO change_log (id_log, tabla, informe, usuario, fecha) VALUES (0, 'Usuarios', 
    CONCAT('Se ha borrado un Usuario con id: ', OLD.id_usuario, 
           ', Nombre: ', OLD.Nombre,' ',old.apellido_paterno,' ',old.apellido_materno, 
           ', Correo: ', OLD.correo, ', Rol: ', rol_), current_user(),
	SYSDATE());
END;$$

DELIMITER $$
CREATE PROCEDURE Insertar_ubicacion ( _pasillo int, _seccion int, _estante VARCHAR(500), out mensaje varchar(255))
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET mensaje = 'Tipo de dato incorrecto, Pasillo y Seccion deben ser datos numéricos';
    END;
    SET autocommit = 0;
    START TRANSACTION;
    CASE
		WHEN EXISTS (SELECT * FROM Ubicaciones_Libros WHERE seccion=_seccion and estante=_estante) THEN set mensaje =  'Ya existe una ubicación con esa información';
        ROLLBACK;
		WHEN NOT _estante REGEXP '^[a-zA-Z]+$' then
		SET mensaje = 'El estante deben ser letras';
        ROLLBACK;
    ELSE
		INSERT INTO ubicaciones_libros (pasillo, seccion, estante) VALUES (cast(_pasillo as unsigned), cast(_seccion as unsigned), UPPER(_estante));
		SET mensaje = 'Se ha registrado la ubicación correctamente';
		COMMIT;
    END CASE;
    SET autocommit = 1;
END;$$

DELIMITER $$
CREATE PROCEDURE actualizar_ubicacion( _id int, _pasillo int, _seccion int, _estante VARCHAR(500), out mensaje varchar(255))
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET mensaje = 'Tipo de dato incorrecto, Pasillo y Seccion deben ser datos numéricos';
    END;
    SET autocommit = 0;
    START TRANSACTION;
    CASE
		WHEN EXISTS (SELECT * FROM Ubicaciones_Libros WHERE pasillo=_pasillo and seccion=_seccion and estante=_estante and id<>_id) THEN set mensaje =  'Ya existe una ubicación con esa información';
        ROLLBACK;
		WHEN NOT _estante REGEXP '^[a-zA-Z]+$' then
		SET mensaje = 'El estante deben ser letras';
        ROLLBACK;
    ELSE
		update ubicaciones_libros set pasillo =cast(_pasillo as unsigned) , seccion = cast(_seccion as unsigned), estante = UPPER(_estante) where id=_id;
		SET mensaje = 'Se ha actualizado la ubicacion correctamente';
		COMMIT;
    END CASE;
    SET autocommit = 1;
END;$$

DELIMITER $$
CREATE PROCEDURE Eliminar_ubicacion (_id int, out mensaje varchar(255))
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
		SET mensaje = 'La ubicacion no se puede eliminar porque esta en uso';
    END;
    CASE
		WHEN NOT EXISTS(select * from ubicaciones_libros where id=_id) then
			SET mensaje = 'La ubicación no existe';
            ROLLBACK;
	ELSE
		DELETE FROM ubicaciones_libros WHERE id=cast(_id as unsigned);
        SET mensaje = 'La ubicación se eliminó correctamente';
        COMMIT;
    END CASE;
    SET autocommit = 1;
END;$$


DELIMITER $$
CREATE PROCEDURE Insertar_Libro ( _titulo VARCHAR(500), _isbn VARCHAR(500), _editorial VARCHAR(500),
    _id_ub int, _id_ejemmplar int, _observaciones varchar(500), _autores varchar(100),
    _file_name varchar(100), _file MEDIUMBLOB, out mensaje varchar(255))
BEGIN
    DECLARE _id_libro int;
    DECLARE  _id_autor int;

	DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET mensaje = 'Error en la transacción';
    END;
    SET autocommit = 0;
    START TRANSACTION;
    SET _id_ub = cast(_id_ub as unsigned);
    case
		when LENGTH(_isbn) <> 17  AND LENGTH(_isbn) <> 13 then set mensaje = 'El formato del ISBN es incorrecto, contempla dígitos y guiones';
		ROLLBACK;
        when EXISTS (SELECT * FROM Libros WHERE isbn = _isbn) THEN set mensaje =  'EL ISBN debe ser único';
        ROLLBACK;
        when length(_titulo) > 50 or length(_editorial) > 50 then set mensaje = 'El nombre de la editorial o el titulo son demasiado largos';
		ROLLBACK;
        when EXISTS(SELECT * FROM ejemplares WHERE id_ejemplar = _id_ejemmplar) THEN SET mensaje = 'El id del ejemplar no puede repetirse';
        ROLLBACK;
        else
            INSERT INTO Libros (titulo, isbn, editorial, id_ub) VALUES (_titulo, _isbn, _editorial, _id_ub);
            set _id_libro = last_insert_id();
            IF (_file_name IS NOT NULL) AND (_file IS NOT NULL) THEN
                INSERT INTO libros_img (id_libro, file_name, file) values (_id_libro, _file_name, _file);
            END IF;
            INSERT INTO ejemplares(id_ejemplar, observaciones, id_libro) values (_id_ejemmplar, _observaciones, _id_libro);
            WHILE LENGTH(_autores) > 0 DO
                SET _id_autor = SUBSTRING_INDEX(_autores, ',', 1);
                INSERT INTO libros_autores(id_libro, id_autor) VALUES (_id_libro, _id_autor);
                SET _autores = SUBSTRING(_autores, LENGTH(_id_autor) + 2);
            END WHILE;
                set mensaje = 'El libro se ha insertado correctamente';
				COMMIT;
    end case;
    SET autocommit = 1;
END;$$

DELIMITER $$
CREATE PROCEDURE actualizar_libro (_id int, _titulo VARCHAR(500), _editorial VARCHAR(500),
    _id_ub int, _observaciones varchar(500), _file_name varchar(100), _file MEDIUMBLOB, out mensaje varchar(255))
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET mensaje = 'Error en la transacción';
    END;
    SET autocommit = 0;
    START TRANSACTION;
    SET _id_ub = cast(_id_ub as unsigned);
    case
        when length(_titulo) > 50 or length(_editorial) > 50 then set mensaje = 'El nombre de la editorial o el titulo son demasiado largos';
		ROLLBACK;
        else
            UPDATE Libros SET titulo = _titulo, editorial = _editorial, id_ub = _id_ub WHERE id_libro = _id;
            IF (_file_name IS NOT NULL) AND (_file IS NOT NULL) THEN
                UPDATE libros_img SET file_name = _file_name, file = _file WHERE id_libro = _id;
            END IF;
            UPDATE ejemplares SET observaciones = _observaciones WHERE id_libro = _id;
            set mensaje = 'El libro se ha actualizado correctamente';
			COMMIT;
    end case;
    SET autocommit = 1;
END;$$



DELIMITER $$
CREATE PROCEDURE eliminar_libro (_id_libro INTEGER, out mensaje varchar(255))
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET mensaje = 'Error';
    END;
    SET autocommit = 0;
    START TRANSACTION;
    IF EXISTS (SELECT * FROM Libros WHERE id_libro=_id_libro) AND (SELECT count(*) FROM ejemplares WHERE id_libro=_id_libro) <= 1 THEN

        DELETE FROM ejemplares
        WHERE id_libro =_id_libro;

        DELETE FROM libros_autores
        WHERE id_libro=_id_libro;

        DELETE FROM libros_img
        WHERE id_libro =_id_libro;

        DELETE FROM Libros
        WHERE id_libro=_id_libro;

        SET mensaje =  'El libro se eliminó correctamente';
        COMMIT;
    ELSE
        SET mensaje =  'Uso incorrecto';
        ROLLBACK;
    END IF;
    SET autocommit = 1;
END;$$


DELIMITER $$
CREATE PROCEDURE insertar_bibliotecario (
    _nombre VARCHAR(50),
    _apellido_paterno VARCHAR(50),
    _apellido_materno VARCHAR(50),
    _correo VARCHAR(100),
    _contrasenia varchar(100),
    _telefono VARCHAR(12),
    OUT mensaje varchar(100)
)
BEGIN
    DECLARE contrasenia_encrypt varbinary(128);

    DECLARE exit HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET mensaje = 'Se produjo una excepción durante la transacción';
    END;
    SET autocommit = 0;
    START TRANSACTION;
    IF EXISTS (SELECT * FROM Usuarios WHERE correo = _correo) THEN
        SET mensaje = CONCAT('El correo ', _correo  ,' ya se encuentra ligado a una cuenta');
        ROLLBACK;
    ELSE
        SET contrasenia_encrypt = aes_encrypt(_contrasenia, 'A4E7C6AE4013276DEABC1BE4F9C65A6DC382F317E0CB81497ABA26915CDE5B66');
            INSERT INTO Usuarios (nombre, apellido_paterno, apellido_materno, correo, contrasenia, telefono, rol)
            VALUES (_nombre, _apellido_paterno, _apellido_materno, _correo, contrasenia_encrypt, _telefono, 2);
            SET mensaje = 'La cuenta de bibliotecario se ha registrado correctamente';
        COMMIT;
    END IF;
    SET autocommit = 1;
END;$$

DELIMITER $$
CREATE PROCEDURE Eliminar_Usuario (_id_usuario INT, OUT mensaje VARCHAR(255))
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET mensaje = 'El usuario no se puede eliminar porque tiene un prestamo pendiente';
    END;
    SET autocommit = 0;
    START TRANSACTION;
    IF EXISTS (SELECT * FROM Usuarios WHERE id_usuario=_id_usuario) THEN
        DELETE FROM Usuarios
        WHERE id_usuario=_id_usuario;
        IF EXISTS (SELECT * FROM Alumnos WHERE id_usuario=_id_usuario) THEN
                DELETE FROM Alumnos
                WHERE id_usuario=_id_usuario;
		END IF;
        IF EXISTS (SELECT * FROM Docentes WHERE id_usuario=_id_usuario) THEN
                DELETE FROM Docentes
                WHERE id_usuario=_id_usuario;
		END IF;
        COMMIT;
    ELSE
        SET mensaje = 'El usuario a eliminar no existe';
    END IF;
    SET autocommit = 1;
END;$$

create procedure eliminar_sala(IN _id Integer, OUT mensaje varchar(255))
begin
 DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SELECT 'No se puede eliminar la sala porque tiene un prestamo activo' into mensaje;
    END;
	SET autocommit = 0;
    START TRANSACTION;
    IF EXISTS (SELECT * FROM salas WHERE id_sala=_id) THEN
        DELETE FROM salas
        WHERE id_sala=_id;
        SET mensaje =  'Se ha elimindado la sala correctamente';
        COMMIT;
    ELSE
        SET mensaje =  'La sala a eliminar no existe';
        ROLLBACK;
    END IF;
    SET autocommit = 1;
end;$$

DELIMITER $$
CREATE PROCEDURE actualizar_sala (_id_sala integer ,_nombre varchar(500), _capacidad VARCHAR(500), _descripcion varchar(500), out mensaje varchar(255))
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET mensaje = 'Tipo de dato incorrecto, la capacidad debe ser un numero entero';
    END;
    set _capacidad = cast(_capacidad as unsigned);
    SET autocommit = 0;
    START TRANSACTION;
    CASE
		WHEN Length(_nombre) > 50 then set mensaje =  'Nombre de la sala demasiado largo';
        ROLLBACK;
		WHEN LENGTH(_descripcion) > 200  then set mensaje = 'Descripción demasiado larga';
		WHEN EXISTS (SELECT * FROM salas WHERE nombre=_nombre and id_sala <> _id_sala) THEN set mensaje =  'El nombre de la sala ya esta en uso';
        ROLLBACK;
    ELSE
		update salas set nombre = _nombre, capacidad =  _capacidad, descripcion = _descripcion where  id_sala = _id_sala;
		SET mensaje = 'Se ha actualizado la sala correctamente';
		COMMIT;
    END CASE;
    SET autocommit = 1;
END;$$

delimiter $$
create procedure ver_salas(inicio int, limite int)
begin
select s.*, p.estatus as prestamo from salas s LEFT OUTER JOIN prestamos_salas p ON s.id_sala=p.id_sala limit inicio, limite;
end;$$

DELIMITER $$
CREATE PROCEDURE Insertar_sala ( _nombre varchar(500), _capacidad VARCHAR(500), _descripcion varchar(500), out mensaje varchar(255))
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET mensaje = 'Tipo de dato incorrecto, la capacidad debe ser un numero entero';
    END;
    set _capacidad = cast(_capacidad as unsigned);
    SET autocommit = 0;
    START TRANSACTION;
    CASE 
		WHEN Length(_nombre) > 50 then set mensaje =  'Nombre de la sala demasiado largo';
        ROLLBACK;
		WHEN LENGTH(_descripcion) > 100  then set mensaje = 'Descripción demasiado larga';
		WHEN EXISTS (SELECT * FROM salas WHERE nombre=_nombre) THEN set mensaje =  'El nombre de la sala ya esta en uso';
        ROLLBACK;
    ELSE  
		INSERT INTO salas (nombre, capacidad, descripcion) VALUES (_nombre, _capacidad, _descripcion);
		SET mensaje = 'Se ha regsitrado la sala correctamente';
		COMMIT;
    END CASE;
    SET autocommit = 1;
END;$$

delimiter $$
create procedure get_sala(_id_sala integer)
begin
select * from salas where id_sala=_id_sala;
end;$$

delimiter  $$
create procedure  get_ubicacion(_id int)
    begin
        select * from ubicaciones_libros where id=_id;
    end; $$

delimiter $$
create procedure get_libro(id int)
begin
    SELECT
    l.id_libro as id,
    l.titulo as titulo,
    l.editorial as editorial,
    l.isbn as isbn,
    u.id as id_ub,
    u.pasillo as pasillo,
    u.seccion as seccion,
    u.estante as estante
FROM libros l
    INNER JOIN ubicaciones_libros u ON l.id_ub = u.id WHERE id_libro = id;
end; $$

delimiter $$
create procedure ver_usuarios()
begin
select * from usuarios;
end;$$

delimiter $$
create procedure ver_ubicaciones(inicio int, limite int)
begin
select * from ubicaciones_libros u ORDER BY pasillo, seccion, estante limit inicio, limite;
end;$$

delimiter $$
create procedure cargar_usuario(_correo VARCHAR(100), _contrasenia VARCHAR(100))
begin
    DECLARE contra varbinary(128);
    DECLARE r int;
    SET contra = AES_ENCRYPT(_contrasenia, 'A4E7C6AE4013276DEABC1BE4F9C65A6DC382F317E0CB81497ABA26915CDE5B66');
    SELECT rol INTO r FROM usuarios WHERE correo = _correo AND contrasenia = contra;

    IF r = 1 OR r = 2 THEN
    SELECT id_usuario, nombre, apellido_paterno, apellido_materno, telefono, rol FROM usuarios WHERE correo = _correo AND contrasenia = contra;
    ELSEIF r = 3 THEN
    SELECT u.id_usuario, u.nombre, u.apellido_paterno, u.apellido_materno, u.telefono, u.rol, d.* FROM usuarios u INNER JOIN docentes d ON u.id_usuario = d.id_usuario WHERE u.correo = _correo AND u.contrasenia = contra;
    ELSEIF r = 4 THEN
    SELECT u.id_usuario, u.nombre, u.apellido_paterno, u.apellido_materno, u.telefono, u.rol, a.* FROM usuarios u INNER JOIN alumnos a ON u.id_usuario = a.id_usuario WHERE u.correo = _correo AND u.contrasenia = contra;
    END IF;
end;$$

DELIMITER $$
CREATE PROCEDURE insertar_alumno (
    _nombre VARCHAR(50),
    _apellido_paterno VARCHAR(50),
    _apellido_materno VARCHAR(50),
    _correo VARCHAR(100),
    _contrasenia varchar(100),
    _telefono VARCHAR(12),
    _matricula varchar(30),
    _carrea varchar(100),
    _grado int,
    _grupo varchar(2),
    OUT mensaje varchar(100)
)
BEGIN
    DECLARE contrasenia_encrypt varbinary(128);

    DECLARE exit HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET mensaje = 'Se produjo una excepción durante la transacción';
    END;
    SET autocommit = 0;
    START TRANSACTION;
    IF EXISTS (SELECT * FROM Usuarios WHERE correo = _correo) THEN
        SET mensaje = CONCAT('El correo: ', _correo  ,' ya se encuentra ligado a una cuenta');
        ROLLBACK;
    ELSEIF EXISTS (SELECT * FROM Usuarios INNER JOIN alumnos a WHERE matricula = _matricula) THEN
        SET mensaje = concat('Ya hay un alumno registrado con la matrícula: ', _matricula);
        ROLLBACK;
    ELSE
        SET contrasenia_encrypt = aes_encrypt(_contrasenia, 'A4E7C6AE4013276DEABC1BE4F9C65A6DC382F317E0CB81497ABA26915CDE5B66');

        INSERT INTO Usuarios (nombre, apellido_paterno, apellido_materno, correo, contrasenia, telefono, rol)
            VALUES (_nombre, _apellido_paterno, _apellido_materno, _correo, contrasenia_encrypt, _telefono, 4);
            INSERT INTO alumnos(matricula, carrera, grado, grupo, id_usuario) values (_matricula, _carrea, _grado,UPPER(_grupo), last_insert_id());
            SET mensaje = concat('Se ha registrado correctamente, Ya puede iniciar sesión con el correo: ', _correo);
        COMMIT;
    END IF;
    SET autocommit = 1;
END;$$

DELIMITER $$
CREATE PROCEDURE insertar_docente (
    _nombre VARCHAR(50),
    _apellido_paterno VARCHAR(50),
    _apellido_materno VARCHAR(50),
    _correo VARCHAR(100),
    _contrasenia varchar(100),
    _telefono VARCHAR(12),
    _no_control varchar(30),
    _division varchar(100),
    OUT mensaje varchar(100)
)
BEGIN
    DECLARE contrasenia_encrypt varbinary(128);

    DECLARE exit HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET mensaje = 'Se produjo una excepción durante la transacción';
    END;
    SET autocommit = 0;
    START TRANSACTION;
    IF EXISTS (SELECT * FROM Usuarios WHERE correo = _correo) THEN
        SET mensaje = CONCAT('El correo: ', _correo  ,' ya se encuentra ligado a una cuenta');
        ROLLBACK;
    ELSEIF EXISTS (SELECT * FROM usuarios INNER JOIN docentes d WHERE no_trabajador = _no_control) THEN
        SET mensaje = concat('Ya hay un docente registrado con la matrícula: ', _no_control);
        ROLLBACK;
    ELSE
        SET contrasenia_encrypt = aes_encrypt(_contrasenia, 'A4E7C6AE4013276DEABC1BE4F9C65A6DC382F317E0CB81497ABA26915CDE5B66');

        INSERT INTO Usuarios (nombre, apellido_paterno, apellido_materno, correo, contrasenia, telefono, rol)
            VALUES (_nombre, _apellido_paterno, _apellido_materno, _correo, contrasenia_encrypt, _telefono, 3);
            INSERT INTO docentes(no_trabajador, division, id_usuario) values (_no_control, _division, last_insert_id());
            SET mensaje = concat('Se ha registrado correctamente, Ya puede iniciar sesión con el correo: ', _correo);
        COMMIT;
    END IF;
    SET autocommit = 1;
END;$$


delimiter $$
create procedure buscar_salas(palabra varchar(20) , num varchar(3))
begin
    set palabra = LOWER(palabra);
    if palabra is not null and num is not null then
        select  s.*, p.estatus as prestamo from salas s
        left outer join prestamos_salas p on s.id_sala=p.id_sala
        where (lower(s.nombre) like concat('%', palabra, '%')
        and lower(s.capacidad) like  num)
        or (lower(s.descripcion) like concat('%', palabra, '%')
        and s.capacidad like  num);
    else
        select  s.*, p.estatus  as prestamo from salas s
        left outer join prestamos_salas p on s.id_sala=p.id_sala
        where lower(s.nombre) like concat('%', palabra, '%')
        or lower(s.descripcion) like concat('%', palabra, '%')
        or s.capacidad like num;
    end if;
end; $$

delimiter $$
create procedure buscar_autores(palabra varchar(100))
    begin
        select  * from autores where concat(nombre,' ',apellido_paterno,' ', apellido_materno) like concat('%', palabra,'%')
        order by nombre, apellido_paterno , apellido_materno;
    end; $$


delimiter $$
create procedure buscar_ubicaciones(palabra varchar(100))
    begin
        select  * from ubicaciones_libros where concat(pasillo,', ', seccion, ', ', estante) like concat('%', palabra, '%')
        order by pasillo, seccion , estante;
    end; $$

delimiter $$
create procedure buscar_libros(palabra varchar(100))
begin
    SELECT
    l.id_libro as id,
    l.titulo as titulo,
    l.editorial as editorial,
    l.isbn as isbn,
    (SELECT COUNT(*) FROM ejemplares e WHERE e.id_libro = l.id_libro) as ejemplares,
    u.pasillo as pasillo,
    u.seccion as seccion,
    u.estante as estante
FROM libros l
    INNER JOIN ubicaciones_libros u ON l.id_ub = u.id
    INNER JOIN libros_autores li ON li.id_libro = l.id_libro
    INNER JOIN autores a ON a.id_autor = li.id_autor
    WHERE titulo like concat('%',palabra,'%') OR
    editorial like concat('%',palabra,'%') OR
    concat(a.nombre,' ', a.apellido_paterno,' ',apellido_materno) like concat('%',palabra,'%');
end; $$




delimiter $$
create procedure contar_salas()
begin
    select count(*) as total from salas;
end; $$


delimiter $$
create procedure contar_libros()
begin
    select count(*) from libros;
end; $$

delimiter $$
create procedure contar_autores()
begin
    select count(*) from autores;
end; $$

delimiter $$
create procedure contar_ubicaciones()
begin
    select count(*) as total from ubicaciones_libros;
end; $$

delimiter $$
create procedure ver_libros(inicio int, limite int)
begin
    SELECT
    l.id_libro as id,
    l.titulo as titulo,
    l.editorial as editorial,
    l.isbn as isbn,
    (SELECT COUNT(*) FROM ejemplares e WHERE e.id_libro = l.id_libro) as ejemplares,
    u.pasillo as pasillo,
    u.seccion as seccion,
    u.estante as estante
FROM libros l
    INNER JOIN ubicaciones_libros u ON l.id_ub = u.id
    LIMIT inicio, limite;
end; $$

delimiter $$
create procedure ver_autores(inicio int, limite int)
begin
    select * from autores order by nombre, apellido_paterno, apellido_materno limit inicio, limite;
end; $$

delimiter $$
create procedure autores(id int)
begin
    IF id is null THEN
        SELECT * FROM autores order by nombre, apellido_paterno , apellido_materno;
    ELSE
        SELECT a.id_autor as id_autor, a.nombre as nombre, a.apellido_materno as apellido_materno, a.apellido_paterno as apellido_paterno
        FROM autores a
        INNER JOIN libros_autores la ON a.id_autor = la.id_autor
        WHERE la.id_libro = id order by a.nombre, a.apellido_paterno , a.apellido_materno ;
    END IF;
end; $$

delimiter $$
create procedure libros(id_autor int)
begin
    select
        l.titulo
    from libros l
    inner join libros_autores la ON la.id_libro = l.id_libro
        where la.id_autor = id_autor;
end; $$

delimiter $$
create procedure ubicaciones()
    begin
        select * from ubicaciones_libros order by pasillo, seccion, estante;
    end; $$


delimiter $$
create procedure findFile(id int)
begin
    select file_name, file from libros_img where id_libro=id;
end; $$


DELIMITER $$
CREATE PROCEDURE Insertar_autor ( _nombre varchar(500), _ap VARCHAR(500), _am varchar(500), out mensaje varchar(255))
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET mensaje = 'Tipo de dato incorrecto';
    END;
    SET autocommit = 0;
    START TRANSACTION;
    CASE
		WHEN EXISTS (SELECT * FROM autores WHERE nombre=_nombre and apellido_paterno = _ap and apellido_materno = _am) THEN set mensaje =  'El autor ya existe';
        ROLLBACK;
    ELSE
		INSERT INTO autores (nombre, apellido_paterno, apellido_materno) VALUES (_nombre, _ap, _am);
		SET mensaje = 'Se ha regsitrado el autor correctamente';
		COMMIT;
    END CASE;
    SET autocommit = 1;
END;$$

DELIMITER $$
CREATE PROCEDURE actulizar_autor (_id int,  _nombre varchar(500), _ap VARCHAR(500), _am varchar(500), out mensaje varchar(255))
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET mensaje = 'Tipo de dato incorrecto';
    END;
    SET autocommit = 0;
    START TRANSACTION;
    CASE
		WHEN EXISTS (SELECT * FROM autores WHERE nombre=_nombre and apellido_paterno = _ap and apellido_materno = _am and id_autor <> _id) THEN set mensaje =  'El autor ya existe';
        ROLLBACK;
    ELSE
		update autores set nombre =_nombre, apellido_paterno = _ap, apellido_materno = _am where id_autor=_id;
		SET mensaje = 'Se ha actualizado el autor correctamente';
		COMMIT;
    END CASE;
    SET autocommit = 1;
END;$$

delimiter $$
create procedure get_autor(_id_autor integer)
begin
select * from autores where id_autor=_id_autor;
end;$$

delimiter $$
create procedure eliminar_autor(IN _id Integer, OUT mensaje varchar(255))
begin
 DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SELECT 'No se puede eliminar el autor porque tiene un prestamo activo' into mensaje;
    END;
	SET autocommit = 0;
    START TRANSACTION;
    IF EXISTS (SELECT * FROM autores WHERE id_autor=_id) THEN
        DELETE FROM autores
        WHERE id_autor=_id;
        SET mensaje =  'Se ha elimindado el autor correctamente';
        COMMIT;
    ELSE
        SET mensaje =  'El autor a eliminar no existe';
        ROLLBACK;
    END IF;
    SET autocommit = 1;
end;$$

insert into salas values(0, 'Sala 1', '30', '1 proyector, mesas redondas, sillas giratorias c/u y 2 refrigeradores');
insert into salas values(0, 'Sala 2', '10', '2 mesas cuadradas, sillas b3 c/u y 1 aire acondicionado');
insert into salas values(0, 'Sala 3', '15', '1 mesa');

INSERT INTO Autores (nombre, apellido_paterno, apellido_materno)
VALUES ('Juan', 'Pérez', 'García');

INSERT INTO Autores (nombre, apellido_paterno, apellido_materno)
VALUES ('María', 'López', 'Martínez');

INSERT INTO Autores (nombre, apellido_paterno, apellido_materno)
VALUES ('Carlos', 'González', 'Sánchez');

INSERT INTO Autores (nombre, apellido_paterno, apellido_materno)
VALUES ('Ana', 'Rodríguez', 'Hernández');

INSERT INTO Autores (nombre, apellido_paterno, apellido_materno)
VALUES ('Luis', 'Martínez', 'Ramírez');

-- Insertar registros en la tabla Ubicaciones_libros
INSERT INTO Ubicaciones_libros (pasillo, seccion, estante)
VALUES (1, 2, 'A');

INSERT INTO Ubicaciones_libros (pasillo, seccion, estante)
VALUES (2, 3, 'B');

INSERT INTO Ubicaciones_libros (pasillo, seccion, estante)
VALUES (1, 1, 'C');

INSERT INTO Ubicaciones_libros (pasillo, seccion, estante)
VALUES (3, 2, 'D');

INSERT INTO Ubicaciones_libros (pasillo, seccion, estante)
VALUES (2, 1, 'E');

