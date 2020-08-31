import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

import se.miun.distsys.User;

import se.miun.distsys.GroupCommuncation;
import se.miun.distsys.listeners.ChatMessageListener;
import se.miun.distsys.messages.*;

import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.*;

import javax.swing.JScrollPane;

public class WindowProgram implements ChatMessageListener, ActionListener {

	HashMap<String, User> users = new HashMap<String, User>();

	public User user = new User("Ogge");

	JFrame frame;
	JTextPane txtpnChat = new JTextPane();
	JTextPane txtpnMessage = new JTextPane();

	JTextPane txtpnUsers = new JTextPane();

	GroupCommuncation gc = null;

	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					final WindowProgram window = new WindowProgram();
					window.frame.setVisible(true);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public WindowProgram() {
		initializeFrame();

		gc = new GroupCommuncation();
		gc.sendJoinMessage(user);
		gc.setChatMessageListener(this);
		System.out.println("Group Communcation Started");
	}

	private void initializeFrame() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));

		final JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());

		GridBagConstraints chatConstraints = new GridBagConstraints();

		final JPanel chatPanel = new JPanel(new GridBagLayout());
		// chatPanel.setLayout(new GridLayout(0, 1, 0, 0));

		chatConstraints.fill = GridBagConstraints.BOTH;
		chatConstraints.weightx = 1.0;
		chatConstraints.weighty = 1.0;
		chatConstraints.gridy = 1;

		chatConstraints.insets = new Insets(0, 0, 0, 5);

		final JScrollPane scrollPane = new JScrollPane();
		chatPanel.add(scrollPane, chatConstraints);
		scrollPane.setViewportView(txtpnChat);
		txtpnChat.setEditable(false);
		txtpnChat.setText("--== Group Chat ==--");

		JScrollPane usersPane = new JScrollPane();
		usersPane.setPreferredSize(new Dimension(100, 100));

		// JTextPane usersTxt = new JTextPane();
		usersPane.setViewportView(txtpnUsers);
		txtpnUsers.setText("Connected Users:");
		txtpnUsers.setEditable(false);

		usersPane.setPreferredSize(new Dimension(200, 100));

		GridBagConstraints usersConstraints = new GridBagConstraints();

		usersConstraints.fill = GridBagConstraints.VERTICAL;
		usersConstraints.weighty = 1.0;
		usersConstraints.gridy = 1;

		chatPanel.add(usersPane, usersConstraints);

		// frame.getContentPane().add(usersPane);

		final JPanel sendPanel = new JPanel();
		sendPanel.setSize(frame.getSize());
		sendPanel.setLayout(new GridBagLayout());
		final GridBagConstraints messageConstraints = new GridBagConstraints();

		messageConstraints.fill = GridBagConstraints.HORIZONTAL;
		messageConstraints.weightx = 1.0;

		// txtpnMessage.setText("Type message here...");
		txtpnMessage.setPreferredSize(new Dimension(75, 50));
		sendPanel.add(txtpnMessage, messageConstraints);

		final JButton btnSendChatMessage = new JButton("Send");
		btnSendChatMessage.addActionListener(this);
		btnSendChatMessage.setActionCommand("send");
		btnSendChatMessage.setPreferredSize(new Dimension(75, 50));

		sendPanel.add(btnSendChatMessage);

		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(final WindowEvent winEvt) {
				gc.sendLeaveMessage(user);
				gc.shutdown();
			}
		});

		GridBagConstraints mainConstraints = new GridBagConstraints();
		mainConstraints.fill = GridBagConstraints.BOTH;
		mainConstraints.weightx = 1.0;
		mainConstraints.weighty = 1.0;
		mainConstraints.gridy = 1;

		mainConstraints.insets = new Insets(10, 10, 10, 10);

		mainPanel.add(chatPanel, mainConstraints);

		GridBagConstraints sendConstraints = new GridBagConstraints();
		sendConstraints.fill = GridBagConstraints.HORIZONTAL;
		sendConstraints.gridy = 2;

		sendConstraints.insets = new Insets(-5, 10, 10, 10);

		mainPanel.add(sendPanel, sendConstraints);

		frame.setContentPane(mainPanel);
	}

	private void updateClientsList() {
		
		System.out.println("\n");

		txtpnUsers.setText("Connected users:");

		Iterator it = users.entrySet().iterator();

		while(it.hasNext())
		{
			Map.Entry pair = (Map.Entry)it.next();
			System.out.println(pair.getKey() + "\t" + pair.getValue());
			txtpnUsers.setText(txtpnUsers.getText() + "\n" + pair.getKey());
		}
	}

	private void addClient(User user) {
		
		users.put(user.name, user);
		updateClientsList();
	}

	private void removeClient(User user) {
		users.remove(user.name);
		updateClientsList();
	}

	@Override
	public void actionPerformed(final ActionEvent event) {
		if (event.getActionCommand().equalsIgnoreCase("send")) {
			gc.sendChatMessage(txtpnMessage.getText());
		}
	}

	@Override
	public void onIncomingChatMessage(final ChatMessage chatMessage) {
		System.out.println("incoming chat message...");
		txtpnChat.setText(txtpnChat.getText() + "\n" + chatMessage.chat);
	}

	@Override
	public void onIncomingStatusMessage(final StatusMessage statusMessage) {
		System.out.println("status message");
		addClient(statusMessage.user);
	}

	@Override
	public void onIncomingJoinMessage(final JoinMessage joinMessage) {
		System.out.println("join message");
		txtpnChat.setText(txtpnChat.getText() + "\n" + joinMessage.user.name + " joined the chat!");
		addClient(joinMessage.user);

		gc.sendStatusMessage(user);
	}

	@Override
	public void onIncomingLeaveMessage(final LeaveMessage leaveMessage) {
		txtpnChat.setText(txtpnChat.getText() + "\n" + leaveMessage.user.name + " left the chat!");

		removeClient(leaveMessage.user);
	}
}
