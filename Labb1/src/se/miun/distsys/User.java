package se.miun.distsys;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements Serializable {
    public String name;

    public int clock = 0;

    public User(String name, int clock) {
        this.name = name;
        this.clock = clock;
    }
}