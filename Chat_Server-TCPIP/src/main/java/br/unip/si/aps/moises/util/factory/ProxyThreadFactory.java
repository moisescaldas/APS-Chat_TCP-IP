package br.unip.si.aps.moises.util.factory;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Logger;

import br.unip.si.aps.moises.bus.MessageListener;
import br.unip.si.aps.moises.network.domain.NetworkProxy;
import br.unip.si.aps.moises.network.manager.ConnectionPoolManager;

public class ProxyThreadFactory implements ThreadFactory{
	private ProxyThreadFactory() {}

	@Override
	public Thread newThread(Runnable r) {
		return new Thread(r);
	}

	public static Thread newThread(ServerSocket socket, MessageListener serviceBus, ConnectionPoolManager pool) {
		try {
			NetworkProxy proxy = new NetworkProxy(socket.accept()).setServiceBus(serviceBus);
			pool.putNerworkProxy(proxy, new ArrayList<String>());
			ProxyThreadFactory factory = new ProxyThreadFactory();
			Logger.getGlobal().info("Nova Conexão:");
			Logger.getGlobal().info("Conexões Ativas" + pool.getConnectionPool().keySet());

			return factory.newThread(proxy);
			
		} catch (IOException e) {
			return null;
		}
	}
}
