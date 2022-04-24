package br.unip.si.aps.moises.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Properties;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JOptionPane;

import br.unip.si.aps.moises.file.ApplicationConfig;
import br.unip.si.aps.moises.file.resolver.ApplicationFileResolver;
import br.unip.si.aps.moises.file.resolver.KeyPairFileResolver;

public class ChatApp {
	/**
	 * Application Components
	 */
	private KeyPairGenerator generator;
	private Properties config;

	/**
	 * SWING Components
	 */
	private JFrame frame;
	private JTextField serverIPField;
	private JTextField serverPortField;
	private JTextField nickNameField;
	private JTextField keyPairPathField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		ApplicationFileResolver.getInstance().directoriesTreeResolver();


		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					ChatApp window = new ChatApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChatApp() {
		loadObjects();
		initialize();
	}

	/**
	 * Load Application Objects
	 */
	private void loadObjects() {
		try {
			this.generator = KeyPairGenerator.getInstance("RSA");
			this.config = ApplicationConfig.getInstance().loadConfig();
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setSize(315, 175);
		frame.setTitle("Chat TCP/IP");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		JLabel lblNewLabel = new JLabel("Server IP:");

		serverIPField = new JTextField();
		serverIPField.setColumns(10);
		serverIPField.setText(config.getProperty("server.ip"));

		JLabel lblNewLabel_1 = new JLabel("TCP/Port:");

		serverPortField = new JTextField();
		serverPortField.setColumns(10);
		serverPortField.setText(config.getProperty("server.port"));

		JLabel lblNewLabel_2 = new JLabel("Apelido:");

		nickNameField = new JTextField();
		nickNameField.setColumns(10);
		nickNameField.setText(config.getProperty("user.name"));

		JLabel lblNewLabel_3 = new JLabel("Keypair:");

		keyPairPathField = new JTextField();
		keyPairPathField.setColumns(10);
		keyPairPathField.setText(config.getProperty("user.rsa.privatekey"));
		

		JButton generateKeyPairButton = new JButton("Gerar");
		generateKeyPairButton.addActionListener(new GenerateKeyPair());

		JButton loadKeyPairButton = new JButton("Abrir");
		loadKeyPairButton.addActionListener(new OpenKeyPair());

		JButton openApplicationButton = new JButton("Conectar");
		openApplicationButton.addActionListener(new ConnectToServer());

		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel)
								.addComponent(lblNewLabel_1)
								.addComponent(lblNewLabel_2)
								.addComponent(lblNewLabel_3))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(generateKeyPairButton)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(loadKeyPairButton)
										.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(openApplicationButton))
								.addComponent(keyPairPathField, GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
								.addComponent(serverPortField, GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
								.addComponent(serverIPField, GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
								.addComponent(nickNameField, GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE))
						.addContainerGap())
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel)
								.addComponent(serverIPField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_1)
								.addComponent(serverPortField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_2)
								.addComponent(nickNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_3)
								.addComponent(keyPairPathField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(generateKeyPairButton)
								.addComponent(loadKeyPairButton)
								.addComponent(openApplicationButton))
						.addContainerGap(21, Short.MAX_VALUE))
				);
		frame.getContentPane().setLayout(groupLayout);
	}

	/**
	 * Listeners
	 */
	private class GenerateKeyPair implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(() -> {
				generator.initialize(4096);
				KeyPair pair = generator.generateKeyPair();
				if (pair != null) {
					KeyPairFileResolver.getInstance().savePublicKey(pair.getPublic(), "userkey");
					keyPairPathField.setText(KeyPairFileResolver.getInstance().savePrivateKey(pair.getPrivate(), "userkey"));
				} else
					throw new RuntimeException("Não foi possivel gerar as chaves");
			}).start();
		}		
	}

	private class OpenKeyPair implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser(new File(System.getProperty("user.dir")));
			chooser.setFileFilter(new FileNameExtensionFilter("rsa", "rsa"));

			if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				try {
					keyPairPathField.setText(chooser.getSelectedFile().getCanonicalPath());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}		
	}

	private class ConnectToServer implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (JOptionPane.showConfirmDialog(frame, "Deseja Salvar a Configuração?")) {
			case JOptionPane.OK_OPTION:
				config.setProperty("server.ip", serverIPField.getText());
				config.setProperty("server.port", serverPortField.getText());
				config.setProperty("user.name", nickNameField.getText());
				config.setProperty("user.rsa.privatekey", keyPairPathField.getText());
				config.setProperty("user.rsa.publickey", keyPairPathField.getText() + ".pub");				
				break;
			
			case JOptionPane.NO_OPTION:
				config.setProperty("server.ip", "");
				config.setProperty("server.port", "");
				config.setProperty("user.name", "");
				config.setProperty("user.rsa.privatekey", "");
				config.setProperty("user.rsa.publickey", "");
				break;
				
			default:
				return;
			}
			ApplicationConfig.getInstance().saveConfig(config);
		}
	}
}
