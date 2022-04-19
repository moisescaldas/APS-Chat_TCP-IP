package br.unip.si.aps.moises.bus.services;

import java.util.Map;

import br.unip.si.aps.moises.network.domain.NetworkProxy;
import br.unip.si.aps.moises.network.manager.ConnectionPoolManager;
import br.unip.si.aps.moises.observer.action.MessageAction;
import br.unip.si.aps.moises.util.JsonMessageUtil;
import lombok.Setter;

@Setter
public class Register implements Service {
	
	@Override
	public void exec(Map<String, Object> data) {
		var pool = (ConnectionPoolManager) data.get("pool");
		var proxy = (NetworkProxy) data.get("proxy");
		var target = (String) data.get("target");
		var id = (String) data.get("id");
		pool.addTargetToNetworkProxy(proxy, target);
		
		proxy.onMessage(new MessageAction(null, JsonMessageUtil.getMessageInfo(id, "Registro Sucedido")));
	}
}
