package br.unip.si.aps.moises.bus.actions;

import java.util.Map;

public interface Action {
	public void triggerAction(Map<String, Object> data);
}
