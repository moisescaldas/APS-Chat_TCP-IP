package br.unip.si.aps.moises.network;

import static java.lang.System.out;

import static br.unip.si.aps.moises.factory.ServerSocketFactory.createServerSocket;
import static br.unip.si.aps.moises.factory.ServerSocketFactory.getHostSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.unip.si.aps.moises.bus.ServiceBusManager;
import br.unip.si.aps.moises.util.MessageListener;


public class ProxyManager implements Runnable{
	private ServerSocket server;
	private static Map<Session, List<String>> connections = new HashMap<>();
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
		runConnectionCleanBargage();

	}

	// Esse Metodo fica recebendo novas conexões e adiciona ela para a fila
	private void runConnectionObserverThread() {
		new Thread(() -> {
			while(isServerRunning()) {
				Socket host = getHostSocket(server);

				if (host != null) {
					Session session = new Session(host, serviceBus);
					new Thread(session).start();
					// Botar um log aqui
					out.println("Nova conexão com cliente [" 
							+ host.getPort() + host.getInetAddress() + "]");
					connections.put(session, null);
					out.println("Conexões Ativas:" + connections.keySet());
				}
			}
		}).start();
	}

	// Esse Metodo fica verificando quais conexões estão ativas
	private void runConnectionCleanBargage() {
		new Thread(() -> {
			while (isServerRunning()) {
				try {
					Thread.currentThread();
					Thread.sleep(5 * 1000);
				} catch (InterruptedException e) {}
				connections.keySet().forEach(session -> {
					if (!session.isClientConnected())
						connections.remove(session);
						out.println("Removendo Conexão:[" + session + "]");
				});
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

	public static Map<Session, List<String>> getConnections(){
		return connections;
	}
}
