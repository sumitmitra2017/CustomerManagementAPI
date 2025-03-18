FROM openjdk:23-jdk
ADD target/customermanagement-rest-api.jar customermanagement-rest-api.jar
ENTRYPOINT ["sh","-c","java -jar /customermanagement-rest-api.jar"]