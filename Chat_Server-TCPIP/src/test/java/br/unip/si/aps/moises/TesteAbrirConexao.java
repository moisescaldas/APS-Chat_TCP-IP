package br.unip.si.aps.moises;

import static java.lang.System.out;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import br.unip.si.aps.moises.bus.MessageBus;
import br.unip.si.aps.moises.network.manager.NetworkProxyManager;

public class TesteAbrirConexao {
	private static NetworkProxyManager manager;
	
	@BeforeClass
	public static void loadObjects() {
		(manager = new NetworkProxyManager(new MessageBus())).run();	
	}

	@Test
	public void teste1() throws UnknownHostException, IOException, InterruptedException {
		Socket client1 = new Socket("localhost", 7777);
		client1.close();
		Socket client2 = new Socket("localhost", 7777);
		Socket client3 = new Socket("localhost", 7777);
		Socket client4 = new Socket("localhost", 7777);
		client2.close();
		Socket client5 = new Socket("localhost", 7777);
		Thread.currentThread();
		Thread.sleep(1000);

		client3.close();
		client4.close();
		client5.close();

	}

	@Test
	public void teste2() throws UnknownHostException, IOException, InterruptedException {
		Socket client = new Socket("localhost", 7777);
		Scanner scanner = new Scanner(client.getInputStream());
		new PrintStream(client.getOutputStream()).println("dasdasdas}");
		Thread.currentThread();
		Thread.sleep(1000);

		if(scanner.hasNext())
			out.println(scanner.nextLine());

		client.close();
		Thread.sleep(1000);
	}
	
	@AfterClass
	public static void closeObjects() throws InterruptedException {
		manager.stop();
		Thread.sleep(1000);
	}
	
}
