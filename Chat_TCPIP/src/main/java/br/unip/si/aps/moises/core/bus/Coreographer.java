package br.unip.si.aps.moises.core.bus;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.github.openjson.JSONObject;

import br.unip.si.aps.moises.application.domain.manager.ApplicationIDList;
import br.unip.si.aps.moises.core.bus.services.AcknowledgeService;
import br.unip.si.aps.moises.core.bus.services.AnnounceService;
import br.unip.si.aps.moises.core.bus.services.MessageService;
import br.unip.si.aps.moises.core.bus.services.Service;
import br.unip.si.aps.moises.core.dto.Acknowledge;
import br.unip.si.aps.moises.core.dto.Announce;
import br.unip.si.aps.moises.core.dto.Message;
import br.unip.si.aps.moises.observer.event.MessageEvent;
import br.unip.si.aps.moises.observer.listener.MessageListener;

public class Coreographer implements MessageListener {
	/**
	 * Pattern Singleton
	 */
	private static Coreographer instance;

	private Coreographer() {
		this.targetList = ApplicationIDList.getInstance();
	}
	
	public static synchronized Coreographer getInstance() {
		return instance == null ? (instance = new Coreographer()) : instance; 
	}
	/**
	 * Objeto
	 */
	private ApplicationIDList targetList;
	
	public void onMessage(MessageEvent event) {
		Logger.getGlobal().info(event.getMessage().toString());
		if (filterTarget(event.getMessage().getJSONObject("header")))
			execService(event);
	}

	private void execService(MessageEvent event) {
		Method method = getMethod(event.getMessage().getJSONObject("body").getString("method"));
		if(method != null) {
			try {
				method.invoke(this, event);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private Boolean filterTarget(JSONObject json) {
		return targetList.hasInTargetList(json.getString("target"));
	}
	
	private Method getMethod(String methodName) {
		try {
			return Coreographer.class.getMethod(methodName, MessageEvent.class);
		} catch (Exception e) {
			return null;
		}
	}	

	/**
	 * Serviços
	 */
	public void announce(MessageEvent event) {
		Service service = new AnnounceService();
		Map<String, Object> data = new HashMap<>();
		data.put("announce", Announce.loadFromJson(event.getMessage()));
		service.exec(data);
	}
	
	public void acknowledge(MessageEvent event) {
		Service service = new AcknowledgeService();
		Map<String, Object> data = new HashMap<>();
		data.put("acknowledge", Acknowledge.loadFromJson(event.getMessage()));
		service.exec(data);
	}
	
	public void message(MessageEvent event) {
		Service service = new MessageService();
		Map<String, Object> data = new HashMap<>();
		data.put("message", Message.loadFromJson(event.getMessage()));
		service.exec(data);
	}
}
