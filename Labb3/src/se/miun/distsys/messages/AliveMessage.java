package se.miun.distsys.messages;

@SuppressWarnings("serial")
public class AliveMessage extends Message {
    public int authorId;

    public AliveMessage(int authorId) {
        this.authorId = authorId;
    }
}
