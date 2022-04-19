package br.unip.si.aps.moises;

import org.junit.Test;

import br.unip.si.aps.moises.util.JsonMessageUtil;

public class TesteJsonMessages {
	
	@Test
	public void testeErro() {
		System.out.println(JsonMessageUtil.getMessageErro("Porque isso funcionou").toString());
	}
	
	@Test
	public void testeInfo() {
		System.out.println(JsonMessageUtil.getMessageInfo("ID", "Porque isso funcionou").toString());		
	}
	
	@Test
	public void testeRegistro() {
		System.out.println(JsonMessageUtil.getMessageRegistro("Porque isso funcionou").toString());
	}

	@Test
	public void testeUnregistro() {
		System.out.println(JsonMessageUtil.getMessageUnregistro("Porque isso funcionou").toString());
	}

	@Test
	public void testeSend() {
		System.out.println(JsonMessageUtil.getMessageSend("Destino", "Origem", "ID", "Porque isso funcionou").toString());
	}	
	
}
