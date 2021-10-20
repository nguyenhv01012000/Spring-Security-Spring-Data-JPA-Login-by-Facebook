# Spring Boot JWT Authentication example with Spring Security & Spring Data JPA + Login by Facebook + MYSQL
swagger : http://localhost:8080/swagger-ui.html

Login facebook: http://localhost:8080/oauth2/authorization/facebook
## User Registration, User Login and Authorization process.
The diagram shows flow of how we implement User Registration, User Login and Authorization process.

```
## Run Spring Boot application
```
mvn spring-boot:run
```

## Run following SQL insert statements(Create database testdb)
```
POST API: http://localhost:8080/api/roles

{
    "name":"ROLE_USER"
}

{
    "name":"ROLE_ADMIN"
}

{
    "name":"ROLE_MODERATOR"
}
```
