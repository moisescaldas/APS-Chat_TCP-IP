package br.unip.si.aps.moises.util;

import java.io.File;
import java.nio.file.Files;

import com.github.openjson.JSONObject;

public class JSONMessageUtil {	
	private JSONMessageUtil() {}
	
	private final static String PREFIX = "contratos/";
		
		public static JSONObject getMessageRegister(String from) {
		try {
			File file = new File(PREFIX + "register.json");
			StringBuilder template = new StringBuilder(new String(Files.readAllBytes(file.toPath())));
			Integer start = template.toString().indexOf("{from}");
			template.replace(start, start + 6, from);
			
			return new JSONObject(template.toString());
			
		} catch (Exception e) {
			return null;
		}		
	}

	public static JSONObject getMessageUnregistro(String from) {
		try {
			File file = new File(PREFIX + "unregister.json");
			StringBuilder template = new StringBuilder(new String(Files.readAllBytes(file.toPath())));
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
			StringBuilder template = new StringBuilder(new String(Files.readAllBytes(file.toPath())));
			
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
	
	public static JSONObject getMessageAnnounce(String from, String name) {
		try {
			StringBuilder template = new StringBuilder(new String(Files.readAllBytes(new File(PREFIX + "announce.json").toPath())));
			int start;
			
			start = template.toString().indexOf("{from}");
			template.replace(start, start + 6, from);

			start = template.toString().indexOf("{name}");
			template.replace(start, start + 6, name);

			return new JSONObject(template.toString());
		} catch (Exception e) {
			return null;
		}
	}

	public static JSONObject getMessageAcknowledge(String target, String from, String name) {
		try {
			File file = new File(PREFIX + "acknowledge.json");
			StringBuilder template = new StringBuilder(new String(Files.readAllBytes(file.toPath())));
			
			Integer start = template.toString().indexOf("{target}");
			template.replace(start, start + 8, target);

			start = template.toString().indexOf("{from}");
			template.replace(start, start + 6, from);

			start = template.toString().indexOf("{name}");
			template.replace(start, start + 6, name);			

			return new JSONObject(template.toString());
		} catch (Exception e) {
			return null;
		}
	}
	
	public static JSONObject getMessageMessage(String target, String from, String name, String message, String date) {
		try {
			File file = new File(PREFIX + "message.json");
			StringBuilder template = new StringBuilder(new String(Files.readAllBytes(file.toPath())));
			
			Integer start = template.toString().indexOf("{target}");
			template.replace(start, start + 8, target);

			start = template.toString().indexOf("{from}");
			template.replace(start, start + 6, from);

			start = template.toString().indexOf("{name}");
			template.replace(start, start + 6, name);

			start = template.toString().indexOf("{message}");
			template.replace(start, start + 9, message);

			start = template.toString().indexOf("{time}");
			template.replace(start, start + 6, date);
			
			return new JSONObject(template.toString());
		} catch (Exception e) {
			return null;
		}
	}
}
