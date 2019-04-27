package org.learn.functional.java;

public class User {
    private final String name;
    private final String place;

    public User(String name, String place) {
        this.name = name;
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }
}
