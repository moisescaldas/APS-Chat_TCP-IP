package br.unip.si.aps.moises;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.unip.si.aps.moises.util.JSONMessageUtil;

public class JsonMessageUtil {
	@Test
	public void testAnnounce() {
		assertTrue(JSONMessageUtil.getMessageAnnounce("Origem", "Nome") != null);
	}

	@Test
	public void testAcknowledge() {
		assertTrue(JSONMessageUtil.getMessageAcknowledge("Destino", "Origem", "Nome") != null);
	}
	
	@Test
	public void testMessage() {
		assertTrue(JSONMessageUtil.getMessageMessage("Destino", "Origem", "Nome", "Mensagem", "Data") != null);
	}
}
