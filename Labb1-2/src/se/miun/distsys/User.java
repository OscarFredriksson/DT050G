package se.miun.distsys;

import java.io.Serializable;

import java.util.Map;
import java.util.HashMap;

@SuppressWarnings("serial")
public class User implements Serializable {
    public String name;

    public HashMap<String, Integer> clocks = new HashMap<String, Integer>();

    public User(String name) {
        this.name = name;
        clocks.put(name, 0);
    }
}