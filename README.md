# Sistema de Biblioteca - Arquitectura de Microservicios

Proyecto semestral DSY1103 (Desarrollo FullStack 1). Sistema distribuido para la
gestion completa de una biblioteca: usuarios, roles, prestamos, inventario,
multas, listas personales, notificaciones, sugerencias y valoraciones.

## Link Video
[Link video](https://www.youtube.com/watch?v=553wKZcdLow)

## Integrantes del equipo

| Nombre | Aporte |
|---|---|
| Diego Patricio Soto León | Auth - User - Security - Inventario - Prestamos - Multas |
| Nabih Aballay | Sugerencias - Favoritos |
| Cristopher Retamal Carrera| Notificaciones - Valoraciones |


## Microservicios

| # | Servicio | Puerto | BD | Descripcion |
|---|---|---|---|---|
| 0 | `eureka-server` | 8761 | — | Registry / Service Discovery |
| 1 | `auth-service` | 8083 | `library_auth_db` | Login y emision de JWT |
| 2 | `user-service` | 8081 | `library_users_db` | CRUD de usuarios |
| 3 | `security-service` | 8082 | `library_security_db` | Roles y asignaciones |
| 4 | `ms-inventario` | 8084 | `library_inventory_db` | Catalogo de libros |
| 5 | `ms-prestamos` | 8085 | `library_loans_db` | Registro de prestamos |
| 6 | `ms-multas` | 8086 | `library_fines_db` | Calculo y cobro de multas |
| 7 | `ms-favoritos-listas` | 8087 | `library_favorites_db` | Listas personales |
| 8 | `ms-notificaciones` | 8088 | `library_notifications_db` | Alertas y avisos |
| 9 | `ms-sugerencias` | 8089 | `library_suggestions_db` | Propuestas de libros |
| 10 | `ms-valoraciones` | 8090 | `library_reviews_db` | Reviews y rating |
| 99 | `api-gateway` | 8080 | — | Routing centralizado |

## Comunicacion entre microservicios (Feign)

| Origen | Destino | Proposito |
|---|---|---|
| auth-service | user-service | Obtener usuario y validar password |
| auth-service | security-service | Obtener roles para el JWT |
| security-service | user-service | Validar existencia del usuario al asignar rol |
| ms-prestamos | user-service | Validar email del solicitante |
| ms-prestamos | ms-inventario | Validar libro y stock |
| ms-prestamos | ms-multas | Bloquear si tiene multas pendientes |
| ms-multas | ms-prestamos | Obtener fecha de devolucion del prestamo |
| ms-multas | user-service | Validar usuario del prestamo |
| ms-favoritos-listas | user-service | Validar email del propietario |
| ms-notificaciones | user-service | Validar destinatario |
| ms-notificaciones | ms-prestamos | Obtener fechaDevolucion (vencimientos) |
| ms-sugerencias | user-service | Validar socio que sugiere |
| ms-sugerencias | ms-inventario | Verificar que el ISBN no este en catalogo |
| ms-valoraciones | user-service | Validar autor de la resenia |
| ms-valoraciones | ms-inventario | Validar libro a valorar |

## Funcionalidades implementadas
- Autenticacion JWT (`POST /api/auth/login`)
- CRUD completo de usuarios, libros, roles, prestamos, listas, sugerencias,
  valoraciones, notificaciones y multas
- Asignacion y revocacion de roles a usuarios
- Validacion cruzada de referencias por Feign antes de persistir
- Bloqueo automatico de prestamos cuando el usuario tiene multas pendientes
- Calculo automatico de multas en base a dias de retraso (`multas.tarifa-por-dia`)
- Promedio de valoraciones por libro
- Notificaciones automaticas de vencimiento de prestamo
- Validacion JSR 380 en todos los endpoints de entrada
- Manejo centralizado de excepciones con `@RestControllerAdvice`
- Logs estructurados SLF4J en todas las capas

## Pasos para ejecutar

Abrir el proyecto completo en VS-Code, abrir una instancia de XAMPP, levantando el servicio de apache y Mysql, ejecutar y esperar a levantar el servicio de Eureka, ejecutar el resto de microservicios en orden, por ultimo abriendo API Gateway.

Verifica el registro en `http://localhost:8761` (dashboard de Eureka). Los 10
microservicios + el gateway deben aparecer en `Instances currently registered`.

### SQL

Para la creación de las bases de datos, se adjuntan 3 SQL en el proyecto, en los cuales hay que ejecutarlos en orden en el perfil de phpadmin de la instancia de XAMPP


## Licencia

Proyecto academico - Duoc UC - DSY1103 - 2026

---

# 1. Objetivo del proyecto

1. Objetivo del proyecto

El sistema permite administrar el funcionamiento de una biblioteca mediante distintos microservicios especializados.

Entre sus principales funcionalidades se encuentran:

1.Registro y autenticación de usuarios.
2.Administración del catálogo de libros.
3.Gestión del inventario.
4.Administración de préstamos.
5.Gestión de multas.
6.Administración de listas de favoritos.
7.Registro de valoraciones de libros.
8.Generación de sugerencias.
9.Envío de notificaciones a los usuarios.

Cada funcionalidad se encuentra desacoplada en un microservicio independiente para facilitar el mantenimiento y la escalabilidad.
---

# 2. Arquitectura general

```text
Cliente Web / Postman
        |
        v
API Gateway :8080
        |
        +--> auth-service
        +--> user-service
        +--> security-service
        +--> ms-inventario
        +--> ms-prestamos
        +--> ms-multas
        +--> ms-favoritos-listas
        +--> ms-notificaciones
        +--> ms-sugerencias
        +--> ms-valoraciones

Eureka Server :8761
```

---

# 3. Microservicios del sistema
| Módulo                | Responsabilidad                             |
| --------------------- | ------------------------------------------- |
| `eureka-server`       | Registro y descubrimiento de servicios      |
| `api-gateway`         | Punto único de acceso al sistema            |
| `auth-service`        | Autenticación y emisión de JWT              |
| `security-service`    | Seguridad y autorización                    |
| `user-service`        | Administración de usuarios                  |
| `ms-inventario`       | Gestión del catálogo e inventario de libros |
| `ms-prestamos`        | Administración de préstamos                 |
| `ms-multas`           | Gestión de multas                           |
| `ms-favoritos-listas` | Administración de listas de favoritos       |
| `ms-notificaciones`   | Gestión de notificaciones                   |
| `ms-sugerencias`      | Recomendaciones y sugerencias               |
| `ms-valoraciones`     | Registro de valoraciones y opiniones        |

---

# 4. Tecnologías utilizadas

* Java 21
* Spring Boot
* Spring Cloud
* Eureka Server
* Eureka Client
* Spring Cloud Gateway
* OpenFeign
* Spring Web
* Spring Data JPA
* MySQL
* XAMPP
* Lombok
* Bean Validation
* Swagger / OpenAPI
* Maven
* VSCode

---

# 5. Estructura del proyecto

```text
ProyectoBiblioteca/

├── eureka-server/
├── api-gateway/
├── auth-service/
├── security-service/
├── user-service/
├── ms-inventario/
├── ms-prestamos/
├── ms-multas/
├── ms-favoritos-listas/
├── ms-notificaciones/
├── ms-sugerencias/
├── ms-valoraciones/
│
└── Bases de datos/
```

---

# 6. Bases de datos

El proyecto usa una base de datos independiente por microservicio.
| Microservicio         | Base de datos          |
| --------------------- | ---------------------- |
| `auth-service`        | Base de autenticación  |
| `user-service`        | Base de usuarios       |
| `ms-inventario`       | Base de inventario     |
| `ms-prestamos`        | Base de préstamos      |
| `ms-multas`           | Base de multas         |
| `ms-favoritos-listas` | Base de favoritos      |
| `ms-notificaciones`   | Base de notificaciones |
| `ms-sugerencias`      | Base de sugerencias    |
| `ms-valoraciones`     | Base de valoraciones   |

Los scripts de creación de bases y datos iniciales se encuentran en:

```text
Bases de datos/01_create_databases.sql
Bases de datos/02_seed_roles.sql
Bases de datos/03_create_test_databases.sql
```

---

# 7. Configuración de MySQL

Este proyecto está configurado para usar MySQL mediante XAMPP en el puerto:

```text
3307
```

Ejemplo de configuración usada en los microservicios:

```properties
spring.datasource.url=jdbc:mysql://localhost:3307/nombre_bd
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

---

# 8. Orden de ejecución

Antes de iniciar el sistema se debe:

Iniciar MySQL.
Crear las bases de datos utilizando los scripts SQL.
Verificar la configuración de conexión de cada microservicio.

Posteriormente iniciar los servicios en el siguiente orden:

| Orden | Servicio              |
| ----: | --------------------- |
|     1 | `eureka-server`       |
|     2 | `auth-service`        |
|     3 | `security-service`    |
|     4 | `user-service`        |
|     5 | `ms-inventario`       |
|     6 | `ms-prestamos`        |
|     7 | `ms-multas`           |
|     8 | `ms-favoritos-listas` |
|     9 | `ms-notificaciones`   |
|    10 | `ms-sugerencias`      |
|    11 | `ms-valoraciones`     |
|    12 | `api-gateway`         |

---

# 9. Ejecución desde VSCode

Se recomienda usar la extensión **Spring Boot Dashboard** de VSCode.

Desde el panel de Spring Boot se pueden iniciar los servicios uno por uno:

```text
eureka-server
auth-service
security-service
user-service
ms-inventario
ms-prestamos
ms-multas
ms-favoritos-listas
ms-notificaciones
ms-sugerencias
ms-valoraciones
api-gateway
```

También se pueden ejecutar desde terminal.

Ejemplo:

```bash
cd "4. ms-inventario"
./mvnw spring-boot:run
```

---

# 10. Compilación del proyecto completo

Cada microservicio tiene su propio wrapper de Maven. Para compilar o probar un
módulo se debe entrar primero a su carpeta.

```bash
cd "4. ms-inventario"
./mvnw clean test
```

Los tests automáticos usan el perfil `test` y una base H2 en memoria, por lo que
no modifican las bases de datos MySQL de desarrollo.

---

# 11. Eureka Server

La consola de Eureka se encuentra en:

```text
http://localhost:8761
```

Cuando todos los servicios están levantados, deben aparecer registrados:

```text
API-GATEWAY
AUTH-SERVICE
SECURITY-SERVICE
USER-SERVICE
MS-INVENTARIO
MS-PRESTAMOS
MS-MULTAS
MS-FAVORITOS-LISTAS
MS-NOTIFICACIONES
MS-SUGERENCIAS
MS-VALORACIONES
```

---

# 12. API Gateway

El API Gateway permite consumir todos los microservicios desde el puerto:

```text
http://localhost:8080
```

Rutas principales:

| Recurso        | URL                                        |
| -------------- | ------------------------------------------ |
| Autenticación  | `http://localhost:8080/api/auth`           |
| Usuarios       | `http://localhost:8080/api/users`          |
| Inventario     | `http://localhost:8080/inventario`         |
| Préstamos      | `http://localhost:8080/prestamos`          |
| Multas         | `http://localhost:8080/multas`             |
| Favoritos      | `http://localhost:8080/api/listas`         |
| Notificaciones | `http://localhost:8080/api/notificaciones` |
| Sugerencias    | `http://localhost:8080/api/sugerencias`    |
| Valoraciones   | `http://localhost:8080/api/valoraciones`   |


---

# 13. Swagger

Para simplificar el uso en clases, Swagger se revisa directamente por puerto de cada microservicio.

| Microservicio       | Swagger                                      |
| ------------------- | -------------------------------------------- |
| auth-service        | `http://localhost:8083/doc/swagger-ui.html`  |
| user-service        | `http://localhost:8081/doc/swagger-ui.html`  |
| security-service    | `http://localhost:8082/doc/swagger-ui.html`  |
| ms-inventario       | `http://localhost:8084/doc/swagger-ui.html`  |
| ms-prestamos        | `http://localhost:8085/doc/swagger-ui.html`  |
| ms-multas           | `http://localhost:8086/doc/swagger-ui.html`  |
| ms-favoritos-listas | `http://localhost:8087/doc/swagger-ui.html`  |
| ms-notificaciones   | `http://localhost:8088/doc/swagger-ui.html`  |
| ms-sugerencias      | `http://localhost:8089/doc/swagger-ui.html`  |
| ms-valoraciones     | `http://localhost:8090/doc/swagger-ui.html`  |

El Gateway se usa para consumir APIs, pero no para centralizar Swagger en esta versión.

---

# 14. Comunicación entre microservicios

El proyecto usa OpenFeign para comunicación entre servicios.

| Servicio origen     | Servicio destino | Objetivo                              |
| ------------------- | ---------------- | ------------------------------------- |
| auth-service        | user-service     | Validar usuarios registrados          |
| ms-prestamos        | ms-inventario    | Verificar disponibilidad del libro    |
| ms-prestamos        | user-service     | Obtener información del usuario       |
| ms-multas           | ms-prestamos     | Consultar préstamos vencidos          |
| ms-notificaciones   | ms-prestamos     | Notificar devoluciones y vencimientos |
| ms-sugerencias      | ms-valoraciones  | Obtener libros mejor evaluados        |
| ms-sugerencias      | ms-inventario    | Recomendar libros disponibles         |
| ms-favoritos-listas | ms-inventario    | Consultar información de libros       |


---

# 15. Flujo funcional principal

## Paso 1: Registrar usuario

```http
POST http://localhost:8080/api/users/crear
Content-Type: application/json
```

```json
{
  "nombre": "Laura Fuentes",
  "email": "laura.fuentes@biblioteca.cl",
  "password": "123456",
  "telefono": "+56956789123"
}
```

## Paso 2: Iniciar sesion

```http
POST http://localhost:8080/api/auth/login
Content-Type: application/json
```

```json
{
  "email": "laura.fuentes@biblioteca.cl",
  "password": "123456"
}
```

## Paso 3: Registrar libro en inventario

```http
POST http://localhost:8080/inventario/crear
Content-Type: application/json
```

```json
{
  "titulo": "Clean Code",
  "autor": "Robert C. Martin",
  "isbn": "9780132350884",
  "editorial": "Prentice Hall",
  "stock": 5
}
```

## Paso 4: Consultar catalogo

```http
GET http://localhost:8080/inventario/listar
```

## Paso 5: Solicitar prestamo

```http
POST http://localhost:8080/prestamos/registrar
Content-Type: application/json
```

```json
{
  "emailUsuario": "laura.fuentes@biblioteca.cl",
  "libroId": 1,
  "fechaDevolucion": "2026-07-15"
}
```

El sistema verifica:

* existencia del usuario;
* disponibilidad del libro;
* multas pendientes;
* reglas del negocio del prestamo.

## Paso 6: Consultar notificaciones del usuario

```http
GET http://localhost:8080/api/notificaciones/usuario/laura.fuentes@biblioteca.cl
```

## Paso 7: Registrar valoracion de un libro

```http
POST http://localhost:8080/api/valoraciones
Content-Type: application/json
```

```json
{
  "libroId": 1,
  "emailUsuario": "laura.fuentes@biblioteca.cl",
  "puntuacion": 5,
  "comentario": "Excelente libro, muy recomendado"
}
```

---

# 16. Validaciones implementadas

## `auth-service`
* Usuario obligatorio.
* Contraseña obligatoria.
* Validación de credenciales.
* Generación segura de JWT.
## `user-service`
* Nombre obligatorio.
* Correo electrónico válido.
* Correo único.
* Contraseña obligatoria.
* Validación de datos personales.
## `ms-inventario`
* Título obligatorio.
* Autor obligatorio.
* ISBN único.
* Stock mayor que cero.
## `ms-prestamos`
* Usuario obligatorio.
* Libro obligatorio.
* Validación de disponibilidad.
* Validación de fecha de devolución.
* Verificación de préstamos activos.
## `ms-multas`
* Monto mayor que cero.
* Préstamo válido.
* Fecha obligatoria.
## `ms-favoritos-listas`
* Usuario obligatorio.
* Libro obligatorio.
* Evita duplicados en favoritos.
## `ms-notificaciones`
* Usuario existente.
* Mensaje obligatorio.
* Fecha de envío válida.
## `ms-sugerencias`
* Usuario obligatorio.
* Recomendaciones basadas en historial y valoraciones.
## `ms-valoraciones`
* Usuario obligatorio.
* Libro obligatorio.
* Calificación dentro del rango permitido.
* Comentario opcional.

---

# 17. Manejo de errores

Cada microservicio incorpora un manejo centralizado de excepciones mediante @RestControllerAdvice, permitiendo entregar respuestas homogéneas al cliente cuando ocurre un error.

Entre las excepciones controladas se encuentran:

* Recursos no encontrados.
* Errores de validación.
* Credenciales inválidas.
* Acceso no autorizado.
* Errores internos del servidor.
* Errores de comunicación entre microservicios.

Ejemplo de respuesta de error:

```json
{
"timestamp": "2026-06-29T16:20:35",
  "status": 400,
  "error": "Error de validación",
  "message": "Existen campos inválidos en la solicitud",
  "path": "/api/prestamos",
  "errors": {
    "fechaPrestamo": "La fecha es obligatoria",
    "idLibro": "El libro seleccionado no existe"
  }
}
```

---

# 18. Logs

Cada microservicio incorpora logs básicos mediante Lombok:

```java
@Slf4j
```

Ejemplo:

```java
log.info("Usuario autenticado correctamente");
log.info("Creando préstamo");
log.warn("Libro sin disponibilidad");
log.warn("Usuario no encontrado");
log.error("Error al consumir el servicio de inventario");
log.error("Fallo interno del servidor");
```

---

# 19. Comandos útiles

## Compilar un microservicio

```bash
cd "4. ms-inventario"
./mvnw clean test
```

## Ejecutar un microservicio desde terminal

```bash
cd "4. ms-inventario"
./mvnw spring-boot:run
```

## Ejecutar un test especifico

```bash
cd "4. ms-inventario"
./mvnw -Dtest=LibroServiceTest test
```

---

# 20. Documentación adicional

La documentación complementaria se encuentra en:

```text
README.md
Scripts SQL
application.properties
pom.xml
Documentación Swagger
Colección Postman (si aplica)
```

---

# 21. Estado actual del proyecto

| Elemento                          | Estado        |
| --------------------------------- | ------------- |
| Proyecto Maven Multi Module       | Implementado  |
| Eureka Server                     | Implementado  |
| API Gateway                       | Implementado  |
| Auth Service                      | Implementado  |
| Security Service                  | Implementado  |
| User Service                      | Implementado  |
| Inventario                        | Implementado  |
| Préstamos                         | Implementado  |
| Multas                            | Implementado  |
| Favoritos                         | Implementado  |
| Notificaciones                    | Implementado  |
| Sugerencias                       | Implementado  |
| Valoraciones                      | Implementado  |
| OpenFeign                         | Implementado  |
| Swagger                           | Implementado  |
| Spring Security                   | Implementado  |
| JWT                               | Implementado  |
| Bases de datos MySQL              | Implementadas |
| Comunicación entre microservicios | Implementada  |
| Manejo global de excepciones      | Implementado  |
| Logs                              | Implementados |
| Testing automatizado              | Implementado  |
| Docker                            | Pendiente     |
| Frontend Web                      | Pendiente     |


---

# 22. Próximas mejoras sugeridas

* Crear frontend web con Spring Boot + Thymeleaf.
* Implementar pruebas de controller con MockMvc.
* Crear colección Postman.
* Crear Docker Compose.
* Mejorar centralización de Swagger.
