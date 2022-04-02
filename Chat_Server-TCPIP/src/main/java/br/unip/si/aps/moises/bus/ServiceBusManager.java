package br.unip.si.aps.moises.bus;

import br.unip.si.aps.moises.network.Session;
import br.unip.si.aps.moises.util.MessageAction;
import br.unip.si.aps.moises.util.MessageListener;

public class ServiceBusManager implements Runnable, MessageListener{
	
	public void run() {
		
	}
	
	// Metodo vai chamar os serviços para fazer a orquestração
	public void onMessage(MessageAction action) {
		System.out.println(action.getMessage());
		
		Session session = (Session) action.getSource();
		session.onMessage(action);
		
	}
}
