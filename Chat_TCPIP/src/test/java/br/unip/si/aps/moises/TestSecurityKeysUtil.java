package br.unip.si.aps.moises;

import static org.junit.Assert.assertEquals;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import br.unip.si.aps.moises.util.SecurityKeysUtil;

public class TestSecurityKeysUtil {
	@Test
	public void test() throws NoSuchAlgorithmException {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(2048);
		KeyPair pair = generator.generateKeyPair();
		
		assertEquals(new String(SecurityKeysUtil.encodePrivateKey(pair.getPrivate()).getBytes()),
				new String(SecurityKeysUtil.encodePrivateKey(SecurityKeysUtil.decodePrivateKey(SecurityKeysUtil.encodePrivateKey(pair.getPrivate()))).getBytes()));
	}
}
