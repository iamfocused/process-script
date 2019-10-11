FROM java:8
VOLUME ["/tmp"]
ADD build/libs/process-script-1.0.jar app.jar
EXPOSE 8001
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
