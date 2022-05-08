package br.unip.si.aps.moises.core.actions;

import java.util.logging.Logger;

import br.unip.si.aps.moises.core.dto.Message;
import br.unip.si.aps.moises.core.factory.MessageEventFactory;
import br.unip.si.aps.moises.core.network.NetworkProxy;

public class MessageAction implements Action{
	/**
	 * Singletton
	 */
	
	private static MessageAction instance;
	
	private MessageAction() {
		proxy = NetworkProxy.getInstance();
	}
	
	public static synchronized MessageAction getInstance() {
		return instance == null ? (instance= new MessageAction()) : instance;
	}
	
	
	/**
	 * Atributos e Metodos do Objeto
	 */
	private NetworkProxy proxy;
	
	
	@Override
	public void triggerAction(Object object) {
		Message message = (Message) object;
		proxy.onMessage(MessageEventFactory.createMessageEvent(null, message.getJson()));
		Logger.getGlobal().info(message.getJson().toString());
	}
}
