package br.unip.si.aps.moises.domain;

import java.security.PrivateKey;
import java.security.PublicKey;

import lombok.Data;

@Data
public class UserKeychain implements Keychain{
	private PrivateKey privateKey;
	private PublicKey publicKey;
	
	private static UserKeychain instance;
	private UserKeychain(PrivateKey privateKey, PublicKey publicKey){
		this.privateKey = privateKey;
		this.publicKey = publicKey;
		ApplicationIDList.getInstance()
			.setTargetList(null)
			.setTargetList(publicKey);
	}
	
	public static UserKeychain newInstance(PrivateKey privateKey, PublicKey publicKey) {
		return (instance = new UserKeychain(privateKey, publicKey));
	}

	public static UserKeychain getInstance() {
		return instance;
	}
}
