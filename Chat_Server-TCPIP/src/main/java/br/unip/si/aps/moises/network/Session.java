package br.unip.si.aps.moises.network;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import br.unip.si.aps.moises.factory.IOStreamFactory;
import br.unip.si.aps.moises.util.MessageListener;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Session implements Runnable, MessageListener {
	@Include
	private Socket connection;
	private Scanner scanner;
	private PrintStream printer;
	private MessageListener serviceBus;
	
	public Session(Socket connection, MessageListener serviceBus) {
		this.connection = connection;
		this.serviceBus = serviceBus;
		
		loadObjects();
		
	}
	
	private void loadObjects() {
		this.scanner = IOStreamFactory.getSocketScanner(connection);
		this.printer = IOStreamFactory.getSockePrintStream(connection);
	}
	
	@Override
	public void run() {
		while(connection.isConnected()) {
			if (scanner.hasNext()) {
				serviceBus.onMessage(scanner.nextLine());
			}
		}
	}
	
	@Override
	public void onMessage(String message) {
		printer.println(message);
	}
}
