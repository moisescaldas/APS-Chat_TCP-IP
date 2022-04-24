package br.unip.si.aps.moises;

import org.junit.Test;

import br.unip.si.aps.moises.file.resolver.ApplicationFileResolver;

public class TesteFile {
	@Test
	public void teste1() {
		ApplicationFileResolver.getInstance().directoriesTreeResolver();
	}
}
