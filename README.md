
dkbmc_springboot_blog
=============================================================================================================================================

Environment
-----------

-	java 17.0.4.1
-	Springboot 2.7.4
-	Postgresql 14.5
-   Gradle 7.5.1

Prerequisite
------------

-	[Java SE 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

Installation
------------

### Setting Environment

Set username, password, and url according to your database environment in application.yml

```yml
  datasource:
    username: <your-database-username>
    password: <your-database-password>
    url: jdbc:postgresql://localhost:5432/<your-database-name>
```

### Creating an Executable jar File

Use the following command to delete the contenexcutat of the build directory.

```
> gradlew clean
```

Use the following command to create executable jar file 

```
> gradlew build
```

After successful build, you can find jar file under /build/libs

```
/build
    /libs
        /<your-project-build-name>.jar
```

### Setting Procfile

Add following line according to your jar file in Procfile 

```
web: java -jar build/libs/<your-project-build-name>.jar
````

Deploy the App
------------

### using Heroku cli

Use the following command to create heroku app and connectting to local/remote git repository.

```
> heroku create <your-app-name>
```

Use the following command to deploy the latest committed version of your code to Heroku.

```
> git push heroku main
```


API Documentation
-----------------

[Click here](docs/API.md) for API documentation.

Model Documentation
-------------------

[Click here](docs/MODEL.md) for model documentation.

