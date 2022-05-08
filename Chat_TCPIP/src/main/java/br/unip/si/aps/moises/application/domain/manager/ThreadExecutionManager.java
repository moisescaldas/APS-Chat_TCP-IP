package br.unip.si.aps.moises.application.domain.manager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.Getter;

@Getter
public class ThreadExecutionManager {
	private static ThreadExecutionManager instance;
	
	private ExecutorService executor;
	
	
	private ThreadExecutionManager() {
		executor = Executors.newFixedThreadPool(20);
	}
	
	public static synchronized ThreadExecutionManager getInstance() {
		return instance == null ? (instance = new ThreadExecutionManager()) : instance;
	}
}
