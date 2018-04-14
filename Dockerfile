FROM openjdk:8-jdk-alpine

RUN adduser -S brian

RUN mkdir -p /app/lib
RUN mkdir /app/config

ADD build/libs/atm-machine.jar /app/lib/atm-machine.jar

USER brian

ENTRYPOINT ["java","-jar","/app/lib/atm-machine.jar"]