package br.unip.si.aps.moises.factory;

import com.github.openjson.JSONObject;

public class JSONFactory {
	private JSONFactory() {}
	
	public static JSONObject createJSONObject(String jsonString) {
		return new JSONObject(jsonString);
	}
}
