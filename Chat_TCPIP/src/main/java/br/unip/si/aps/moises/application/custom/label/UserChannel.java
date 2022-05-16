package br.unip.si.aps.moises.application.custom.label;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import br.unip.si.aps.moises.application.domain.bean.RemoteUser;
import br.unip.si.aps.moises.application.domain.bean.Status;
import br.unip.si.aps.moises.application.domain.manager.DocumentManager;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class UserChannel extends JLabel{

	private static final long serialVersionUID = -2336523658689991070L;
	private static UserChannel lastSelected;
	@EqualsAndHashCode.Include
	private RemoteUser user;
	private DocumentManager documentManager;
	private Status status;
	private Status older;
	
	public UserChannel(RemoteUser user) {
		this.user = user;
		documentManager = DocumentManager.getInstance();
		this.status = Status.ONLINE;
		this.older = status;
		
		setPreferredSize(new Dimension(160, 34));
		setIcon(new ImageIcon(status.getImagePath()));
		setVerticalTextPosition(CENTER);
		setHorizontalTextPosition(RIGHT);
		setText(user.getName());
		documentManager.put(user);
		
		addMouseListener(new ClickMouseHandle());
	}
	
	public void updateChannel(Status status) {
		this.older = this.status;
		setIcon(new ImageIcon((this.status = status).getImagePath()));		
	}

	public void returnLastStatus() {
		updateChannel(older);
	}
	
	private class ClickMouseHandle implements MouseListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			if (!(status == Status.SELECTED)) {
				if (lastSelected != null) {
					lastSelected.returnLastStatus();
				}
				lastSelected = (UserChannel) e.getSource();
				updateChannel(Status.SELECTED);
				documentManager.selectUser(user);
			}
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
