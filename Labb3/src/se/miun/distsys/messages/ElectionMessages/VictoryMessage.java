package se.miun.distsys.messages.ElectionMessages;

import se.miun.distsys.messages.Message;

@SuppressWarnings("serial")
public class VictoryMessage extends Message {
    public int authorId;

    public VictoryMessage(int authorId) {
        this.authorId = authorId;
    }
}
