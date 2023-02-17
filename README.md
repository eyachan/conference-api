# conference-api
api to create session and their speakers 

#requirements
- docker
- maven
- java 17
- rabbitMQ
- zipkin
- springboot security

# Run docker compose to initialize the environment

under the root folder, you will find a docker-compose file, either run it with itellj or in the console
```docker 
docker-compose -it up 
```
# run the SQL script under the resources/sql folder to create the tables and insert the data 

# to check the message sent to rabbitMQ check the URL : 
```http request
http://localhost:15672
```
# to monitor requests 

```http request
http://localhost:9411
```
# for the security just use admin and password 