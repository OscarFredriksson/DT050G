package se.miun.distsys.messages;

@SuppressWarnings("serial")
public class AssignMessage extends Message {
    public int processId;

    public AssignMessage(int processId) {
        this.processId = processId;
    }
}
