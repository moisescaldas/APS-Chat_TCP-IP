package br.unip.si.aps.moises.application.domain.bean;

import java.util.LinkedList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ChatFile {
	String username;
	String id;
	List<String> messages = new LinkedList<>();
}
