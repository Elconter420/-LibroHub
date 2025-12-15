# 📚 LibroHub - Sistema de Gestión de Biblioteca Universitaria

## 🎯 Descripción del Proyecto
**LibroHub** es una aplicación web basada en microservicios diseñada para modernizar y automatizar los procesos de gestión en bibliotecas universitarias. El sistema permite gestionar préstamos de libros, reservas de salas de estudio, control de inventario y administración de usuarios con diferentes roles, todo contenedorizado con Docker y desarrollado con Spring Boot.

## 👥 Integrantes del Equipo
- **Jaider Bermúdez**
- **Jhojan Bueno** 
- **Juan Contreras**

## 🏗️ Arquitectura del Sistema

### Diagrama de Arquitectura
```
┌─────────────────────────────────────────────────────────────┐
│                    Frontend (React)                          │
│                   http://localhost:3000                      │
└───────────────────────────┬─────────────────────────────────┘
                            │
┌───────────────────────────▼─────────────────────────────────┐
│                    API Gateway                               │
│              Spring Cloud Gateway                           │
│                   http://localhost:8080                      │
└─────┬──────────────┬──────────────┬──────────────┬──────────┘
      │              │              │              │
┌─────▼─────┐  ┌─────▼─────┐  ┌─────▼─────┐  ┌─────▼─────┐
│ User      │  │ Catalog   │  │ Loan      │  │Reservation│
│ Service   │  │ Service   │  │ Service   │  │ Service   │
│ 8081      │  │ 8082      │  │ 8083      │  │ 8084      │
└─────┬─────┘  └─────┬─────┘  └─────┬─────┘  └─────┬─────┘
      │              │              │              │
┌─────▼─────┐  ┌─────▼─────┐  ┌─────▼─────┐  ┌─────▼─────┐
│ MySQL     │  │ MySQL     │  │ MySQL     │  │ MySQL     │
│ users_db  │  │ catalog_db│  │ loans_db  │  │reserv_db  │
└───────────┘  └───────────┘  └───────────┘  └───────────┘
```

## 🚀 Objetivo de este README actualizado
Proveer una guía práctica y reproducible para crear el proyecto desde 0: scaffolding de microservicios, configuración mínima, Dockerfiles, docker-compose y checklist de tareas para que el equipo implemente el MVP siguiendo pasos claros.

## 1. MVP (alcance mínimo)
- Autenticación (registro/login) con JWT.
- Catálogo: CRUD básico de libros.
- Préstamos: crear préstamo y devolución.
- Reservas: reservar sala.
- Frontend: login, listado de libros, solicitar préstamo, reservar sala.
- Base de datos: MySQL para cada microservicio (pueden compartirse en fases iniciales).

## 2. Estructura recomendada (carpetas)
```
-Librohub/
├── microservices/
│   ├── api-gateway/           # API Gateway (Spring Cloud Gateway)
│   ├── user-service/          # Gestión de usuarios y autenticación
│   ├── catalog-service/       # Catálogo de libros y autores
│   ├── loan-service/          # Préstamos, devoluciones y multas
│   └── reservation-service/   # Reservas de salas de estudio
├── frontend/                  # Aplicación React
├── docker-compose.yml         # Orquestación de contenedores
├── database/
│   └── init-scripts/          # Scripts SQL iniciales
└── documentation/             # Documentación del proyecto
```

## 3. Comandos para generar scaffolding rápido

1) Generar microservicios backend con Spring Initializr (ejemplo, repetir para cada servicio: user-service, catalog-service, loan-service, reservation-service, api-gateway)
- Requisitos: Java 17, Maven
- Ejemplo usando curl (ajustar group/artifact/dependencies según servicio):
```bash
# Ejemplo: crear catalog-service
curl "https://start.spring.io/starter.zip?type=maven-project&language=java&bootVersion=3.1.0&baseDir=catalog-service&groupId=edu.univalle.librohub&artifactId=catalog-service&name=catalog-service&packageName=edu.univalle.librohub.catalog&dependencies=web,data-jpa,mysql,security,actuator" -o catalog-service.zip
unzip catalog-service.zip -d microservices/
```

2) Generar frontend con Vite + React + TypeScript (ejemplo)
```bash
npm create vite@latest frontend -- --template react-ts
cd frontend
npm install
```

## 4. Dockerfile y ejemplo de producción (plantillas)

- Dockerfile para Spring Boot (colocar en cada microservicio)
```dockerfile
# filepath: c:\Users\Jhojan\Downloads\LibroHub\-LibroHub\microservices\<service>\Dockerfile
FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=target/*.jar
WORKDIR /app
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
```

- Dockerfile para frontend (React)
```dockerfile
# filepath: c:\Users\Jhojan\Downloads\LibroHub\-LibroHub\frontend\Dockerfile
FROM node:18-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

FROM nginx:stable-alpine
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
```

