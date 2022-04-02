package br.unip.si.aps.moises.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import br.unip.si.aps.moises.bus.ServiceBusManager;
import br.unip.si.aps.moises.util.MessageListener;

import static br.unip.si.aps.moises.factory.ServerSocketFactory.*;

public class ProxyManager implements Runnable{
	private ServerSocket server;
	private List<Session> connections = new LinkedList<>();
	private MessageListener serviceBus;

	public ProxyManager(ServiceBusManager serviceBusManager) {
		this();
		this.serviceBus = serviceBusManager;
	}

	public ProxyManager() {
		server = createServerSocket(7777);
	}

	@Override
	public void run() {
		runConnectionObserverThread();

	}

	// Esse Metodo fica recebendo novas conexões e adiciona ela para a fila
	private void runConnectionObserverThread() {
		new Thread(() -> {
			while(isServerRunning()) {
				Socket host = getHostSocket(server);

				if (host != null) {
					Session session = new Session(host, serviceBus); 
					connections.add(session);
					new Thread(session).start();
					
				}
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
