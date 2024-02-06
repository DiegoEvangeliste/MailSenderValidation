# Mail Sender Validation
Repositorio base para consumo API-REST de registro de usuarios validados por envio del token por email.

## Configuracion proyecto

### Versionado de desarrollo

- Java 17
- Spring 3.2.2


### Dependencias agregadas
- DEVTOOLS 
  - Spring Dev Tools
  - Lombok
- WEB
  - Spring Web
- DATA
  - Spring Data JPA
  - H2 Database 
- SECURITY
  - Spring Security
  - JsonWebToken
    - jjwt-api
    - jjwt-jackson
    - jjwt-impl
- TEST
  - Spring Boot Starter Test
  - Spring Security Test
- DOCUMENTATION
  - Springdoc Openapi Starter Webmvc Ui
- MAIL
  - Java Mail Sender

### Plugins configurados

- Lombok


## Utilizacion
Para que el consumo API-REST funcione en el archivo **application.properties** se deben configurar:
- email.sender=*CORREO_ELECTRONICO*.
- email.password=*CONTRASEÑA_DEL_CORREO_ELECTRONICO*.

Se puede consultar la documentacion generada por Swagger en la siguiente ruta: `http://localhost:8080/swagger-ui/index.html`.