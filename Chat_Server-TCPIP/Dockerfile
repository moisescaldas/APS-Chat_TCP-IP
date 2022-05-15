FROM openjdk:11
ADD target/chat_server.jar /chat_server.jar
ADD target/libs /libs
ADD target/contratos /contratos
EXPOSE 7777
WORKDIR /
ENTRYPOINT ["sh", "-c", "java -Xms256m -Xmx2048m -jar  /chat_server.jar"]