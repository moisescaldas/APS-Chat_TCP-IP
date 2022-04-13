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

	}
	
	@Test
	@SuppressWarnings("unused")
	public void teste1() throws UnknownHostException, IOException, InterruptedException {
		Socket cliente1 = new Socket("localhost", 7777);
		Socket cliente2 = new Socket("localhost", 7777);
		cliente1.close();
		Thread.sleep(100);
		Socket cliente3 = new Socket("localhost", 7777);
		cliente2.close();
		Socket cliente4 = new Socket("localhost", 7777);


	}
		
	@After
	public void closeObjects() {
		out.println("Teste finalizado");
	}
}
