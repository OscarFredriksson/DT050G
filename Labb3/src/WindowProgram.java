import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.Map;
import java.util.Iterator;

import se.miun.distsys.User;
import se.miun.distsys.Coordinator;
import se.miun.distsys.GroupCommuncation;
import se.miun.distsys.listeners.ChatMessageListener;
import se.miun.distsys.messages.*;
import se.miun.distsys.messages.CoordinatorMessages.GenerateSequenceNumberMessage;
import se.miun.distsys.messages.CoordinatorMessages.GetUserIDMessage;
import se.miun.distsys.messages.CoordinatorMessages.SequenceNumberMessage;
import se.miun.distsys.messages.CoordinatorMessages.UserIDMessage;

import javax.swing.JButton;
import javax.swing.JTextPane;

import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.*;
import java.awt.event.KeyEvent;

import javax.swing.JScrollPane;

import java.awt.event.InputEvent;

import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class WindowProgram implements ChatMessageListener, ActionListener {

	// HashMap<String, User> users = new HashMap<String, User>();

	List<Message> messages = new ArrayList<Message>();

	public User user;

	JFrame chatFrame;
	JTextPane txtpnChat = new JTextPane();
	JTextPane txtpnMessage = new JTextPane();
	JTextPane txtpnUsers = new JTextPane();

	JFrame joinFrame;
	JTextPane txtpnUsername = new JTextPane();

	GroupCommuncation gc = null;

	boolean assignMessageReceived = false;

	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					final WindowProgram window = new WindowProgram();
					window.chatFrame.setVisible(false);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public WindowProgram() {

		gc = new GroupCommuncation();
		gc.setChatMessageListener(this);

		initializeChatFrame();

		initializeJoinFrame();

		chatFrame.setVisible(false);
		joinFrame.setVisible(true);
	}

	private void initializeJoinFrame() {
		joinFrame = new JFrame();
		joinFrame.setBounds(100, 100, 300, 300);
		joinFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		joinFrame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
		joinFrame.setMaximumSize(new Dimension(300, 200));
		JPanel mainPanel = new JPanel();

		mainPanel.setPreferredSize(new Dimension(200, 100));
		mainPanel.setMinimumSize(new Dimension(200, 100));
		mainPanel.setMaximumSize(new Dimension(200, 100));

		mainPanel.setLayout(new GridLayout(0, 1));

		JLabel labelTitle = new JLabel();
		labelTitle.setText("Enter your username:");
		mainPanel.add(labelTitle);

		txtpnUsername.setPreferredSize(new Dimension(200, 25));
		txtpnUsername.setMinimumSize(new Dimension(200, 50));
		mainPanel.add(txtpnUsername);

		joinFrame.setContentPane(mainPanel);

		final JButton btnJoin = new JButton("join");
		btnJoin.addActionListener(this);
		btnJoin.setActionCommand("join");
		btnJoin.setPreferredSize(new Dimension(75, 50));
		btnJoin.setMinimumSize(new Dimension(75, 50));
		btnJoin.setMaximumSize(new Dimension(75, 50));
		mainPanel.add(btnJoin);
	}

	private void initializeChatFrame() {
		chatFrame = new JFrame();
		chatFrame.setBounds(100, 100, 450, 300);
		chatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chatFrame.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));

		final JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridBagLayout());

		GridBagConstraints chatConstraints = new GridBagConstraints();

		final JPanel chatPanel = new JPanel(new GridBagLayout());

		chatConstraints.fill = GridBagConstraints.BOTH;
		chatConstraints.weightx = 0.5;
		chatConstraints.weighty = 1.0;
		chatConstraints.gridy = 1;

		chatConstraints.insets = new Insets(0, 0, 0, 5);

		final JScrollPane scrollPane = new JScrollPane();
		chatPanel.add(scrollPane, chatConstraints);
		scrollPane.setViewportView(txtpnChat);
		txtpnChat.setEditable(false);

		JScrollPane usersPane = new JScrollPane();
		chatFrame.setContentPane(usersPane);

		usersPane.setViewportView(txtpnUsers);
		txtpnUsers.setText("Connected Users:");
		txtpnUsers.setEditable(false);

		usersPane.setPreferredSize(new Dimension(200, 100));
		usersPane.setMinimumSize(new Dimension(200, 100));

		GridBagConstraints usersConstraints = new GridBagConstraints();

		usersConstraints.fill = GridBagConstraints.VERTICAL;
		usersConstraints.weighty = 1.0;
		usersConstraints.gridy = 1;

		chatPanel.add(usersPane, usersConstraints);

		final JPanel sendPanel = new JPanel();
		sendPanel.setSize(chatFrame.getSize());
		sendPanel.setLayout(new GridBagLayout());
		final GridBagConstraints messageConstraints = new GridBagConstraints();

		messageConstraints.fill = GridBagConstraints.HORIZONTAL;
		messageConstraints.weightx = 1.0;

		txtpnMessage.setPreferredSize(new Dimension(75, 50));
		txtpnMessage.setMinimumSize(new Dimension(75, 50));
		sendPanel.add(txtpnMessage, messageConstraints);

		final JButton btnSendChatMessage = new JButton("Send");
		btnSendChatMessage.addActionListener(this);
		btnSendChatMessage.setActionCommand("send");
		btnSendChatMessage.setPreferredSize(new Dimension(75, 50));
		btnSendChatMessage.setMinimumSize(new Dimension(75, 50));

		sendPanel.add(btnSendChatMessage);

		chatFrame.addWindowListener(new java.awt.event.WindowAdapter() {
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

		chatFrame.setContentPane(mainPanel);

		initializeMessageEnterSend();
	}

	private void initializeMessageEnterSend() {

		int condition = JComponent.WHEN_FOCUSED;
		InputMap iMap = txtpnMessage.getInputMap(condition);
		ActionMap aMap = txtpnMessage.getActionMap();

		iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "enter");

		aMap.put("enter", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				sendChatMessage();
			}
		});

		iMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.SHIFT_DOWN_MASK), "shift-enter");
		aMap.put("shift-enter", new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				txtpnMessage.setText(txtpnMessage.getText() + "\n");
			}
		});
	}

	private void updateClientsList() {

		txtpnUsers.setText("Connected users:");

		Iterator<Map.Entry<String, User>> it = this.user.users.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry<String, User> pair = (Map.Entry<String, User>) it.next();
			txtpnUsers.setText(txtpnUsers.getText() + "\n" + pair.getKey());
		}
	}

	private void sendChatMessage() {

		// PLACEHOLDER, RETREIVE FROM COORDINATOR
		int sequenceNumber = 0;

		gc.sendChatMessage(txtpnMessage.getText(), user, sequenceNumber);
		txtpnMessage.setText("");
	}

	private void addClient(User user) {

		this.user.users.put(user.name, user);
		updateClientsList();
	}

	private void removeClient(User user) {
		this.user.users.remove(user.name);

		// this.user.clocks.remove(user.name);

		updateClientsList();
	}

	private void refreshMessagePane() {

		txtpnChat.setText("");

		for (int i = 0; i < messages.size(); i++) {

			if (messages.get(i) instanceof ChatMessage) {

				ChatMessage message = (ChatMessage) messages.get(i);

				txtpnChat.setText(txtpnChat.getText() + "\n" + message);

				// String clocks = "";

				// Iterator<Map.Entry<String, Integer>> it = user.clocks.entrySet().iterator();

				// while (it.hasNext()) {
				// Map.Entry<String, Integer> pair = (Map.Entry<String, Integer>) it.next();
				// clocks += " " + pair.getKey() + " " +
				// user.clocks.get(pair.getKey()).toString();
				// }

				appendToChat(message.user.name + ": " + message.chat);

			} else if (messages.get(i) instanceof JoinMessage) {

				JoinMessage message = (JoinMessage) messages.get(i);

				appendToChat(message.user.name + " joined the chat!");

			} else if (messages.get(i) instanceof LeaveMessage) {

				LeaveMessage message = (LeaveMessage) messages.get(i);

				appendToChat(message.user.name + " left the chat.");
			}

		}
	}

	private void appendToChat(String message) {

		if (txtpnChat.getText().equals("")) {
			txtpnChat.setText(message);
		} else {

			txtpnChat.setText(txtpnChat.getText() + "\n" + message);
		}
	}

	// private void insertMessage(ChatMessage message) {

	// for (int i = 0; i < messages.size(); i++) {

	// if (messages.get(i) instanceof ChatMessage) {

	// ChatMessage iterMessage = (ChatMessage) messages.get(i);

	// if (iterMessage.user.clock >= mess) {
	// messages.add(i, message);
	// return;
	// }
	// }
	// }
	// messages.add(message);
	// }

	// private boolean validateClocks(User other) {
	// if (other.name.equals(user.name)) {
	// return true;
	// }
	// int myclock = user.clocks.get(other.name);
	// int otherclock = other.clocks.get(other.name);

	// if (myclock + 1 == otherclock) {
	// user.clocks.put(other.name, otherclock);
	// return user.clocks.equals(other.clocks);
	// }
	// return false;
	// }

	private void openChat() {

		// user = new User(txtpnUsername.getText());

		// String username = txtpnUsername.getText();

		// gc = new GroupCommuncation();

		// gc.sendAuthMessage(username);

		gc.sendJoinMessage(user);

		joinFrame.setVisible(false);
		chatFrame.setVisible(true);

		System.out.println("Group Communcation Started");
	}

	@Override
	public void actionPerformed(final ActionEvent event) {
		if (event.getActionCommand().equalsIgnoreCase("send")) {
			sendChatMessage();
		}
		if (event.getActionCommand().equalsIgnoreCase("join")) {

			String username = txtpnUsername.getText();

			this.assignMessageReceived = false;

			gc.sendAuthMessage(username);

			try {
				Thread.sleep(1000);

				if (!this.assignMessageReceived) {

					this.assignMessageReceived = true;

					System.out.println("new coordinator created");

					this.user = new Coordinator(user, 0);

					openChat();
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onIncomingAuthMessage(AuthMessage authMessage) {
		if (this.user instanceof Coordinator) {
			Coordinator coordinator = (Coordinator) user;

			int processId = coordinator.approveUsername(authMessage.username);

			System.out.println("sending assign message...");

			gc.sendAssignMessage(processId);
		}
	}

	@Override
	public void onIncomingAssignMessage(AssignMessage assignMessage) {
		if (this.user == null && !this.assignMessageReceived) {

			System.out.println("received assign message...");

			this.assignMessageReceived = true;

			if (assignMessage.processId == -1) {
				System.out.println("Username is already taken...");
				this.txtpnUsername.setText("");
			} else {

				System.out.println("processId: " + assignMessage.processId);

				this.user = new User(txtpnUsername.getText(), assignMessage.processId);
				openChat();
			}
		}
	}

	@Override
	public void onIncomingChatMessage(final ChatMessage chatMessage) {

		if (!this.assignMessageReceived)
			return;

		// if (!validateClocks(chatMessage.user)) {
		// System.out.println("System is no longer in sync :(");

		// } else {

		messages.add(chatMessage);

		refreshMessagePane();
		// }
	}

	@Override
	public void onIncomingStatusMessage(final StatusMessage statusMessage) {
		if (!this.assignMessageReceived)
			return;
		addClient(statusMessage.user);
		// user.clocks.put(statusMessage.user.name,
		// statusMessage.user.clocks.get(statusMessage.user.name));
	}

	@Override
	public void onIncomingJoinMessage(final JoinMessage joinMessage) {
		if (!this.assignMessageReceived)
			return;

		messages.add(joinMessage);

		addClient(joinMessage.user);

		gc.sendStatusMessage(user);

		refreshMessagePane();
	}

	@Override
	public void onIncomingLeaveMessage(final LeaveMessage leaveMessage) {
		if (!this.assignMessageReceived)
			return;

		messages.add(leaveMessage);

		removeClient(leaveMessage.user);

		refreshMessagePane();
	}

	@Override
	public void onIncomingGenerateSequenceNumberMessage(GenerateSequenceNumberMessage generateSequenceNumberMessage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onIncomingGetUserIDMessageMessage(GetUserIDMessage getUserIDMessage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onIncomingSequenceNumberMessage(SequenceNumberMessage sequenceNumberMessage) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onIncomingUserIDMessage(UserIDMessage userIDMessage) {
		// TODO Auto-generated method stub

	}
}
