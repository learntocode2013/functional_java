package org.learn.functional.java.concepts;

public class City {
    private final String name;
    private final int pinCode;

    public City(String name, int pinCode) {
        this.name = name;
        this.pinCode = pinCode;
    }

    public String getName() {
        return name;
    }

    public int getPinCode() {
        return pinCode;
    }
}
