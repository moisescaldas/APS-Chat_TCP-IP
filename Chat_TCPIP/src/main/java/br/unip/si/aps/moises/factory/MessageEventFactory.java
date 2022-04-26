package br.unip.si.aps.moises.factory;

import com.github.openjson.JSONObject;

import br.unip.si.aps.moises.observer.event.MessageEvent;

public class MessageEventFactory {
	private MessageEventFactory() {
		
	}
	public static MessageEvent createMessageEvent(Object source, String message) {
		return new MessageEvent(source, new JSONObject(message));
	}
}
