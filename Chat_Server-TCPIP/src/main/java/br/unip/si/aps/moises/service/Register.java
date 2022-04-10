package br.unip.si.aps.moises.service;

import static br.unip.si.aps.moises.factory.JSONFactory.createJSONObject;
import static java.lang.System.out;

import java.util.List;

import br.unip.si.aps.moises.network.Session;
import br.unip.si.aps.moises.util.MessageAction;

public class Register implements Service{
	public void run(MessageAction action) {
		Session host = (Session) action.getSource();
		List<String> listenID = ServicebusManager.getSessions().get(host);
		listenID.add(
				createJSONObject(action.getMessage())
				.getJSONObject("header")
				.getString("from"));
		
		ServicebusManager.registerConnection(listenID, host);		
		out.println("Nova Conexão Estabelecida");
		out.println("Conexões ativas: " + ServicebusManager.getSessions().keySet());
	}
}
