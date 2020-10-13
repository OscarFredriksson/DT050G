package se.miun.distsys;

import java.io.Serializable;

import java.util.HashMap;

@SuppressWarnings("serial")
public class User implements Serializable {
    public String name;

    public int processId;

    public HashMap<String, Integer> clocks = new HashMap<String, Integer>();

    public User(String name, int processId) {
        this.name = name;
        this.processId = processId;
        clocks.put(name, 0);
    }
}