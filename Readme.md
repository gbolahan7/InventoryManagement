
# Inventory Management System

## Quick Run - Docker
```sh
docker-compose up
```

## Project Run
### How to run the project
#### FRONT-END
- You need to have node-js installed on your machine globally
- `cd` to the ui directory and run these commands sequentially on the shell terminal when each operation are complete
```sh
 npm install
 ng serve --port 8081
```
#### BACK-END
- You must have mysql server running on port 3306
- You must have (jdk 11 or above) and maven installed on your machine globally
- Then `cd` to the core directory and run this command 
```sh
 mvn clean spring-boot:run
```

#### Technology Used
- SPRING-BOOT
- MYSQL
- Lombok
- DOCKER
- MAPSTRUCT
- ANGULAR