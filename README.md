# Proyecto: Gestión de Funcionarios (Java Swing + MariaDB)

Aplicación de escritorio en **Java Swing** con **patrón DAO**, que implementa **CRUD de la tabla `funcionarios`**
y mantiene las relaciones con `grupo_familiar` y `formacion_academica` (nivel esquema).
Se usa **MariaDB** como motor y **Maven** para dependencias.

## Tecnologías
- Java 17
- Swing (JTable, JTextField, JButton)
- Maven
- MariaDB 10.x
- Driver JDBC: `org.mariadb.jdbc:mariadb-java-client:3.3.2`
- NetBeans / IntelliJ / Eclipse

## Estructura
```
proyectoiudigital/
├─ src/main/java/com/iudigital/funcionarios/
│  ├─ config/ConnectionConfig.java
│  ├─ domain/Funcionario.java
│  ├─ dao/FuncionarioDao.java
│  └─ view/{FuncionarioFrame.java, Main.java}
├─ database/
│  ├─ script_creacion.sql
│  └─ script_datos.sql
└─ pom.xml
```

## Cómo ejecutar
1. Ejecuta los scripts de `database/` en MariaDB:
   - `script_creacion.sql`
   - `script_datos.sql` (opcional)
2. Edita las credenciales en `ConnectionConfig.java`.
3. Compila y ejecuta con NetBeans (Run) o Maven:
   ```bash
   mvn clean package
   java -jar target/proyectoiudigital-1.0.0.jar
   ```

## Notas
- El CRUD de ejemplo cubre **funcionarios**. Las tablas relacionadas están en el esquema para futuras ampliaciones.
- Si prefieres MySQL, solo cambia la URL `jdbc:mariadb://` por `jdbc:mysql://` y usa su driver.
