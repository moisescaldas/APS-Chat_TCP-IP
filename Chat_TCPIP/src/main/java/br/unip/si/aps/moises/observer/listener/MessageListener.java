package br.unip.si.aps.moises.observer.listener;

import br.unip.si.aps.moises.observer.event.MessageEvent;

public interface MessageListener {
	public void onMessage(MessageEvent event);
}
