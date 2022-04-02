package br.unip.si.aps.moises;

import static java.lang.System.out;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.unip.si.aps.moises.network.ProxyManager;

public class TesteAberturaDeConexao {
	private ProxyManager cm;
	private Thread proxyThread;
	
	@Before
	public void loadObjects() {
		cm = new ProxyManager();
		out.println("Criou os objetos");

	}
	
	@Test
	public void teste1() throws UnknownHostException, IOException {
		out.println("Executando teste1");
		new Thread(cm).start();
		new Socket("localhost", 7777);
		new Socket("localhost", 7777);
		cm.closeSocket();
		new Socket("localhost", 7777);
		new Socket("localhost", 7777);

	}
	
	@After
	public void closeObjects() {
	}
}
