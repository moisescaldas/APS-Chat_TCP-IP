package br.unip.si.aps.moises.observer.listener;

import br.unip.si.aps.moises.network.domain.NetworkProxy;

public interface CloseConnectionListener {
	public void notifyConnectionClosed(NetworkProxy proxy);
}
