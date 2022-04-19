package br.unip.si.aps.moises.observer.action;

import com.github.openjson.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MessageAction {
	private Object source;
	private JSONObject message;
}
