package se.miun.distsys.messages.ConnectMessages;

import se.miun.distsys.messages.Message;

@SuppressWarnings("serial")
public class AuthMessage extends Message {

    public String username;

    public AuthMessage(String username) {
        this.username = username;
    }
}
