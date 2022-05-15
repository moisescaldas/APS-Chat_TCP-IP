package br.unip.si.aps.moises.core.bus.actions;

import br.unip.si.aps.moises.core.dto.Acknowledge;
import br.unip.si.aps.moises.core.factory.MessageEventFactory;
import br.unip.si.aps.moises.core.network.NetworkProxy;

public class AcknowledgeAction implements Action{
	/**
	 * Pattern Singleton
	 */
	private static AcknowledgeAction instance;
	
	private AcknowledgeAction() {
		proxy = NetworkProxy.getInstance();
	}
	
	public static synchronized AcknowledgeAction getInstance() {
		return instance == null ? (instance = new AcknowledgeAction()) : instance;
	}
	
	/**
	 * Objeto
	 */
	private NetworkProxy proxy;
	
	@Override
	public void triggerAction(Object object) {
		Acknowledge ack = (Acknowledge) object;
		proxy.onMessage(MessageEventFactory.createMessageEvent(null, ack.getJson()));
	}
}
