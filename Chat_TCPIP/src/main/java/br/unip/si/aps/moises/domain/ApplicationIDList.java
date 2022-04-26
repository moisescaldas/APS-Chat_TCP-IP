package br.unip.si.aps.moises.domain;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ApplicationIDList {
	private ExecutorService executor;
	private List<String> destinations;

	
	private static ApplicationIDList instance;
	
	private ApplicationIDList() {
		destinations = new ArrayList<String>();
		executor = Executors.newFixedThreadPool(1);
	}
	
	public static synchronized ApplicationIDList getInstance() {
		return instance == null ? (instance = new ApplicationIDList()) : instance;
	}

	public ApplicationIDList setTargetList(PublicKey target) {
		executor.submit(() -> {
		if (target == null)
			destinations.add("0");
		else {
			destinations.add(Base64.getEncoder().encodeToString(target.getEncoded()));
		}});
		return this;
	}
	
	public Boolean hasInTargetList(String target) {
		Future<Boolean> future = executor.submit(() -> {
			return destinations.stream().anyMatch(destination -> destination.equals(target));
		});
		try {
			return future.get();
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	public List<String> getDestinations() {
		Future<List<String>> future = executor.submit(() -> {
			return destinations;
		});
		
		try {
			return future.get();
		} catch(Exception e) {
			return null;
		}
	}
}
