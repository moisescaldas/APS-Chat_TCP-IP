package br.unip.si.aps.moises.factory;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class IOStreamFactory {
	private IOStreamFactory() {}
	
	public static Scanner getSocketScanner(Socket socket) {
		try {
			return new Scanner(socket.getInputStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static PrintStream getSockePrintStream(Socket socket) {
		try {
			return new PrintStream(socket.getOutputStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}		
	}
}
