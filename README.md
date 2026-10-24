# Post-contenido — Unidad 7: Patrones Arquitectónicos I

## Descripción

Repositorio del post-contenido de la Unidad 7 de Patrones de Diseño de Software. Un único proyecto Spring Boot (`multas-biblioteca-api`) para la gestión de multas de biblioteca universitaria: una API REST en capas (Model, Repository, Service, Controller) sobre H2.

## Parte 1: Arquitectura en capas

MultaController (presentación) expone `/api/multas` y solo habla con MultaService, que concentra las reglas de negocio de aplicación (límite de multas pendientes y generación). MultaRepository (infraestructura) extiende JpaRepository y agrega la consulta agregada `countByEstudianteIdAndEstado`. El cálculo del monto vive en la propia entidad Multa (`Multa.calcularMonto`).

### Estructura de paquetes

```
multas-biblioteca-api/src/main/java/com/example/multas/
├── controller/   ← Presentación (MultaController, GlobalExceptionHandler)
├── service/      ← Aplicación (MultaService)
├── model/        ← Dominio (Multa, EstadoMulta, excepciones de negocio)
├── repository/   ← Infraestructura (MultaRepository)
└── MultasApplication.java
```

## Cómo ejecutar

```powershell
cd multas-biblioteca-api
mvn spring-boot:run
```

## Evidencias

| Prueba | Captura |
|---|---|
| GET /api/multas (lista vacía, 200) | ![GET vacío](docs/01-get-vacio.png) |
| POST /api/multas (201, monto calculado) | ![POST 201](docs/02-post-201.png) |
| POST sin estudianteId (400) | ![POST 400](docs/03-post-400.png) |
| GET /api/multas/999 (404) | ![GET 404](docs/04-get-404.png) |
| PATCH pagar (200) y repetido (409) | ![Pagar](docs/05-pagar-200-y-409.png) |
| Cuarta multa pendiente (409) | ![Límite](docs/06-limite-409.png) |