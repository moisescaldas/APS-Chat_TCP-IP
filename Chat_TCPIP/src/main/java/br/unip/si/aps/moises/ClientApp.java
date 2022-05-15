package br.unip.si.aps.moises;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.KeyPair;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import br.unip.si.aps.moises.application.MainFrame;
import br.unip.si.aps.moises.application.domain.bean.LocalUser;
import br.unip.si.aps.moises.application.domain.manager.RemoteUserManager;
import br.unip.si.aps.moises.application.domain.manager.ThreadExecutionManager;
import br.unip.si.aps.moises.core.bus.Coreographer;
import br.unip.si.aps.moises.core.file.ApplicationConfig;
import br.unip.si.aps.moises.core.file.resolver.ApplicationFileResolver;
import br.unip.si.aps.moises.core.network.NetworkProxy;
import br.unip.si.aps.moises.util.SecurityKeysUtil;

public class ClientApp {
	/**
	 * Application Components
	 */
	private Properties config;
	private ExecutorService executor;

	/**
	 * SWING Components
	 */
	private JFrame frmChatTcpip;
	private JTextField serverIPField;
	private JTextField serverPortField;
	private JTextField nickNameField;
	private JTextField keyPairField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		ApplicationFileResolver.getInstance().directoriesTreeResolver();


		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					ClientApp window = new ClientApp();
					window.frmChatTcpip.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientApp() {
		loadObjects();
		initialize();
	}

	/**
	 * Load Application Objects
	 */
	private void loadObjects() {
		try {
			this.config = ApplicationConfig.getInstance().loadConfig();
			this.executor = ThreadExecutionManager.getInstance().getExecutor();
		} catch (Exception e1) {
			throw new RuntimeException(e1);
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmChatTcpip = new JFrame();
		frmChatTcpip.setResizable(false);
		frmChatTcpip.setSize(315, 175);
		frmChatTcpip.setTitle("Chat TCP/IP");
		frmChatTcpip.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChatTcpip.setLocationRelativeTo(null);

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

		keyPairField = new JTextField();
		keyPairField.setColumns(10);
		keyPairField.setText(config.getProperty("user.rsa.privatekey"));


		JButton generateKeyPairButton = new JButton("Gerar");
		generateKeyPairButton.addActionListener(new GenerateKeyPair());

		JButton loadKeyPairButton = new JButton("Abrir");
		loadKeyPairButton.addActionListener(new OpenKeyPair());

		JButton openApplicationButton = new JButton("Conectar");
		openApplicationButton.addActionListener(new ConnectToServer());

		GroupLayout groupLayout = new GroupLayout(frmChatTcpip.getContentPane());
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
								.addComponent(keyPairField, GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
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
								.addComponent(keyPairField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(generateKeyPairButton)
								.addComponent(loadKeyPairButton)
								.addComponent(openApplicationButton))
						.addContainerGap(21, Short.MAX_VALUE))
				);
		frmChatTcpip.getContentPane().setLayout(groupLayout);
	}

	/**
	 * Listeners
	 */
	private class GenerateKeyPair implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(() -> {
				KeyPair pair = SecurityKeysUtil.newKeyPair(4096);
				if (pair == null)
					throw new RuntimeException("Não foi possivel gerar as chaves");
				else {
					config.setProperty(
							"user.rsa.privatekey",
							SecurityKeysUtil.encodePrivateKey(pair.getPrivate()));

					config.setProperty(
							"user.rsa.publickey",
							SecurityKeysUtil.encodePublicKey(pair.getPublic()));

					keyPairField.setText(SecurityKeysUtil.encodePublicKey(pair.getPublic()));
				}
			}).start();
		}		
	}

	private class OpenKeyPair implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser(new File(System.getProperty("user.dir")));
			chooser.setFileFilter(new FileNameExtensionFilter("rsa", "rsa"));

			if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			}
		}		
	}

	private class ConnectToServer implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			loadConfiguration();
		}
	}

	private void loadApplication() {
		LocalUser.newInstance(config.getProperty("user.name"),
				SecurityKeysUtil.decodePrivateKey(config.getProperty("user.rsa.privatekey")),
				SecurityKeysUtil.decodePublicKey(config.getProperty("user.rsa.publickey")));
		NetworkProxy.newInstance(config.getProperty("server.ip"), Integer.parseInt(config.getProperty("server.port")));
		Coreographer.getInstance();
		RemoteUserManager.getInstance();
		executor.submit(NetworkProxy.getInstance());
	}

	private void loadConfiguration() {
		config.setProperty("user.name", nickNameField.getText());
		config.setProperty("server.ip", serverIPField.getText());
		config.setProperty("server.port", serverPortField.getText());

		int choose = JOptionPane.showConfirmDialog(frmChatTcpip, "Deseja Manter a Configuração?");

		if (choose == JOptionPane.OK_OPTION) {
			ApplicationConfig.getInstance().saveConfig(config);
		} else if(choose == JOptionPane.NO_OPTION) {
			Properties configSpec = new Properties();
			configSpec.setProperty("user.name", "");
			configSpec.setProperty("server.ip", "");
			configSpec.setProperty("server.port", "");
			configSpec.setProperty("user.rsa.privatekey", "");
			configSpec.setProperty("user.rsa.publickey", "");
			ApplicationConfig.getInstance().saveConfig(configSpec);
		} else
			return;

		Logger.getGlobal().info("Aplicação iniciada");
		loadApplication();
		frmChatTcpip.dispose();
		MainFrame.main(null);		
	}
}
