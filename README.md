# Ecommerce

Aplicación de ejemplo desarrollada con Java 21 y Spring Boot. Expone endpoints REST para consultar productos e inventario, y un endpoint compuesto que reúne esos datos y convierte el precio de USD a MXN usando la API de Frankfurter.

## Requisitos

- Java 21
- PostgreSQL
- Maven (opcional; el repositorio incluye Maven Wrapper)

## Configurar PostgreSQL

Inicia PostgreSQL y crea un usuario y una base de datos. Por ejemplo, desde `psql` como un usuario con permisos para crear bases y usuarios:

```sql
CREATE USER ecommerce_user WITH PASSWORD 'cambia_esta_clave';
CREATE DATABASE ecommerce OWNER ecommerce_user;
```

En la raíz del proyecto, copia `src/main/resources/application.properties.example` a `src/main/resources/application.properties` y actualiza la configuración para que coincida con PostgreSQL:

```properties
spring.application.name=ecommerce

spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce
spring.datasource.username=ecommerce_user
spring.datasource.password=cambia_esta_clave
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=true
```

Reemplaza la contraseña de ejemplo por la que configuraste. No publiques credenciales reales ni agregues `application.properties` con secretos al control de versiones.

Al iniciar la aplicación, Hibernate crea o actualiza las tablas `products` e `inventory` a partir de las entidades JPA. `ddl-auto=update` resulta práctico para desarrollo; para producción, utiliza migraciones de esquema controladas en lugar de depender de la actualización automática. `spring.jpa.show-sql=true` imprime las consultas SQL en la salida de la aplicación y puede desactivarse si no se necesita.

## Ejecutar la aplicación

Desde la raíz del proyecto:

```bash
./mvnw spring-boot:run
```

En Windows:

```bat
mvnw.cmd spring-boot:run
```

La aplicación inicia por defecto en `http://localhost:8080`. En el primer inicio, la clase `DataSeeder` inserta un producto y su inventario de ejemplo si todavía no existe el producto `P-101`:

- Producto: `Laptop Gamer XYZ`
- Precio: `1200.00` USD
- Inventario: `15` unidades, estado `IN_STOCK`

Puedes compilar y ejecutar las pruebas con:

```bash
./mvnw test
```

## API y ejemplos de uso

### Consultar un producto

```bash
curl http://localhost:8080/microservices/products/P-101
```

Respuesta de ejemplo:

```json
{
  "id": "P-101",
  "name": "Laptop Gamer XYZ",
  "description": "Laptop de alto rendimiento",
  "category": "Electrónica",
  "priceUsd": 1200.0
}
```

### Consultar el inventario

```bash
curl http://localhost:8080/microservices/inventory/P-101
```

Respuesta de ejemplo:

```json
{
  "productId": "P-101",
  "stockStatus": "IN_STOCK",
  "availableUnits": 15,
  "location": "Almacén Central Chihuahua",
  "estimatedDelivery": "2-3 días hábiles"
}
```

### Consultar el resumen de producto

```bash
curl http://localhost:8080/api/v1/product-summary/P-101
```

Este endpoint combina producto e inventario y obtiene el tipo de cambio USD/MXN de Frankfurter. El valor de `price_mxn` depende del tipo de cambio al momento de la consulta. La respuesta tiene esta estructura:

```json
{
  "product_id": "P-101",
  "title": "Laptop Gamer XYZ",
  "price_usd": 1200.0,
  "price_mxn": 20400.0,
  "stock_status": "IN_STOCK",
  "available_units": 15,
  "currency_conversion_source": "Frankfurter External API",
  "status": "SUCCESS"
}
```

Si Frankfurter no está disponible, el servicio de tipo de cambio utiliza `20.00` como valor de respaldo. El ejemplo de precio en MXN también es ilustrativo y no representa necesariamente el tipo de cambio actual.

## Endpoints disponibles

| Método | Ruta | Descripción |
|---|---|---|
| `GET` | `/microservices/products/{id}` | Consulta un producto por identificador. |
| `GET` | `/microservices/inventory/{id}` | Consulta el inventario por identificador de producto. |
| `GET` | `/api/v1/product-summary/{id}` | Devuelve producto, inventario y precio convertido a MXN. |

El identificador disponible para probar la aplicación recién iniciada es `P-101`.
