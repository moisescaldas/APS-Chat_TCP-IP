package br.unip.si.aps.moises.core.factory;

import com.github.openjson.JSONObject;

import br.unip.si.aps.moises.observer.event.MessageEvent;

public class MessageEventFactory {
	private MessageEventFactory() {
		
	}
	public static MessageEvent createMessageEvent(Object source, String message) {
		return new MessageEvent(source, new JSONObject(message));
	}

	public static MessageEvent createMessageEvent(Object source, JSONObject message) {
		return new MessageEvent(source, message);
	}
}
