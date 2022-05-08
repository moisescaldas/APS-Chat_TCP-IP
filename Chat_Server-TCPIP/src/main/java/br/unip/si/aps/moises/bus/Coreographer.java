package br.unip.si.aps.moises.bus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import br.unip.si.aps.moises.bus.services.Register;
import br.unip.si.aps.moises.bus.services.Send;
import br.unip.si.aps.moises.bus.services.Unregister;
import br.unip.si.aps.moises.network.domain.NetworkProxy;
import br.unip.si.aps.moises.network.manager.ConnectionPoolManager;
import br.unip.si.aps.moises.observer.action.MessageAction;
import br.unip.si.aps.moises.observer.listener.MessageListener;
import br.unip.si.aps.moises.util.JsonMessageUtil;

public class Coreographer implements MessageListener {
	private static Coreographer instance;
	
	private ConnectionPoolManager pool;
	
	public Coreographer() {
		this.pool = ConnectionPoolManager.getInstance();
	}
	
	public static synchronized Coreographer getInstance() {
		return instance == null ? (instance = new Coreographer()) : instance;
	}
	
	@Override
	public void onMessage(MessageAction action) {
		this.execService(action);
	}
	
	public void execService(MessageAction action) {
		Method method = getMethod(action.getMessage().getJSONObject("header").getString("method"));
		if(method != null) {
			try {
				method.invoke(this, action);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				((NetworkProxy) action.getSource()).onMessage(new MessageAction(null, JsonMessageUtil.getMessageErro("Mensagem Invalida!")));				
			}
		}else
			((NetworkProxy) action.getSource()).onMessage(new MessageAction(null, JsonMessageUtil.getMessageErro("Metodo não reconhecido")));
	}

	private Method getMethod(String methodName) {
		try {
			return Coreographer.class.getMethod(methodName, MessageAction.class);
		} catch (Exception e) {
			return null;
		}
	}	

	// Serviços
	public void register(MessageAction action) {
		var service = new Register();
		var data = new HashMap<String, Object>();
		var json = action.getMessage().getJSONObject("header");
		
		data.put("proxy", action.getSource());
		data.put("pool", pool);
		data.put("target", json.getString("from"));
		data.put("id", json.getString("id"));
		
		service.exec(data);
	}

	public void unregister(MessageAction action) {
		var service = new Unregister();
		var data = new HashMap<String, Object>();
		var json = action.getMessage().getJSONObject("header");
		
		data.put("proxy", action.getSource());
		data.put("pool", pool);
		data.put("target", json.getString("from"));
		data.put("id", json.getString("id"));
		
		service.exec(data);
	}	
	
	public void send(MessageAction action) {
		var service = new Send();
		var data = new HashMap<String, Object>();
		var json = action.getMessage().getJSONObject("header");

		data.put("proxy", action.getSource());
		data.put("pool", pool);
		data.put("target", json.getString("target"));
		data.put("message", action.getMessage());
		
		service.exec(data);
	}	
}
