package br.unip.si.aps.moises.application.domain.manager;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import br.unip.si.aps.moises.application.custom.panel.channels.GlobalChannel;
import br.unip.si.aps.moises.application.domain.bean.RemoteUser;
import br.unip.si.aps.moises.observer.listener.UserListener;

public class RemoteUserList {
	/**
	 * Singleton
	 */
	private static RemoteUserList instance;
	
	private RemoteUserList() {
		userList = new HashSet<>();
		executor = Executors.newFixedThreadPool(1);
		addUserAction = user -> {
			GlobalChannel.getInstance().addUser(user);
		};
	}
	
	public static synchronized RemoteUserList getInstance() {
		return instance == null ? (instance = new RemoteUserList()) : instance;
	}
	
	/**
	 * Atributos e Metodos do Objeto
	 */
	private Set<RemoteUser> userList;
	private ExecutorService executor;
	private UserListener addUserAction;
	
	private void addUser(RemoteUser user) {
		executor.submit(() -> {
			userList.add(user);
			addUserAction.actionPerformed(user);
		});
	}

	public void addUser(String name, String id) {
		Logger.getGlobal().info("Host Adicionado!");
		addUser(new RemoteUser(name, id));
	}
	
	public RemoteUser getUser(String name, String id) {
		Future<RemoteUser> future = executor.submit(() -> {
			RemoteUser user = new RemoteUser(name, id);
			return userList.stream().anyMatch(userCache -> userCache.equals(user)) ? user : null; 
		});
		
		try {
			return future.get();
		}catch(Exception e) {
			return null;
		}
	}

}
