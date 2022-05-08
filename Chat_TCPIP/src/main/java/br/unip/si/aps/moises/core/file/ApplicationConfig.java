package br.unip.si.aps.moises.core.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class ApplicationConfig {
	private final String PATH;
	private static ApplicationConfig instance;
	private ApplicationConfig() {
		this.PATH = "./files/config.properties";
	}
	
	public static synchronized ApplicationConfig getInstance() {
		return instance == null ? (instance = new ApplicationConfig()) : instance;
	}
	
	public Properties loadConfig() {
		Properties config = new Properties();
		try(InputStream is = new FileInputStream(PATH)) {
			config.load(is);
			return config;
		} catch (Exception e) {
			return config;
		}
	}
	
	public void saveConfig(Properties config) {
		try (OutputStream os = new FileOutputStream(PATH)) {
			config.store(os, null);
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}
