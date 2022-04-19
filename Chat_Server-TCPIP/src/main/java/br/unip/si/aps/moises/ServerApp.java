package br.unip.si.aps.moises;

import br.unip.si.aps.moises.bus.MessageBusManager;
import br.unip.si.aps.moises.network.manager.NetworkProxyManager;

public class ServerApp {
	public static void main(String[] args) {
		new NetworkProxyManager(new MessageBusManager()).run();
	}
	
	public static void stop() {
		System.exit(0);
	}
}
