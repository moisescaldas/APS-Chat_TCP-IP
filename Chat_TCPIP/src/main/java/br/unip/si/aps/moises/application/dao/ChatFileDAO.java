package br.unip.si.aps.moises.application.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import com.github.openjson.JSONArray;
import com.github.openjson.JSONObject;

import br.unip.si.aps.moises.application.domain.bean.ChatFile;

public class ChatFileDAO {
	private final String PATH = "files/.channels/";
	
	public void save(ChatFile chat) {
		String data = getJSONString(chat);
		File file = new File(PATH + chat.getId());
		try (FileOutputStream writer = new FileOutputStream(file)){
			ByteBuffer wrap = ByteBuffer.allocate(4);
			wrap.putInt(data.length());
			writer.write(wrap.array());
			writer.write(data.getBytes());
		} catch (IOException e) {
		}
	}
	
	public ChatFile load(String id) {
		File file = new File(PATH + id);
		byte[] b;
		
		try (FileInputStream stream = new FileInputStream(file)){
			stream.read((b = new byte[4]));
			ByteBuffer wrap = ByteBuffer.wrap(b);
			stream.read((b = new byte[wrap.getInt()]));
			return getChatFile(new String(b));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String getJSONString(ChatFile chat) {
		JSONObject json = new JSONObject();
		json.put("username", chat.getUsername());
		json.put("id", chat.getId());
		JSONArray array = new JSONArray();
		chat.getMessages().forEach(message -> array.put(message));
		json.put("messages", array);
		return json.toString();
	}
	
	private ChatFile getChatFile(String data) {
		JSONObject json = new JSONObject(data);
		ChatFile chat = new ChatFile();
		chat.setUsername(json.getString("username"));
		chat.setId(json.getString("id"));
		json.getJSONArray("messages").forEach(message -> chat.getMessages().add((String) message));
		return chat;
	}
}
