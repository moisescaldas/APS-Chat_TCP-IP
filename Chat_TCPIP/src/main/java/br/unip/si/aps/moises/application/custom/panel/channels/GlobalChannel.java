package br.unip.si.aps.moises.application.custom.panel.channels;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import br.unip.si.aps.moises.application.MainFrame;
import br.unip.si.aps.moises.application.custom.label.channel.UserChannel;
import br.unip.si.aps.moises.application.domain.bean.RemoteUser;
import javax.swing.ScrollPaneConstants;

public class GlobalChannel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 912049652074524506L;
	private static GlobalChannel instance;
	private JPanel panel;
	/**
	 * Create the panel.
	 */
	public GlobalChannel() {
		GlobalChannel.instance = this;
		setLayout(new GridLayout(0, 1, 0, 0));
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, 1));
		panel.setBackground(Color.WHITE);
		
		JScrollPane scroll = new JScrollPane(panel);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scroll);
	}
	
	public void addUser(RemoteUser user) {
		panel.add(new UserChannel(user));
		MainFrame.getInstance().refreshCard();
	}
	
	public void updateUsers() {
		
	}
	
	public static synchronized GlobalChannel getInstance() {
		return instance;
	}	
}
