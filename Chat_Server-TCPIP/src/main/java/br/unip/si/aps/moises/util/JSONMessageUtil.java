package br.unip.si.aps.moises.util;

import java.io.File;
import java.nio.file.Files;

import com.github.openjson.JSONObject;

import lombok.NonNull;

public class JSONMessageUtil {	
	private JSONMessageUtil() {}
	
	private final static String PREFIX = "contratos/";
		
	public static JSONObject getMessageErro(@NonNull String message) {		
		try {
			File file = new File(PREFIX + "error.json");
			StringBuilder template = new StringBuilder(Files.readString(file.toPath()));
			Integer start = template.toString().indexOf("{message}");
			template.replace(start, start + 9, message);
			
			
			return new JSONObject(template.toString());
			
		} catch (Exception e) {
			return null;
		}
	}

	public static JSONObject getMessageInfo(@NonNull String id,@NonNull String message) {
		int start;
		
		try {
			File file = new File(PREFIX + "info.json");
			StringBuilder template = new StringBuilder(Files.readString(file.toPath()));

			start = template.toString().indexOf("{id}");
			template.replace(start, start + 4, id);			
			
			start = template.toString().indexOf("{message}");
			template.replace(start, start + 9, message);
			return new JSONObject(template.toString());
			
		} catch (Exception e) {
			return null;
		}		
	}

	public static JSONObject getMessageRegistro(@NonNull String from) {
		try {
			File file = new File(PREFIX + "register.json");
			StringBuilder template = new StringBuilder(Files.readString(file.toPath()));
			Integer start = template.toString().indexOf("{from}");
			template.replace(start, start + 6, from);
			
			return new JSONObject(template.toString());
			
		} catch (Exception e) {
			return null;
		}		
	}

	public static JSONObject getMessageUnregistro(@NonNull String from) {
		try {
			File file = new File(PREFIX + "unregister.json");
			StringBuilder template = new StringBuilder(Files.readString(file.toPath()));
			Integer start = template.toString().indexOf("{from}");
			template.replace(start, start + 6, from);
			
			return new JSONObject(template.toString());
			
		} catch (Exception e) {
			return null;
		}	
	}
	
	@Deprecated
	public static JSONObject getMessageSend(String target, String from, String id, String message) {
		try {
			File file = new File(PREFIX + "send.json");
			StringBuilder template = new StringBuilder(Files.readString(file.toPath()));
			
			Integer start = template.toString().indexOf("{target}");
			template.replace(start, start + 8, target);

			start = template.toString().indexOf("{from}");
			template.replace(start, start + 6, from);

			start = template.toString().indexOf("{id}");
			template.replace(start, start + 4, id);

			start = template.toString().indexOf("{message}");
			template.replace(start, start + 9, message);
			return new JSONObject(template.toString());	
		} catch (Exception e) {
			return null;
		}		
	}
	public static JSONObject getMessageNotifyClosedUser(@NonNull String from) {
		try {
			File file = new File(PREFIX + "notify.json");
			StringBuilder template = new StringBuilder(Files.readString(file.toPath()));
			
			Integer start = template.toString().indexOf("{from}");
			template.replace(start, start + 6, from);
			return new JSONObject(template.toString());	
		} catch (Exception e) {
			return null;
		}	
	}
}
