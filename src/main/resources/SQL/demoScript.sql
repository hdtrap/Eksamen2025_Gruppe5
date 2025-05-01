CREATE DATABASE Bilabonnement;

USE Bilabonnement;

CREATE TABLE users (
                       username VARCHAR(50)UNIQUE PRIMARY KEY,
                       first_name VARCHAR(50),
                       last_name VARCHAR(50),
                       password VARCHAR(50),
                       role VARCHAR(50)
);

CREATE TABLE Cars (
                      vehicle_no INT UNIQUE PRIMARY KEY,
                      chassis_no VARCHAR(50),
                      brand VARCHAR(50),
                      model VARCHAR(50),
                      production_year INT,
                      price DOUBLE,
                      fuel_type VARCHAR(50),
                      available BOOL
);

CREATE TABLE Lease (
                       lease_id INT UNIQUE PRIMARY KEY,
                       vehicle_no INT,
                       length_in_months INT,
                       price_pr_month DOUBLE,
                       fully_processed BOOL,
                       start_date DATE,
                       end_date DATE
);

CREATE TABLE Damages (
                         damage_id INT UNIQUE PRIMARY KEY,
                         lease_no INT,
                         damage_type VARCHAR(50),
                         category INT,
                         price DOUBLE
);