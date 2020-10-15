package se.miun.distsys;

@SuppressWarnings("serial")
public class Coordinator extends User {

    private static int invalidUsername = -1;
    public int processIdCounter = 0;

    public Coordinator(User user, int processIdCounter) {
        super(user.name, user.processId);
        this.users = user.users;
        this.processIdCounter = processIdCounter;
        this.sequenceList = user.sequenceList;
    }

    public int approveUsername(String username) {
        synchronized (users) {
            if (this.users.get(username) == null)
                return ++processIdCounter;
        }
        return invalidUsername;
    }

    public int getSequenceNumber(int processId) {
        int nextSequenceNumber = 0;
        synchronized (sequenceList) {
            sequenceList.add(processId);
            nextSequenceNumber = sequenceList.size() - 1;
        }
        return nextSequenceNumber;
    }

    public int getUserIdFromSequenceNumber(int sequenceNumber) {
        int processId = 0;
        synchronized (sequenceList) {
            processId = sequenceList.get(sequenceNumber);
        }
        return processId;
    }

}
