package br.unip.si.aps.moises.core.dto;

import com.github.openjson.JSONObject;

import br.unip.si.aps.moises.util.JSONMessageUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Announce implements JSONSerializable {
	private String from;
	private String name;

	@Override
	public JSONObject getJson() {
		return JSONMessageUtil.getMessageAnnounce(from, name);
	}
	
	public static Announce loadFromJson(JSONObject json) {	
		return new Announce(
				json.getJSONObject("header").getString("from"),
				json.getJSONObject("body").getJSONObject("announce").getString("user"));
	}
	
}
