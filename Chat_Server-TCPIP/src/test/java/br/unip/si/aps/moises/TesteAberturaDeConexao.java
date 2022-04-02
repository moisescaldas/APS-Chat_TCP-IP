package br.unip.si.aps.moises;

import static java.lang.System.out;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.unip.si.aps.moises.bus.ServiceBusManager;
import br.unip.si.aps.moises.network.ProxyManager;


public class TesteAberturaDeConexao {
	private ProxyManager cm;
	private ServiceBusManager sb;
	
	@Before
	public void loadObjects() {
		sb = new ServiceBusManager();
		cm = new ProxyManager(sb);
		new Thread(cm).start();
		out.println("Criou os objetos e rodou o servidor");

	}
	
	@Test
	public void teste1() throws UnknownHostException, IOException {
		Socket cliente = new Socket("localhost", 7777);
		
		Scanner scanner = new Scanner(cliente.getInputStream());
		PrintStream printer = new PrintStream(cliente.getOutputStream());
		// Não sei escrever teclado vei kkk
		Scanner keyboard = new Scanner(System.in);
		
		new Thread(()-> {
			while(!cliente.isClosed()) {
				out.println("Etrou aqui");
				printer.println("Teste :)");
			}			
		}).start();
		
		new Thread(()-> {
			while(!cliente.isClosed()) {
				if(scanner.hasNext()) {
					out.println(scanner.nextLine());
				}
			}
		}).start();
		
		cliente.close();
		keyboard.close();
		out.println("Executado teste1");
	}
	
	@After
	public void closeObjects() {
	}
}
