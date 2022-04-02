package br.unip.si.aps.moises.network;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import static br.unip.si.aps.moises.factory.IOStreamFactory.*;

import br.unip.si.aps.moises.util.MessageAction;
import br.unip.si.aps.moises.util.MessageListener;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;


// Classe que vai servir como proxy do servidor
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
		this.scanner = createSocketScanner(connection);
		this.printer = createSocketPrintStream(connection);
	}
	
	@Override
	public void run() {
		while(isSocketRunning()) {
			if (scanner.hasNext()) {
				serviceBus.onMessage(new MessageAction((Object)this, scanner.nextLine()));
			}
		}
	}
	
	@Override
	public void onMessage(MessageAction action) {
		printer.println(action.getMessage());
	}
	
	public Boolean isSocketRunning() {
		return !connection.isClosed();
	}
}
