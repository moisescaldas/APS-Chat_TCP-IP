package br.unip.si.aps.moises.core.bus.services;

import java.util.Map;

import br.unip.si.aps.moises.application.domain.manager.MessageManager;
import br.unip.si.aps.moises.core.dto.Message;

public class MessageService implements Service{
	/*
	 * Singleton
	 */
	private static MessageService instance;
	
	private MessageService() {
		this.mm = MessageManager.getInstance();
	}
	
	public static synchronized MessageService getInstance() {
		return instance == null ? (instance = new MessageService()) : instance;
	}
	/*
	 * Metodos e Atributos
	 */
	private MessageManager mm;
		
	@Override
	public void exec(Map<String, Object> data) {
		Message message = (Message) data.get("message");
		mm.receiveMessage(message);
	}
}
