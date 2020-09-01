package se.miun.distsys.messages;

import se.miun.distsys.User;

@SuppressWarnings("serial")
public class LeaveMessage extends Message {
    public User user;

    public LeaveMessage(User user) {
        this.user = user;
    }
}