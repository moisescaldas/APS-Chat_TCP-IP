package br.unip.si.aps.moises.application.domain.manager;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import br.unip.si.aps.moises.application.domain.bean.LocalUser;
import br.unip.si.aps.moises.application.domain.bean.MessageData;
import br.unip.si.aps.moises.application.domain.bean.RemoteUser;
import br.unip.si.aps.moises.core.actions.MessageAction;
import br.unip.si.aps.moises.core.dto.Message;

public class MessageManager {
	/**
	 * Singletton
	 */
	private static MessageManager instance;
	
	private MessageManager() {
		dm = DocumentManager.getInstance();
		aidl = ApplicationIDList.getInstance();
		rul = RemoteUserList.getInstance();
	}
	
	public static synchronized MessageManager getInstance() {
		return instance == null ? (instance = new MessageManager()) : instance;
	}
	
	/**
	 * Atributos e Objetos da classe
	 */
	private DocumentManager dm;
	private ApplicationIDList aidl;
	private RemoteUserList rul;
	
	public void sendMessage(String message) {
		RemoteUser user = dm.getSelectedUser();
		MessageData md = new MessageData("Você", message);
		Document document;
		if ((document = dm.getDocument(user)) == null)
			return;
		append(md, document);
		md.setAuthor(LocalUser.getInstance().getName());
		MessageAction.getInstance().triggerAction(new Message(
				user.getId(),
				aidl.getIdList().get(1),
				md.getAuthor(),
				md.getMessage(),
				md.getDate().toString()));
	}

	private void append(MessageData md, Document document) {
		try {
			document.insertString(document.getLength(), md.getFormatedMessage() + "\n", null);
		} catch (BadLocationException e) {
		}		
	}
	
	public void receiveMessage(Message message) {
		RemoteUser user;
		Document document;
		
		if ((user = rul.getUser(message.getName(), message.getFrom())) == null)
			return;
		
		if ((document = dm.getDocument(user)) == null)
			return;
		append(new MessageData(user.getName(), message.getMessage()), document);
	}

}
