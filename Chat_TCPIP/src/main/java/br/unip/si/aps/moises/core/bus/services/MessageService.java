package br.unip.si.aps.moises.core.bus.services;

import java.util.Map;

import br.unip.si.aps.moises.application.domain.manager.MessageManager;
import br.unip.si.aps.moises.core.dto.Message;

public class MessageService implements Service{
	private MessageManager mm;
	
	public MessageService() {
		mm = MessageManager.getInstance();
	}
	
	@Override
	public void exec(Map<String, Object> data) {
		Message message = (Message) data.get("message");
		mm.receiveMessage(message);
	}
}
