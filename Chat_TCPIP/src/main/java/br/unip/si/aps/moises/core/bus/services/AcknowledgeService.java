package br.unip.si.aps.moises.core.bus.services;

import java.util.Map;

import br.unip.si.aps.moises.application.domain.manager.RemoteUserList;
import br.unip.si.aps.moises.core.dto.Acknowledge;

public class AcknowledgeService implements Service{

	@Override
	public void exec(Map<String, Object> data) {
		Acknowledge ack = (Acknowledge) data.get("acknowledge");
		RemoteUserList.getInstance().addUser(ack.getName(), ack.getFrom());
	}

}
