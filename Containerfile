FROM openjdk:11.0-jre-slim

WORKDIR /work/
RUN chown :root /work \
    && chmod "g+rwX" /work \
    && chown :root /work

RUN apt update
RUN apt install openjdk-11-jdk -y

COPY target/quarkus-app/*-run.jar /work/application.jar
COPY target/quarkus-app/lib/ /work/lib/
COPY target/quarkus-app/app/ /work/app/
COPY target/quarkus-app/quarkus/ /work/quarkus/

EXPOSE 8080

CMD ["java","-Xmx4G","-jar","application.jar","-Dquarkus.http.host=0.0.0.0"]
