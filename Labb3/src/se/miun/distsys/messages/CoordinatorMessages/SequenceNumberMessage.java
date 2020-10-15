package se.miun.distsys.messages.CoordinatorMessages;

import se.miun.distsys.messages.Message;

@SuppressWarnings("serial")
public class SequenceNumberMessage extends Message {
    public int sequenceNumber;
    public int recipientId;

    public SequenceNumberMessage(int sequenceNumber, int recipientId) {
        this.sequenceNumber = sequenceNumber;
        this.recipientId = recipientId;
    }
}
