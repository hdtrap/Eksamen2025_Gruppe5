USE Bilabonnement;

DROP TABLE Damages;
DROP TABLE Leases;
DROP TABLE Cars;
DROP TABLE Users;


CREATE TABLE Users (
                       username VARCHAR(50)UNIQUE PRIMARY KEY,
                       first_name VARCHAR(50),
                       last_name VARCHAR(50),
                       password VARCHAR(50),
                       role VARCHAR(50)
);

CREATE TABLE Cars (
                      vehicle_no INT AUTO_INCREMENT UNIQUE PRIMARY KEY,
                      chassis_no VARCHAR(50),
                      brand VARCHAR(50),
                      model VARCHAR(50),
                      production_year INT,
                      price DOUBLE,
                      fuel_type VARCHAR(50),
                      available BOOL
);

CREATE TABLE Leases (
                        lease_id INT AUTO_INCREMENT UNIQUE PRIMARY KEY,
                        vehicle_no INT,
                        length_in_months INT,
                        price_pr_month DOUBLE,
                        fully_processed BOOL,
                        start_date DATE,
                        end_date DATE,
                        CONSTRAINT fk_vehicle_no FOREIGN KEY (vehicle_no) REFERENCES Cars(vehicle_no)
);

CREATE TABLE Damages (
                         damage_id INT AUTO_INCREMENT UNIQUE PRIMARY KEY,
                         lease_id INT,
                         damage_type VARCHAR(50),
                         category INT,
                         price DOUBLE,
                         CONSTRAINT fk_lease_id FOREIGN KEY (lease_id) REFERENCES Leases(lease_id)
);