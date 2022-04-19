package br.unip.si.aps.moises.observer.listener;

import br.unip.si.aps.moises.observer.action.MessageAction;

public interface MessageListener {
	public void onMessage(MessageAction action);
}
