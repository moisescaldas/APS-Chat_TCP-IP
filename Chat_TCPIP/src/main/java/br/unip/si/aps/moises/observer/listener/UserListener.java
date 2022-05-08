package br.unip.si.aps.moises.observer.listener;

import br.unip.si.aps.moises.application.domain.bean.RemoteUser;

public interface UserListener {
	public void actionPerformed(RemoteUser user);
}
