package br.unip.si.aps.moises.bus.services;

import java.util.Map;
import java.util.logging.Logger;

import com.github.openjson.JSONObject;

import br.unip.si.aps.moises.network.domain.NetworkProxy;
import br.unip.si.aps.moises.network.manager.ConnectionPoolManager;
import br.unip.si.aps.moises.observer.action.MessageAction;
import br.unip.si.aps.moises.util.JsonMessageUtil;

public class Send implements Service {
	
	@Override
	public void exec(Map<String, Object> data) {
		var pool = (ConnectionPoolManager) data.get("pool");
		var originProxy = (NetworkProxy) data.get("proxy");

		var target = (String) data.get("target");
		var message = (JSONObject) data.get("message");
		
		try {
			var proxyList = pool.findNetworkProxyTarget(target);
			
			if (proxyList != null && proxyList.size() > 0)
				proxyList.forEach(proxy -> {
					proxy.onMessage(new MessageAction(null, message));
					Logger.getGlobal().info(originProxy + " -> enviou mensagem para -> " + proxy);
				});
			else
				originProxy.onMessage(new MessageAction(null, JsonMessageUtil.getMessageErro("Target Not Found")));
			
		}catch(Exception e) {
			originProxy.onMessage(new MessageAction(null, JsonMessageUtil.getMessageErro(e.getMessage())));
		}
	}
}