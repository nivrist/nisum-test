# Prueba Técnica para Nisum

## Descripción

Este proyecto es una aplicación Java basada en la arquitectura hexagonal, desarrollada en Java 1.8. A continuación, se describen los aspectos clave del proyecto.

## Detalles del Proyecto

- **Arquitectura Hexagonal**: El proyecto se adhiere a la arquitectura hexagonal, una metodología de diseño que separa claramente las capas de negocio y la infraestructura. Esto proporciona un código modular y fácil de mantener.


- **Lombok para Mayor Claridad del Código**: Se emplea Lombok, una biblioteca de Java, para eliminar la necesidad de escribir código repetitivo como getters, setters y constructores. Esto resulta en un código más limpio y fácil de entender.


- **Pruebas Unitarias con Mockito**: El código se somete a pruebas unitarias utilizando Mockito. Esto garantiza que la lógica del programa funcione como se espera y facilita las futuras actualizaciones.


- **Base de Datos H2**: La base de datos se implementa mediante H2, una base de datos en memoria. Esto permite almacenar datos en tiempo de ejecución sin necesidad de una base de datos externa, acceso a base H2:

  [http://localhost:8080/nisum-service/h2-console](http://localhost:8080/nisum-service/h2-console)

   Nota: Se adjunta Script de base de datos **nisumDB.sql**, para poder crear la estructura de tablas en un gestor diferente.


- **Documentación con Swagger**: Swagger se emplea para documentar y exponer los servicios RESTful. Proporciona una interfaz interactiva que facilita la comprensión y el consumo de los servicios. La documentación de Swagger está disponible en:

  [http://localhost:8080/nisum-service/swagger-ui.html](http://localhost:8080/nisum-service/swagger-ui.html)


- **Jwt como Token**: Se genera un token para persistir en conjunto con los datos del usuario.


- **Jacoco para Medición de Cobertura de Código**: El proyecto utiliza Jacoco para medir la cobertura de código. La cobertura actual es del 80%, asegurando una amplia evaluación de las pruebas unitarias.


- **Archivo Collection Postman**: Se adjunta archivo collection **NisumApi.postman_collection.json** para poder hacer import y ejecutar el request desde Postman.


- **Docker para la Portabilidad**: El proyecto incluye un archivo DockerFile para ejecutar la aplicación en un contenedor Docker. Los comandos para construir y ejecutar la imagen del contenedor son los siguientes:

    ```bash
    docker build -t nisum:latest .
    docker run -d -p 8080:8080 nisum:latest
    ```

  Esto permite que la aplicación sea fácilmente transportable y ejecutable en diferentes entornos.

- **Ejecución con `java -jar`**: La aplicación se puede ejecutar localmente utilizando el comando `java -jar` después de compilarla con Maven. Ejemplo:

    ```bash
    java -jar target/nisum.jar
    ```
## Requisitos

Tener instalados los siguientes componentes antes de ejecutar el proyecto:

- Java 1.8
- Apache Maven 3.8.7
- Docker (opcional para ejecutar en contenedor)

## Instalación

Pasos para ejecutar la aplicación en local:

1. Clonar el repositorio https://github.com/nivrist/nisum-test
2. Asegúrarse de tener Java 1.8 instalado.
3. Utiliza Maven para compilar y construir el proyecto.

    ```bash
    mvn clean install
    ```

## Compilación y Verificación de Cobertura

Para compilar el proyecto y verificar la cobertura con Jacoco, seguir las siguientes instrucciones:

1. Abrir una terminal e ir al directorio raíz del proyecto.

2. Ejecutar el siguiente comando para compilar el proyecto:

    ```bash
    mvn clean install
    ```

3. Luego, para verificar la cobertura de código con Jacoco, ejecutar:

    ```bash
    mvn clean verify
    ```

   Este comando ejecutará las pruebas unitarias y generará un informe de cobertura en el directorio `target/site/jacoco`.


4. Abrir el informe de cobertura en el navegador web. se puede hacer con el siguiente comando:

    ```bash
    open target/site/jacoco/index.html
    ```

   O simplemente ir al directorio `target/site/jacoco` y abrir el archivo `index.html` en el navegador.

   El informe de cobertura  proporcionará los detalles sobre qué porcentaje del código está cubierto por las pruebas unitarias.

