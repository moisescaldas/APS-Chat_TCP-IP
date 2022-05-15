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
		dm = DocumentManager.getInstance();
		aidl = ApplicationIDList.getInstance();
		rul = RemoteUserManager.getInstance();
	}

	public static synchronized MessageManager getInstance() {
		return instance == null ? (instance = new MessageManager()) : instance;
	}

	/**
	 * Atributos e Objetos da classe
	 */
	private DocumentManager dm;
	private ApplicationIDList aidl;
	private RemoteUserManager rul;

	public void sendMessage(String message) {
		RemoteUser user = dm.getSelectedUser();
		MessageData md = new MessageData("Você", message);
		Document document;
		if ((document = dm.getDocument(user)) == null)
			return;
		append(md, document);
		md.setAuthor(LocalUser.getInstance().getName());
		md.setMessage(CryptographicUtil.encrypt(
				SecurityKeysUtil.decodePublicKey(user.getId()),
				message));

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
		LocalUser localUser = LocalUser.getInstance();
		Document document;

		if ((user = rul.getUser(message.getName(), message.getFrom())) == null)
			return;

		if ((document = dm.getDocument(user)) == null)
			return;
		append(new MessageData(user.getName(),
				CryptographicUtil.decrypt(localUser.getPrivateKey(), message.getMessage())),
				document);
	}
}
