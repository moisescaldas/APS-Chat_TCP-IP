package br.unip.si.aps.moises.util.factory;

import java.io.IOException;
import java.net.ServerSocket;

import lombok.NonNull;

public class SocketFactory {
	private SocketFactory() {}
	
	public static ServerSocket createServerSocket(@NonNull Integer port) {
		try {
			return new ServerSocket(port);
		} catch (IOException e) {
			return null;
		}
	}
}
