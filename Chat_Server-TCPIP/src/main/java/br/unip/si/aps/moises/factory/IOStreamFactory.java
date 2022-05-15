package br.unip.si.aps.moises.factory;

import java.net.Socket;
import java.util.Scanner;

import lombok.NonNull;

import java.io.*;

public class IOStreamFactory {
	private IOStreamFactory() {}
	
	public static Scanner socketScanner(@NonNull Socket socket) {
		try {
			return new Scanner(socket.getInputStream());
		} catch (IOException e) {
			return null;
		}
	}
	
	public static PrintStream socketPrintStream(@NonNull Socket socket) {
		try {
			return new PrintStream(socket.getOutputStream());
		} catch (IOException e) {
			return null;
		}
	}
}
