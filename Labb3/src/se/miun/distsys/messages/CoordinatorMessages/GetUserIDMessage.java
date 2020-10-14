package se.miun.distsys.messages.CoordinatorMessages;

import se.miun.distsys.messages.Message;

@SuppressWarnings("serial")
public class GetUserIDMessage extends Message {
    public int sequenceNumber;
    public int authorId;

    public GetUserIDMessage(int sequenceNumber, int authorId) {
        this.sequenceNumber = sequenceNumber;
        this.authorId = authorId;
    }
}
