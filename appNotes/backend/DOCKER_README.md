# AppNotes Backend - Docker Setup

Este proyecto utiliza Docker Compose para ejecutar MySQL y el backend de Spring Boot.

## Requisitos

- Docker
- Docker Compose

## Cómo ejecutar

### Opción 1: Con Docker Compose (Recomendado)

```bash
# Desde el directorio backend
docker-compose up -d
```

Esto iniciará:
- **MySQL**: en `localhost:3306`
- **Backend**: en `http://localhost:8080`

### Opción 2: Local (Sin Docker)

1. Asegúrate de que MySQL esté corriendo en `localhost:3306`
2. Copia las credenciales a `application.properties` si son diferentes
3. Ejecuta:

```bash
./mvnw spring-boot:run
```

## Configuración de MySQL

- **Host**: mysql (en Docker) / localhost (local)
- **Puerto**: 3306
- **Base de Datos**: appnotes_db
- **Usuario**: appnotes_user
- **Contraseña**: appnotes_pass
- **Root Password**: rootpassword

## Detener los contenedores

```bash
docker-compose down
```

## Ver logs

```bash
# Todos los servicios
docker-compose logs -f

# Solo MySQL
docker-compose logs -f mysql

# Solo Backend
docker-compose logs -f backend
```

## Conectar desde el Frontend (React)

El frontend React debe hacer peticiones a:
```
http://localhost:8080
```

La configuración CORS ya está configurada para permitir solicitudes desde `http://localhost:5173` (puerto por defecto de Vite).
