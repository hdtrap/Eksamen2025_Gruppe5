/*Sarah*/

CREATE DATABASE IF NOT EXISTS bilabonnement;


USE bilabonnement;

CREATE TABLE users (
             username VARCHAR(50)UNIQUE PRIMARY KEY,
             first_name VARCHAR(50),
             last_name VARCHAR(50),
             password VARCHAR(50),
             role VARCHAR(50)
);

CREATE TABLE car_models  (
             id int PRIMARY KEY AUTO_INCREMENT UNIQUE,
             brand varchar(100),
             model varchar(100),
             production_year int,
             fuel_type varchar(50),
             color VARCHAR(50)
);

CREATE TABLE addon_types(
             id int PRIMARY KEY AUTO_INCREMENT,
             type varchar(50),
             description LONGTEXT,
             price double
);

CREATE TABLE cars (
             vehicle_no INT AUTO_INCREMENT UNIQUE PRIMARY KEY,
             chassis_no VARCHAR(50) UNIQUE,
             car_model int,
             price DOUBLE,
             status_of_car VARCHAR(50),
             CONSTRAINT fk_carmodel_id FOREIGN KEY (car_model) REFERENCES car_models(id)
);


CREATE TABLE leases (
             lease_id INT AUTO_INCREMENT UNIQUE PRIMARY KEY,
             vehicle_no INT,
             start_date DATE,
             end_date DATE,
             customer_name VARCHAR(100),
             customer_email VARCHAR(100),
             customer_number VARCHAR(50),
             price_to_start DOUBLE,
             price_pr_month DOUBLE,
             type_of_lease VARCHAR(50),
             fully_processed BOOL,
             CONSTRAINT fk_vehicle_no FOREIGN KEY (vehicle_no) REFERENCES cars(vehicle_no)
);


CREATE TABLE add_ons(
             id int PRIMARY KEY AUTO_INCREMENT UNIQUE,
             addon_type int,
             lease_id int,
             CONSTRAINT fk_addon_type FOREIGN KEY (addon_type) REFERENCES addon_types(id),
             CONSTRAINT fk_add_ons_lease_id FOREIGN KEY (lease_id) REFERENCES leases(lease_id) ON DELETE CASCADE
);


CREATE TABLE damages (
             damage_id INT AUTO_INCREMENT UNIQUE PRIMARY KEY,
             lease_id INT,
             damage_type VARCHAR(50),
             category INT,
             price DOUBLE,
             isPaid bool,
             isFixed bool,
             CONSTRAINT fk_lease_id FOREIGN KEY (lease_id) REFERENCES leases(lease_id) ON DELETE CASCADE
);