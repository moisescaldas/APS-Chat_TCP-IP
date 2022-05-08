package br.unip.si.aps.moises.application.domain.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import br.unip.si.aps.moises.application.MainFrame;
import br.unip.si.aps.moises.application.domain.bean.RemoteUser;
import lombok.Getter;

@Getter
public class DocumentManager {
	/**
	 * Singletton
	 */
	private static DocumentManager instance;
	
	private DocumentManager() {
		documentsCache = new HashMap<>();
		executor = Executors.newFixedThreadPool(1);
	}
	
	public static synchronized DocumentManager getInstance() {
		return instance == null ? (instance = new DocumentManager()) : instance;
	}
	
	/**
	 * Atributos e Metodos da Classe
	 */
	private Map<RemoteUser, Document> documentsCache;
	private ExecutorService executor;
	private RemoteUser selectedUser;
	
	public void selectUser(RemoteUser user) {
		executor.submit(() -> {
			selectedUser = user;
			MainFrame.getInstance().getChatArea().setDocument(documentsCache.get(user));
			Logger.getGlobal().info("Usuario [" + user + "] selecionado");
		});
	}
	
	public void put(RemoteUser user) {
		executor.submit(() -> {
			if (documentsCache.get(user) == null) {
				documentsCache.put(user, new PlainDocument());
			}
		});
	}
	
	public Document getDocument(RemoteUser user) {
		Future<Document> future = executor.submit(() -> {
			return documentsCache.get(user);
		});
		
		try {
			return future.get();
		} catch(Exception e) {
			return null;
		}
	}
}
