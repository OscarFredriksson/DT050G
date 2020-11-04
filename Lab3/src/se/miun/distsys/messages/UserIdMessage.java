package se.miun.distsys.messages;

@SuppressWarnings("serial")
public class UserIdMessage extends Message {
    public int userId;
    public int recipientId;

    public UserIdMessage(int userId, int recipientId) {
        this.userId = userId;
        this.recipientId = recipientId;
    }
}
