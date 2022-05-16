package br.unip.si.aps.moises.application.domain.manager;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import br.unip.si.aps.moises.application.domain.bean.LocalUser;
import br.unip.si.aps.moises.application.domain.bean.MessageData;
import br.unip.si.aps.moises.application.domain.bean.RemoteUser;
import br.unip.si.aps.moises.core.bus.actions.MessageAction;
import br.unip.si.aps.moises.core.dto.Message;
import br.unip.si.aps.moises.util.CryptographicUtil;
import br.unip.si.aps.moises.util.SecurityKeysUtil;

public class MessageManager {
	/**
	 * Singletton
	 */
	private static MessageManager instance;

	private MessageManager() {
		this.documentManager = DocumentManager.getInstance();
		this.aidl = ApplicationIDList.getInstance();
		this.rul = RemoteUserManager.getInstance();
		this.localUser = LocalUser.getInstance();
		this.messageAction = MessageAction.getInstance();
	}

	public static synchronized MessageManager getInstance() {
		return instance == null ? (instance = new MessageManager()) : instance;
	}

	/**
	 * Atributos e Objetos da classe
	 */
	private DocumentManager documentManager;
	private ApplicationIDList aidl;
	private RemoteUserManager rul;
	private LocalUser localUser;
	private MessageAction messageAction;

	public void sendMessage(String message) {
		RemoteUser user = documentManager.getSelectedUser();
		MessageData md = new MessageData("Você", message);
		Document document;
		if ((document = documentManager.getDocument(user)) == null)
			return;
		append(md, document);
		md.setAuthor(localUser.getName());
		md.setMessage(CryptographicUtil.encrypt(SecurityKeysUtil.decodePublicKey(user.getId()),message));

		messageAction.triggerAction(new Message(
				user.getId(), aidl.getIdList().get(1), 
				md.getAuthor(), md.getMessage(), md.getDate().toString()));
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
		if ((document = documentManager.getDocument(user)) == null)
			return;
		
		append(new MessageData(user.getName(),
				CryptographicUtil.decrypt(localUser.getPrivateKey(), message.getMessage())),
				document);
	}
}
