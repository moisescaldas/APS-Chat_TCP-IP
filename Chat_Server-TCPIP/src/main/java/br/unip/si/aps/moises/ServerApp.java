package br.unip.si.aps.moises;

import br.unip.si.aps.moises.bus.ServiceBusManager;
import br.unip.si.aps.moises.network.ProxyManager;

public class ServerApp {
	private ProxyManager proxy;
	private ServiceBusManager bus;
	
	public static void main( String[] args ){
		ServerApp self = new ServerApp();
		self.run();
	}
	
	public void run() {
		proxy.run();
	}
	
	public ServerApp() {
		bus = new ServiceBusManager();
		proxy = new ProxyManager(bus);
	}
}
