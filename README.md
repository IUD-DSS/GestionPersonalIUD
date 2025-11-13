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
- NetBeans
  
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
│  └─ Diagrama.pdf
└─ pom.xml
```

## Participantes
- Cristian Felipe Vargas Sanchez
- Juan David Marriaga Pertuz
- Juan Guillermo Osorio Gómez
