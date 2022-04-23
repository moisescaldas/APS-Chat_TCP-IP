package br.unip.si.aps.moises;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.openjson.JSONObject;

import br.unip.si.aps.moises.bus.MessageBus;
import br.unip.si.aps.moises.network.manager.NetworkProxyManager;
import br.unip.si.aps.moises.util.JsonMessageUtil;

public class TesteOrquestracao {
	private static NetworkProxyManager manager;
	private Socket socket;
	private Scanner scanner;
	private PrintStream out;
	
	@BeforeClass
	public static void server() {
		(manager = new NetworkProxyManager(new MessageBus())).run();
	}
	
	@Before
	public void loadObjects() throws UnknownHostException, IOException {
		socket = new Socket("localhost", 7777);
		scanner = new Scanner(socket.getInputStream());
		out = new PrintStream(socket.getOutputStream());
	}
	
	public void testeRegistro() throws NoSuchAlgorithmException, InterruptedException {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(2048);
		KeyPair pair = generator.generateKeyPair();
		
		String pbk = Base64.getEncoder().encodeToString(pair.getPublic().getEncoded());	
		JSONObject json = JsonMessageUtil.getMessageRegistro(pbk);
		out.println(json);
		Thread.currentThread();
		Thread.sleep(1000);
		if(scanner.hasNext())
			System.out.println(scanner.nextLine());

		json = JsonMessageUtil.getMessageUnregistro(pbk);
		out.println(json);
		Thread.currentThread();
		Thread.sleep(1000);
		if(scanner.hasNext())
			System.out.println(scanner.nextLine());
		
	}
	
	@Test
	public void testeSend() throws NoSuchAlgorithmException, UnknownHostException, IOException, InterruptedException {
		var generatorA = KeyPairGenerator.getInstance("RSA");
		var generatorB = KeyPairGenerator.getInstance("RSA");

		
		generatorA.initialize(2048);
		var pbkA = Base64.getEncoder().encodeToString(generatorA.genKeyPair().getPublic().getEncoded());
		
		generatorB.initialize(2048);
		var pbkB = Base64.getEncoder().encodeToString(generatorB.genKeyPair().getPublic().getEncoded());
		
		var socketA = new Socket("localhost", 7777);
		var socketB = new Socket("localhost", 7777);
		
		var scannerB = new Scanner(socketB.getInputStream());
		
		var printerA = new PrintStream(socketA.getOutputStream());
		var printerB = new PrintStream(socketB.getOutputStream());
		
		printerA.println(JsonMessageUtil.getMessageRegistro(pbkA));
		printerB.println(JsonMessageUtil.getMessageRegistro(pbkB));
		
		var t = new Thread(() -> {
			while(!socketB.isClosed())
					if(scannerB.hasNext())
						System.out.println(scannerB.nextLine());
		});
		
		t.start();
		
		Thread.sleep(1000);					
		printerA.println(JsonMessageUtil.getMessageSend(pbkB, pbkA, "1234", "Olá"));
		Thread.sleep(1000);		
		
		socketA.close();
		socketB.close();
		scannerB.close();
		printerA.close();
		printerB.close();
		t.join();
		

	}
	
	@After
	public void closeObjects() throws IOException {
		scanner.close();
		out.close();
		socket.close();
	}
	
	@AfterClass
	public static void closeServer() throws InterruptedException {
		manager.stop();
		Thread.sleep(1000);
	}

}
