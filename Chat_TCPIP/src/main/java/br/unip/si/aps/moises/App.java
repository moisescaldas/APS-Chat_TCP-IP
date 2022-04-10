package br.unip.si.aps.moises;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class App {
    public static void main( String[] args ){
    	Socket connection = new Socket();
    	try {
			connection.connect(new InetSocketAddress("localhost", 7777));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
    }
}
