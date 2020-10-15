package se.miun.distsys.messages.ElectionMessages;

import se.miun.distsys.messages.Message;

@SuppressWarnings("serial")
public class AliveMessage extends Message {
    public int authorId;

    public AliveMessage(int authorId) {
        this.authorId = authorId;
    }
}
