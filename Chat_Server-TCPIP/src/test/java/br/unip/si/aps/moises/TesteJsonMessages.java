package br.unip.si.aps.moises;

import org.junit.Test;

import br.unip.si.aps.moises.util.JSONMessageUtil;

public class TesteJsonMessages {
	
	@Test
	public void testeErro() {
		System.out.println(JSONMessageUtil.getMessageErro("Porque isso funcionou").toString());
	}
	
	@Test
	public void testeInfo() {
		System.out.println(JSONMessageUtil.getMessageInfo("ID", "Porque isso funcionou").toString());		
	}
	
	@Test
	public void testeRegistro() {
		System.out.println(JSONMessageUtil.getMessageRegistro("Porque isso funcionou").toString());
	}

	@Test
	public void testeUnregistro() {
		System.out.println(JSONMessageUtil.getMessageUnregistro("Porque isso funcionou").toString());
	}

	@Test
	public void testeNotifyClosedUser() {
		System.out.println(JSONMessageUtil.getMessageNotifyClosedUser("Origem").toString());
	}	
	
}
