package br.unip.si.aps.moises.core.network;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

import br.unip.si.aps.moises.application.domain.manager.ApplicationIDList;
import br.unip.si.aps.moises.core.bus.Coreographer;
import br.unip.si.aps.moises.core.factory.MessageEventFactory;
import br.unip.si.aps.moises.core.factory.SocketFactory;
import br.unip.si.aps.moises.core.factory.SocketIOFactory;
import br.unip.si.aps.moises.observer.event.MessageEvent;
import br.unip.si.aps.moises.observer.listener.MessageListener;
import br.unip.si.aps.moises.util.JSONMessageUtil;

public class NetworkProxy implements Runnable, MessageListener {
	private Socket socket;
	private Scanner scanner;
	private PrintStream stream;
	private MessageListener bus;

	private static NetworkProxy instance;

	private NetworkProxy(String hostname, Integer port) {
		if ((this.socket = SocketFactory.getSocket(hostname, port)) == null) System.exit(100);
		this.scanner = SocketIOFactory.createScanner(socket);
		this.stream = SocketIOFactory.createPrintStream(socket);
	}

	public static synchronized NetworkProxy newInstance(String hostname, Integer port) {
		return instance == null ? (instance = new NetworkProxy(hostname, port)) : instance;
	}

	public static NetworkProxy getInstance() {
		return instance;
	}

	@Override
	public void run() {
		this.bus = Coreographer.getInstance();
		Logger.getGlobal().info("Proxy Iniciado para comunicações");
		onMessage(new MessageEvent(null, 
				JSONMessageUtil.getMessageRegister(ApplicationIDList.getInstance().getIdList().get(1))));

		while(scanner.hasNext()) {
			try {
				String message = scanner.nextLine();
				bus.onMessage(MessageEventFactory.createMessageEvent(null, message));
			} catch (Exception e) {
				Logger.getGlobal().info(e.getMessage());
			}
		}
		ApplicationIDList.getInstance().getIdList().stream().filter(id -> !id.equals("0")).forEach(id ->
		onMessage(MessageEventFactory.createMessageEvent(null, JSONMessageUtil.getMessageUnregister(id))));
		closeObjects();
	}

	@Override
	public void onMessage(MessageEvent event) {
		stream.println(event.getMessage());
	}

	private void closeObjects() {
		try {
			scanner.close();
			stream.close();
			socket.close();
		} catch (Exception e) {
		}
		Logger.getGlobal().info("Proxy Fechado");
	}
}
