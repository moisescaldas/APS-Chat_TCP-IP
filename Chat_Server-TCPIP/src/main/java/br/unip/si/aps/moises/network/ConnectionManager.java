package br.unip.si.aps.moises.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import br.unip.si.aps.moises.factory.ServerSocketFactory;

public class ConnectionManager implements Runnable{
	private ServerSocket server;
	private List<Socket> connections = new LinkedList<>();
	
	public ConnectionManager() {
		server = ServerSocketFactory.createServerSocket(7777);
	}
	
	@Override
	public void run() {
		runConnectionObserverThread();
		
	}
	// Esse Metodo vai funcionar com o proxy do servidor
	
	// Esse Metodo fica recebendo novas conexões e adiciona ela para a fila
	private void runConnectionObserverThread() {
		new Thread(() -> {
			while(isServerRunning()) {
				Socket host = ServerSocketFactory.getHostSocket(server);
				
				if (host != null)
					// Colocar LOG Aqui
					System.out.println("Nova Conexão com o cliente [" + host.getInetAddress() +"]");
					connections.add(host);
			}
		}).start();
	}
	private boolean isServerRunning() {
		return !server.isClosed();
	}
	
	public void closeSocket() {
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
