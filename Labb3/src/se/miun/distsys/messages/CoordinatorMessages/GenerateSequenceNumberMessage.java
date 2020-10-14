package se.miun.distsys.messages.CoordinatorMessages;

import se.miun.distsys.messages.Message;

@SuppressWarnings("serial")
public class GenerateSequenceNumberMessage extends Message {
    public int authorId;

    public GenerateSequenceNumberMessage(int authorId) {
        this.authorId = authorId;
    }
}
