package br.unip.si.aps.moises.factory;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Logger;

import br.unip.si.aps.moises.network.domain.NetworkProxy;
import br.unip.si.aps.moises.network.manager.ConnectionPoolManager;
import br.unip.si.aps.moises.observer.listener.CloseConnectionListener;

public class ProxyThreadFactory implements ThreadFactory{
	private static ConnectionPoolManager pool = ConnectionPoolManager.getInstance();
	
	private ProxyThreadFactory() {}

	@Override
	public Thread newThread(Runnable r) {
		return new Thread(r);
	}

	public static Thread newThread(ServerSocket socket, CloseConnectionListener master) {
		try {
			NetworkProxy proxy = new NetworkProxy(socket.accept(), master);
			pool.putNetworkProxy(proxy, new HashSet<String>());
			ProxyThreadFactory factory = new ProxyThreadFactory();
			Logger.getGlobal().info("Conexão[" + proxy + "]Aberta"
					+"\nConexões Ativas:" + pool.getConnectionPool().size());
			return factory.newThread(proxy);
			
		} catch (IOException e) {
			return null;
		}
	}
}
