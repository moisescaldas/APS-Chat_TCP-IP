package br.unip.si.aps.moises.application.domain.bean;

public enum Status {
	ONLINE, OFFLINE, SELECTED;
	
	public String getImagePath() {
		switch (this) {
		case ONLINE:
			return "files/icons/usuario.png";
		case OFFLINE:
			return "files/icons/usuario-off.png";
		default:
			return "files/icons/usuario-selecionado.png";
		}
	}
}
