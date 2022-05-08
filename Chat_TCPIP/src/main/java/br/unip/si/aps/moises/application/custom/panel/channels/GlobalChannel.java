package br.unip.si.aps.moises.application.custom.panel.channels;

import java.awt.FlowLayout;

import javax.swing.JPanel;

import br.unip.si.aps.moises.application.MainFrame;
import br.unip.si.aps.moises.application.custom.label.channel.UserChannel;
import br.unip.si.aps.moises.application.domain.bean.RemoteUser;

public class GlobalChannel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 912049652074524506L;
	private static GlobalChannel instance;
	
	/**
	 * Create the panel.
	 */
	public GlobalChannel() {
		GlobalChannel.instance = this;
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	}
	
	public void addUser(RemoteUser user) {
		add(new UserChannel(user));
		MainFrame.getInstance().refreshCard();
	}
	
	public void removeUser(RemoteUser user) {
		remove(new UserChannel(user));
	}
	
	public static synchronized GlobalChannel getInstance() {
		return instance;
	}	
}
