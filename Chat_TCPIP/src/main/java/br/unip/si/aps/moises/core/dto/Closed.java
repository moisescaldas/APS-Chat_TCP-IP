package br.unip.si.aps.moises.core.dto;

import static br.unip.si.aps.moises.util.JSONMessageUtil.getMessageNotifyClosedUser;

import com.github.openjson.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Closed implements JSONSerializable{
	private String from;

	@Override
	public JSONObject getJson() {
		return getMessageNotifyClosedUser(from);
	}

	public static Closed loadFromJson(JSONObject json) {
		return new Closed(json.getJSONObject("header").getString("from"));
	}
}
