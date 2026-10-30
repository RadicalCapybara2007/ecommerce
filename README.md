# E-Commerce Gateway & Dashboard - Composite API

Aplicación desarrollada con Java 21 y Spring Boot que implementa el patrón arquitectónico **Composite API / Aggregator**. Este proyecto resuelve el problema de "Chatty I/O" en clientes web/móviles al orquestar peticiones concurrentes (mediante `CompletableFuture`) a microservicios internos (Catálogo e Inventario) y a una API externa de divisas (Frankfurter), entregando un payload JSON consolidado en una sola petición.

## Requisitos

- Java 21
- PostgreSQL
- Maven (opcional; el repositorio incluye Maven Wrapper)

## Configurar PostgreSQL

Inicia PostgreSQL y crea un usuario y una base de datos. Por ejemplo, desde `psql` como un usuario con permisos para crear bases y usuarios:

```sql
CREATE USER ecommerce_user WITH PASSWORD 'cambia_esta_clave';
CREATE DATABASE ecommerce OWNER ecommerce_user;