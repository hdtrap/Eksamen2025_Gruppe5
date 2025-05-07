CREATE DATABASE IF NOT EXISTS bilabonnement;

USE bilabonnement;

DROP TABLE IF EXISTS damages;
DROP TABLE IF EXISTS leases;
DROP TABLE IF EXISTS cars;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
                       username VARCHAR(50)UNIQUE PRIMARY KEY,
                       first_name VARCHAR(50),
                       last_name VARCHAR(50),
                       password VARCHAR(50),
                       role VARCHAR(50)
);

CREATE TABLE cars (
                      vehicle_no INT AUTO_INCREMENT UNIQUE PRIMARY KEY,
                      chassis_no VARCHAR(50) UNIQUE,
                      brand VARCHAR(50),
                      model VARCHAR(50),
                      production_year INT,
                      price DOUBLE,
                      fuel_type VARCHAR(50),
                      available BOOL
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

CREATE TABLE damages (
                         damage_id INT AUTO_INCREMENT UNIQUE PRIMARY KEY,
                         lease_id INT,
                         damage_type VARCHAR(50),
                         category INT,
                         price DOUBLE,
                         CONSTRAINT fk_lease_id FOREIGN KEY (lease_id) REFERENCES leases(lease_id)
);

-- Insert Users
INSERT INTO users (username, first_name, last_name, password, role) VALUES
                                ('syad', 'System', 'Admin', '1234', 'SYSADMIN'),
                                ('rede', 'Repair', 'Demo', '1234', 'REPAIR'),
                                ('bude', 'Business', 'Demo', '1234', 'BUSINESS'),
                                ('dade', 'Data', 'Demo', '1234', 'DATA');

-- Insert Cars
INSERT INTO cars (chassis_no, brand, model, production_year, price, fuel_type, available) VALUES
                                ('CHS123456789', 'Toyota', 'Corolla', 2020, 20000.00, 'Petrol', TRUE),       -- vehicle_no 1
                                ('CHS987654321', 'Tesla', 'Model 3', 2021, 35000.00, 'Electric', TRUE),      -- vehicle_no 2
                                ('CHS567890123', 'Ford', 'Focus', 2019, 18000.00, 'Diesel', FALSE),          -- vehicle_no 3
                                ('CHS112233445', 'Volkswagen', 'Golf', 2022, 22000.00, 'Petrol', TRUE),      -- vehicle_no 4
                                ('CHS998877665', 'BMW', 'i3', 2023, 30000.00, 'Electric', FALSE);            -- vehicle_no 5

-- Insert Leases
INSERT INTO leases (vehicle_no, start_date, end_date, customer_name, customer_email, customer_number, price_to_start, price_pr_month, type_of_lease, fully_processed) VALUES
                               (1, '2025-05-01', '2026-05-01', 'Anna Jensen', 'anna.jensen@email.com', '12345678', 10000, 2500, 'ABONNEMENT', true),             -- lease_id 1
                               (2, '2025-04-15', '2026-04-15', 'Mark Sørensen', 'mark.sorensen@email.com', '23456789', 8000, 2200, 'VAREBIL', false),       -- lease_id 2
                               (3, '2025-03-10', '2026-03-10', 'Lise Hansen', 'lise.hansen@email.com', '34567890', 12000, 2800, 'ABONNEMENT', true),             -- lease_id 3
                               (4, '2025-05-02', '2026-05-02', 'Thomas Nielsen', 'thomas.nielsen@email.com', '45678901', 9000, 2400, 'VAREBIL', false),     -- lease_id 4
                               (5, '2025-01-20', '2026-01-20', 'Mette Larsen', 'mette.larsen@email.com', '56789012', 11000, 2600, 'ABONNEMENT', true);           -- lease_id 5

-- Insert Damages
INSERT INTO damages (lease_id, damage_type, category, price) VALUES
                                 (1, 'Scratched Paint', 1, 150.00),
                                 (2, 'Broken Mirror', 2, 250.00),
                                 (3, 'Flat Tire', 1, 100.00),
                                 (4, 'Cracked Windshield', 3, 400.00),
                                 (5, 'Dented Door', 2, 300.00),
                                 (1, 'Stained Seats', 1, 80.00);