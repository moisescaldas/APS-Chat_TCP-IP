package br.unip.si.aps.moises.network.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import br.unip.si.aps.moises.network.domain.NetworkProxy;
import lombok.NonNull;

// Classe que vai ficar gerenciando o acesso a fila
public class ConnectionPoolManager {
	private Map<NetworkProxy, List<String>> pool = new HashMap<NetworkProxy, List<String>>();
	private ExecutorService executor = Executors.newFixedThreadPool(1);

	public synchronized void putNerworkProxy(@NonNull NetworkProxy proxy, @NonNull List<String> destinations) {
		executor.submit(() -> pool.put(proxy, destinations));
	}
	
	public Map<NetworkProxy, List<String>> getConnectionPool(){
		Future<Map<NetworkProxy, List<String>>> future = executor.submit(() -> {return pool;});
		try {
			return future.get();
		} catch (InterruptedException | ExecutionException e) {
			Logger.getGlobal().warning(e.getMessage());
			return null;
		}
	}
}
