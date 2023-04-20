FROM maven:3.8.5-openjdk-17

WORKDIR /app

COPY ./ /app/

RUN mvn install

EXPOSE 8880

CMD ["java", "-jar", "target/app.jar"]