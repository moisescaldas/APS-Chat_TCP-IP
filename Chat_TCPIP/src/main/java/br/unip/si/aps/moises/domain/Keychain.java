package br.unip.si.aps.moises.domain;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface Keychain {
	public PrivateKey getPrivateKey();
	public void setPrivateKey(PrivateKey key);

	public PublicKey getPublicKey();
	public void setPublicKey(PublicKey key);	
}
