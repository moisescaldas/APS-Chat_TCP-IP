package br.unip.si.aps.moises.util.factory;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class IOStreamFactory {
	private IOStreamFactory() {}
	
	public static Scanner socketScanner(Socket socket) {
		try {
			return new Scanner(socket.getInputStream());
		} catch (IOException e) {
			return null;
		}
	}
	
	public static PrintStream socketPrintStream(Socket socket) {
		try {
			return new PrintStream(socket.getOutputStream());
		} catch (IOException e) {
			return null;
		}
	}
}
