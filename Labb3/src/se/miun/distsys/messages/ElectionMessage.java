package se.miun.distsys.messages;

@SuppressWarnings("serial")
public class ElectionMessage extends Message {
    public int authorId;

    public ElectionMessage(int authorId) {
        this.authorId = authorId;
    }
}
