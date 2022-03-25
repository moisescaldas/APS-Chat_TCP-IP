package br.unip.si.aps.moises.network;

import java.net.Socket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Session implements Runnable{
	private Socket connection;
	
	public Session() {
		
	}
	
	@Override
	public void run() {
		
	}

}
