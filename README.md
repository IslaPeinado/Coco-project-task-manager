# 🥥 coco

coco es una aplicación web para la gestión de proyectos y tareas, pensada con un enfoque práctico y empresarial.

La aplicación permite trabajar con proyectos y tareas asociadas, controlar el acceso mediante autenticación JWT y roles, y mantener una API REST clara y documentada. El objetivo es tener una base sólida y escalable, con una estructura de código ordenada y mantenible.

---

## 🚀 Tecnologías utilizadas

### Frontend
- Angular
- TypeScript
- Angular Router
- Formularios reactivos
- Autenticación JWT (interceptor)

### Backend
- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA + Hibernate
- API REST
- Swagger / OpenAPI

### Base de datos
- Oracle Database

---

## 🔐 Funcionalidades

### Autenticación y autorización
- Registro e inicio de sesión
- Autenticación basada en JWT
- Roles: USUARIO / ADMIN
- Endpoints protegidos según permisos

### Gestión de proyectos
- Crear, actualizar y eliminar proyectos
- Asignación de proyectos a usuarios

### Gestión de tareas
- Operaciones CRUD para tareas
- Estados: TODO / EN PROGRESO / TERMINADO
- Tareas vinculadas a proyectos

### Otros
- Patrón DTO
- Manejo global de errores
- Arquitectura por capas (enfoque limpio)
- Documentación de la API con Swagger

---

## 📐 Arquitectura

Frontend (Angular)  
↓  
API REST (Spring Boot)  
↓  
Oracle Database

---

## ✅ Requisitos

- Node.js (LTS recomendado)
- Angular CLI (si aplica)
- Java 21
- Maven o Gradle
- Oracle DB (local, Docker o remoto)

---

## ▶️ Puesta en marcha (resumen)


### Backend
1. Configurar la conexión a Oracle y variables de entorno.
2. Ejecutar:
   - `./mvnw spring-boot:run`
   - o `mvn spring-boot:run`

### Frontend
1. Instalar dependencias:
   - `npm install`
2. Ejecutar:
   - `ng serve -o`

---

## ⚙️ Variables de entorno (ejemplo)

Backend:
- `DB_URL=jdbc:oracle:thin:@localhost:1521/XEPDB1`
- `DB_USERNAME=...`
- `DB_PASSWORD=...`
- `JWT_SECRET=...`
- `JWT_EXP_MINUTES=60`

---

## 📚 Swagger / OpenAPI

Con el backend levantado:
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`


