package br.unip.si.aps.moises;

import static java.lang.System.out;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TesteAberturaDeConexao {
	
	@Before
	public void loadObjects() {
		ServerApp.main(new String[0]);
		out.println("Rodou o servidor");

	}
	
	@Test
	public void teste1() throws UnknownHostException, IOException, InterruptedException {
		Socket cliente = new Socket("localhost", 7777);
		Socket cliente2 = new Socket("localhost", 7777);
		cliente.close();
		Thread.sleep(10 * 1000);

	}
		
	@After
	public void closeObjects() {
	}
}
