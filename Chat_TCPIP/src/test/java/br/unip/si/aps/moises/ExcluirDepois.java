package br.unip.si.aps.moises;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.unip.si.aps.moises.application.dao.ChatFileDAO;
import br.unip.si.aps.moises.application.domain.bean.ChatFile;

public class ExcluirDepois {

	@Test
	public void test() {
		ChatFile chat = new ChatFile();
		chat.setUsername("Moises");
		chat.setId("id");
		chat.getMessages().add("Gosta da aritane");
		ChatFileDAO dao = new ChatFileDAO();
		dao.save(chat);
		assertEquals(chat, dao.load("id"));
	}
}
