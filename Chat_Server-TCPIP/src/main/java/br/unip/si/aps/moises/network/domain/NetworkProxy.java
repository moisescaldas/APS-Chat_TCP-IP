package br.unip.si.aps.moises.network.domain;

import static br.unip.si.aps.moises.util.factory.IOStreamFactory.socketPrintStream;
import static br.unip.si.aps.moises.util.factory.IOStreamFactory.socketScanner;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import com.github.openjson.JSONObject;

import br.unip.si.aps.moises.bus.MessageAction;
import br.unip.si.aps.moises.bus.MessageListener;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class NetworkProxy implements Runnable, MessageListener{
	@EqualsAndHashCode.Include
	@ToString.Include
	private Integer id;
	private Socket socket;
	private Scanner scanner;
	private PrintStream output;
	private MessageListener serviceBus;
	
	
	
	public NetworkProxy(Socket socket) {
		super();
		this.socket = socket;
		if ((scanner = socketScanner(socket)) == null)
			throw new RuntimeException("Client Close Connection!");
		if ((output = socketPrintStream(socket)) == null)
			throw new RuntimeException("Client Close Connection!");
		this.id = socket.getPort();
		
	}
	
	@Override
	public void run() {
		while(isSocketRunning()) {
			if (scanner.hasNext()) {
				serviceBus.onMessage(
						new MessageAction(this, new JSONObject(scanner.nextLine())));
			}
		}
	}

	public Boolean isSocketRunning() {
			try {
				socket.getOutputStream().write(0);
				return true;
			} catch (IOException e) {
				return false;
			}
	}

	@Override
	public void onMessage(MessageAction action) {
		output.println(action.getMessage());
	}
	
	public NetworkProxy setServiceBus(MessageListener serviceBus) {
		this.serviceBus = serviceBus;
		return this;
	}
}
