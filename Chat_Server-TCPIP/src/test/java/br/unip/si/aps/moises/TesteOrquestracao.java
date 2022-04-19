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

import br.unip.si.aps.moises.bus.MessageBusManager;
import br.unip.si.aps.moises.network.manager.NetworkProxyManager;
import br.unip.si.aps.moises.util.JsonMessageUtil;

public class TesteOrquestracao {
	private static NetworkProxyManager manager;
	private Socket socket;
	private Scanner scanner;
	private PrintStream out;
	
	@BeforeClass
	public static void server() {
		(manager = new NetworkProxyManager(new MessageBusManager())).run();
	}
	
	@Before
	public void loadObjects() throws UnknownHostException, IOException {
		socket = new Socket("localhost", 7777);
		scanner = new Scanner(socket.getInputStream());
		out = new PrintStream(socket.getOutputStream());
	}
	
	@Test
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
