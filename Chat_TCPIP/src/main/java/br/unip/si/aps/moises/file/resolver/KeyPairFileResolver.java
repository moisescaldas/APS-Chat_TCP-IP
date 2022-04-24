package br.unip.si.aps.moises.file.resolver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;

import lombok.NonNull;

public class KeyPairFileResolver {
	private final String ROOT;
	
	private static KeyPairFileResolver instance;
	
	private KeyPairFileResolver() {
		this.ROOT = "./files/keypair";
	}
	
	public static synchronized KeyPairFileResolver getInstance() {
		return instance == null ? (instance = new KeyPairFileResolver()) : instance;
	}
		
	public String savePublicKey(@NonNull final PublicKey key, @NonNull final String name) {
		File keyFile = new File(ROOT + "/" + name + ".rsa.pub");
		try(FileOutputStream fos = new FileOutputStream(keyFile)){
			fos.write(key.getEncoded());
			return keyFile.getCanonicalPath();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String savePrivateKey(PrivateKey key, String name) {
		File keyFile = new File(ROOT + "/" + name + ".rsa");
		try(FileOutputStream fos = new FileOutputStream(keyFile)){
			fos.write(key.getEncoded());
			return keyFile.getCanonicalPath();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
