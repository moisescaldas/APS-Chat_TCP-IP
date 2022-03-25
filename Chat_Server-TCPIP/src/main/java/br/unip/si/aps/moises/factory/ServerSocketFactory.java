package br.unip.si.aps.moises.factory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketFactory {
	private ServerSocketFactory() {}

	public static ServerSocket createServerSocket(Integer port) {
		try {
			return new ServerSocket(port);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Socket getHostSocket(ServerSocket socket) {
		try {
			return socket.accept();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
