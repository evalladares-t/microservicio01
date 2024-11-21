# Customer Microservice (Microservicio01)

## Description

This microservice handles customer management, including operations such as creating, updating, and deleting profiles. You can also provide functionality to associate customers with other elements, such as accounts or credits. It serves as the core of user information in the system.

## Pre - requisites

- **Java 20**
- **Maven**
- **Docker** (for Docker execution only)
- Red Docker compartida: `my-network`

## Initial Configuration

### Run Locally

#### Modify the file`bootstrap.yml`

Configure the active profile to use Docker:

```yaml
spring:
  profiles:
    active: local
```

#### Execute the Main Class

```yaml
mvn spring-boot:run
```

### Run Docker

#### Modify the file`bootstrap.yml`

Configure the active profile to use Docker:

```yaml
spring:
  profiles:
    active: docker
```
#### Compile and generate the Docker container
Build the Docker image with:

```yaml
docker build -t microservicio01:0.0.1-SNAPSHOT .
```

####  Run the microservice Docker container
Start the container with:

```yaml
docker run --name microservicio01 --network my-network -p 8081:8081 microservicio01:0.0.1-SNAPSHOT
```

## Notes:
- **If you need to change ports or other settings, edit the corresponding application.yml and Dockerfile files.**
- **To debug errors, check the container logs:**
    ```yaml
    docker logs microservicio01
    ``` 
- **Make sure that the Config Server and Registry Server are running before starting other services.**
