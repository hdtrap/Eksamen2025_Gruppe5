CREATE DATABASE IF NOT EXISTS bilabonnement;

USE bilabonnement;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS add_ons;
DROP TABLE IF EXISTS addon_types;
DROP TABLE IF EXISTS damages;
DROP TABLE IF EXISTS leases;
DROP TABLE IF EXISTS cars;
DROP TABLE IF EXISTS car_models;


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
         fuel_type varchar(50)
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

-- Insert Car Models
INSERT INTO car_models (brand, model, production_year, fuel_type) VALUES
                  ('Reneault', 'Clio', 2019, 'Hybrid'),
                  ('Ford', 'Focus', 2017, 'Benzin'),
                  ('Volkswagen', 'Beetle', '1972', 'Benzin'),
                  ('BMW', 'M4', 2024, 'Benzin');

INSERT INTO addon_types (type, description, price) VALUES
                  ('Afleveringsforsikring', 'Tilvalg af afleveringsforsikring er betinget af en aftale på minimum 12 måneder.', 119),
                  ('Lav selvrisiko', 'Tilvalg af lav selvrisiko er betinget af at kunden minimum er fyldt 30 år.', 89),
                  ('Dækleje', 'Vinterdæk inkl. montering kan tilkøbes, da bilen leveres på sommerdæk. Straks efter bestillingen kontaktes kunden af vores dækservicepartner Rubberduck, som vil foretage monteringen på deres hjemme- eller arbejdsadresse. Alle øvrige dækskift samt opbevaring af sommer- og vinterdæk i perioden er inkluderet i prisen. Vinterdæk monteres på bilens eksisterende fælge, men bilen skal tilbageleveres på sommerdæk. Prisen gælder ved binding i +12 måneder.', 275),
                  ('Viking - Vejhjælp', 'I samarbejde med Viking tilbyder vi vejhjælp til kun 49 kr. pr. måned.', 49);


-- Insert Users
INSERT INTO users (username, first_name, last_name, password, role) VALUES
                  ('syad', 'System', 'Admin', '1234', 'SYSADMIN'),
                  ('rede', 'Repair', 'Demo', '1234', 'REPAIR'),
                  ('bude', 'Business', 'Demo', '1234', 'BUSINESS'),
                  ('dade', 'Data', 'Demo', '1234', 'DATA');

-- Insert Cars
INSERT INTO cars (chassis_no, car_model, price, status_of_car) VALUES
                 ('CHS123456789', 1, 20000.00, 'AvailableToLease'),     -- vehicle_no 1
                 ('CHS987654321', 2, 35000.00, 'Leased'),               -- vehicle_no 2
                 ('CHS567890123', 3, 18000.00, 'GettingRepaired'),      -- vehicle_no 3
                 ('CHS112233445', 4, 22000.00, 'Sold'),                 -- vehicle_no 4
                 ('CHS998877665', 1, 21000.00, 'AvailableToLease'),     -- vehicle_no 5
                 ('CHS998877245', 4, 21000.00, 'Leased');               -- vehicle_no 6

-- Insert Leases
INSERT INTO leases (vehicle_no, start_date, end_date, customer_name, customer_email, customer_number, price_to_start, price_pr_month, type_of_lease, fully_processed) VALUES
                 (1, '2025-05-01', '2026-05-01', 'Anna Jensen', 'anna.jensen@email.com', '12345678', 10000, 2500, 'ABONNEMENT', true),             -- lease_id 1
                 (2, '2025-04-15', '2026-04-15', 'Mark Sørensen', 'mark.sorensen@email.com', '23456789', 8000, 2200, 'VAREBIL', false),       -- lease_id 2
                 (3, '2025-03-10', '2026-03-10', 'Lise Hansen', 'lise.hansen@email.com', '34567890', 12000, 2800, 'ABONNEMENT', true),             -- lease_id 3
                 (4, '2025-05-02', '2026-05-02', 'Thomas Nielsen', 'thomas.nielsen@email.com', '45678901', 9000, 2400, 'VAREBIL', false),     -- lease_id 4
                 (5, '2025-01-20', '2026-01-20', 'Mette Larsen', 'mette.larsen@email.com', '56789012', 11000, 2600, 'ABONNEMENT', true),           -- lease_id 5
                (6, '2025-01-20', '2025-01-20', 'Henning Larsen', 'henning.larsen@email.com', '56789012', 11000, 2600, 'ABONNEMENT', true);           -- lease_id 5

INSERT INTO add_ons (addon_type, lease_id) VALUES
                                               (1,1),
                                               (2,5),
                                               (3,3),
                                               (4,1),
                                               (1,4);

-- Insert Damages
INSERT INTO damages (lease_id, damage_type, category, price) VALUES
                 (1, 'Scratched Paint', 1, 150.00),
                 (2, 'Broken Mirror', 2, 250.00),
                 (3, 'Flat Tire', 1, 100.00),
                 (4, 'Cracked Windshield', 3, 400.00),
                 (5, 'Dented Door', 2, 300.00),
                 (1, 'Stained Seats', 1, 80.00);