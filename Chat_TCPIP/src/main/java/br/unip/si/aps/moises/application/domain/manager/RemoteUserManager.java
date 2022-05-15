package br.unip.si.aps.moises.application.domain.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import br.unip.si.aps.moises.application.MainFrame;
import br.unip.si.aps.moises.application.custom.label.channel.UserChannel;
import br.unip.si.aps.moises.application.custom.panel.channels.GlobalChannel;
import br.unip.si.aps.moises.application.domain.bean.RemoteUser;
import br.unip.si.aps.moises.application.domain.bean.Status;
import br.unip.si.aps.moises.observer.listener.UserListener;

public class RemoteUserManager {
	/**
	 * Singleton
	 */
	private static RemoteUserManager instance;

	private RemoteUserManager() {
		this.userMap = new HashMap<>();
		this.executor = Executors.newSingleThreadExecutor();		
		addUserAction = user -> {
			return GlobalChannel.getInstance().addUser(user);
		};
	}

	public static synchronized RemoteUserManager getInstance() {
		return instance == null ? (instance = new RemoteUserManager()) : instance;
	}

	/**
	 * Atributos e Metodos do Objeto
	 */
	private Map<RemoteUser, UserChannel> userMap;
	private ExecutorService executor;
	private UserListener<UserChannel> addUserAction;

	private void addUser(RemoteUser user) {
		executor.submit(() -> {
			if (userMap.keySet().contains(user)) {
				updateUserStatus(user, Status.ONLINE);
			} else {
				UserChannel channel = addUserAction.actionPerformed(user);
				userMap.put(user, channel);
				MainFrame.getInstance().refreshCard();
				Logger.getGlobal().info("Host Adicionado!");
			}
		});
	}

	public void addUser(String name, String id) {
		addUser(new RemoteUser(name, id));
	}

	public RemoteUser getUser(String name, String id) {
		Future<RemoteUser> future = executor.submit(() -> {
			RemoteUser user = new RemoteUser(name, id);
			return userMap.keySet().stream().anyMatch(userCache -> userCache.equals(user)) ? user : null; 
		});
		try {
			return future.get();
		}catch(Exception e) {
			return null;
		}
	}

	public void updateUserStatus(RemoteUser user, Status status) {
		executor.submit(() -> {
			userMap.get(user).updateChannel(status);
		});
	}
	
	public List<RemoteUser> findRemoteUserById(String id){
		Future<List<RemoteUser>> future = executor.submit(() -> {
			return userMap.keySet().stream()
					.map(user -> user.getId().equals(id) ? user : null)
					.filter(user -> user != null)
					.collect(Collectors.toList());
		});
		try {
			return future.get();
		} catch (Exception e) {
			return null;
		}
	}
}
