package se.miun.distsys.messages.ChatMessages;

import se.miun.distsys.User;
import se.miun.distsys.messages.Message;

@SuppressWarnings("serial")
public class ChatMessage extends Message {

	public String chat = "";
	public User user;

	public int sequenceNumber;

	public ChatMessage(String chat, User user, int sequenceNumber) {
		this.chat = chat;
		this.user = user;
		this.sequenceNumber = sequenceNumber;
	}
}
