# 📚 Sistema de Gestión de Reservas - Bosco MDV

API REST desarrollada con Spring Boot para la gestión de reservas de aulas en un centro educativo. Incluye autenticación JWT, control de roles y gestión completa de aulas, tramos horarios y reservas.

## 🚀 Tecnologías

- **Java 21**
- **Spring Boot 3.5.6**
- **Spring Security** (JWT Authentication)
- **Spring Data JPA**
- **MySQL 8.0**
- **Lombok**
- **Maven**
- **Docker & Docker Compose**

## 📋 Requisitos Previos

- JDK 21 o superior
- Maven 3.6+
- Docker y Docker Compose (opcional)
- MySQL 8.0 (si no usas Docker)

## ⚙️ Configuración

### Base de Datos

La aplicación usa MySQL. Puedes usar Docker Compose o una instalación local.

**Con Docker Compose:**

```bash
docker-compose up -d
```

**Configuración Manual:**

Crea una base de datos llamada `gestion-reservas` y actualiza las credenciales en `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://127.0.0.1:3307/gestion-reservas
spring.datasource.username=root
spring.datasource.password=root
```

### Ejecución

```bash
# Compilar el proyecto
./mvnw clean install

# Ejecutar la aplicación
./mvnw spring-boot:run
```

La API estará disponible en `http://localhost:8080`

## 🔐 Autenticación y Autorización

La API utiliza JWT (JSON Web Tokens) para la autenticación. Los usuarios pueden tener los siguientes roles:

- **ROLE_USER**: Usuario estándar
- **ROLE_ADMIN**: Administrador del sistema

### Flujo de Autenticación

1. **Registrarse**: `POST /auth/register`
2. **Login**: `POST /auth/login` (devuelve un token JWT)
3. **Usar el token**: Incluir en el header `Authorization: Bearer {token}` en las peticiones protegidas

## 📡 Endpoints

### 🔓 Autenticación (`/auth`)

| Método | Endpoint | Descripción | Acceso | Request Body |
|--------|----------|-------------|--------|--------------|
| POST | `/auth/register` | Registrar nuevo usuario | Público | `RegisterRequest` |
| POST | `/auth/login` | Iniciar sesión | Público | `LoginRequest` |

**RegisterRequest:**
```json
{
  "email": "usuario@example.com",
  "password": "password123",
  "nombre": "Juan",
  "apellidos": "Pérez García"
}
```

**LoginRequest:**
```json
{
  "email": "usuario@example.com",
  "password": "password123"
}
```

**Response (Login):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

---

### 👥 Usuarios (`/usuario`)

| Método | Endpoint | Descripción | Acceso |
|--------|----------|-------------|--------|
| GET | `/usuario/list` | Listar todos los usuarios | Autenticado |
| GET | `/usuario/list-name?nombre={nombre}` | Buscar usuarios por nombre | Autenticado |
| GET | `/usuario/list-email?email={email}` | Buscar usuario por email | Autenticado |
| GET | `/usuario/{id}` | Obtener usuario por ID | Autenticado |
| PUT | `/usuario/update/{id}` | Actualizar usuario | Autenticado |
| DELETE | `/usuario/delete/{id}` | Eliminar usuario | Autenticado |

**UsuarioResponse:**
```json
{
  "id": 1,
  "nombre": "Juan Pérez",
  "email": "juan@example.com"
}
```

---

### 🏫 Aulas (`/aula`)

| Método | Endpoint | Descripción | Acceso |
|--------|----------|-------------|--------|
| POST | `/aula/insert` | Crear nueva aula | Autenticado |
| GET | `/aula/list` | Listar todas las aulas | Autenticado |
| GET | `/aula/{id}` | Obtener aula por ID | Autenticado |
| GET | `/aula/list/{nombre}` | Buscar aulas por nombre | Autenticado |
| GET | `/aula/list/ordenadores` | Listar aulas con ordenadores | Autenticado |
| GET | `/aula/list/no-ordenadores` | Listar aulas sin ordenadores | Autenticado |
| PUT | `/aula/update/{id}` | Actualizar aula | Autenticado |
| DELETE | `/aula/delete/{id}` | Eliminar aula | Autenticado |

**AulaRequest:**
```json
{
  "nombre": "Aula A1",
  "capacidad": 30,
  "esAulaOrdenador": true,
  "numOrdenadores": 25
}
```

**AulaResponse:**
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

| Método | Endpoint | Descripción | Acceso |
|--------|----------|-------------|--------|
| POST | `/tramo-horario/insert` | Crear tramo horario | Público |
| GET | `/tramo-horario/list` | Listar todos los tramos | Público |
| GET | `/tramo-horario/{id}` | Obtener tramo por ID | Público |
| PUT | `/tramo-horario/update/{id}` | Actualizar tramo horario | Público |
| DELETE | `/tramo-horario/delete/{id}` | Eliminar tramo horario | Público |

**TramoHorarioRequest:**
```json
{
  "diaSemana": "LUNES",
  "horaInicio": "08:00:00",
  "horaFin": "09:00:00",
  "tipoTramo": "LECTIVO",
  "aulaId": 1
}
```

