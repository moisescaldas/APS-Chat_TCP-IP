package br.unip.si.aps.moises.core.bus.services;

import java.util.Map;

import br.unip.si.aps.moises.application.domain.bean.Status;
import br.unip.si.aps.moises.application.domain.manager.RemoteUserManager;
import br.unip.si.aps.moises.core.dto.Closed;

public class ClosedService implements Service{
	/*
	 * Singleton
	 */
	private static ClosedService instance;
	
	private ClosedService() {
		this.remoteUserManager = RemoteUserManager.getInstance();
	}
	
	public static synchronized ClosedService getInstance() {
		return instance == null ? (instance = new ClosedService()) : instance;
	}
	
	/*
	 * Atributos e Metodos
	 */
	private RemoteUserManager remoteUserManager;
	
	@Override
	public void exec(Map<String, Object> data) {
		Closed closed = (Closed) data.get("closed");
		remoteUserManager.findRemoteUserById(closed.getFrom())
			.forEach(user -> remoteUserManager.updateUserStatus(user, Status.OFFLINE));
	}
}
