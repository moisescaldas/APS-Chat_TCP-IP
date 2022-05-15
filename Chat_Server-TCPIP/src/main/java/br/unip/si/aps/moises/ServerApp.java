package br.unip.si.aps.moises;

import br.unip.si.aps.moises.bus.Coreographer;
import br.unip.si.aps.moises.network.manager.ConnectionPoolManager;
import br.unip.si.aps.moises.network.manager.NetworkProxyManager;

public class ServerApp {
	public static void main(String[] args) {
		ConnectionPoolManager.getInstance();
		Coreographer.getInstance();
		new NetworkProxyManager().run();
	}
	
	public static void stop() {
		System.exit(0);
	}
}
