USE bilabonnement;

-- Insert Car models
INSERT INTO car_models (brand, model, production_year, fuel_type, color) VALUES
            ('Mitsubishi', 'Space Star Cosmic 71 HK', 2022, 'Benzin', 'Grå'),
            ('Mitsubishi', 'Space Star Cosmic 71 HK', 2022, 'Benzin', 'Sort'),

            ('Fiat', '500e Icon 118 HK', 2023, 'Elektrisk', 'Mintgrøn'),
            ('Fiat', '500e Icon 118 HK', 2023, 'Elektrisk', 'Sort'),

            ('Fiat', '500e La Prima 118 HK', 2023, 'Elektrisk', 'Grå'),

            ('Fiat', '500e Icon Pack 118 HK', 2023, 'Elektrisk', 'Rød'),
            ('Fiat', '500e Icon Pack 118 HK', 2023, 'Elektrisk', 'Hvid'),

            ('Opel', 'Crossland Edition+ 83HK', 2022, 'Benzin', 'Sølv'),
            ('Opel', 'Crossland Edition+ 83HK', 2022, 'Benzin', 'Grå'),

            ('Citroën', 'C3 Aircross Impress 110 HK', 2023, 'Benzin', 'Grå'),
            ('Citroën', 'C3 Aircross Impress 110 HK', 2023, 'Benzin', 'Hvid'),

            ('NAVOR', 'E5 ROCK 218 HK', 2023, 'Elektrisk', 'Sort'),
            ('NAVOR', 'E5 ROCK 218 HK', 2023, 'Elektrisk', 'Mørkeblå'),

            ('Citroën', 'C4 VTR Sport 130 HK', 2022, 'Benzin', 'Blå'),

            ('Citroën', 'C5 Aircross Feel 130 HK', 2022, 'Diesel', 'Sort'),
            ('Citroën', 'C5 Aircross Feel 130 HK', 2022, 'Diesel', 'Hvid'),

            ('Nissan', 'ARIYA EVOVLE 238 HK', 2023, 'Elektrisk', 'Kobber'),

            ('Dacia', 'Sandero Expression 90 HK', 2023, 'Benzin', 'Blå'),
            ('Dacia', 'Sandero Expression 90 HK', 2023, 'Benzin', 'Hvid'),

            ('Peugeot', '2008 Allure 130 HK. AUT.', 2023, 'Benzin', 'Sort'),

            ('Renault', 'Captur Techno 160 HK', 2023, 'Hybrid', 'Rød'),
            ('Renault', 'Captur Techno 160 HK', 2023, 'Hybrid', 'Sort'),

            ('Honda', 'Civic Sport 184 HK', 2023, 'Hybrid', 'Mørkeblå'),
            ('Honda', 'Civic Sport 184 HK', 2023, 'Hybrid', 'Grå'),

            ('Honda', 'Civic Advance 184 HK', 2023, 'Hybrid', 'Sølv'),
            ('Honda', 'Civic Advance 184 HK', 2023, 'Hybrid', 'Sort'),

            ('Peugeot', 'Lille varebil', 2023, 'Diesel', 'Hvid'),
            ('Peugeot', 'Mellem varebil', 2023, 'Diesel', 'Hvid'),
            ('Citroën', 'Stor varebil', 2021, 'Diesel', 'Hvid'),
            ('Fiat', 'Lille elektrisk varebil', 2023, 'Elektrisk', 'Hvid'),
            ('Opel', 'Mellem elektrisk varebil', 2023, 'Elektrisk', 'Hvid');


INSERT INTO addon_types (type, description, price) VALUES
            ('Afleveringsforsikring', 'Tilvalg af afleveringsforsikring er betinget af en aftale på minimum 12 måneder.', 119),
            ('Lav selvrisiko', 'Tilvalg af lav selvrisiko er betinget af at kunden minimum er fyldt 30 år.', 89),
            ('Dækleje', 'Vinterdæk inkl. montering kan tilkøbes, da bilen leveres på sommerdæk. Straks efter bestillingen kontaktes kunden af vores dækservicepartner Rubberduck, som vil foretage monteringen på deres hjemme- eller arbejdsadresse. Alle øvrige dækskift samt opbevaring af sommer- og vinterdæk i perioden er inkluderet i prisen. Vinterdæk monteres på bilens eksisterende fælge, men bilen skal tilbageleveres på sommerdæk. Prisen gælder ved binding i +12 måneder.', 275),
            ('Viking - Vejhjælp', 'I samarbejde med Viking tilbyder vi vejhjælp til kun 49 kr. pr. måned.', 49);

