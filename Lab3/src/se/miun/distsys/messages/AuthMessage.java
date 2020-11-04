package se.miun.distsys.messages;

@SuppressWarnings("serial")
public class AuthMessage extends Message {

    public String username;

    public AuthMessage(String username) {
        this.username = username;
    }
}
