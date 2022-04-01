package br.unip.si.aps.moises.util;

import java.util.List;

public interface MessageObserver {
	public void actionMessage(List<MessageListener> literns, String message);
}
