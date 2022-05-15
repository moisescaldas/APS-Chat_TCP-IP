package br.unip.si.aps.moises.bus.actions;

import java.util.Map;
import java.util.logging.Logger;

import com.github.openjson.JSONObject;

import br.unip.si.aps.moises.network.manager.ConnectionPoolManager;
import br.unip.si.aps.moises.observer.action.MessageAction;

public class NotifyClosedUserAction implements Action {
	/*
	 * Singleton
	 */
	private static NotifyClosedUserAction instance;
	
	private NotifyClosedUserAction() {
		this.pool = ConnectionPoolManager.getInstance();
	}
	
	public static synchronized NotifyClosedUserAction getInstance() {
		return instance == null ? (instance = new NotifyClosedUserAction()) : instance;
	}
	
	/*
	 * Atributos e Metodos
	 */
	private ConnectionPoolManager pool;
	
	@Override
	public void triggerAction(Map<String, Object> data) {
		var message = (JSONObject) data.get("message");

		try {
			var proxyList = pool.getConnectionPool().keySet();
			
			if (proxyList != null && proxyList.size() > 0)
				proxyList.stream().forEach(proxy -> {
					proxy.onMessage(new MessageAction(null, message));
					Logger.getGlobal().info("Servidor -> enviou mensagem para -> " + proxy);
				});
			
		}catch(Exception e) {
			Logger.getGlobal().info(e.getMessage());
		}
	}
}
