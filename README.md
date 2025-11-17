# 📚 Sistema de Gestión de Reservas - Bosco MDV

![Java](https://img.shields.io/badge/Java-21-orange?style=flat&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen?style=flat&logo=spring)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=flat&logo=mysql)
![License](https://img.shields.io/badge/License-Educational-yellow?style=flat)

API REST desarrollada con Spring Boot para la gestión integral de reservas de aulas en un centro educativo. Incluye autenticación JWT, control de roles (ADMIN/USER), gestión completa de aulas, tramos horarios y reservas con validaciones de negocio.

---

## 📋 Tabla de Contenidos

- [Características Principales](#-características-principales)
- [Tecnologías Utilizadas](#-tecnologías-utilizadas)
- [Requisitos Previos](#-requisitos-previos)
- [Instalación y Ejecución](#-instalación-y-ejecución)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Endpoints de la API](#-endpoints-de-la-api)
- [Configuración](#-configuración)
- [Ejemplos de Uso](#-ejemplos-de-uso)
- [Testing](#-testing)
- [Contribuir](#-contribuir)
- [Licencia](#-licencia)
- [Autores](#-autores)

---

## ✨ Características Principales

- 🔐 **Autenticación JWT** - Sistema de login/registro con tokens JWT seguros
- 👥 **Control de Roles** - Diferenciación entre usuarios (USER/ADMIN)
- 🏫 **Gestión de Aulas** - CRUD completo con filtros (capacidad, ordenadores)
- ⏰ **Tramos Horarios** - Definición de horarios lectivos por día de semana
- 📅 **Sistema de Reservas** - Reservas validadas con control de disponibilidad
- ✅ **Validaciones de Negocio** - Control de capacidad, solapamientos y coherencia
- 🚫 **Manejo Global de Errores** - Respuestas de error consistentes y claras
- 📊 **Base de Datos Relacional** - Modelo de datos normalizado con MySQL
- 🎨 **Interfaz Web Incluida** - SPA con Vanilla JavaScript para gestión completa

---

## 🛠 Tecnologías Utilizadas

### Backend
- **Java 21** - Lenguaje de programación
- **Spring Boot 3.5.6** - Framework principal
- **Spring Security** - Autenticación y autorización
- **Spring Data JPA** - ORM y acceso a datos
- **JWT (jjwt 0.12.6)** - Generación y validación de tokens
- **MySQL 8.0** - Base de datos relacional
- **Lombok** - Reducción de código boilerplate
- **Maven** - Gestión de dependencias y build
- **Hibernate Validator** - Validación de datos

### Frontend (Incluido)
- **Vanilla JavaScript** - SPA sin frameworks
- **CSS3** - Estilos modernos con gradientes
- **HTML5** - Estructura semántica

### DevOps
- **Docker & Docker Compose** - Contenedorización
- **phpMyAdmin** - Administración de base de datos

---

## 📦 Requisitos Previos

Asegúrate de tener instalado:

- **JDK 21** o superior ([Descargar](https://www.oracle.com/java/technologies/downloads/))
- **Maven 3.6+** ([Descargar](https://maven.apache.org/download.cgi))
- **MySQL 8.0** (o usar Docker Compose incluido)
- **Git** para clonar el repositorio

---

## 🚀 Instalación y Ejecución

### 1. Clonar el Repositorio

```bash
git clone https://github.com/tu-usuario/reservas-bosco-mdv.git
cd reservas-bosco-mdv
```

### 2. Configurar Base de Datos

**Opción A: Usar Docker Compose (Recomendado)**

```bash
docker-compose up -d
```

Esto iniciará:
- MySQL en `localhost:3306`
- phpMyAdmin en `http://localhost:8081`

**Opción B: MySQL Local**

Crea la base de datos manualmente:

```sql
CREATE DATABASE gestion_reservas CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

Luego actualiza `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/gestion_reservas
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password
```

### 3. Compilar el Proyecto

```bash
./mvnw clean install
```

O si usas Maven instalado globalmente:

```bash
mvn clean install
```

### 4. Ejecutar la Aplicación

```bash
./mvnw spring-boot:run
```

O ejecutar el JAR generado:

```bash
java -jar target/reservas-bosco-mdv-0.0.1-SNAPSHOT.jar
```

La aplicación estará disponible en:
- **API**: `http://localhost:8080`
- **Web UI**: `http://localhost:8080/index.html`
- **Swagger UI** (si configurado): `http://localhost:8080/swagger-ui.html`

---

## 📁 Estructura del Proyecto

```
reservas-bosco-mdv/
├── src/
│   ├── main/
│   │   ├── java/com/example/reservasBoscoMdv/
│   │   │   ├── config/
│   │   │   │   └── SecurityConfig.java          # Configuración Spring Security
│   │   │   ├── controllers/
│   │   │   │   ├── AuthController.java          # Login/Register
│   │   │   │   ├── AulaController.java          # Gestión de Aulas
│   │   │   │   ├── TramoHorarioController.java  # Gestión de Tramos
│   │   │   │   ├── ReservaController.java       # Gestión de Reservas
│   │   │   │   └── UsuarioController.java       # Gestión de Usuarios
│   │   │   ├── DTO/
│   │   │   │   ├── LoginRequest.java
│   │   │   │   ├── RegisterRequest.java
│   │   │   │   ├── aula/
│   │   │   │   ├── reserva/
│   │   │   │   └── tramoHorario/
│   │   │   ├── entities/
│   │   │   │   ├── Usuario.java                 # Entidad Usuario
│   │   │   │   ├── Aula.java                    # Entidad Aula
│   │   │   │   ├── TramoHorario.java            # Entidad TramoHorario
│   │   │   │   └── Reserva.java                 # Entidad Reserva
│   │   │   ├── enums/
│   │   │   │   ├── Roles.java                   # ADMIN, USER
│   │   │   │   ├── DiaSemana.java               # LUNES-VIERNES
│   │   │   │   ├── TipoTramo.java               # LECTIVO, RECREO, MEDIO_DIA
│   │   │   │   └── ErrorType.java               # Tipos de errores
│   │   │   ├── errors/
│   │   │   │   ├── BusinessException.java
│   │   │   │   ├── ErrorResponse.java
│   │   │   │   └── GlobalExceptionHandler.java  # Manejo global de errores
│   │   │   ├── repositories/
│   │   │   │   ├── IUsuarioRepository.java
│   │   │   │   ├── IAulaRepository.java
│   │   │   │   ├── ITramoHorarioRepository.java
│   │   │   │   └── IReservaRepository.java
│   │   │   ├── services/
│   │   │   │   ├── AuthService.java
│   │   │   │   ├── JwtService.java
│   │   │   │   ├── CustomUserDetailsService.java
│   │   │   │   ├── AulaService.java
│   │   │   │   ├── TramoHorarioService.java
│   │   │   │   ├── ReservaService.java
│   │   │   │   └── UsuarioService.java
│   │   │   └── ReservasBoscoMdv.java            # Clase principal
│   │   └── resources/
│   │       ├── application.properties            # Configuración de la app
│   │       └── static/                           # Frontend web
│   │           ├── index.html
│   │           ├── assets/styles.css
│   │           └── js/
│   │               ├── main.js
│   │               ├── api.js
│   │               ├── router.js
│   │               └── views/
│   └── test/
│       └── java/com/example/reservasBoscoMdv/
│           └── SegundoProyectoSpringMySqlApplicationTests.java
├── docker-compose.yml
├── pom.xml
├── .gitignore
└── README.md
```

---

## 🔌 Endpoints de la API

### 🔓 Autenticación (`/auth`)

| Método | Endpoint | Descripción | Autenticación | Request Body |
|--------|----------|-------------|---------------|--------------|
| POST | `/auth/register` | Registrar nuevo usuario | No | `RegisterRequest` |
| POST | `/auth/login` | Iniciar sesión | No | `LoginRequest` |

**Ejemplo - Login:**

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@bosco.com",
    "password": "admin123"
  }'
```

**Respuesta:**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Ejemplo - Registro:**

```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "nuevo@bosco.com",
    "password": "password123",
    "nombre": "Juan",
    "apellidos": "Pérez García"
  }'
```

---

### 🏫 Aulas (`/aula`)

| Método | Endpoint | Descripción | Rol Requerido |
|--------|----------|-------------|---------------|
| POST | `/aula/insert` | Crear nueva aula | ADMIN |
| GET | `/aula/list` | Listar todas las aulas | USER |
| GET | `/aula/{id}` | Obtener aula por ID | USER |
| GET | `/aula/list/{nombre}` | Buscar aulas por nombre | USER |
| GET | `/aula/list/ordenadores` | Listar aulas con ordenadores | USER |
| GET | `/aula/list/no-ordenadores` | Listar aulas sin ordenadores | USER |
| GET | `/aula/list/capacidad/{capacidad}` | Aulas con capacidad mayor | USER |
| GET | `/aula/with-reservas/{id}` | Aula con sus reservas | USER |
| PUT | `/aula/update/{id}` | Actualizar aula | ADMIN |
| DELETE | `/aula/delete/{id}` | Eliminar aula | ADMIN |

**Ejemplo - Crear Aula:**

```bash
curl -X POST http://localhost:8080/aula/insert \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Aula A1",
    "capacidad": 30,
    "esAulaOrdenador": true,
    "numOrdenadores": 25
  }'
```

**Respuesta:**

```json
{
  "id": 1,
  "nombre": "Aula A1",
  "capacidad": 30,
  "esAulaOrdenador": true,
  "numOrdenadores": 25
}
```

---

### ⏰ Tramos Horarios (`/tramo-horario`)

| Método | Endpoint | Descripción | Rol Requerido |
|--------|----------|-------------|---------------|
| POST | `/tramo-horario/insert` | Crear tramo horario | ADMIN |
| GET | `/tramo-horario/list` | Listar todos los tramos | USER |
| GET | `/tramo-horario/{id}` | Obtener tramo por ID | USER |
| PUT | `/tramo-horario/update/{id}` | Actualizar tramo | ADMIN |
| DELETE | `/tramo-horario/delete/{id}` | Eliminar tramo | ADMIN |

**Ejemplo - Crear Tramo:**

```bash
curl -X POST http://localhost:8080/tramo-horario/insert \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "diaSemana": "LUNES",
    "horaInicio": "08:00:00",
    "horaFin": "09:00:00",
    "tipoTramo": "LECTIVO",
    "aulaId": null
  }'
```

**Enums disponibles:**
- **DiaSemana**: `LUNES`, `MARTES`, `MIERCOLES`, `JUEVES`, `VIERNES`
- **TipoTramo**: `RECREO`, `LECTIVO`, `MEDIO_DIA`

---

### 📅 Reservas (`/reserva`)

| Método | Endpoint | Descripción | Rol Requerido |
|--------|----------|-------------|---------------|
| POST | `/reserva/insert` | Crear nueva reserva | USER |
| GET | `/reserva/list` | Listar todas las reservas | USER |
| GET | `/reserva/list-usuario/{usuarioId}` | Reservas de un usuario | USER |
| GET | `/reserva/{id}` | Obtener reserva por ID | USER |
| PUT | `/reserva/update/{id}` | Actualizar reserva | USER |
| DELETE | `/reserva/delete/{id}` | Eliminar reserva | USER |

**Ejemplo - Crear Reserva:**

```bash
curl -X POST http://localhost:8080/reserva/insert \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "motivo": "Clase de programación",
    "numAsistentes": 25,
    "fechaReserva": "2025-11-20",
    "aulaId": 1,
    "tramoId": 1,
    "usuarioId": 1
  }'
```

**Respuesta:**

```json
{
  "id": 1,
  "motivo": "Clase de programación",
  "numAsistentes": 25,
  "fechaCreacion": "2025-11-16",
  "fechaReserva": "2025-11-20",
  "aula": {
    "id": 1,
    "nombre": "Aula A1",
    "capacidad": 30,
    "esAulaOrdenador": true,
    "numOrdenadores": 25
  },
  "tramo": {
    "id": 1,
    "diaSemana": "LUNES",
    "horaInicio": "08:00:00",
    "horaFin": "09:00:00",
    "tipoTramo": "LECTIVO"
  },
  "usuario": {
    "id": 1,
    "nombre": "Juan Pérez",
    "email": "juan@bosco.com"
  }
}
```

---

### 👥 Usuarios (`/usuario`)

| Método | Endpoint | Descripción | Rol Requerido |
|--------|----------|-------------|---------------|
| GET | `/usuario/list` | Listar todos los usuarios | ADMIN |
| GET | `/usuario/list-name/{nombre}` | Buscar por nombre | ADMIN |
| GET | `/usuario/list-email/{email}` | Buscar por email | ADMIN |
| GET | `/usuario/{id}` | Obtener usuario por ID | USER |
| PUT | `/usuario/update/{id}` | Actualizar usuario | USER |
| DELETE | `/usuario/delete/{id}` | Eliminar usuario | ADMIN |

---

## ⚙️ Configuración

### Variables de Entorno

Puedes usar variables de entorno en lugar de `application.properties`:

```bash
export SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3307/gestion-reservas
export SPRING_DATASOURCE_USERNAME=root
export SPRING_DATASOURCE_PASSWORD=root
```

### application.properties

Archivo de configuración principal ubicado en `src/main/resources/application.properties`:

```properties
# Nombre de la aplicación
spring.application.name=reservas-bosco-mdv

# Configuración de MySQL
spring.datasource.url=jdbc:mysql://127.0.0.1:3307/gestion-reservas
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Configuración JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Puerto del servidor (opcional)
server.port=8080
```

### Configuración de Seguridad

- **JWT Secret Key**: Se genera automáticamente en `JwtService.java` usando una clave segura de 256 bits
- **Expiración del Token**: 24 horas (configurable en `JwtService.java`)
- **Cifrado de Contraseñas**: BCrypt con 10 rondas

---

## 💡 Ejemplos de Uso

### Flujo Completo de Uso

#### 1. Registrar un Usuario

```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "profesor@bosco.com",
    "password": "profe123",
    "nombre": "María",
    "apellidos": "López Sánchez"
  }'
```

#### 2. Iniciar Sesión

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "profesor@bosco.com",
    "password": "profe123"
  }'
```

Guarda el token recibido:

```bash
TOKEN="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
```

#### 3. Listar Aulas Disponibles

```bash
curl -X GET http://localhost:8080/aula/list \
  -H "Authorization: Bearer $TOKEN"
```

#### 4. Crear una Reserva

```bash
curl -X POST http://localhost:8080/reserva/insert \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "motivo": "Examen final de matemáticas",
    "numAsistentes": 28,
    "fechaReserva": "2025-12-10",
    "aulaId": 2,
    "tramoId": 3,
    "usuarioId": 1
  }'
```

---

## 🧪 Testing

### Ejecutar Tests Unitarios

```bash
./mvnw test
```

### Ejecutar Tests con Cobertura

```bash
./mvnw test jacoco:report
```

El reporte de cobertura estará en: `target/site/jacoco/index.html`

### Tests Disponibles

Actualmente incluye:
- Test de carga de contexto de Spring Boot
- (Puedes agregar más tests unitarios e integración)

---

## 🤝 Contribuir

¡Las contribuciones son bienvenidas! Sigue estos pasos:

1. **Fork** el proyecto
2. Crea una **rama** para tu feature:
   ```bash
   git checkout -b feature/nueva-funcionalidad
   ```
3. **Commit** tus cambios:
   ```bash
   git commit -m "feat: agregar nueva funcionalidad"
   ```
4. **Push** a la rama:
   ```bash
   git push origin feature/nueva-funcionalidad
   ```
5. Abre un **Pull Request**

### Convenciones de Commits

Usamos [Conventional Commits](https://www.conventionalcommits.org/):

- `feat:` Nueva funcionalidad
- `fix:` Corrección de bugs
- `docs:` Documentación
- `style:` Formato de código
- `refactor:` Refactorización
- `test:` Tests
- `chore:` Tareas de mantenimiento

---

## 📄 Licencia

Este proyecto está bajo licencia **privada para uso educativo** en Bosco MDV.

---

## 👨‍💻 Autores

**Equipo de Desarrollo - Bosco MDV**

- Proyecto educativo desarrollado para la gestión de reservas de aulas
- Contacto: [info@boscomdv.com](mailto:info@boscomdv.com)

---

## 📞 Soporte

Para preguntas o reportar problemas:

- 🐛 [Crear un Issue](https://github.com/tu-usuario/reservas-bosco-mdv/issues)
- 💬 [Discusiones](https://github.com/tu-usuario/reservas-bosco-mdv/discussions)
- 📧 Email: soporte@boscomdv.com

---

## 🔗 Enlaces Útiles

- [Documentación de Spring Boot](https://spring.io/projects/spring-boot)
- [Documentación de Spring Security](https://spring.io/projects/spring-security)
- [JWT.io](https://jwt.io/) - Decodificador de tokens JWT
- [MySQL Documentation](https://dev.mysql.com/doc/)

---

**Última actualización**: Noviembre 2025 | **Versión**: 0.0.1-SNAPSHOT

---

⭐ Si este proyecto te ha sido útil, considera darle una estrella en GitHub!
