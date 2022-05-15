package br.unip.si.aps.moises.network.manager;

import static br.unip.si.aps.moises.factory.SocketFactory.createServerSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;
import java.util.logging.Logger;

import br.unip.si.aps.moises.bus.Coreographer;
import br.unip.si.aps.moises.factory.ProxyThreadFactory;
import br.unip.si.aps.moises.network.domain.NetworkProxy;
import br.unip.si.aps.moises.observer.listener.CloseConnectionListener;

public class NetworkProxyManager implements Runnable, CloseConnectionListener {
	private ServerSocket socket;
	private ConnectionPoolManager poolManager;
	private Coreographer bus;
	
	public NetworkProxyManager() {
		if((socket = createServerSocket(7777)) == null)
			throw new RuntimeException();
		this.poolManager = ConnectionPoolManager.getInstance();
		this.bus = Coreographer.getInstance();
	}
		
	@Override
	public void run() {
		Logger.getGlobal().info("Servidor Aberto para comunicações");
		listenSocket().start();
	}

	private Thread listenSocket() {
		return new Thread(() -> {
			Thread proxy;
			while(!isProxyClosed()) {
				if((proxy = ProxyThreadFactory.newThread(socket, this)) != null) proxy.start();
			}
		});
	}
		
	public Boolean isProxyClosed() {
		return socket.isClosed();
	}

	@Override
	public void notifyConnectionClosed(NetworkProxy proxy) {
		List<String> idList = poolManager.getIDsFromNetworkProxy(proxy);
		poolManager.removeNetworkProxy(proxy);
		bus.notifyClosedUser(idList);
		Logger.getGlobal().info("Conexão[" + proxy + "]Fechada");
	}
	
	public void stop() {
		try {
			socket.close();
		} catch (IOException e) {
			Logger.getGlobal().warning("Servidor Abriu erro ao fechar");
		}
	}
}
