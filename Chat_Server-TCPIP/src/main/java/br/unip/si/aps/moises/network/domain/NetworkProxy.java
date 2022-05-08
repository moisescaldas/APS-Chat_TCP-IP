package br.unip.si.aps.moises.network.domain;

import static br.unip.si.aps.moises.factory.IOStreamFactory.socketPrintStream;
import static br.unip.si.aps.moises.factory.IOStreamFactory.socketScanner;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

import com.github.openjson.JSONException;
import com.github.openjson.JSONObject;

import br.unip.si.aps.moises.bus.Coreographer;
import br.unip.si.aps.moises.observer.action.MessageAction;
import br.unip.si.aps.moises.observer.listener.CloseConnectionListener;
import br.unip.si.aps.moises.observer.listener.MessageListener;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
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
	private CloseConnectionListener master;

	public NetworkProxy(@NonNull Socket socket, @NonNull CloseConnectionListener master) {
		this.socket = socket;
		if ((scanner = socketScanner(socket)) == null)
			Logger.getGlobal().warning(new RuntimeException("Client Close Connection!").getMessage());
		if ((output = socketPrintStream(socket)) == null)
			Logger.getGlobal().warning(new RuntimeException("Client Close Connection!").getMessage());
		this.id = socket.getPort();
		this.serviceBus = Coreographer.getInstance();
		this.master = master;
	}

	@Override
	public void run() {		
		while(scanner.hasNext()) {
			try {				
				serviceBus.onMessage(new MessageAction(this, new JSONObject(scanner.nextLine())));
			}catch(JSONException e) {
				Logger.getGlobal().info("Mensagem não decodificada");
			}
		}		

		master.notifyConnectionClosed(this);
		try {
			socket.close();
			scanner.close();
			output.close();
		} catch (Exception e) {
			Logger.getGlobal().warning(e.getMessage());
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
