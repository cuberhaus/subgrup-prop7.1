# Sistema de Recomanació - PROP (Grup 7.1)

Aquest repositori conté el projecte de l'assignatura Projecte de Programació (PROP) de la Facultat d'Informàtica de Barcelona (FIB - UPC). 
El projecte consisteix en el disseny i desenvolupament d'un **Sistema de Recomanació** utilitzant Java, aplicant patrons de disseny de programari i estructurant l'aplicació en tres capes (Domini, Persistència i Presentació).

## 👥 Membres de l'equip (Subgrup 7.1)

- **Pol Casacuberta Gil** ([pol.casacuberta@estudiantat.upc.edu](mailto:pol.casacuberta@estudiantat.upc.edu))
- **Edgar Moreno Martínez** ([edgar.moreno.martinez@estudiantat.upc.edu](mailto:edgar.moreno.martinez@estudiantat.upc.edu))
- **Maria Prat Colomer** ([maria.prat@estudiantat.upc.edu](mailto:maria.prat@estudiantat.upc.edu))
- **Pablo Vega Gallego** ([pablo.vega.gallego@estudiantat.upc.edu](mailto:pablo.vega.gallego@estudiantat.upc.edu))

## 📁 Estructura del Projecte

L'arquitectura del projecte es divideix principalment en els següents directoris:

- `FONTS/`: Codi font de l'aplicació en Java.
  - `domini/`: Classes del model de domini i controladors de domini.
  - `persistencia/`: Classes encarregades de la gestió de dades i fitxers.
  - `presentacio/`: Interfície gràfica d'usuari (GUI) i controladors de presentació.
  - `utilitats/`: Classes d'utilitat transversal.
  - `excepcions/`: Excepcions personalitzades del sistema.
  - `lib/`: Llibreries externes necessàries (ex: JUnit per als jocs de proves).
- `EXE/`: Directori generat automàticament on es desen els executables (`.class`) i conjunts de dades de proves un cop compilat el projecte.
- `DOCS/`: Documentació tècnica generada (JavaDoc) i documents corresponents a les diferents entregues del projecte.
- `PROP/`: Documentació relacionada amb l'organització de l'equip, casos d'ús, disseny de classes i fitxers interns de l'assignatura.

## 🛠️ Compilació i Execució

Tot el codi font es troba a la carpeta `FONTS/`. Per compilar i executar el projecte s'utilitza l'eina `make`. 

Primer de tot, cal situar-se al directori on hi ha el codi font:
```bash
cd FONTS
```

A continuació es detallen les comandes disponibles:

- **Compilar l'aplicació principal:**
  ```bash
  make
  ```
  *(Això compilarà el programa i els jocs de proves, dipositant els `.class` al directori `EXE/`)*

- **Executar l'aplicació principal (interfície gràfica):**
  ```bash
  make run
  ```

- **Compilar i executar un driver específic (testeig):**
  ```bash
  make Driver[NomDeLaClasse]
  ```
  *(Substitueix `[NomDeLaClasse]` pel nom de la classe que vols testejar)*

- **Executar tests unitaris (JUnit):**
  ```bash
  make [NomClasseTest]Test
  ```
  *(Executa el test de domini corresponent)*

- **Executar jocs de proves pràctics:**
  ```bash
  make joc[1/2/3]
  ```
  *(Exemple: `make joc1` executa el joc de prova 1)*

- **Netejar l'entorn (esborrar compilats):**
  ```bash
  make clean
  ```
  *(Esborra tots els fitxers compilats `.class` del directori `EXE/`)*