create database concesionariohito2;
use concesionariohito2;
SET SQL_SAFE_UPDATES=0;

create table if not exists motor(codigo varchar(10) primary key, combustible varchar(30) not null, cilindrada int(5), potencia int(4), nCilindros int(2), nValvulas int(2));

create table if not exists coches(matricula char(8) primary key, marca varchar(30) not null, modelo varchar(30) not null, anio int(4) not null, color varchar(30), precio float(8,2) not null, etiqueta varchar(4) not null, codMotor varchar(10) not null, foreign key(codMotor) references motor(codigo));

insert into motor values("7AFE", "Gasolina", 1800, 116, 4, 16),
("EV", "Gasolina", 1800, 112, 4, 8),
("12GT", "Diesel", 2200, 198, 4, 16),
("1ZZGE", "GLP", 2400, 230, 6, 24),
("TGY756", "Hidrogeno", 5000, 340, 10, 40);

insert into coches values("M6490TS", "Toyota", "Celica", 1996, "Azul", 3400, "A", "7AFE"),
("7429DSS", "Hyundai", "Getz", 2005, "Plata", 4000, "B", "12GT"),
("M0121NB", "Volkswagen", "Golf", 1984, "Verde", 6000, "A", "EV"),
("4567MCH", "Nissan", "Mira", 2023, "Negro", 84000, "0", "TGY756"),
("7654HKS", "Seat", "Leon", 2011, "Rojo", 17400, "ECO", "1ZZGE");

CREATE TABLE usuarios(id int auto_increment PRIMARY KEY,nombreUsuario varchar(25) NOT NULL, contrasenia varbinary(200) not null, tipoUsuario varchar(20) not null);

select * from usuarios;
select * from coches;
select * from motor;

/* PRUEBAS
insert into usuarios(nombreUsuario, contrasenia, tipoUsuario) values('prueba', SHA1('root'), 'Administrador');
select tipoUsuario from usuarios where nombreUsuario='dani33' and contrasenia = SHA('root');
select nombreUsuario from usuarios where nombreUsuario="dani33";
truncate table usuarios;
select PWDENCRYPT('password');
select HASHBYTES('SHA2_512', 'Hola');
SELECT SHA1('password');
select @@version;
SELECT * FROM coches where matricula = "M6490TS";
SELECT * FROM coches INNER JOIN motor ON coches.codMotor = motor.codigo where matricula = "M6490TS";
SELECT * FROM coches INNER JOIN motor ON coches.codMotor = motor.codigo where motor.combustible = "Gasolina";
SELECT * FROM coches INNER JOIN motor ON coches.codMotor = motor.codigo where motor.potencia >= 200;
SELECT * FROM coches INNER JOIN motor ON coches.codMotor = motor.codigo where precio BETWEEN 1000 and 7000;
DELETE FROM coches WHERE matricula="5555";
select coches.*, motor.combustible, motor.cilindrada, motor.potencia, motor.nCilindros as "Numero de cilindros", motor.nValvulas as "Número de válvulas"  from coches inner join motor on coches.codMotor = motor.codigo;*/