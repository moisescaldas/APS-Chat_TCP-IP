package br.unip.si.aps.moises.core.dto;

import com.github.openjson.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static br.unip.si.aps.moises.util.JSONMessageUtil.getMessageMessage;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message implements JSONSerializable{
	private String target;
	private String from;
	private String name;
	private String message;
	private String date;

	@Override
	public JSONObject getJson() {
		return getMessageMessage(target, from, name, message, date);
	}

	public static Message loadFromJson(JSONObject json) {
		return new Message(
				json.getJSONObject("header").getString("target"),
				json.getJSONObject("header").getString("from"),
				json.getJSONObject("body").getJSONObject("message").getString("user"),
				json.getJSONObject("body").getJSONObject("message").getString("message"),
				json.getJSONObject("body").getJSONObject("message").getString("dateTime"));
	}

}