## 5. docker-compose.yml de ejemplo (colocar en la raíz del repo)
- Este compose levanta 1 servicio de ejemplo + MySQL; replicar bloques para cada microservicio cambiando puertos y nombres.
```yaml
# filepath: c:\Users\Jhojan\Downloads\LibroHub\-LibroHub\docker-compose.yml
version: '3.8'
services:
  mysql-common:
    image: mysql:8.0
    container_name: librohub-mysql
    environment:
      MYSQL_ROOT_PASSWORD: rootpassword
    volumes:
      - db-data:/var/lib/mysql
      - ./database/init-scripts:/docker-entrypoint-initdb.d
    ports:
      - "3306:3306"

  api-gateway:
    build: ./microservices/api-gateway
    ports:
      - "8080:8080"
    depends_on:
      - mysql-common
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql-common:3306/gateway_db
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: rootpassword

  user-service:
    build: ./microservices/user-service
    ports:
      - "8081:8081"
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql-common:3306/users_db
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: rootpassword
    depends_on:
      - mysql-common

  catalog-service:
    build: ./microservices/catalog-service
    ports:
      - "8082:8082"
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql-common:3306/catalog_db
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: rootpassword
    depends_on:
      - mysql-common

  frontend:
    build: ./frontend
    ports:
      - "3000:80"
    depends_on:
      - api-gateway

volumes:
  db-data:
```

## 6. application.properties / application.yml mínimos (ejemplo para user-service)
```properties
# filepath: c:\Users\Jhojan\Downloads\LibroHub\-LibroHub\microservices\user-service\src\main\resources\application.properties
spring.datasource.url=jdbc:mysql://mysql-common:3306/users_db?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=rootpassword
spring.jpa.hibernate.ddl-auto=update
server.port=8081
jwt.secret=ReemplazaPorSecretoSeguro
jwt.expirationMs=3600000
```

## 7. Scripts SQL iniciales
- Colocar scripts en database/init-scripts/ para inicializar BD y usuarios de prueba.
```sql
-- filepath: c:\Users\Jhojan\Downloads\LibroHub\-LibroHub\database\init-scripts\init.sql
CREATE DATABASE IF NOT EXISTS users_db;
CREATE DATABASE IF NOT EXISTS catalog_db;
CREATE DATABASE IF NOT EXISTS loans_db;
CREATE DATABASE IF NOT EXISTS reserv_db;
-- Crear tablas mínimas en cada DB (ejemplos)
```

