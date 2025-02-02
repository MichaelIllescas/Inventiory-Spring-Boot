
# Inventiory-Spring-Boot

Este proyecto es una aplicación web desarrollada para la gestión de inventarios y control de ventas realizadas, proporcionando funcionalidades para el seguimiento y control de productos.

## Tabla de Contenidos

- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Requisitos Previos](#requisitos-previos)
- [Instalación](#instalación)
- [Uso](#uso)

## Tecnologías Utilizadas

- **Java 17**: Lenguaje de programación principal.
- **Spring Boot**: Framework para facilitar la creación de aplicaciones Java.
- **Spring Data JPA**: Para la gestión de la persistencia de datos.
- **Thymeleaf**: Motor de plantillas para la generación de vistas.
- **MySQL**: Sistema de gestión de bases de datos relacional.
- **Maven**: Herramienta de gestión de dependencias y construcción del proyecto.
- **Bootstap 5**: Framework de css para el diseño de interfaces responsivas.

## Estructura del Proyecto

La estructura del proyecto es la siguiente:

```
Inventiory-Spring-Boot/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── michael/
│   │   │           └── inventiory/
│   │   │               ├── controller/
│   │   │               ├── model/
│   │   │               ├── repository/
│   │   │               └── service/
│   │   └── resources/
│   │       ├── static/
│   │       ├── templates/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/
│               └── michael/
│                   └── inventiory/
├── .gitignore
├── pom.xml
└── README.md
```

- **src/**: Contiene el código fuente del proyecto.
  - **main/**: Código principal de la aplicación.
    - **java/**: Archivos Java.
      - **com/michael/inventiory/**: Paquete base del proyecto.
        - **controller/**: Controladores que manejan las solicitudes HTTP.
        - **model/**: Clases que representan las entidades del dominio.
        - **repository/**: Interfaces que interactúan con la base de datos.
        - **service/**: Clases que contienen la lógica de negocio.
    - **resources/**: Recursos de la aplicación.
      - **static/**: Archivos estáticos como CSS, JS e imágenes.
      - **templates/**: Vistas Thymeleaf de la aplicación.
      - **application.properties**: Archivo de configuración de la aplicación.
  - **test/**: Pruebas unitarias y de integración.
- **.gitignore**: Especifica los archivos y directorios que Git debe ignorar.
- **pom.xml**: Archivo de configuración de Maven que gestiona las dependencias del proyecto.
- **README.md**: Archivo que estás leyendo actualmente.

## Requisitos Previos

Antes de instalar y ejecutar la aplicación, asegúrate de tener instalados los siguientes componentes:

- [Java Development Kit (JDK) 17]
- [Apache Maven 3.6.3 o superior]
- [MySQL 8.0 o superior]

## Instalación

Sigue estos pasos para instalar y configurar el proyecto:

1. **Clonar el repositorio**

   ```bash
   git clone https://github.com/MichaelIllescas/Inventiory-Spring-Boot.git
   cd Inventiory-Spring-Boot
   ```

2. **Configurar la base de datos**

   - Crea una base de datos en MySQL:

     ```sql
     CREATE DATABASE inventiory_db;
     ```

   - Actualiza el archivo `src/main/resources/application.properties` con las credenciales de tu base de datos:

     ```
     spring.datasource.url=jdbc:mysql://localhost:3306/inventiory_db
     spring.datasource.username=tu_usuario
     spring.datasource.password=tu_contraseña
     spring.jpa.hibernate.ddl-auto=update
     spring.jpa.show-sql=true
     spring.jpa.properties.hibernate.format_sql=true
     ```

3. **Compilar y empaquetar la aplicación**

   ```bash
   mvn clean package
   ```

4. **Ejecutar la aplicación**

   ```bash
   java -jar target/inventiory-0.0.1-SNAPSHOT.jar
   ```

   La aplicación estará disponible en `http://localhost:8080`.

## Uso

Una vez que la aplicación esté en funcionamiento, puedes acceder a la interfaz web para:

- Gestionar productos: agregar, editar, eliminar y listar productos.
- Gestionar categorías: agregar, editar, eliminar y listar categorías.
- Gestionar ventas: agregar, editar, eliminar y listar ventas.
- Gestionar Reportes estadisticos de ventas realizadas.
 Autor

Jonathan - GitHub
📞 Contacto Si tienes preguntas o sugerencias, puedes contactarme en: ✉️ Email: joni.illes@hotmail.com 🐙 GitHub: MichaelIllescas 🚀 ¡Gracias por visitar Ceibo! ⚽💙

Nos encontramos en proceso de desarrollo y mejoras de constantemente!

