package se.miun.distsys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("serial")
public class User implements Serializable {
    public String name;

    public int processId;

    public transient HashMap<String, User> users = new HashMap<String, User>();

    public transient List<Integer> sequenceList = new ArrayList<Integer>();

    public User(String name, int processId) {
        this.name = name;
        this.processId = processId;
        // users.put(name, 0);
    }
}