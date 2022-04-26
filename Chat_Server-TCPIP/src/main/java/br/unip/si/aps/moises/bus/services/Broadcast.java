package br.unip.si.aps.moises.bus.services;

import java.util.List;
import java.util.Map;

import br.unip.si.aps.moises.network.domain.NetworkProxy;
import br.unip.si.aps.moises.observer.action.MessageAction;

public class Broadcast implements Service{
	@SuppressWarnings("unchecked")
	@Override
	public void exec(Map<String, Object> data) {
		MessageAction message = (MessageAction) data.get("message");
		((List<NetworkProxy>) data.get("destinations")).forEach(proxy -> proxy.onMessage(message));
	}
}
