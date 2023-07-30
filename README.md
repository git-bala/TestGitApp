# Build a REST service in Java

## 1. Compile and run application:
 - Open a terminal (git bash) inside the project folder
 - Run command: mvn clean install
 - Run command: mvn spring-boot:run

## 2. The application Swagger is in the URL: 
 - http://localhost:8080/swagger-ui.html

## 3. H2
 - http://localhost:8080/h2/
 - URL jdbc:h2:mem:clients
 - username bala
 - password bala

## 4. Dockerfile
 - Run the published image on docker hub > docker run -p 8080:8080 bala/client:1.0
 - Generate image from api local > docker build -t bala/client:1.0 .
 - Run image in container local > docker run -p 8080:8080 bala/client:1.0
 
## 5. Openshift
 - https://rest-api-assurebala-dev.apps.sandbox-m3.1530.p1.openshiftapps.com//swagger-ui.html
## Authentication
	 - user: bala
	 - password: bala

	 - user: user
	 - password: user
 
## 6. Repository in Github
 - https://github.com/git-bala/TestGitApp
 
 
