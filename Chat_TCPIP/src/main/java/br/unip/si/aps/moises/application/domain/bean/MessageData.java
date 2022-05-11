package br.unip.si.aps.moises.application.domain.bean;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MessageData {
	private String message, author;
	private LocalDateTime date;
	
	public MessageData(String author, String message) {
		this.author = author;
		this.message = message;
		this.date = LocalDateTime.now();
	}
	
	public String getFormatedMessage() {	
		return "<" + date.toString() + ">" + author + ":" + message;
	}
}
