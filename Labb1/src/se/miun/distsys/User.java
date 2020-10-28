package se.miun.distsys;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements Serializable {
    public String name;

    public User(String name) {
        this.name = name;
    }
}