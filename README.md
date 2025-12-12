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

## 🛠️ Tecnologías Utilizadas

### Backend (Spring Boot)
- **Java 17** - Lenguaje principal
- **Spring Boot 3.x** - Framework base
- **Spring Data JPA** - Persistencia de datos
- **Spring Security + JWT** - Autenticación y autorización
- **Spring Cloud Gateway** - API Gateway
- **MySQL 8.0** - Base de datos
- **Docker & Docker Compose** - Contenedores y orquestación

### Frontend
- **React 18** - Biblioteca UI
- **TypeScript** - Tipado estático
- **Axios** - Cliente HTTP
- **React Router** - Navegación
- **Bootstrap 5** - Estilos y componentes
- **React Query** - Gestión de estado del servidor

### Herramientas de Desarrollo
- **Git & GitHub** - Control de versiones
- **Postman** - Pruebas de API
- **Swagger/OpenAPI** - Documentación de APIs
- **Maven** - Gestión de dependencias

## 📂 Estructura del Proyecto
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

## 🚀 Instalación y Configuración

### Prerrequisitos
- Java JDK 17 o superior
- Docker y Docker Compose
- Node.js 18+ y npm
- Git

### Pasos de Instalación

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/Elconter420/-LibroHub.git
   cd -LibroHub
   ```

2. **Construir y ejecutar con Docker Compose**
   ```bash
   docker-compose up --build
   ```

3. **Acceder a la aplicación**
   - Frontend: http://localhost:3000
   - API Gateway: http://localhost:8080
   - Swagger UI: http://localhost:8080/swagger-ui.html

4. **Credenciales de prueba**
   ```
   Estudiante:
   - Email: estudiante@universidad.edu
   - Password: estudiante123
   
   Bibliotecario:
   - Email: bibliotecario@universidad.edu
   - Password: bibliotecario123
   
   Administrador:
   - Email: admin@universidad.edu
   - Password: admin123
   ```

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
