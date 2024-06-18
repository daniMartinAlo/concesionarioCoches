# concesionarioCoches
This is a small application for a car dealership. These are projects I have undertaken to practice for the Java project. To create it, I based it on an exercise whose instructions can be found in the README.

# Data Source

To obtain the data, we have various methods such as SQL databases, NoSQL databases, CSV files, etc. In this case, I decided to create the application to obtain data from an SQL database. The program will allow queries in the database, enabling the user to retrieve, save, edit, and delete records.

# Technologies Used

For the creation of the program, we used the Eclipse environment with the MySQL-Connector to establish the connection to the database. For the database creation, I used MySQL Workbench.

# Project Explanation

This is a program for a car dealership that allows registering and validating users. The password with which the user registers will be stored in a table in the database in an encrypted form so that if someone accesses the database, they can never know the password used by the client. Each time a user opens the program, they must register or log in to access the system.

Once we have accessed the system, depending on whether we have identified ourselves as an Administrator or as a Client, a different menu will be shown. In the case of clients, we have 7 options.

The first option allows us to search for cars in the dealership by license plate, the second option allows us to search for cars by their environmental label, the third option allows us to search within a specified price range, the fourth gives the possibility to search by fuel type, the fifth allows us to search for cars with power higher than specified by the user, the sixth shows all the cars available in the dealership, and the last option allows us to log out and return to the registration menu.

If the user is an administrator, the menu will have six options. The first option allows the user to display all the cars in the database, this option is common for both clients and administrators. The second option allows adding a new car to the database, the third option allows adding a new engine to the database, the fourth option allows the administrator to edit an existing car in the database, the fifth option allows deleting a record from the database, and finally, the last option allows logging out of the administrator account.

# Database Script
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
