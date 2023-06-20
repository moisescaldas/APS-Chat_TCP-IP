# Trabalho da APS SI5P30 - CHAT TCP/IP

## Integrantes
- Fernando Schineider
- John Marcelino
- Henrique Araujo
- Moises Costa Caldas

## Sobre o Trabalho

Trabalho para compor a apresentação da APS(Atividades praticas supervisionadas), foi desenvolvido um chat que se comunica através de sockets TCP/IP. A aplicação cliente desenvolvida em Java Swing é responsável por receber e enviar as mensagens. Já a aplicação servidor desenvolvida em Java e conteinerizada em docker, é responsável por mapear os clientes, trafegar com as mensagens, e informar aos clientes quais estão ativos na rede.

Para a segurança das mensagens foi implementado um algoritmo de criptografia de ponta a ponta com RSA.



### Compilando e Executando o Servidor

```bash
$ mvn clean package
$ docker build -t aps/chat_server .
$ docker run -d --name APS_CHAT -p 7777:7777 -t aps/chat_server
```



### Compilando e Executando o Cliente

```bash
$ mvn clean package
$ java -jar chat.jar
```



### Telas

Tela 1 - Conexão com o sistema

![tela_conexao](Chat_TCPIP\telas\tela_conexao.png)



Tela 2 - Tela dos Chats

![tela_chats](Chat_TCPIP\telas\tela_chats.png)



Tela 3 - Pacote de Rede

![conteudo_pacote_tcp](Chat_TCPIP\telas\conteudo_pacote_tcp.png)
