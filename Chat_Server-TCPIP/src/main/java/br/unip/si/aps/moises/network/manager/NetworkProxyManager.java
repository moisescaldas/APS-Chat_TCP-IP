package br.unip.si.aps.moises.network.manager;

import static br.unip.si.aps.moises.util.factory.SocketFactory.createServerSocket;

import java.net.ServerSocket;
import java.util.logging.Logger;

import br.unip.si.aps.moises.bus.MessageListener;
import br.unip.si.aps.moises.util.factory.ProxyThreadFactory;
import lombok.Data;

@Data
public class NetworkProxyManager implements Runnable {
	private ServerSocket socket;
	private MessageListener serviceBus;
	private ConnectionPoolManager poolManager;
	
	public NetworkProxyManager() {
		if((socket = createServerSocket(7777)) == null)
			throw new RuntimeException();
		this.poolManager = new ConnectionPoolManager();
	}
	
	@Override
	public void run() {
		Logger.getGlobal().info("Servidor Aberto para comunicações");
		listenSocket().start();
	}

	private Thread listenSocket() {
		return new Thread(() -> {
			while(!isProxyClosed()) {
				ProxyThreadFactory.newThread(socket, serviceBus, poolManager).start();
			}
		});
	}
		
	public Boolean isProxyClosed() {
		return socket.isClosed();
	}
}
