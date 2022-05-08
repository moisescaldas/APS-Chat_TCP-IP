package br.unip.si.aps.moises.core.network;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

import br.unip.si.aps.moises.core.bus.Coreographer;
import br.unip.si.aps.moises.core.factory.MessageEventFactory;
import br.unip.si.aps.moises.core.factory.SocketFactory;
import br.unip.si.aps.moises.core.factory.SocketIOFactory;
import br.unip.si.aps.moises.observer.event.MessageEvent;
import br.unip.si.aps.moises.observer.listener.MessageListener;

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
		this.bus = Coreographer.getInstance();
	}

	public static synchronized NetworkProxy newInstance(String hostname, Integer port) {
		return instance == null ? (instance = new NetworkProxy(hostname, port)) : instance;
	}

	public static NetworkProxy getInstance() {
		return instance;
	}

	@Override
	public void run() {
		Logger.getGlobal().info("Proxy Iniciado para comunicações");
		while(isSocketRunning())
			while(scanner.hasNext()) 
				bus.onMessage(MessageEventFactory.createMessageEvent(null, scanner.nextLine()));
		closeObjects();
	}

	public Boolean isSocketRunning() {
		try {
			return socket.getInetAddress().isReachable(0);
		} catch (Exception e) {
			return false;
		}
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
