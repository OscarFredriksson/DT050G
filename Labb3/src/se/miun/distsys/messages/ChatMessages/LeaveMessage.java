package se.miun.distsys.messages.ChatMessages;

import se.miun.distsys.User;
import se.miun.distsys.messages.Message;

@SuppressWarnings("serial")
public class LeaveMessage extends Message {
    public User user;

    public LeaveMessage(User user) {
        this.user = user;
    }
}