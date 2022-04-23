package br.unip.si.aps.moises;

import br.unip.si.aps.moises.bus.MessageBus;
import br.unip.si.aps.moises.network.manager.NetworkProxyManager;

public class ServerApp {
	public static void main(String[] args) {
		var manager = new NetworkProxyManager(new MessageBus());
		manager.run();
	}
	
	public static void stop() {
		System.exit(0);
	}
}
