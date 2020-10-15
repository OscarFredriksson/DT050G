package se.miun.distsys.messages;

@SuppressWarnings("serial")
public class GetUserIdMessage extends Message {
    public int sequenceNumber;
    public int authorId;

    public GetUserIdMessage(int sequenceNumber, int authorId) {
        this.sequenceNumber = sequenceNumber;
        this.authorId = authorId;
    }
}
