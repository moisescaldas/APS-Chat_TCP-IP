package br.unip.si.aps.moises.factory;

import java.net.Socket;

public class SocketFactory {
	private SocketFactory() {}
	
	public static Socket getSocket(String address, Integer port) {
		try {
			return new Socket(address, port);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
