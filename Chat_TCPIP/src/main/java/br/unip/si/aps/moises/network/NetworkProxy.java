package br.unip.si.aps.moises.network;

import java.net.Socket;

import br.unip.si.aps.moises.factory.SocketFactory;

public class NetworkProxy {
	private Socket socket;
	
	private static NetworkProxy instance;
	
	private NetworkProxy(String hostname, Integer port) {
		this.socket = SocketFactory.getSocket(hostname, port);
	}
	
	public static synchronized NetworkProxy getInstance(String hostname, Integer port) {
		return (instance = new NetworkProxy(hostname, port));
	}
	
	public static NetworkProxy getInstance() {
		return instance;
	}
}
