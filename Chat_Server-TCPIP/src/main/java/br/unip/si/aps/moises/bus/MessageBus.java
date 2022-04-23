package br.unip.si.aps.moises.bus;

import java.util.logging.Logger;

import br.unip.si.aps.moises.network.manager.ConnectionPoolManager;
import br.unip.si.aps.moises.observer.action.MessageAction;
import br.unip.si.aps.moises.observer.listener.MessageListener;


public class MessageBus implements MessageListener {
	private Coreographer orquestrador;
	
	public MessageBus setConnectionPoolManager(ConnectionPoolManager poolManager) {
		this.orquestrador = new Coreographer(poolManager);
		return this;
	}
	
	@Override
	public void onMessage(MessageAction action) {
		Logger.getGlobal().info("Iniciando Orquestração");
		orquestrador.execService(action);
	}
}
