package br.unip.si.aps.moises.service;

import static br.unip.si.aps.moises.factory.JSONFactory.createJSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.unip.si.aps.moises.factory.ServiceFactory;
import br.unip.si.aps.moises.network.Session;
import br.unip.si.aps.moises.util.MessageAction;
import br.unip.si.aps.moises.util.MessageListener;

public class ServicebusManager implements MessageListener{
	// Da pra melhorar isso aqui colocando um container
	private static Map<Session, List<String>> sessions = new HashMap<>();
	
	
	// Metodo vai chamar os serviços para fazer a orquestração
	public void onMessage(MessageAction action) {
		System.out.println("Orquestração Iniciada!");
		ServiceFactory
			.getService(createJSONObject(action
					.getMessage())
					.getJSONObject("header")
					.getString("method"))
			.run(action);
	}
	
	public static void registerConnection(List<String> id, Session session){
		sessions.put(session, id);
	}
	
	public static void removeConnection(Session session) {
		sessions.keySet().forEach(key -> {
			if (key.equals(session)) sessions.remove(key);
		});
	}
	
	public static Map<Session, List<String>> getSessions(){
		return sessions;
	}
}
