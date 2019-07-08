# elasticsearch-ssl
Test project connecting to elasticsearch via SSL using Spring Data Elasticsearch 3.2.0.RC1

Create an instance of Databases for Elasticsearch in IBM Cloud

View the service credentials for your service instance, find the values for 
```
https/certificate/certificate_base64
https/authentication/username
https/authentication/password
https/hosts/hostname
https/hosts/port
```

Edit `src/main/resources/application.properties` set the values from the service credentials. Concatenate host & port with a `:` for the hostandport property.

Build and run the app.
`mvn clean package spring-boot:run`

Hit the test endpoint.
`curl http://127.0.0.1:8080/`

Observe the 'ok' after 'Hello' indicating Ping was successful.
