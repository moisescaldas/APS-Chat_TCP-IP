package br.unip.si.aps.moises.bus;

import br.unip.si.aps.moises.domain.ApplicationIDList;
import br.unip.si.aps.moises.observer.event.MessageEvent;
import br.unip.si.aps.moises.observer.listener.MessageListener;

public class Coreographer implements MessageListener {
	private ApplicationIDList targetList;
	
	private static Coreographer instance;

	private Coreographer() {
		this.targetList = ApplicationIDList.getInstance();
	}
	
	public static synchronized Coreographer getInstance() {
		return instance == null ? (instance = new Coreographer()) : instance; 
	}
	
	public void onMessage(MessageEvent event) {
		
	}
	
}
