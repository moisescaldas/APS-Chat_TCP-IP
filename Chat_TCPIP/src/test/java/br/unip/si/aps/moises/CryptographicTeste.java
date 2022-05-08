package br.unip.si.aps.moises;

import static org.junit.Assert.assertEquals;

import java.security.KeyPair;

import org.junit.Test;

import br.unip.si.aps.moises.util.SecurityKeysUtil;
import br.unip.si.aps.moises.util.CryptographicUtil;

public class CryptographicTeste {
	@Test
	public void test() {
		KeyPair pair = SecurityKeysUtil.newKeyPair(4096);
		
		String secretMessage = "Eu gosto de torta prestigio";	
		String encryptedMessage = CryptographicUtil.encrypt(pair.getPublic(), secretMessage);
		assertEquals(secretMessage, CryptographicUtil.decrypt(pair.getPrivate(), encryptedMessage));
	}
}