## 8. Checklist de implementación (pasos sugeridos por prioridad)
1. Crear repositorios locales con Spring Initializr para cada microservicio.
2. Implementar user-service: entidad User, repositorio, servicio de autenticación (JWT), endpoints /api/auth/*
3. Implementar catalog-service: entidad Book, repositorio, controladores CRUD.
4. Implementar loan-service: endpoints borrow/return básicos, integración simple con catalog-service por HTTP (usar RestTemplate o WebClient).
5. Implementar reservation-service: CRUD de reservas.
6. Crear API Gateway con rutas y balanceo hacia los servicios.
7. Construir frontend mínimo: login, listado de libros, botones para solicitar préstamo y reservar.
8. Dockerizar cada servicio y probar con docker-compose.
9. Añadir pruebas unitarias básicas (JUnit + Mockito).
10. Documentar endpoints con Swagger en cada servicio (springdoc-openapi).

## 9. Comandos útiles
- Construir y ejecutar docker-compose:
```bash
docker-compose up --build
```
- Construir un microservicio con Maven:
```bash
cd microservices/catalog-service
mvn clean package -DskipTests
```
- Logs:
```bash
docker-compose logs -f
```

## 10. Plantillas rápidas (boilerplate) y recomendaciones
- Seguridad: implementar filtro JWT en api-gateway o en cada servicio según preferencia. Para MVP, poner validación en user-service y exigir token en los otros servicios.
- Comunicación entre microservicios: usar REST sobre HTTP y mantener contratos simples (JSON). Para producción considerar API Gateway + circuit-breaker.
- Variables secretas: usar Docker secrets o variables de entorno (no comitear secretos).

## 11. Tareas recomendadas para los integrantes
- Jaider: implementar catalog-service (modelos, repos, controllers).
- Jhojan: API Gateway + docker-compose + CI.
- Juan: user-service + autenticación JWT + documentación Swagger.

## 12. Próximos pasos inmediatos (qué hacer ahora)
1. Ejecutar los comandos de "scaffolding" para crear los proyectos.
2. Añadir los Dockerfile mostrados en cada microservicio.
3. Copiar el docker-compose.yml a la raíz y ajustar rutas de build.
4. Crear folder database/init-scripts y añadir init.sql.
5. Implementar user-service básico (registro/login) y probar con Postman.
6. Iterar con catalog-service y frontend.

---

## 📡 Endpoints Principales de la API

### Autenticación
```
POST   /api/auth/login           # Iniciar sesión
POST   /api/auth/register        # Registrar nuevo usuario
GET    /api/auth/profile         # Obtener perfil del usuario
```

### Gestión de Libros (Catalog Service)
```
GET    /api/catalog/books        # Listar todos los libros
GET    /api/catalog/books/{id}   # Obtener libro por ID
POST   /api/catalog/books        # Crear nuevo libro (bibliotecario)
PUT    /api/catalog/books/{id}   # Actualizar libro
DELETE /api/catalog/books/{id}   # Eliminar libro
GET    /api/catalog/search       # Buscar libros por criterios
```

### Préstamos (Loan Service)
```
POST   /api/loans/borrow         # Solicitar préstamo de libro
POST   /api/loans/return/{id}    # Devolver libro
GET    /api/loans/user/{userId}  # Obtener préstamos del usuario
GET    /api/loans/overdue        # Listar préstamos atrasados (admin)
PUT    /api/loans/pay-fine/{id}  # Pagar multa
```

### Reservas (Reservation Service)
```
GET    /api/reservations/rooms   # Listar salas disponibles
POST   /api/reservations         # Crear reserva de sala
GET    /api/reservations/user    # Obtener reservas del usuario
DELETE /api/reservations/{id}    # Cancelar reserva
```

### Usuarios (User Service)
```
GET    /api/users                # Listar usuarios (admin)
GET    /api/users/{id}           # Obtener usuario por ID
PUT    /api/users/{id}           # Actualizar usuario
PUT    /api/users/{id}/status    # Cambiar estado de usuario
GET    /api/users/stats          # Estadísticas de usuarios
```

### Comandos Docker útiles
```bash
# Iniciar todos los servicios
docker-compose up

# Iniciar en segundo plano
docker-compose up -d

# Detener servicios
docker-compose down

# Ver logs
docker-compose logs -f

# Reconstruir imágenes
docker-compose build --no-cache
```

## 📋 Funcionalidades Implementadas

### Para Estudiantes
- 🔍 Búsqueda y consulta de catálogo de libros
- 📚 Solicitud de préstamos de libros
- 📅 Reserva de salas de estudio
- 👁️ Visualización de historial de préstamos
- 💳 Pago de multas en línea
- 🔔 Notificaciones de vencimientos

### Para Bibliotecarios
- 📊 Panel de control administrativo
- 📝 Registro de nuevos libros
- ✅ Aprobación/gestión de préstamos
- ⚠️ Gestión de multas y sanciones
- 📈 Reportes de uso de la biblioteca

### Para Administradores
- 👥 Gestión completa de usuarios
- ⚙️ Configuración del sistema
- 📊 Estadísticas detalladas
- 🔧 Mantenimiento de base de datos
- 📋 Auditoría de operaciones

## 🔐 Seguridad y Autenticación

### Roles y Permisos
- **ESTUDIANTE**: Préstamos, reservas, consulta
- **BIBLIOTECARIO**: + Gestión de libros, préstamos, multas
- **ADMIN**: + Gestión de usuarios, configuración, reportes

### JSON Web Tokens (JWT)
- Tokens de acceso con expiración (1 hora)
- Refresh tokens para renovación
- Validación de roles en cada endpoint
- Cifrado de contraseñas con BCrypt

## 🧪 Pruebas

### Pruebas Unitarias
```bash
# Ejecutar pruebas en cada microservicio
cd microservices/user-service
mvn test

cd ../catalog-service
mvn test
```

### Pruebas de Integración
```bash
# Ejecutar con Docker
docker-compose -f docker-compose.test.yml up --build --abort-on-container-exit
```

### Pruebas de API con Postman
- Colección de Postman incluida en `/documentation/postman`
- Variables de entorno preconfiguradas
- Ejemplos de requests para todos los endpoints

## 📈 Características Avanzadas

### 1. Sistema de Multas Automático
- Cálculo automático: $1.000 por día de retraso
- Suspensión automática de usuarios con multas pendientes
- Notificaciones por email de vencimientos

### 2. Gestión de Disponibilidad en Tiempo Real
- Actualización instantánea de stock de libros
- Bloqueo de reservas concurrentes para la misma sala
- Validación de conflictos de horarios

### 3. Sistema de Búsqueda Avanzada
- Búsqueda por título, autor, categoría, ISBN
- Filtros por disponibilidad, año, editorial
- Paginación y ordenamiento de resultados

---

## 🎓 Información Académica
**Universidad:** Universidad del Valle  
**Programa:** Tecnología en Desarrollo de Software  
**Materia:** Desarrollo de Software III  
**Docente:** Juan Pablo Pinillos Reina  
**Semestre:** 2025-2  

*Proyecto desarrollado con fines académicos demostrando habilidades en microservicios, Spring Boot, Docker y React.*

---

<div align="center">
  
**✨ Desarrollado por Jaider, Jhojan y Juan ✨**

[![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/)
[![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)](https://reactjs.org/)
[![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)
[![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)

</div>
