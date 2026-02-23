# 💬 ForoHub – API REST con Spring Boot

Proyecto desarrollado como parte del desafío de programación del bloque de Backend de Alura Latam y Oracle Next Education. Se trata de una API RESTful construida en Java con Spring Boot que simula el funcionamiento del backend de un foro de discusiones.

El objetivo principal de este proyecto es consolidar la creación de una API escalable, integrando buenas prácticas como el patrón MVC, persistencia de datos relacional, migraciones automáticas y un sólido sistema de seguridad sin estado (Stateless) mediante tokens JWT.

---

## 🚀 Características principales

- **CRUD Completo de Tópicos:** Permite crear, listar, detallar, actualizar y eliminar tópicos de discusión respetando los verbos HTTP.
- **Seguridad y Autenticación:** Acceso restringido a los endpoints mediante **Spring Security** y **JSON Web Tokens (JWT)**.
- **Persistencia y Migraciones:** Base de datos MySQL con control de versiones estructural utilizando **Flyway**.
- **Manejo Global de Errores:** Interceptor `@RestControllerAdvice` para capturar excepciones (404, 400) y devolver respuestas JSON limpias y legibles.
- **Paginación Dinámica:** Listado de tópicos optimizado con `@PageableDefault` para evitar sobrecarga de datos.
- **Reglas de Negocio:** Validación estricta para evitar tópicos duplicados (mismo título y mensaje) en la base de datos.
- **Encriptación de contraseñas:** Uso de `BCryptPasswordEncoder` para proteger las credenciales de los usuarios.

---

## 🛠️ Tecnologías utilizadas

- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA (Hibernate)**
- **Spring Security**
- **MySQL (Base de datos relacional)**
- **Flyway (Migraciones de BD)**
- **Auth0 java-jwt (Tokens JWT)**
- **Lombok**
- **Maven**
- **IntelliJ IDEA & Postman**

---

## 📂 Estructura del proyecto

```text
src/main/java/com/aluracursos/forohub
│
├── ForohubApplication.java           // Clase principal de ejecución
│
├── controller
│   ├── AutenticacionController.java  // Maneja el login y devuelve el JWT
│   └── TopicoController.java         // Endpoints del CRUD de tópicos
│
├── domain
│   ├── topico                        // Lógica, DTOs (Records), Entidad y Repositorio
│   └── usuario                       // Lógica, DTOs, Entidad (UserDetails) y Repositorio
│
└── infra
    ├── exceptions                    // Manejo global de errores (@RestControllerAdvice)
    └── security                      // Configuración de Spring Security, Filtros y TokenService
```

---

## 🔑 Configuración inicial

1. Instalar **Java 17** y **MySQL Server**.
2. Crear una base de datos en MySQL llamada `forohub_db`.
3. Clonar el proyecto y abrirlo en IntelliJ IDEA.
4. Configurar las credenciales de la base de datos y la clave secreta del JWT en `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/forohub_db
spring.datasource.username=root
spring.datasource.password=tu_contraseña_aqui

# Clave secreta para firmar los tokens JWT
api.security.token.secret=tu_palabra_secreta_aqui
```

---

## 🌐 Autenticación (Flujo JWT)

La API es *Stateless*. Para consumir los endpoints de `/topicos`, primero debes autenticarte enviando tus credenciales a la ruta de login.

**Endpoint de Login:**
```text
POST /login
```

**Body de la request:**
```json
{
  "login": "juan@correo.com",
  "clave": "123456"
}
```

La API responderá con un **Token JWT** que deberá ser incluido en el Header `Authorization` como un `Bearer Token` en todas las peticiones posteriores.

---

## 🖥️ Ejemplo de uso (CRUD Tópicos)

### Crear un Tópico (POST `/topicos`)
Requiere Bearer Token en el Header.

**Request:**
```json
{
  "titulo": "Duda con Spring Boot para mi portfolio",
  "mensaje": "Estoy armando mi portfolio con proyectos RESTful...",
  "autor": "Juan Castro",
  "curso": "Spring Boot 3"
}
```

### Listar Tópicos (GET `/topicos?size=2&page=0`)
Devuelve un JSON paginado y ordenado por fecha de creación.

**Response:**
```json
{
  "content": [
    {
      "id": 1,
      "titulo": "Duda con Spring Boot para mi portfolio",
      "mensaje": "Estoy armando mi portfolio...",
      "fechaCreacion": "2026-02-23T14:10:18",
      "status": "NO_RESPONDIDO",
      "autor": "Juan Castro",
      "curso": "Spring Boot 3"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 2
  },
  "totalElements": 1,
  "totalPages": 1
}
```

---

## ▶️ Cómo ejecutar el programa

1. Abrir el proyecto en IntelliJ IDEA.
2. Esperar a que Maven descargue las dependencias.
3. Ejecutar `ForohubApplication.java`.
4. Flyway creará automáticamente las tablas en MySQL al iniciar.
5. (Opcional) Insertar un usuario manual en la base de datos con contraseña encriptada en BCrypt para probar el login.
6. Realizar las peticiones HTTP utilizando Postman o Insomnia.

---

## 🎯 Objetivo educativo

Este proyecto permitió poner en práctica conceptos avanzados de desarrollo backend:

- Arquitectura de APIs RESTful bajo el estándar de madurez de Richardson.
- Autenticación y Autorización Stateless con **Spring Security** y **JWT**.
- Versionado de bases de datos con **Flyway**.
- Paginación y ordenamiento a nivel de base de datos.
- Tratamiento global de excepciones (`@ExceptionHandler`).
- Patrón Data Transfer Object (DTO) usando `Records` de Java.

---

## 👤 Autor

Proyecto realizado por **Juan Castro** como parte de la formación Java Back-End de Alura Latam y Oracle Next Education.
