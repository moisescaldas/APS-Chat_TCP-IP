package br.unip.si.aps.moises.factory;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class SocketIOFactory {
	private SocketIOFactory() {}
	
	public static Scanner createScanner(Socket socket) {
		try {
			return new Scanner(socket.getInputStream());
		} catch(Exception e) {
			return null;
		}
	}

	public static PrintStream createPrintStream(Socket socket) {
		try {
			return new PrintStream(socket.getOutputStream());
		} catch(Exception e) {
			return null;
		}
	}
	
}
