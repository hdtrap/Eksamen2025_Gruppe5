# Bilabonnement
Et backend softwaresystem til udlejning af biler, hvor du kan
1. Oprette lejeaftaler / leases
2. Oprette skader og skadesrapport
3. Tilføje nye brugere til systemet
4. Se firmaets vigtigste KPI'er

## Installation af kode
Tilføj linket til vores GitHub projekt, i jeres IDE, Link : https://github.com/safirestorm/Projekt2_Gruppe5.git

## For at bruge programmet online
Bare brug dette link : http://bilabonnementintern-def5b9gcd8c9aaat.swedencentral-01.azurewebsites.net

## For at køre programmet lokalt
Hvis du ønsker at køre applikationen lokalt skal du:

1. Opret en lokal MySQL-database
2. Kør disse SQL-script i projektet (src/main/resources/SQL/CREATE.sql) og (src/main/resources/SQL/INSERT.sql)
3. I application.properties, tilføj dine egne databaseoplysninger:
![carbon (2)](https://github.com/user-attachments/assets/bf3ed565-6aa0-449b-a46d-b4465170b76f)

### Hvordan bruger jeg programmet
1. Åben vores webapp på linket : http://bilabonnementintern-def5b9gcd8c9aaat.swedencentral-01.azurewebsites.net
2. Login med vores Demo bruger **brugernavn: demo kodeord: demo**
3. Tryk på pilen ved siden af "Skift bruger type" i headeren
4. Tryk på Dataregistrering
5. Tryk på "Opret ny lease"
6. Udfyld formularen og tryk "Opret lease"
7. Se Lejeaftale ID i højre del af siden under Informationer og husk det.
8. Tryk på skift bruger type og vælg skaderegistrering
9. Tryk på "Find et lease" indtast dit lejeaftale ID og tryk på "Søg"
10. Tryk på "Tilføj skade" udfyld formularen og tryk "Tilføj skade"
11. Tryk på skift bruger type og vælg Systemadminstrator
12. Tryk på "Opret ny bruger" udfyld formularen og tryk "submit"

### Hvad består vores kode af?
1. Vi bruger Java 21
2. Vi bruger frameworket Spring Boot, se pom.xml for dependencies
3. Vi bruger HTML og CSS

#### Dette er en opgave lavet i forbindelse med Datamatiker studiet
#### Projektgruppe
- Mikkel Mikkelsen – Frontend, Backend og Database
- Peter Tange – Frontend, Backend og Database
- Sarah Fagerstrøm – Frontend, Backend og Database
- Frederik Fuglø - Frontend, Backend og Database
