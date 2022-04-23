package br.unip.si.aps.moises.network.domain;

import static br.unip.si.aps.moises.factory.IOStreamFactory.*;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

import com.github.openjson.*;

import br.unip.si.aps.moises.observer.action.MessageAction;
import br.unip.si.aps.moises.observer.listener.*;
import br.unip.si.aps.moises.util.JsonMessageUtil;

import lombok.*;

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



	public NetworkProxy(@NonNull Socket socket, @NonNull MessageListener serviceBus, @NonNull CloseConnectionListener master) {
		this.socket = socket;
		if ((scanner = socketScanner(socket)) == null)
			Logger.getGlobal().warning(new RuntimeException("Client Close Connection!").getMessage());
		if ((output = socketPrintStream(socket)) == null)
			Logger.getGlobal().warning(new RuntimeException("Client Close Connection!").getMessage());

		this.id = socket.getPort();
		this.serviceBus = serviceBus;
		this.master = master;
	}

	@Override
	public void run() {
		while(isSocketRunning()) {
			if (scanner.hasNext()) {
				try {				
					serviceBus.onMessage(new MessageAction(this, new JSONObject(scanner.nextLine())));
				}catch(JSONException e) {
					output.println(JsonMessageUtil.getMessageErro("Não foi possivel converter a mensagem"));
				}
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
