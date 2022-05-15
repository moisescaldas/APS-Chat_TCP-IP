package br.unip.si.aps.moises.bus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import br.unip.si.aps.moises.bus.services.RegisterService;
import br.unip.si.aps.moises.bus.services.SendService;
import br.unip.si.aps.moises.bus.services.Service;
import br.unip.si.aps.moises.network.domain.NetworkProxy;
import br.unip.si.aps.moises.observer.action.MessageAction;
import br.unip.si.aps.moises.observer.listener.MessageListener;
import br.unip.si.aps.moises.util.JSONMessageUtil;

public class Coreographer implements MessageListener {
	/*
	 * Singleton
	 */
	private static Coreographer instance;
		
	private Coreographer() {
		this.register = RegisterService.getInstance();
		this.send = SendService.getInstance();
		
	}
	
	public static synchronized Coreographer getInstance() {
		return instance == null ? (instance = new Coreographer()) : instance;
	}
	/*
	 * Atributos e Metodos
	 */
	private Service register;
	private Service send;
	
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
				((NetworkProxy) action.getSource()).onMessage(new MessageAction(null, JSONMessageUtil.getMessageErro("Mensagem Invalida!")));				
			}
		}else
			((NetworkProxy) action.getSource()).onMessage(new MessageAction(null, JSONMessageUtil.getMessageErro("Metodo não reconhecido")));
	}

	private Method getMethod(String methodName) {
		try {
			return Coreographer.class.getMethod(methodName, MessageAction.class);
		} catch (Exception e) {
			return null;
		}
	}	

	/*
	 * Serviços 
	 */
	public void register(MessageAction action) {
		var data = new HashMap<String, Object>();
		var json = action.getMessage().getJSONObject("header");
		
		data.put("proxy", action.getSource());
		data.put("target", json.getString("from"));
		data.put("id", json.getString("id"));
		
		register.exec(data);
	}
	
	public void send(MessageAction action) {
		var data = new HashMap<String, Object>();
		var json = action.getMessage().getJSONObject("header");

		data.put("proxy", action.getSource());
		data.put("target", json.getString("target"));
		data.put("message", action.getMessage());
		
		send.exec(data);
	}
	
	/*
	 * Ações
	 */
	
}
