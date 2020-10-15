package se.miun.distsys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("serial")
public class User implements Serializable {
    public String name;

    public Integer processId;

    public transient HashMap<String, User> users = new HashMap<String, User>();

    public transient ArrayList<Integer> sequenceList = new ArrayList<Integer>();

    public User(String name, Integer processId) {
        this.name = name;
        this.processId = processId;
        // users.put(name, 0);
    }
}