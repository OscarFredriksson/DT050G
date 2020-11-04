package se.miun.distsys.messages;

@SuppressWarnings("serial")
public class GenerateSequenceNumberMessage extends Message {
    public int authorId;

    public GenerateSequenceNumberMessage(int authorId) {
        this.authorId = authorId;
    }
}
