# 🧾 Proyecto: Gestión de Funcionarios (Caso de Estudio)

## 📘 Descripción

Aplicación de escritorio desarrollada en **Java Swing**, que permite gestionar la información de los **funcionarios** de la *Institución Universitaria Digital de Antioquia*, incluyendo sus **grupos familiares** y **formación académica**.  

El sistema utiliza el **patrón DAO (Data Access Object)** para la gestión de datos, y aplica **manejo de excepciones** para garantizar la robustez y seguridad en las operaciones de acceso a la base de datos.  
El proyecto implementa el **CRUD completo de la tabla “Funcionario”**, considerando las relaciones descritas en el caso de estudio.

---

## ⚙️ Tecnologías utilizadas
- **Lenguaje:** Java 17  
- **Interfaz gráfica:** Java Swing  
- **Base de datos:** Microsoft SQL Server  
- **Conector JDBC:** `mssql-jdbc-12.2.0.jar`  
- **Entorno de desarrollo:** IntelliJ IDEA / NetBeans / Eclipse  
- **Control de versiones:** Git + GitHub o Bitbucket  
- **Gestor de base de datos:** SQL Server Management Studio (SSMS)

---

## 🧱 Modelo de base de datos
**Motor:** SQL Server  
**Nombre:** `RecursosHumanosDB`

**Tablas principales:**
- `Funcionario` — Datos personales del funcionario  
- `GrupoFamiliar` — Miembros del grupo familiar y su rol  
- `FormacionAcademica` — Nivel, título y universidad del funcionario  

**Relaciones:**
- Un funcionario puede tener varios registros en `GrupoFamiliar`.  
- Un funcionario puede tener varios registros en `FormacionAcademica`.  

Los scripts de creación (`script_creacion.sql`) y de inserción (`script_datos.sql`) se encuentran en la carpeta `/database`.


