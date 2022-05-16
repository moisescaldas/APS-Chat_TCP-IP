package br.unip.si.aps.moises;

import org.junit.Test;

import br.unip.si.aps.moises.application.dao.ChatFileDAO;

public class ExcluirDepois {

	@Test
	public void test() {
		ChatFileDAO dao = new ChatFileDAO();
		dao.list();
	}
}
