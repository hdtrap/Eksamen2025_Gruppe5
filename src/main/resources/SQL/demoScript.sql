CREATE DATABASE Bilabonnement;

USE Bilabonnement;

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

-- Insert Users
INSERT INTO Users (username, first_name, last_name, password, role) VALUES
                                   ('jdoe', 'John', 'Doe', 'password123', 'sysadmin'),
                                   ('asmith', 'Alice', 'Smith', 'alicepass', 'repair'),
                                   ('bwhite', 'Bob', 'White', 'bobpass', 'business'),
                                   ('knguyen', 'Kim', 'Nguyen', 'kimsecure', 'repair'),
                                   ('rgreen', 'Rachel', 'Green', 'rachelpwd', 'data');

-- Insert Cars
INSERT INTO Cars (chassis_no, brand, model, production_year, price, fuel_type, available) VALUES
                                   ('CHS123456789', 'Toyota', 'Corolla', 2020, 20000.00, 'Petrol', TRUE),       -- vehicle_no 1
                                   ('CHS987654321', 'Tesla', 'Model 3', 2021, 35000.00, 'Electric', TRUE),      -- vehicle_no 2
                                   ('CHS567890123', 'Ford', 'Focus', 2019, 18000.00, 'Diesel', FALSE),          -- vehicle_no 3
                                   ('CHS112233445', 'Volkswagen', 'Golf', 2022, 22000.00, 'Petrol', TRUE),      -- vehicle_no 4
                                   ('CHS998877665', 'BMW', 'i3', 2023, 30000.00, 'Electric', FALSE);            -- vehicle_no 5

-- Insert Leases
INSERT INTO Leases (vehicle_no, length_in_months, price_pr_month, fully_processed, start_date, end_date) VALUES
                                   (1, 12, 400.00, TRUE, '2024-01-01', '2024-12-31'),      -- lease_id 1
                                   (2, 24, 500.00, FALSE, '2024-06-01', '2026-05-31'),     -- lease_id 2
                                   (3, 6, 350.00, TRUE, '2023-03-01', '2023-08-31'),       -- lease_id 3
                                   (4, 18, 450.00, TRUE, '2024-09-01', '2026-02-28'),      -- lease_id 4
                                   (5, 12, 420.00, FALSE, '2025-01-01', '2025-12-31');     -- lease_id 5

-- Insert Damages
INSERT INTO Damages (lease_id, damage_type, category, price) VALUES
                                   (1, 'Scratched Paint', 1, 150.00),
                                   (2, 'Broken Mirror', 2, 250.00),
                                   (3, 'Flat Tire', 1, 100.00),
                                   (4, 'Cracked Windshield', 3, 400.00),
                                   (5, 'Dented Door', 2, 300.00),
                                   (1, 'Stained Seats', 1, 80.00);