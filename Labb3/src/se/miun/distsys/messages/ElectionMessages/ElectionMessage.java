package se.miun.distsys.messages.ElectionMessages;

import se.miun.distsys.messages.Message;

@SuppressWarnings("serial")
public class ElectionMessage extends Message {
    public int authorId;

    public ElectionMessage(int authorId) {
        this.authorId = authorId;
    }
}
