package se.miun.distsys.messages;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class AssignMessage extends Message {
    public int processId;
    public ArrayList<Integer> history;
    public ArrayList<Message> chatHistory;

    public AssignMessage(int processId, ArrayList<Integer> history, ArrayList<Message> chatHistory) {
        this.processId = processId;
        this.history = history;
        this.chatHistory = chatHistory;
    }
}
