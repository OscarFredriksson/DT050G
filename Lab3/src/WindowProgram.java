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

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("serial")
public class WindowProgram implements ChatMessageListener, ActionListener {

	// HashMap<String, User> users = new HashMap<String, User>();

	ArrayList<Message> messages = new ArrayList<Message>();

	public HashMap<Integer, ChatMessage> misses = new HashMap<Integer, ChatMessage>();

	public User user;

	JFrame chatFrame;
	JTextPane txtpnChat = new JTextPane();
	JTextPane txtpnMessage = new JTextPane();
	JTextPane txtpnUsers = new JTextPane();

	JFrame joinFrame;
	JTextPane txtpnUsername = new JTextPane();

	GroupCommuncation gc = null;

	boolean assignMessageReceived = false;

	boolean expectsSequenceNumberMessage = false;
	SequenceNumberMessage receivedSequenceNumberMessage = null;
	int getSequenceNumberResult;

	boolean expectsUserIDMessage = false;
	UserIdMessage receivedUserIdMessage = null;

	boolean expectsAliveMessage = false;
	AliveMessage receivedAliveMessage = null;

	boolean expectsVictoryMessage = false;
	VictoryMessage receivedVictoryMessage = null;

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

	private boolean isCoordinator() {
		return user instanceof Coordinator;
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

		System.out.println("isCoordinator:" + isCoordinator());
		int sequenceNumber = isCoordinator() ? getSequenceNumberAsCoordinator() : getSequenceNumberAsUser();

		gc.sendChatMessage(txtpnMessage.getText(), user, sequenceNumber);
		txtpnMessage.setText("");
	}

	private int getSequenceNumberAsCoordinator() {
		Coordinator coordinator = (Coordinator) user;

		int seq = coordinator.getSequenceNumber(user.processId);

		return seq;
	}

