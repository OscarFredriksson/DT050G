package se.miun.distsys.messages.ConnectMessages;

import se.miun.distsys.User;
import se.miun.distsys.messages.Message;

@SuppressWarnings("serial")
public class StatusMessage extends Message {

    public User user;

    public StatusMessage(User user) {
        this.user = user;
    }
}