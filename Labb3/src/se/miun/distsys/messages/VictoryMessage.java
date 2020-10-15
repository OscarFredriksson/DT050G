package se.miun.distsys.messages;

@SuppressWarnings("serial")
public class VictoryMessage extends Message {
    public int authorId;

    public VictoryMessage(int authorId) {
        this.authorId = authorId;
    }
}
