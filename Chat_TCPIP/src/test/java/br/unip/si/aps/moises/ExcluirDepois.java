package br.unip.si.aps.moises;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.unip.si.aps.moises.application.domain.bean.RemoteUser;

public class ExcluirDepois {

	@Test
	public void test() {
		assertEquals(new RemoteUser("Nome", "ID"), new RemoteUser("Nome", "ID"));
	}
}
