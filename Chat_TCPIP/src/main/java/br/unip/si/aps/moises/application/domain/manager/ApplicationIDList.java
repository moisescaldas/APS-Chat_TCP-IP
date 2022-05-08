package br.unip.si.aps.moises.application.domain.manager;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

public class ApplicationIDList {
	/**
	 * Pattern Singleton
	 */
	private static ApplicationIDList instance;
	
	private ApplicationIDList() {
		idList = new ArrayList<String>();
		executor = Executors.newFixedThreadPool(1);
	}
	
	public static synchronized ApplicationIDList getInstance() {
		return instance == null ? (instance = new ApplicationIDList()) : instance;
	}

	/**
	 * Objeto começa aqui :)
	 */
	private ExecutorService executor;
	private List<String> idList;

	public synchronized ApplicationIDList setTargetList(PublicKey target) {
		executor.submit(() -> {
		if (target == null)
			idList.add("0");
		else {
			idList.add(Base64.getEncoder().encodeToString(target.getEncoded()));
		Logger.getGlobal().info("Adicionado Origem");
		}});
		return this;
	}
	
	public synchronized Boolean hasInTargetList(String target) {
		Future<Boolean> future = executor.submit(() -> {
			for(String id: idList) {
				if (id.equals(target))
					return true;
			}
			return false;
		});
		try {
			return future.get();
		} catch (Exception e) {
			return false;
		}
	}

	public List<String> getIdList() {
		Future<List<String>> future = executor.submit(() -> {
			return idList;
		});
		
		try {
			return future.get();
		} catch(Exception e) {
			return null;
		}
	}
}
