package se.miun.distsys.messages.ConnectMessages;

import se.miun.distsys.messages.Message;

@SuppressWarnings("serial")
public class AssignMessage extends Message {
    public int processId;

    public AssignMessage(int processId) {
        this.processId = processId;
    }
}
