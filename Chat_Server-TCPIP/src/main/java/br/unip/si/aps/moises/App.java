package br.unip.si.aps.moises;

import br.unip.si.aps.moises.bus.ServiceBusManager;
import br.unip.si.aps.moises.network.ProxyManager;

public class App {
	private ProxyManager proxy;
	private ServiceBusManager bus;
	
	public static void main( String[] args ){
		App self = new App();
		self.run();
	}
	
	public void run() {
		proxy.run();
	}
	
	public App() {
		bus = new ServiceBusManager();
		proxy = new ProxyManager(bus);
	}
}
