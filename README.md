Sistema de Reservas de Sala - CoworkLima S.A.C.

Alumno: Johan Alfaro Mejia
Institucion: CodiGo powered by Tecsup

Para ejecutar el proyecto:

    ./mvnw spring-boot:run

La aplicacion arranca en el puerto 9090.

Endpoints disponibles:

    GET    /api/info
    GET    /api/salas
    GET    /api/salas/{id}
    POST   /api/salas
    PUT    /api/salas/{id}
    DELETE /api/salas/{id}
    POST   /api/reservas
    GET    /api/reservas/{id}
    GET    /api/reservas
    GET    /api/reservas/sala/{salaId}
    PUT    /api/reservas/{id}/estado
    DELETE /api/reservas/{id}
    POST   /api/reservas/{id}/comprobante

Responsabilidad de cada capa:

Controller: recibe las peticiones HTTP y delega el trabajo al Service. No contiene logica de negocio.

Service: aqui viven las reglas del negocio. Por ejemplo, que toda reserva nueva empieza en estado PENDIENTE, o que al borrar una sala se eliminan sus reservas.

Repository: almacena los datos en memoria usando listas. Tiene un contador para generar los IDs automaticamente.

Model: las clases que representan los datos del dominio: Sala y Reserva.

DTO: objetos que se usan para recibir y enviar datos al cliente. Los DTOs de respuesta nunca exponen campos sensibles como passwordInterno.

Mapper: convierte entre modelos y DTOs. Toda la transformacion de datos ocurre aca.

Repositorio GitHub: https://github.com/jojan9898/coworklima
