package se.miun.distsys.messages.CoordinatorMessages;

import se.miun.distsys.messages.Message;

@SuppressWarnings("serial")
public class UserIDMessage extends Message {
    public int userId;
    public int authorId;

    public UserIDMessage(int userId, int authorId) {
        this.userId = userId;
        this.authorId = authorId;
    }
}
