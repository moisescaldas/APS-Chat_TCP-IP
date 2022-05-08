package br.unip.si.aps.moises.core.dto;

import com.github.openjson.JSONObject;

import br.unip.si.aps.moises.util.JSONMessageUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Acknowledge implements JSONSerializable{
	private String target;
	private String from;
	private String name;

	@Override
	public JSONObject getJson() {
		return JSONMessageUtil.getMessageAcknowledge(target, from, name);
	}
	
	public static Acknowledge loadFromJson(JSONObject json) {	
		return new Acknowledge(
				json.getJSONObject("header").getString("target"),
				json.getJSONObject("header").getString("from"),
				json.getJSONObject("body").getJSONObject("acknowledge").getString("user"));
	}
}
