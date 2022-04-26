# Trabalho da APS SI5P30 - CHAT TCP/IP

## Integrantes
- John
- Henrique Araujo
- Fernando Schineider
- Moises Costa Caldas

## Sobre o Trabalho

### Compilando e Executando o Servidor
```
$ mvn clean package
$ docker build -t aps/chat_server .
$ docker run -p 7777:7777 -t aps/chat_server
```

### Compilando e Executando o Cliente
```
$ mvn clean package
$ java -jar chat.jar
```