**Enums disponibles:**
- **DiaSemana**: `LUNES`, `MARTES`, `MIERCOLES`, `JUEVES`, `VIERNES`
- **TipoTramo**: `RECREO`, `LECTIVO`, `MEDIO_DIA`

**TramoHorarioResponse:**
```json
{
  "id": 1,
  "diaSemana": "LUNES",
  "horaInicio": "08:00:00",
  "horaFin": "09:00:00",
  "tipoTramo": "LECTIVO"
}
```

---

### 📅 Reservas (`/reserva`)

| Método | Endpoint | Descripción | Acceso |
|--------|----------|-------------|--------|
| POST | `/reserva/insert` | Crear nueva reserva | Autenticado |

**ReservaRequest:**
```json
{
  "motivo": "Clase de programación",
  "numAsistentes": 25,
  "aulaId": 1,
  "tramoId": 1,
  "usuarioId": 1
}
```

**ReservaResponse:**
```json
{
  "id": 1,
  "motivo": "Clase de programación",
  "numAsistentes": 25,
  "fechaCreacion": "2024-11-15",
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
    "email": "juan@example.com"
  }
}
```

---

## 📊 Modelo de Datos

### Entidades

#### Usuario
- `id`: Long (PK)
- `nombre`: String
- `apellidos`: String
- `email`: String (Unique)
- `password`: String (Encriptada)
- `roles`: String (Separados por comas)

#### Aula
- `id`: Long (PK)
- `nombre`: String (Unique)
- `capacidad`: Integer
- `esAulaOrdenador`: Boolean
- `numOrdenadores`: Integer

#### TramoHorario
- `id`: Long (PK)
- `diaSemana`: DiaSemana (Enum)
- `horaInicio`: LocalTime
- `horaFin`: LocalTime
- `tipoTramo`: TipoTramo (Enum)
- `aulaId`: Long (FK)

#### Reserva
- `id`: Long (PK)
- `motivo`: String
- `numAsistentes`: Integer
- `fechaCreacion`: LocalDate (Auto-generada)
- `aulaId`: Long (FK)
- `tramoHorarioId`: Long (FK)
- `usuarioId`: Long (FK)

### Relaciones

- **Usuario** 1:N **Reserva**
- **Aula** 1:N **Reserva**
- **Aula** 1:N **TramoHorario**
- **TramoHorario** 1:N **Reserva**

## 🛡️ Seguridad

### Endpoints Públicos
- `/auth/register`
- `/auth/login`
- `/tramo-horario/**`
- `/public/**`

### Endpoints Protegidos
- Todos los demás endpoints requieren autenticación con JWT
- Los endpoints `/admin/**` requieren el rol `ROLE_ADMIN`

### Configuración de Seguridad

La aplicación usa Spring Security con las siguientes características:

- **Sin sesiones** (Stateless): Cada petición debe incluir el token JWT
- **CSRF deshabilitado**: No necesario para APIs REST
- **Contraseñas encriptadas**: BCrypt con 10 rondas
- **Tokens JWT**: Expiración de 24 horas

## 🔧 Manejo de Errores

La API incluye un manejador global de excepciones que devuelve respuestas consistentes:

**ErrorResponse:**
```json
{
  "error": "TRAMO_DUPLICADO",
  "message": "El tramo horario ya existe para el día especificado.",
  "detail": "No se pueden crear tramos horarios duplicados para el mismo día."
}
```

### Códigos de Estado HTTP

- `200 OK`: Operación exitosa
- `201 CREATED`: Recurso creado exitosamente
- `204 NO CONTENT`: Operación exitosa sin contenido (DELETE)
- `400 BAD REQUEST`: Error en la petición
- `401 UNAUTHORIZED`: No autenticado o token inválido
- `403 FORBIDDEN`: Sin permisos para el recurso
- `404 NOT FOUND`: Recurso no encontrado
- `500 INTERNAL SERVER ERROR`: Error del servidor

## 📝 Validaciones

### RegisterRequest
- `email`: Obligatorio, formato válido de email
- `password`: Obligatorio, mínimo 3 caracteres
- `nombre`: Opcional
- `apellidos`: Opcional

### LoginRequest
- `email`: Obligatorio, formato válido de email
- `password`: Obligatorio

### Aula
- `nombre`: Obligatorio, único
- `capacidad`: Obligatorio
- `numOrdenadores`: Mínimo 0

### Reserva
- `motivo`: Obligatorio
- Validación de existencia de aula, tramo y usuario

## 🧪 Testing

```bash
# Ejecutar tests
./mvnw test

# Ejecutar tests con cobertura
./mvnw test jacoco:report
```

## 📦 Despliegue

### Docker

```bash
# Construir imagen
docker build -t reservas-bosco-mdv .

# Ejecutar contenedor
docker run -p 8080:8080 reservas-bosco-mdv
```

### JAR

```bash
# Generar JAR
./mvnw clean package

# Ejecutar JAR
java -jar target/reservas-bosco-mdv-0.0.1-SNAPSHOT.jar
```

## 📞 Contacto y Soporte

Para preguntas o reportar problemas, por favor crea un issue en el repositorio del proyecto.

## 📄 Licencia

Este proyecto está bajo licencia privada para uso educativo en Bosco MDV.

---

**Última actualización**: Noviembre 2024  
**Versión**: 0.0.1-SNAPSHOT
