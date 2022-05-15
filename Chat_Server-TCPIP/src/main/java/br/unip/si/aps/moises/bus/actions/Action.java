package br.unip.si.aps.moises.bus.actions;

import java.util.Map;

import lombok.NonNull;

public interface Action {
	public void triggerAction(@NonNull Map<String, Object> data);
}
