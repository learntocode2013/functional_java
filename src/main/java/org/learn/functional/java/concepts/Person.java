package org.learn.functional.java.concepts;

import java.util.Optional;

public class Person {
    private final String name;
    private Address address;

    Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    Optional<Address> getAddress() {
        return Optional.ofNullable(address);
    }

    void setAddress(Address address) {
        this.address = address;
    }
}
