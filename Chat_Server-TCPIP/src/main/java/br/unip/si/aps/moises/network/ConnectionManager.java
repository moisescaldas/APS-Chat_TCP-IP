package br.unip.si.aps.moises.network;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import br.unip.si.aps.moises.factory.ServerSocketFactory;

public class ConnectionManager implements Runnable{
	private ServerSocket server;
	private List<Socket> connections = new LinkedList<>();
	
	public ConnectionManager() {
		server = ServerSocketFactory.createServerSocket(7771);
	}
	@Override
	public void run() {
		runConnectionListenerThread();
		
		while(true) {
			System.out.println(connections);
		}
	}
	
	// Esse Metodo fica recebendo novas conexões e adiciona ela para a fila
	private void runConnectionListenerThread() {
		new Thread(() -> {
			while(true) {
				Socket host = ServerSocketFactory.getHostSocket(server);
				
				if (host != null)
					// Colocar LOG Aqui
					System.out.println("Nova Conexão com o cliente [" + host.getInetAddress() +"]");
					connections.add(host);
			}
		}).run();
	}


}
