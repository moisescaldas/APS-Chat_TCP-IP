package br.unip.si.aps.moises.network.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import br.unip.si.aps.moises.network.domain.NetworkProxy;
import lombok.NonNull;

public class ConnectionPoolManager {
	private static ConnectionPoolManager instance;
	
	private ConnectionPoolManager() {}
	
	public static synchronized ConnectionPoolManager getInstance() {
		return instance == null ? (instance = new ConnectionPoolManager()) : instance;
	}
	
	private Map<NetworkProxy, Set<String>> pool = new HashMap<NetworkProxy, Set<String>>();
	private ExecutorService executor = Executors.newFixedThreadPool(1);

	public void putNetworkProxy(@NonNull NetworkProxy proxy, @NonNull Set<String> destinations) {
		executor.submit(() -> pool.put(proxy, destinations));
	}
	
	public Map<NetworkProxy, Set<String>> getConnectionPool(){
		Future<Map<NetworkProxy, Set<String>>> future = executor.submit(() -> {return pool;});
		try {
			return future.get();
		} catch (InterruptedException | ExecutionException e) {
			Logger.getGlobal().warning(e.getMessage());
			return null;
		}
	}
	
	public void removeNetworkProxy(@NonNull NetworkProxy proxy) {
		executor.submit(() -> {
			pool.remove(proxy);
		});
	}

	public void addTargetToNetworkProxy(@NonNull NetworkProxy proxy, @NonNull String target) {
		executor.submit(() -> {
			pool.get(proxy).add(target);
			Logger.getGlobal().info("Destino Adicionado:"
					+ target + " -> " + proxy.toString());
		});
	}
	
	public void removeTargetFromNetworkProxy(@NonNull NetworkProxy proxy, @NonNull String target) {
		executor.submit(() -> {
			pool.get(proxy).remove(target);
			Logger.getGlobal().info("Destino Removido:"
					+ target + " <- " + proxy.toString());
		});
	}

	public List<NetworkProxy> findNetworkProxyTarget(@NonNull String target) {
		Future<List<NetworkProxy>> proxyList = executor.submit(() -> {
			return pool.keySet().stream().filter(proxy -> {
				return pool.get(proxy).stream().anyMatch(destiny -> destiny.equals(target)) ? true : false ;
			}).collect(Collectors.toList());
		});
		
		try {
			return proxyList.get();
		} catch (InterruptedException | ExecutionException e) {
			Logger.getGlobal().warning(e.getMessage());
			return null;
		}
	}
}
