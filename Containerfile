FROM openjdk:11.0-jre-slim

WORKDIR /work/
RUN chown :root /work \
    && chmod "g+rwX" /work \
    && chown :root /work

COPY target/quarkus-app/*-run.jar /work/application.jar
COPY target/quarkus-app/lib/* /work/lib/

EXPOSE 8080

CMD ["java","-jar","application.jar","-Dquarkus.http.host=0.0.0.0"]