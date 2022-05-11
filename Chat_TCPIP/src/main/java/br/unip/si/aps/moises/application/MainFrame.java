package br.unip.si.aps.moises.application;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.SystemColor;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;

import br.unip.si.aps.moises.application.custom.panel.channels.GlobalChannel;
import br.unip.si.aps.moises.application.domain.manager.MessageManager;
import br.unip.si.aps.moises.application.domain.manager.RemoteUserList;
import lombok.Getter;

@Getter
public class MainFrame extends JFrame {
	private static MainFrame instance;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3680188915246557992L;
	private MessageManager mm;
	private RemoteUserList rul;
	
	private JPanel contentPane;
	private CardLayout cardLayout;
	private JPanel channelsPanel;
	private JTextArea chatArea;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		instance = this;
		mm = MessageManager.getInstance();
		rul = RemoteUserList.getInstance();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		this.setMinimumSize(new Dimension(950, 500));
		
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(null);
		setContentPane(contentPane);
		
		channelsPanel = new JPanel();
		
		JPanel chat = new JPanel();
		chat.setBackground(Color.WHITE);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(channelsPanel, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(chat, GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(chat, GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
				.addComponent(channelsPanel, GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
		);

		chatArea = new JTextArea();
		chatArea.setEditable(false);
		chatArea.setLineWrap(true);
		chatArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		chatArea.setBackground(SystemColor.controlHighlight);
		
		JTextArea textArea = new JTextArea();
		textArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		textArea.setLineWrap(true);
		textArea.setBackground(SystemColor.controlHighlight);
		
		JButton sendButton = new JButton("Enviar");
		sendButton.addActionListener(event -> {
			String message;
			if ((message = textArea.getText()).equals("")) return;
			mm.sendMessage(message);
			textArea.setText(null);
		});
		
		JScrollPane scrollPane = new JScrollPane(chatArea);
		
		JScrollPane scrollPane_1 = new JScrollPane(textArea);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GroupLayout gl_chat = new GroupLayout(chat);
		gl_chat.setHorizontalGroup(
			gl_chat.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_chat.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_chat.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_chat.createSequentialGroup()
							.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 609, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(sendButton, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane))
					.addContainerGap())
		);
		gl_chat.setVerticalGroup(
			gl_chat.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_chat.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_chat.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(sendButton))
					.addGap(13))
		);
		chat.setLayout(gl_chat);
		cardLayout = new CardLayout(0, 0);
		channelsPanel.setLayout(cardLayout);
		
		GlobalChannel globalChannel = new GlobalChannel();
		globalChannel.setBackground(Color.WHITE);
		channelsPanel.add(globalChannel, "globalCard");
		
		JPanel clean = new JPanel();
		channelsPanel.add(clean, "clean");
		contentPane.setLayout(gl_contentPane);
	}
	
	public static synchronized MainFrame getInstance() {
		return instance;
	}
	
	public void refreshCard() {
		cardLayout.previous(channelsPanel);
		cardLayout.next(channelsPanel);
	}	
}