-- Insert Users
INSERT INTO users (username, first_name, last_name, password, role) VALUES
           ('syad0001', 'System', 'Admin', '1234', 'SYSADMIN'),
           ('rede0001', 'Repair', 'Demo', '1234', 'REPAIR'),
           ('bude0001', 'Business', 'Demo', '1234', 'BUSINESS'),
           ('dade0001', 'Data', 'Demo', '1234', 'DATA'),
           ('demo', 'Demo', 'Mode', 'demo', 'SYSADMIN');

-- Insert Cars
INSERT INTO cars (chassis_no, car_model, price, status_of_car) VALUES
            ('1HGCB7659NA045613', 1, 3199.00, 'AvailableToLease'),
            ('JHMZD1723XS983402', 2, 3199.00, 'Leased'),
            ('WDBRF61J54F617983', 3, 3999.00, 'AvailableToLease'),
            ('3VWFE21C04M208541', 4, 3999.00, 'Leased'),
            ('2GCEK19T5Y1283471', 5, 4199.00, 'PendingEvaluation'),
            ('4T1BF1FK4HU123761', 6, 4199.00, 'AvailableToLease'),
            ('1N4AL11D75C234187', 7, 4299.00, 'AvailableToLease'),
            ('KMHDN45D1CU120976', 8, 4299.00, 'Leased'),
            ('5NPEU46F68H391024', 9, 3799.00, 'GettingRepaired'),
            ('JN1CA31D1YT547261', 10, 4599.00, 'PendingEvaluation'),
            ('1FAFP40423F202547', 11, 4599.00, 'AvailableToLease'),
            ('2HGFG11879H511234', 12, 4899.00, 'Sold'),
            ('JTDKB20U377614819', 13, 4899.00, 'AvailableToLease'),
            ('3N1BC13E79L409623', 14, 3799.00, 'Leased'),
            ('5J6RM4H79FL112308', 15, 3799.00, 'AvailableToLease'),
            ('1G1AK15F267841099', 16, 4999.00, 'Leased'),
            ('1GNEK13Z84R305186', 17, 5199.00, 'PendingEvaluation'),
            ('1FTRX12W78FA28491', 18, 3399.00, 'AvailableToLease'),
            ('1C4RJFBG7FC610224', 19, 3399.00, 'AvailableToLease'),
            ('5TDZA23C95S389147', 21, 5599.00, 'Leased'),
            ('KNDMB233866157892', 20, 5599.00, 'Leased'),
            ('WBA3A5G57ENQ12487', 22, 5799.00, 'AvailableToLease'),
            ('JM1BK12F181734905', 24, 5999.00, 'AvailableToLease'),
            ('JH4KA8270RC001486', 25, 2899.00, 'AvailableToLease'),
            ('3FAHP07157R180963', 26, 2999.00, 'AvailableToLease'),
            ('4S4BP61C876303485', 27, 3199.00, 'Leased'),
            ('SALSK254XBA234905', 28, 6199.00, 'AvailableToLease'),
            ('4T3ZF13C35U205178', 29, 6399.00, 'AvailableToLease'),
            ('WA1AVAF12DD032601', 30, 6599.00, 'AvailableToLease'),
            ('YV4952CZ7D1234560', 31, 3199.00, 'Leased'),
            ('1FMCU03G89KB35879', 1, 3199.00, 'AvailableToLease'),
            ('JN8AS5MTXBW689451', 2, 3999.00, 'AvailableToLease'),
            ('2FTRX18W0XCA71248', 3, 3999.00, 'Leased'),
            ('KM8JU3AC2EU925104', 4, 4199.00, 'AvailableToLease'),
            ('4T3BA3BB6FU087463', 5, 4199.00, 'Leased'),
            ('WVGAV7AX9CW512984', 8, 3799.00, 'AvailableToLease'),
            ('1J8HG58237C672509', 9, 3799.00, 'Leased'),
            ('1N6AD0EV8KN723415', 10, 4599.00, 'Leased'),
            ('JHMGE8H55CC051932', 11, 4599.00, 'GettingRepaired'),
            ('WBAFR7C52DDU62851', 12, 4899.00, 'AvailableToLease'),
            ('5FNYF6H59HB123741', 13, 4899.00, 'AvailableToLease'),
            ('2HKRM4H78CH635892', 14, 3799.00, 'Leased'),
            ('1FTSW21R78EC39285', 15, 3799.00, 'Leased'),
            ('3C6UR5FL9GG102857', 18, 5199.00, 'AvailableToLease'),
            ('JM1BL1S58A1125894', 19, 5199.00, 'AvailableToLease'),
            ('1FTWW33F3YEB95421', 20, 3399.00, 'PendingEvaluation'),
            ('5XYZU3LB3EG120483', 21, 3399.00, 'AvailableToLease'),
            ('2HGES16535H573249', 22, 5599.00, 'Leased'),
            ('3VWEF71K39M123894', 23, 5599.00, 'AvailableToLease'),
            ('JN8AZ2KR6ET560394', 24, 5799.00, 'AvailableToLease'),
            ('KL8CB6S98FC817345', 25, 5799.00, 'Leased'),
            ('YV1MS382562132467', 26, 5999.00, 'Leased'),
            ('1N4BU31D6VC262130', 27, 5999.00, 'GettingRepaired'),
            ('WDDGF8AB1EA932158', 28, 5999.00, 'AvailableToLease'),
            ('ZAM45VLA5D0072934', 29, 5999.00, 'Leased'),
            ('4S3BMHB67B3297482', 30, 2899.00, 'AvailableToLease'),
            ('1HGEM21942L092361', 1, 2999.00, 'AvailableToLease'),
            ('2T1BURHE3FC912748', 2, 2999.00, 'Leased'),
            ('JTDKN3DU4B5409218', 3, 3199.00, 'AvailableToLease'),
            ('3GNEC16Z96G123456', 4, 3199.00, 'Leased'),
            ('1N6BF0KY3FN800247', 5, 6199.00, 'AvailableToLease'),
            ('3D7MX48C55G852740', 6, 6199.00, 'AvailableToLease'),
            ('1GNDT13S122839764', 7, 6399.00, 'GettingRepaired'),
            ('3FAHP08Z87R210345', 8, 6399.00, 'AvailableToLease'),
            ('5N1AT2MV4EC893274', 9, 6599.00, 'AvailableToLease'),
            ('1GCGTCE36G1240912', 10, 6599.00, 'Leased'),
            ('2G1WF52E249210374', 11, 3199.00, 'AvailableToLease'),
            ('WBAEB53557CR23241', 12, 3999.00, 'PendingEvaluation'),
            ('JNKCV51E54M700185', 13, 4199.00, 'AvailableToLease'),
            ('4T1BE32K85U514738', 15, 3799.00, 'AvailableToLease'),
            ('WAUFFAFL2DA932145', 17, 4899.00, 'AvailableToLease'),
            ('JM3ER293670123894', 18, 3799.00, 'AvailableToLease'),
            ('1FADP3F21FL332019', 20, 5199.00, 'AvailableToLease'),
            ('1G1PC5SB4E7102963', 21, 3399.00, 'AvailableToLease'),
            ('JTDKBRFU8H3021547', 23, 5799.00, 'AvailableToLease');