	private int getSequenceNumberAsUser() {
		this.expectsSequenceNumberMessage = true;
		this.receivedSequenceNumberMessage = null;

		gc.sendGenerateSequenceNumberMessage(user.processId);

		Thread th = new Thread() {

			public void run() {
				int i = 0;
				while (receivedSequenceNumberMessage == null) {

					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					if (i > 20) {
						// No response from coordinator, elect a new one
						System.out.println(
								"id: " + user.processId + " no sequence number recieved, starting election...");

						doElection();
						// return;

						while (receivedVictoryMessage == null) {
							try {
								System.out.println("waiting for election to be done...");
								Thread.sleep(50);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}

						getSequenceNumberResult = isCoordinator() ? getSequenceNumberAsCoordinator()
								: getSequenceNumberAsUser();
						return;
					}

					i++;
				}

				// user.sequenceList.add(user.processId);
				getSequenceNumberResult = receivedSequenceNumberMessage.sequenceNumber;
			}
		};

		th.start();

		try {
			th.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return getSequenceNumberResult;
	}

	private void doElection() {

		if (isCoordinator())
			return;

		expectsAliveMessage = true;
		receivedAliveMessage = null;

		expectsVictoryMessage = true;
		receivedVictoryMessage = null;

		gc.sendElectionMessage(this.user.processId);

		Thread waitForAliveThread = new Thread() {
			public void run() {
				int i = 0;
				while (receivedAliveMessage == null) {
					try {
						System.out.println("waiting for alive...");
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					if (i > 20) {
						// No alive messages, become the winner
						System.out.println("I am now coordinator");
						user = new Coordinator(user, user.processId);
						gc.sendVictoryMessage(user.processId);
						break;
					}
					i++;
				}
				System.out.println("Alive message received");
			}
		};

		waitForAliveThread.start();

		Thread waitForVictoryThread = new Thread() {
			public void run() {
				int i = 0;
				while (receivedVictoryMessage == null) {
					try {
						// System.out.println("waiting for victory...");
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					if (i > 30) {
						// No victory message, restart election
						if (!isCoordinator()) {
							System.out.println("id: " + user.processId + " started election...");
							doElection();
						}
					}
					i++;
				}

				System.out.println("election done...");
			}
		};
		waitForVictoryThread.start();
	}

	private void addClient(User user) {

		this.user.users.put(user.name, user);
		updateClientsList();
	}

	private void removeClient(User user) {
		this.user.users.remove(user.name);

		updateClientsList();
	}

	private void refreshMessagePane() {

		txtpnChat.setText("");

		for (int i = 0; i < messages.size(); i++) {

			if (messages.get(i) instanceof ChatMessage) {

				ChatMessage message = (ChatMessage) messages.get(i);

				// txtpnChat.setText(txtpnChat.getText() + "\n");
				appendToChat(message.user.name + ": " + message.chat);

			} else if (messages.get(i) instanceof JoinMessage) {

				JoinMessage message = (JoinMessage) messages.get(i);

				appendToChat(message.user.name + " joined the chat as id " + message.user.processId);

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

	private void openChat() {

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

					this.user = new Coordinator(new User(username, 0), 0);

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

			gc.sendAssignMessage(processId, this.user.sequenceList, this.messages);
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

				this.user.sequenceList = assignMessage.history;
				this.messages = assignMessage.chatHistory;
				openChat();
			}
		}
	}

	public int getLastSequenceNumber() {
		int lastSequenceNumber = 0;

		for (int i = messages.size() - 1; i >= 0; i--) {
			if (messages.get(i) instanceof ChatMessage) {
				ChatMessage chatMessage = (ChatMessage) messages.get(i);

				lastSequenceNumber = chatMessage.sequenceNumber;
				break;
			}
		}

		return lastSequenceNumber;
	}

	public void insertMessage(Message message) {

		int lastSequenceNumber = getLastSequenceNumber();

		if (message instanceof ChatMessage) {

			ChatMessage chatMessage = (ChatMessage) message;

			if (lastSequenceNumber + 1 < chatMessage.sequenceNumber) {
				misses.put(chatMessage.sequenceNumber, chatMessage);
				return;
			}
		}

		messages.add(message);

		insertMisses();

		refreshMessagePane();
	}

	public void insertMisses() {
		Integer nextMsg = getLastSequenceNumber() + 1;

		ChatMessage message = misses.get(nextMsg);

		if (message == null)
			return;

		misses.remove(nextMsg);
		messages.add(message);

		insertMisses();
	}

	@Override
	public void onIncomingChatMessage(final ChatMessage chatMessage) {

		if (!this.assignMessageReceived)
			return;

		if (isCoordinator())
			handleChatMessageAsCoordinator(chatMessage);
		else
			handleChatMessageAsUser(chatMessage);
	}

	public void handleChatMessageAsUser(ChatMessage chatMessage) {

		this.expectsUserIDMessage = true;
		this.receivedUserIdMessage = null;

		System.out.println("sending getUserIDMessage...");

		gc.sendGetUserIDMessage(chatMessage.sequenceNumber, user.processId);

		Thread th = new Thread() {

			public void run() {
				int i = 0;
				while (receivedUserIdMessage == null) {
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					if (i > 20) {
						System.out.println("no answer from coordinator, starting election...");
						doElection();
						return;
					}
					i++;
				}

				System.out.println(receivedUserIdMessage);
				int uid = receivedUserIdMessage.userId;

				if (uid != chatMessage.user.processId) {
					return;
				}

				user.sequenceList.add(chatMessage.sequenceNumber + 1);

				insertMessage(chatMessage);
			}
		};

		th.start();
	}

	public void handleChatMessageAsCoordinator(ChatMessage chatMessage) {

		Coordinator coordinator = (Coordinator) user;

		int uid = coordinator.getUserIdFromSequenceNumber(chatMessage.sequenceNumber);

		if (uid != chatMessage.user.processId) {
			// Detta bör aldrig hända, ignorera och hoppa det ej händer igen :)
			return;
		}

		insertMessage(chatMessage);

	}

	@Override
	public void onIncomingStatusMessage(final StatusMessage statusMessage) {

		if (!this.assignMessageReceived)
			return;

		addClient(statusMessage.user);
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

		if (!this.assignMessageReceived || !isCoordinator())
			return;

		Coordinator coordinator = (Coordinator) user;
		int seq = coordinator.getSequenceNumber(generateSequenceNumberMessage.authorId);

		gc.sendSequenceNumberMessage(seq, generateSequenceNumberMessage.authorId);
	}

	@Override
	public void onIncomingGetUserIDMessage(GetUserIdMessage getUserIDMessage) {

		System.out.println("received GetUserIDMessage");

		if (!this.assignMessageReceived || !isCoordinator()) {
			return;
		}

		Coordinator coordinator = (Coordinator) user;
		int uid = coordinator.getUserIdFromSequenceNumber(getUserIDMessage.sequenceNumber);

		System.out.println("Got a getUserIdMessage, responding...");

		gc.sendUserIDMessage(uid, getUserIDMessage.authorId);
	}

	@Override
	public void onIncomingSequenceNumberMessage(SequenceNumberMessage sequenceNumberMessage) {

		System.out.println("incoming sequenceNumberMessage");

		if (!this.assignMessageReceived || isCoordinator() || !expectsSequenceNumberMessage
				|| sequenceNumberMessage.recipientId != user.processId) {
			return;
		}

		this.expectsSequenceNumberMessage = false;
		this.receivedSequenceNumberMessage = sequenceNumberMessage;
	}

	@Override
	public void onIncomingUserIDMessage(UserIdMessage userIdMessage) {

		System.out.println("asdasdasd");
		System.out.println("assigned " + this.assignMessageReceived);
		System.out.println("isCoordinator: " + isCoordinator());
		System.out.println("expects: " + expectsUserIDMessage);
		System.out.println("recipient: " + userIdMessage.recipientId + " " + user.processId);

		if (!this.assignMessageReceived || isCoordinator() || !expectsUserIDMessage
				|| userIdMessage.recipientId != user.processId) {
			return;
		}

		System.out.println("received userid message");

		this.expectsUserIDMessage = false;
		this.receivedUserIdMessage = userIdMessage;

	}

	@Override
	public void onIncomingAliveMessage(AliveMessage aliveMessage) {
		System.out.println("received alive message: " + aliveMessage.authorId + " " + user.processId);

		if (!this.assignMessageReceived || isCoordinator() || !expectsAliveMessage
				|| aliveMessage.authorId <= user.processId) {
			return;
		}

		this.expectsAliveMessage = false;
		this.receivedAliveMessage = aliveMessage;
	}

	@Override
	public void onIncomingElectionMessage(ElectionMessage electionMessage) {
		if (electionMessage.authorId >= this.user.processId) {
			return;
		}

		System.out.println("received election message");

		gc.sendAliveMessage(this.user.processId);

		doElection();
	}

	@Override
	public void onIncomingVictoryMessage(VictoryMessage victoryMessage) {

		System.out.println(victoryMessage.authorId + " is now the Coordinator.");

		if (!this.expectsVictoryMessage) {
			return;
		}

		this.expectsVictoryMessage = false;
		this.receivedVictoryMessage = victoryMessage;
	}
}
