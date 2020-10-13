package se.miun.distsys;

@SuppressWarnings("serial")
public class Coordinator extends User {

    int highestProcessId;

    public Coordinator(String name, int processId, int highestProcessId) {
        super(name, processId);

        this.highestProcessId = highestProcessId;
    }

    public int approveUsername(String username) {

        if (this.clocks.get(username) == null)
            return ++highestProcessId;

        return -1;
    }
}
