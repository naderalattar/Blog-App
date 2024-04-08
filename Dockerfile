FROM amazoncorretto:17

WORKDIR /app

COPY target/blog-rest-api-0.0.1-SNAPSHOT.jar /app/blog-rest-api.jar

ENTRYPOINT ["java","-jar","/app/blog-rest-api.jar"]