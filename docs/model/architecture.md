# Arquitectura Backend – coco

## 1. Visión general

El backend de **coco** está diseñado como una aplicación empresarial moderna, priorizando **mantenibilidad, escalabilidad, seguridad y testabilidad**. La arquitectura sigue un enfoque **hexagonal ligero (Clean Architecture pragmática)**, evitando el sobre-diseño pero manteniendo límites claros entre responsabilidades.

El sistema está implementado en **Java 21 con Spring Boot**, expone una **API REST** y utiliza **PostgreSQL** como base de datos principal. La organización del código se realiza **por módulos funcionales**, no por capas técnicas globales, lo que permite que el proyecto crezca sin degradar su estructura.

---

## 2. Principios arquitectónicos

Los principios que guían la arquitectura son:

* **Separación de responsabilidades**: cada capa y módulo tiene un rol bien definido.
* **Independencia del dominio**: la lógica de negocio no depende de frameworks ni de la base de datos.
* **Contratos explícitos**: comunicación entre capas mediante DTOs y puertos (interfaces).
* **Escalabilidad progresiva**: el sistema permite añadir nuevas funcionalidades sin reestructurar lo existente.
* **Arquitectura orientada a equipos reales**: estructura reconocible en entornos profesionales.

---

## 3. Enfoque arquitectónico

La arquitectura adopta un modelo **Hexagonal / Clean Architecture ligera**, con las siguientes capas conceptuales:

* **API (Web Layer)**: controladores REST y DTOs de entrada/salida.
* **Application Layer**: casos de uso que orquestan la lógica de negocio.
* **Domain Layer**: modelo de dominio, reglas e invariantes.
* **Infrastructure Layer**: persistencia, seguridad y adaptadores técnicos.

El flujo general de una petición es:

```
HTTP Request
  → Controller (API)
    → Use Case (Application)
      → Repository Port (Interface)
        → Repository Adapter (JPA)
          → Database
```

Los errores, la seguridad y el logging actúan como **capas transversales**.

---

## 4. Organización del proyecto

La estructura de paquetes sigue una organización por dominio funcional:

```
com.coco.backend
├── config
├── security
├── common
└── modules
    ├── user
    ├── project
    ├── task
    ├── comment          (futuras mejoras)
    ├── attachment       (futuras mejoras)
    ├── activity         (futuras mejoras)
    ├── notification     (futuras mejoras)
    └── tag              (futuras mejoras)
```

Los módulos marcados como *futuras mejoras* no forman parte del MVP inicial, pero la arquitectura está preparada para integrarlos sin refactorizaciones estructurales.

---

## 5. Módulos principales (MVP)

### 5.1. Módulo `user`

Responsable de la **identidad y autenticación** del sistema.

**Responsabilidades:**

* Autenticación (login y emisión de JWT).
* Gestión del perfil del usuario.
* Búsqueda y administración básica de usuarios.
* Gestión de roles globales (USER / ADMIN).

**Subcapas internas:**

* `api`: AuthController, UserController, DTOs.
* `application`: casos de uso de autenticación y gestión de usuario.
* `domain`: entidad User y reglas asociadas.
* `infra`: persistencia JPA y adaptadores de seguridad.

---

### 5.2. Módulo `project`

Representa el **contexto principal de trabajo**. Un proyecto agrupa tareas y usuarios.

**Responsabilidades:**

* Creación, edición y archivado de proyectos.
* Gestión de miembros por proyecto.
* Asignación de roles a nivel de proyecto (OWNER, MANAGER, MEMBER, VIEWER).

**Decisión clave:**
Los permisos por proyecto se modelan mediante una entidad intermedia (*Membership*), permitiendo relaciones muchos-a-muchos entre usuarios y proyectos.

---

### 5.3. Módulo `task`

Es el **núcleo funcional** de la aplicación.

**Responsabilidades:**

* Creación y edición de tareas.
* Asignación de tareas a usuarios.
* Gestión de estados (workflow simple).
* Priorización, fechas límite y borrado lógico.

**Diseño relevante:**

* Los cambios de estado y movimientos de tareas se modelan como casos de uso independientes.
* Las reglas de negocio se concentran en políticas de dominio.

---

## 6. Módulos planificados como futuras mejoras

Estos módulos no forman parte del MVP, pero la arquitectura está preparada para incorporarlos:

### 6.1. `comment`

* Comentarios asociados a tareas.
* Edición y borrado con control de permisos.

### 6.2. `attachment`

* Gestión de archivos adjuntos.
* Persistencia de metadatos en base de datos.
* Soporte futuro para almacenamiento externo (S3 / MinIO).

### 6.3. `activity`

* Registro de acciones relevantes del sistema.
* Auditoría de cambios (tareas, proyectos, comentarios).

### 6.4. `notification`

* Notificaciones internas para usuarios.
* Estados leído / no leído.

### 6.5. `tag`

* Etiquetas reutilizables.
* Clasificación y filtrado de tareas.

---

## 7. Capas transversales

### 7.1. Seguridad

* Autenticación mediante JWT.
* Filtros de seguridad centralizados.
* Control de acceso por roles globales y por proyecto.

### 7.2. Gestión de errores

* Manejo global de excepciones.
* Respuestas de error consistentes.
* Códigos y mensajes claros para frontend.

### 7.3. Validación

* Validaciones declarativas en DTOs.
* Reglas de negocio protegidas en el dominio.

---

## 8. Beneficios de esta arquitectura

* Facilita el crecimiento del proyecto sin deuda técnica.
* Refleja prácticas reales de entornos empresariales.
* Mejora la testabilidad (unitaria e integración).
* Permite una clara separación entre API, negocio y persistencia.
* Es fácilmente explicable y defendible en un portfolio profesional.

