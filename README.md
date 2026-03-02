# Inventario App

Sistema de gestión de inventario para pequeños comercios, desarrollado como
proyecto de aprendizaje con enfoque en buenas prácticas de arquitectura y
desarrollo profesional de software.

---

## Tecnologías

- Java 21
- Spring Boot 3.4.1
- PostgreSQL
- Maven (proyecto multi-módulo)
- Lombok
- Git + GitFlow

---

## Arquitectura

El proyecto implementa **Arquitectura Hexagonal (Ports & Adapters)**
organizada en un proyecto **multi-módulo Maven** con tres módulos independientes:
```
inventario-app/
├── domain/          → Lógica de negocio pura, sin dependencias externas
├── application/     → Casos de uso y DTOs
└── infrastructure/  → Spring Boot, JPA, PostgreSQL, Controllers REST
```

### Regla de dependencias

Las dependencias apuntan siempre hacia adentro:
```
infrastructure → domain
application    → domain
domain         → nadie
```

El dominio es Java puro. No conoce Spring, no conoce JPA,
no conoce ningún framework externo. Esto permite que la lógica
de negocio sea independiente de la infraestructura tecnológica.

### ¿Por qué esta arquitectura?

- El dominio puede testearse sin levantar Spring ni conectarse a una base de datos
- Cambiar PostgreSQL por otro motor de base de datos no afecta la lógica de negocio
- La separación de responsabilidades hace el código más mantenible y escalable

---

## Flujo de una petición

Ejemplo: `POST /api/productos` para agregar un nuevo producto.
```
HTTP Request (Postman / Frontend)
        ↓
ProductoController          [infrastructure/web]
  - Valida el DTO (@NotBlank, @NotNull)
  - Convierte DTO → modelo de dominio
        ↓
ProductoUseCase             [domain/port/in - interfaz]
        ↓
ProductoService             [domain/service]
  - Verifica que el nombre no esté duplicado
  - Verifica que el precio no sea negativo
  - Asigna fechas de creación
        ↓
ProductoRepository          [domain/port/out - interfaz]
        ↓
ProductoRepositoryAdapter   [infrastructure/persistence/adapter]
  - Convierte modelo de dominio → entidad JPA
        ↓
ProductoJpaRepository       [infrastructure/persistence/repository]
        ↓
PostgreSQL
        ↓ (respuesta sube por las mismas capas)
HTTP Response 201 Created
```

---

## Decisiones técnicas

### Stock calculado vs stock guardado

El stock actual de cada producto **no se guarda** como un número en la
tabla de productos. Se calcula sumando todas las entradas y restando
todas las salidas del historial de movimientos.

**¿Por qué?**
Si el stock fuera un número guardado, una falla a mitad de una
operación podría dejarlo inconsistente sin forma de recuperarlo.
Con movimientos como fuente de verdad, siempre es posible
recalcular, auditar y corregir el historial.

### Multi-módulo Maven

Cada capa es un módulo Maven independiente con su propio `pom.xml`.
Esto hace que las reglas de arquitectura sean una **restricción técnica real**:
es físicamente imposible que el módulo `domain` importe clases de
`infrastructure` porque no lo tiene como dependencia en su `pom.xml`.

### Sin @Service en el dominio

El `ProductoService` no usa `@Service` de Spring. Se instancia
manualmente en `BeanConfiguration` que vive en infrastructure.
Así el dominio permanece como Java puro sin ninguna dependencia
de frameworks externos.

---

## Modelo de datos
```
PRODUCTO
├── id            (PK, generado automáticamente)
├── nombre        (único, obligatorio)
├── categoria     (obligatorio)
├── precio        (obligatorio, no negativo)
├── stock_minimo  (opcional, para alertas)
├── creado_en
└── actualizado_en

MOVIMIENTO (próximo sprint)
├── id
├── tipo          (ENTRADA / SALIDA)
├── cantidad
├── motivo        (opcional)
├── fecha
├── creado_en
└── producto_id   (FK → PRODUCTO)
```

---

## Cómo correr el proyecto

### Requisitos

- Java 21
- Maven 3.8+
- PostgreSQL 12+

### Configuración

1. Clona el repositorio:
```bash
git clone https://github.com/AlainGZ/inventario-app.git
cd inventario-app
```

2. Crea la base de datos en PostgreSQL:
```sql
CREATE DATABASE inventario_db;
```

3. Configura las credenciales en:
```
infrastructure/src/main/resources/application.properties
```
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/inventario_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password
```

4. Compila el proyecto:
```bash
./mvnw clean install -DskipTests
```

5. Corre la aplicación:
```bash
cd infrastructure
../mvnw spring-boot:run
```

La aplicación queda disponible en `http://localhost:8080`

---

## Endpoints disponibles

### Productos

| Método | URL | Descripción |
|--------|-----|-------------|
| POST | `/api/productos` | Agregar un producto |
| GET | `/api/productos` | Listar todos los productos |

### Ejemplo de request
```json
POST /api/productos
{
    "nombre": "Arroz Diana",
    "categoria": "Granos",
    "precio": 3500.00,
    "stockMinimo": 10
}
```

### Ejemplo de response
```json
HTTP 201 Created
{
    "id": 1,
    "nombre": "Arroz Diana",
    "categoria": "Granos",
    "precio": 3500.00,
    "stockMinimo": 10,
    "creadoEn": "2026-02-25T17:10:01"
}
```

---

## Gestión del proyecto

Este proyecto se desarrolla siguiendo **Scrum** con sprints de una semana
y **GitFlow** para el manejo de ramas.

- Ramas principales: `main` (producción) y `develop` (integración)
- Cada Historia de Usuario se desarrolla en una rama `feature/hu-XX-nombre`

### Estado actual

**Sprint 1 en curso**

| HU | Descripción | Estado |
|----|-------------|--------|
| HU-01 | Login de administrador | 🔲 Pendiente |
| HU-02 | Logout | 🔲 Pendiente |
| HU-03 | Agregar producto | ✅ Terminado |
| HU-06 | Registrar entrada | 🔲 Pendiente |
| HU-07 | Registrar salida | 🔲 Pendiente |
| HU-09 | Ver inventario actual | ✅ Terminado |

---

## Autor

**Alain Gómez Zapata**  
[LinkedIn](https://www.linkedin.com/in/alaingz/) ·
[GitHub](https://github.com/AlainGZ)
