package se.miun.distsys.messages;

import se.miun.distsys.User;

import java.util.Map;

@SuppressWarnings("serial")
public class StatusMessage extends Message {

    public User user;

    public StatusMessage(User user) {
        this.user = user;
    }
}