-- Insert Leases
INSERT INTO leases (vehicle_no, start_date, end_date, customer_name, customer_email, customer_number, price_to_start, price_pr_month, type_of_lease, fully_processed) VALUES
            (2, '2025-01-01', '2025-06-01', 'Sofie Larsen', 'sofie.larsen@example.com', '+4531780246', 1111, 3999, 'ABONNEMENT', false),
            (4, '2025-05-01', '2026-05-01', 'Anna Jensen', 'anna.jensen@email.com', '+4512345678', 10000, 2500, 'ABONNEMENT', false),
            (5, '2025-01-15', '2025-05-20', 'Mark Sørensen', 'mark.sorensen@email.com', '+4523456789', 8000, 2200, 'MINILEASING', false),
            (8, '2025-03-10', '2026-03-10', 'Lise Hansen', 'lise.hansen@email.com', '+4534567890', 12000, 2800, 'ABONNEMENT', false),
            (9, '2025-02-02', '2025-05-02', 'Thomas Nielsen', 'thomas.nielsen@email.com', '+4545678901', 9000, 2400, 'MINILEASING', false),
            (10, '2025-01-20', '2025-05-28', 'Mette Larsen', 'mette.larsen@email.com', '+4556789012', 11000, 2600, 'ABONNEMENT', false),
            (14, '2025-01-20', '2025-01-20', 'Henning Larsen', 'henning.larsen@email.com', '+4656789012', 11000, 2600, 'ABONNEMENT', false),
            (16, '2024-08-05', '2025-11-05', 'Nikolaj Jensen', 'nikolaj.jensen@example.com', '+4531911223', 1050, 4199, 'ABONNEMENT', false),
            (17, '2024-12-15', '2025-05-15', 'Martin Olesen', 'martin.olesen@example.com', '+4531996480', 1210, 4299, 'LIMITED', false),
            (20, '2024-10-01', '2025-11-01', 'Sofie Larsen', 'sofie.larsen@example.com', '+4531812345', 1140, 4299, 'ABONNEMENT', false),
            (21, '2025-02-01', '2025-07-01', 'Mike Jensen', 'mike.jensen@example.com', '+4531786543', 1250, 3799, 'ABONNEMENT', false),
            (26, '2025-02-01', '2025-07-01', 'Camilla Sørensen', 'camilla.sorensen@example.com', '+4531573829', 1175, 4599, 'LIMITED', false),
            (30, '2024-06-15', '2025-08-15', 'Brian Olsen', 'brian.olsen@example.com', '+4531400247', 1095, 5599, 'VAREBIL', false),
            (33, '2024-09-10', '2025-05-10', 'Frederik Lindberg', 'frederik.lindberg@example.com', '+4531665432', 1300, 5799, 'ABONNEMENT', false),
            (35, '2024-11-01', '2025-04-01', 'Mathilde Hansen', 'mathilde.hansen@example.com', '+4531230159', 1275, 5999, 'LIMITED', false),
            (37, '2025-01-10', '2025-06-10', 'Jonas Jensen', 'jonas.jensen@example.com', '+4531555667', 1155, 2899, 'ABONNEMENT', false),
            (38, '2024-06-15', '2025-09-15', 'Camilla Andersen', 'camilla.andersen@example.com', '+4531645678', 995, 2999, 'ABONNEMENT', false),
            (39, '2024-07-01', '2025-05-28', 'Betina Christensen', 'betina.christensen@example.com', '+4531788011', 1099, 3199, 'ABONNEMENT', false),
            (42, '2024-10-01', '2025-07-01', 'Maria Sørensen', 'maria.sorensen@example.com', '+4531640598', 1200, 5000, 'ABONNEMENT', false),
            (43, '2024-11-20', '2025-11-19', 'Rikke Møller', 'rikke.moller@example.com', '+4518192021', 5500, 5599, 'ABONNEMENT', false),
            (46, '2024-12-25', '2025-05-24', 'Ole Jakobsen', 'ole.jakobsen@example.com', '+4519202122', 5600, 5999, 'MINILEASING', false),
            (48, '2023-06-05', '2025-06-04', 'Hanne Jacobsen', 'hanne.jacobsen@example.com', '+4520212223', 5700, 2899, 'ABONNEMENT', false),
            (51, '2023-07-12', '2025-07-11', 'Jesper Jensen', 'jesper.jensen@example.com', '+4521222324', 5800, 3199, 'ABONNEMENT', false),
            (52, '2023-08-18', '2025-08-17', 'Inge Nielsen', 'inge.nielsen@example.com', '+4522232425', 5900, 5000, 'ABONNEMENT', false),
            (53, '2023-09-25', '2024-09-24', 'Klaus Hansen', 'klaus.hansen@example.com', '+4523242526', 6000, 4500, 'VAREBIL', false),
            (55, '2023-10-30', '2025-10-29', 'Bente Sørensen', 'bente.sorensen@example.com', '+4524252627', 6100, 4000, 'VAREBIL', false),
            (58, '2023-11-15', '2025-11-14', 'Per Rasmussen', 'per.rasmussen@example.com', '+4525262728', 6200, 3500, 'ABONNEMENT', false),
            (60, '2023-01-01', '2025-06-01', 'Maja Andersen', 'maja.andersen@example.com', '+4526272829', 6300, 4199, 'ABONNEMENT', false),
            (63, '2024-06-15', '2025-05-14', 'Steen Nielsen', 'steen.nielsen@example.com', '+4527282930', 6400, 4599, 'ABONNEMENT', false),
            (66, '2024-03-20', '2026-03-19', 'Tina Hansen', 'tina.hansen@example.com', '+4528293031', 6500, 3399, 'ABONNEMENT', false),
            (69, '2025-01-25', '2025-05-24', 'Bo Pedersen', 'bo.pedersen@example.com', '+4529303132', 6600, 3999, 'MINILEASING', false);

