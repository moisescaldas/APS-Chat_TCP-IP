package br.unip.si.aps.moises.application.custom.label.channel;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import br.unip.si.aps.moises.application.domain.bean.RemoteUser;
import br.unip.si.aps.moises.application.domain.manager.DocumentManager;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class UserChannel extends JLabel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2336523658689991070L;
	@EqualsAndHashCode.Include
	private RemoteUser user;
	private DocumentManager documentManager;
	
	public UserChannel(RemoteUser user) {
		this.user = user;
		documentManager = DocumentManager.getInstance();
		
		setPreferredSize(new Dimension(160, 34));
		setIcon(new ImageIcon("files/icons/usuario.png"));
		setVerticalTextPosition(CENTER);
		setHorizontalTextPosition(RIGHT);
		setText(user.getName());
		documentManager.put(user);
		
		addMouseListener(new ClickMouseHandle());
	}
	
	private class ClickMouseHandle implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			documentManager.selectUser(user);
		}
		@Override
		public void mousePressed(MouseEvent e) {
		}
		@Override
		public void mouseReleased(MouseEvent e) {			
		}
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}
		
	}
	
}
