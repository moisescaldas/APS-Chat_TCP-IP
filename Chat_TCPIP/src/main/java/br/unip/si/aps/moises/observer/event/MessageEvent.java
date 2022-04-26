package br.unip.si.aps.moises.observer.event;

import com.github.openjson.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageEvent {
	private Object source;
	private JSONObject message;
}