-- Insert Leases
INSERT INTO add_ons (addon_type, lease_id) VALUES
            (1, 1),
            (2, 3),
            (3, 5),
            (4, 7),
            (1, 9),
            (2, 11),
            (3, 13),
            (4, 15),
            (1, 17),
            (2, 19),
            (3, 21),
            (4, 23),
            (1, 25),
            (2, 27),
            (3, 29),
            (4, 30);

-- Indsæt Skader
INSERT INTO damages (lease_id, damage_type, category, price) VALUES
            (5, 'Revnet baglygte', 2, 275.00),
            (5, 'Ridser på venstre dør', 1, 150.00),
            (5, 'Plet på forsæde', 1, 90.00),

            (18, 'Slidte dæk', 1, 180.00),
            (18, 'Smadret sidespejl', 2, 300.00),
            (18, 'Revne i kofanger', 2, 250.00),

            (25, 'Stenslag i forrude', 3, 350.00),
            (25, 'Bule i højre bagskærm', 2, 400.00),
            (25, 'Lugt af røg i kabinen', 1, 120.00),

            (29, 'Indvendige ridser på instrumentbræt', 1, 120.00),
            (29, 'Løse gulvmåtter', 1, 60.00),
            (29, 'Defekt klimaanlæg', 3, 900.00);