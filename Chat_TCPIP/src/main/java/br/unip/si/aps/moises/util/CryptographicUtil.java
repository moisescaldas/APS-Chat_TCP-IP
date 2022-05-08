package br.unip.si.aps.moises.util;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.crypto.Cipher;

public class CryptographicUtil {
	private static ExecutorService executor = Executors.newSingleThreadExecutor();
	private static Cipher cipher;


	private static synchronized Cipher getCipher() {
		try {
			return cipher == null ? (cipher = Cipher.getInstance("RSA")) : cipher;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String encrypt(PublicKey key, String secretMessage) {
		Future<String> future = executor.submit(() -> {
			try {
				Cipher cipher = getCipher();
				cipher.init(Cipher.ENCRYPT_MODE, key);

				return Base64.getEncoder().encodeToString(cipher.doFinal(secretMessage.getBytes()));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
		
		try {
			return future.get();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String decrypt(PrivateKey key, String secretMessage) {
		Future<String> future = executor.submit(() -> {
			try {
				Cipher cipher = getCipher();
				cipher.init(Cipher.DECRYPT_MODE, key);
				
				return new String(cipher.doFinal(Base64.getDecoder().decode(secretMessage.getBytes())));
			} catch(Exception e) {
				throw new RuntimeException(e);
			}
		});

		try {
			return future.get();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
