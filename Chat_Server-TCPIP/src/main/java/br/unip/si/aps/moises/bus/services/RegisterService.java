package br.unip.si.aps.moises.bus.services;

import java.util.Map;

import br.unip.si.aps.moises.network.domain.NetworkProxy;
import br.unip.si.aps.moises.network.manager.ConnectionPoolManager;
import lombok.NonNull;
import lombok.Setter;

@Setter
public class RegisterService implements Service {
	/*
	 * Sigleton
	 */
	private static RegisterService instance;
	
	private RegisterService() {
		this.pool = ConnectionPoolManager.getInstance();
	}
	
	public static synchronized RegisterService getInstance() {
		return instance == null ? (instance = new RegisterService()) : instance;
	}
	
	/*
	 * Atributos e Metodos
	 */
	
	private ConnectionPoolManager pool;
	
	@Override
	public void exec(@NonNull Map<String, Object> data) {
		var proxy = (NetworkProxy) data.get("proxy");
		var target = (String) data.get("target");
		pool.addTargetToNetworkProxy(proxy, target);
	}
}
