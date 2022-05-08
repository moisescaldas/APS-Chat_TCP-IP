package br.unip.si.aps.moises.application.domain.bean;

import java.security.PrivateKey;
import java.security.PublicKey;

import br.unip.si.aps.moises.application.domain.manager.ApplicationIDList;
import br.unip.si.aps.moises.util.SecurityKeysUtil;
import lombok.Data;

@Data
public class LocalUser implements User{
	private String name;
	private PrivateKey privateKey;
	private PublicKey publicKey;

	private static LocalUser instance;
	private LocalUser(String name, PrivateKey privateKey, PublicKey publicKey){
		this.name = name;
		this.privateKey = privateKey;
		this.publicKey = publicKey;
		ApplicationIDList.getInstance()
			.setTargetList(null)
			.setTargetList(publicKey);
	}

	public static LocalUser newInstance(String name, PrivateKey privateKey, PublicKey publicKey) {
		return instance == null ? (instance = new LocalUser(name, privateKey, publicKey)) : instance;
	}

	public static LocalUser getInstance() {
		return instance;
	}
}
