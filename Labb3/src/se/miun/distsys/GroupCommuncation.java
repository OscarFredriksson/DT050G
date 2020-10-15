package se.miun.distsys;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

import se.miun.distsys.listeners.ChatMessageListener;
import se.miun.distsys.messages.*;

public class GroupCommuncation {

	private int datagramSocketPort = 7331;

	DatagramSocket datagramSocket = null;
	boolean runGroupCommuncation = true;
	MessageSerializer messageSerializer = new MessageSerializer();

	ChatMessageListener chatMessageListener = null;

	public GroupCommuncation() {
		try {
			runGroupCommuncation = true;
			datagramSocket = new MulticastSocket(datagramSocketPort);
			datagramSocket.setBroadcast(true);

			ReceiveThread rt = new ReceiveThread();
			rt.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void shutdown() {
		runGroupCommuncation = false;
	}

	class ReceiveThread extends Thread {

		@Override
		public void run() {
			byte[] buffer = new byte[65536];
			DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);

			while (runGroupCommuncation) {
				try {
					datagramSocket.receive(datagramPacket);
					byte[] packetData = datagramPacket.getData();
					Message receivedMessage = messageSerializer.deserializeMessage(packetData);
					handleMessage(receivedMessage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		private void handleMessage(Message message) {

			if (message instanceof ChatMessage) {
				ChatMessage chatMessage = (ChatMessage) message;
				if (chatMessageListener != null) {
					chatMessageListener.onIncomingChatMessage(chatMessage);
				}
			} else if (message instanceof StatusMessage) {
				StatusMessage statusMessage = (StatusMessage) message;
				if (chatMessageListener != null) {
					chatMessageListener.onIncomingStatusMessage(statusMessage);
				}
			} else if (message instanceof JoinMessage) {
				JoinMessage joinMessage = (JoinMessage) message;
				if (chatMessageListener != null) {
					chatMessageListener.onIncomingJoinMessage(joinMessage);
				}
			} else if (message instanceof LeaveMessage) {
				LeaveMessage leaveMessage = (LeaveMessage) message;
				if (chatMessageListener != null) {
					chatMessageListener.onIncomingLeaveMessage(leaveMessage);
				}
			} else if (message instanceof AuthMessage) {
				AuthMessage authMessage = (AuthMessage) message;
				if (chatMessageListener != null) {
					chatMessageListener.onIncomingAuthMessage(authMessage);
				}
			} else if (message instanceof AssignMessage) {

				AssignMessage assignMessage = (AssignMessage) message;
				if (chatMessageListener != null) {
					chatMessageListener.onIncomingAssignMessage(assignMessage);
				}
			} else if (message instanceof GenerateSequenceNumberMessage) {

				GenerateSequenceNumberMessage generateSequenceNumberMessage = (GenerateSequenceNumberMessage) message;
				if (chatMessageListener != null) {
					chatMessageListener.onIncomingGenerateSequenceNumberMessage(generateSequenceNumberMessage);
				}
			} else if (message instanceof SequenceNumberMessage) {

				SequenceNumberMessage sequenceNumberMessage = (SequenceNumberMessage) message;
				if (chatMessageListener != null) {
					chatMessageListener.onIncomingSequenceNumberMessage(sequenceNumberMessage);
				}
			} else if (message instanceof GetUserIdMessage) {

				GetUserIdMessage getUserIDMessage = (GetUserIdMessage) message;
				if (chatMessageListener != null) {
					chatMessageListener.onIncomingGetUserIDMessage(getUserIDMessage);
				}
			} else if (message instanceof UserIdMessage) {

				UserIdMessage userIDMessage = (UserIdMessage) message;
				if (chatMessageListener != null) {
					chatMessageListener.onIncomingUserIDMessage(userIDMessage);
				}
			} else if (message instanceof AliveMessage) {

				AliveMessage aliveMessage = (AliveMessage) message;
				if (chatMessageListener != null) {
					chatMessageListener.onIncomingAliveMessage(aliveMessage);
				}
			} else if (message instanceof ElectionMessage) {

				ElectionMessage electionMessage = (ElectionMessage) message;
				if (chatMessageListener != null) {
					chatMessageListener.onIncomingElectionMessage(electionMessage);
				}
			} else if (message instanceof VictoryMessage) {

				VictoryMessage victoryMessage = (VictoryMessage) message;
				if (chatMessageListener != null) {
					chatMessageListener.onIncomingVictoryMessage(victoryMessage);
				}
			} else {
				System.out.println("Unknown message type");
			}
		}

	}

	public void sendAuthMessage(String username) {
		// try {
		AuthMessage authMessage = new AuthMessage(username);
		sendMessage(authMessage);
		// byte[] data = messageSerializer.serializeMessage(authMessage);
		// sendData(data);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	public void sendAssignMessage(int processId, ArrayList<Integer> history, ArrayList<Message> chatHistory) {
		// try {
		AssignMessage assignMessage = new AssignMessage(processId, history, chatHistory);
		sendMessage(assignMessage);
		// byte[] data = messageSerializer.serializeMessage(assignMessage);
		// sendData(data);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	public void sendLeaveMessage(User user) {
		// try {
		LeaveMessage leaveMessage = new LeaveMessage(user);
		sendMessage(leaveMessage);
		// byte[] data = messageSerializer.serializeMessage(leaveMessage);
		// sendData(data);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	public void sendStatusMessage(User user) {
		// try {
		StatusMessage statusMessage = new StatusMessage(user);
		sendMessage(statusMessage);
		// byte[] data = messageSerializer.serializeMessage(statusMessage);
		// sendData(data);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	public void sendJoinMessage(User user) {

		// try {
		JoinMessage joinMessage = new JoinMessage(user);
		sendMessage(joinMessage);
		// byte[] data = messageSerializer.serializeMessage(joinMessage);
		// sendData(data);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	public void sendChatMessage(String message, User user, int sequenceNumber) {

		// try {
		ChatMessage chatMessage = new ChatMessage(message, user, sequenceNumber);
		sendMessage(chatMessage);
		// byte[] data = messageSerializer.serializeMessage(joinMessage);
		// sendData(data);
		// } catch (Exception e) {
		// e.printStackTrace();xz
		// }
	}

	public void sendGenerateSequenceNumberMessage(int authorId) {
		// try {
		GenerateSequenceNumberMessage generateSequenceNumberMessage = new GenerateSequenceNumberMessage(authorId);
		sendMessage(generateSequenceNumberMessage);
		// byte[] data =
		// messageSerializer.serializeMessage(generateSequenceNumberMessage);
		// sendData(data);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	public void sendSequenceNumberMessage(int sequenceNumber, int recipientId) {
		SequenceNumberMessage sequenceNumberMessage = new SequenceNumberMessage(sequenceNumber, recipientId);
		sendMessage(sequenceNumberMessage);
	}

	public void sendGetUserIDMessage(int sequenceNumber, int authorId) {
		GetUserIdMessage getUserIDMessage = new GetUserIdMessage(sequenceNumber, authorId);
		sendMessage(getUserIDMessage);
	}

	public void sendUserIDMessage(int userId, int recipientId) {

		UserIdMessage userIDMessage = new UserIdMessage(userId, recipientId);
		sendMessage(userIDMessage);
	}

	public void sendAliveMessage(int authorId) {

		AliveMessage aliveMessage = new AliveMessage(authorId);
		sendMessage(aliveMessage);
	}

	public void sendElectionMessage(int authorId) {

		ElectionMessage electionMessage = new ElectionMessage(authorId);
		sendMessage(electionMessage);
	}

	public void sendVictoryMessage(int authorId) {

		VictoryMessage victoryMessage = new VictoryMessage(authorId);
		sendMessage(victoryMessage);
	}

	public void setChatMessageListener(ChatMessageListener listener) {
		this.chatMessageListener = listener;
	}

	private void sendMessage(Message message) {
		try {
			byte[] data = messageSerializer.serializeMessage(message);
			sendData(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendData(byte[] data) {
		try {
			DatagramPacket sendPacket = new DatagramPacket(data, data.length, InetAddress.getByName("255.255.255.255"),
					datagramSocketPort);
			datagramSocket.send(sendPacket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
