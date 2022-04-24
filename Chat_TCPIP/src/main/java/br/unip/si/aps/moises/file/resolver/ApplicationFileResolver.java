package br.unip.si.aps.moises.file.resolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ApplicationFileResolver {
	private static ApplicationFileResolver instance;
	public final String ROOT;
	
	private ApplicationFileResolver() {
		ROOT = "./files";
	}
	
	public static synchronized ApplicationFileResolver getInstance() {
		return instance == null ? (instance = new ApplicationFileResolver()) : instance;
	}
	
	public ApplicationFileResolver directoriesTreeResolver() {
		try {
			Files.createDirectories(Paths.get(ROOT));
			Files.createDirectories(Paths.get(ROOT + "/keypair"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
}
