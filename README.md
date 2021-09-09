# Twitch Clips Application 

The aplication consumes Twitch API which uses OAuth2 for authentication and stores data into a local MongoDB database. 
Front end of the application pulls the data from our server and displays it for the end user. 

## Tech Stack
* Spring Boot
* Spring OAuth2
* Spring Web
* DB 
    * MongoDB
    
    
## TODO
* Finish the backend API
    * :heavy_check_mark: Implement Sorting by new or by views
    * :heavy_check_mark: Request validation and Return Codes 
* Implement Update Service
    * :heavy_check_mark: Get Top 100 Games 
    * :heavy_check_mark: Get Top 100 Clips for each Game
    * :heavy_check_mark: Get Top 60 Clips for each Game for the last 24 hours
* Decide on Front-End Client
    * React
    * :heavy_check_mark: Spring Web
* Work on Client side
* Use Docker Compose
    * :heavy_check_mark: `spring-boot:build-image` for Spring Boot
    * :heavy_check_mark: [Mongo Docker Image](https://hub.docker.com/_/mongo) for DB
    * :heavy_check_mark: Seperate Update Service into a seperate container
* Use CI/CD
* Use of Effective Java practices

## Running the application
Both API and Scheduler are available as public Docker images. Also Mongo Docker image is used.
You can build them yourself from the sourcecode or just use ones I have created.
* What you will need is:
    * Your own Twitch CLIENT_ID
    * Your own Twitch CLIENT_SECRET

Example of docker-compose.yml if using my published Docker images:

```yml
version: "3.7"

services:
  api: 
    depends_on: 
      - mongo
    image: devosdev/twitchclips:0.0.2-SNAPSHOT
    ports: 
      - 8080:8080
    environment:
      CLIENT_ID: ${CLIENT_ID}
      CLIENT_SECRET: ${CLIENT_SECRET}
      MONGO_INITDB_ROOT_USERNAME: ${MONGO_INITDB_ROOT_USERNAME}
      MONGO_INITDB_ROOT_PASSWORD: ${MONGO_INITDB_ROOT_PASSWORD}

  scheduler:
    depends_on: 
      - mongo
    image: devosdev/twitchscheduler
    ports: 
      - 8082:8082
    environment:
      CLIENT_ID: ${CLIENT_ID}
      CLIENT_SECRET: ${CLIENT_SECRET}
      MONGO_INITDB_ROOT_USERNAME: ${MONGO_INITDB_ROOT_USERNAME}
      MONGO_INITDB_ROOT_PASSWORD: ${MONGO_INITDB_ROOT_PASSWORD}
    

  mongo:
    image: mongo
    restart: always
    volumes: 
      - twitchclips-db:/data/db
    environment:
      MONGO_INITDB_ROOT_USERNAME: ${MONGO_INITDB_ROOT_USERNAME}
      MONGO_INITDB_ROOT_PASSWORD: ${MONGO_INITDB_ROOT_PASSWORD}

  mongo-express:
    image: mongo-express
    restart: always
    ports:
      - 8081:8081
    environment:
      ME_CONFIG_MONGODB_ADMINUSERNAME: ${MONGO_INITDB_ROOT_USERNAME}
      ME_CONFIG_MONGODB_ADMINPASSWORD: ${MONGO_INITDB_ROOT_PASSWORD}

volumes:
  twitchclips-db:
```


