package br.unip.si.aps.moises.network;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import br.unip.si.aps.moises.bus.Coreographer;
import br.unip.si.aps.moises.factory.MessageEventFactory;
import br.unip.si.aps.moises.factory.SocketFactory;
import br.unip.si.aps.moises.factory.SocketIOFactory;
import br.unip.si.aps.moises.observer.event.MessageEvent;
import br.unip.si.aps.moises.observer.listener.MessageListener;

public class NetworkProxy implements Runnable, MessageListener {
	private Socket socket;
	private Scanner scanner;
	private PrintStream stream;
	private MessageListener bus;
	
	private static NetworkProxy instance;
	
	private NetworkProxy(String hostname, Integer port) {
		this.socket = SocketFactory.getSocket(hostname, port);
		this.scanner = SocketIOFactory.createScanner(socket);
		this.stream = SocketIOFactory.createPrintStream(socket);
		this.bus = Coreographer.getInstance();
	}
	
	public static synchronized NetworkProxy newInstance(String hostname, Integer port) {
		return (instance = new NetworkProxy(hostname, port));
	}
	
	public static NetworkProxy getInstance() {
		return instance;
	}

	@Override
	public void run() {
		while (isSocketRunning())
			if(scanner.hasNext())
				bus.onMessage(MessageEventFactory.createMessageEvent(null, scanner.nextLine()));
		
		closeObjects();
	}
	
	public Boolean isSocketRunning() {
		try {
			socket.getOutputStream().write(0);
			return true;
		} catch(Exception e) {
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
	}
}
