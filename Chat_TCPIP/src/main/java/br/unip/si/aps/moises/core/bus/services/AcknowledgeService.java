package br.unip.si.aps.moises.core.bus.services;

import java.util.Map;

import br.unip.si.aps.moises.application.domain.manager.RemoteUserManager;
import br.unip.si.aps.moises.core.dto.Acknowledge;

public class AcknowledgeService implements Service{
	/*
	 * Singleton
	 */
	private static AcknowledgeService instance;
	
	private AcknowledgeService() {
		this.remoteUserManager = RemoteUserManager.getInstance();
	}
	
	public static synchronized AcknowledgeService getInstance() {
		return instance == null ? (instance = new AcknowledgeService()) : instance;
	}
	
	/*
	 * Metodos e Atributos
	 */
	RemoteUserManager remoteUserManager;
	
	@Override
	public void exec(Map<String, Object> data) {
		Acknowledge ack = (Acknowledge) data.get("acknowledge");
		remoteUserManager.addUser(ack.getName(), ack.getFrom());
	}
}
