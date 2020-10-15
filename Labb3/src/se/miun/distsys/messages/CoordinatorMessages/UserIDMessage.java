package se.miun.distsys.messages.CoordinatorMessages;

import se.miun.distsys.messages.Message;

@SuppressWarnings("serial")
public class UserIDMessage extends Message {
    public int userId;
    public int recipientId;

    public UserIDMessage(int userId, int recipientId) {
        this.userId = userId;
        this.recipientId = recipientId;
    }
}
