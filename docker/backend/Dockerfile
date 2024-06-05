# Build stage
FROM maven:3.8.7-openjdk-18 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17
ARG PROFILE=dev
ARG APP_VERSION=0.0.4

WORKDIR /app
COPY --from=build /build/target/book-network-*.jar /app/

EXPOSE 8088
# postgres DEBE CORRER EN EL PUERTO DE Docker QUE ES 5432 Y NO 5433(ESTE ULTIMO SE COLOCO YA QUE SE DESCARGO LA APP DE postgres y pgadmin Y postgres OCUPA EL PUERTO 5432) 
ENV DB_URL=jdbc:postgresql://postgres-sql-bsn:5432/book_social_network
ENV MAILDEV_URL=localhost

ENV ACTIVE_PROFILE=${PROFILE}
ENV JAR_VERSION=${APP_VERSION}
ENV EMAIL_HOSTNAME=
ENV EMAIL_USER_NAME=
ENV EMAIL_PASSWORD=

CMD java -jar -Dspring.profiles.active=${ACTIVE_PROFILE} -Dspring.datasource.url=${DB_URL} book-network-${JAR_VERSION}-SNAPSHOT.jar