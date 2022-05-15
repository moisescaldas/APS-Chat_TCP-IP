package br.unip.si.aps.moises.core.bus.services;

import static br.unip.si.aps.moises.util.SecurityKeysUtil.encodePublicKey;

import java.util.Map;

import br.unip.si.aps.moises.application.domain.bean.LocalUser;
import br.unip.si.aps.moises.application.domain.manager.RemoteUserManager;
import br.unip.si.aps.moises.core.bus.actions.AcknowledgeAction;
import br.unip.si.aps.moises.core.dto.Acknowledge;
import br.unip.si.aps.moises.core.dto.Announce;

public class AnnounceService implements Service {
	/*
	 * Singleton
	 */
	private static AnnounceService instance;
	
	private AnnounceService() {
		this.remoteUserManager = RemoteUserManager.getInstance();
	}
	
	public static synchronized AnnounceService getInstance() {
		return instance == null ? (instance = new AnnounceService()) : instance;
	}
	
	/*
	 * Metodos e Atributos
	 */
	RemoteUserManager remoteUserManager;
	
	@Override
	public void exec(Map<String, Object> data) {
		Announce announce = (Announce) data.get("announce");
		String target = announce.getFrom();
		remoteUserManager.addUser(announce.getName(), target);
		LocalUser user = LocalUser.getInstance();
		AcknowledgeAction.getInstance().triggerAction(
				new Acknowledge(target,
						encodePublicKey(user.getPublicKey()), 
						user.getName()));
	}
}
