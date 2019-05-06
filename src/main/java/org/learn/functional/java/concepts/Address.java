package org.learn.functional.java.concepts;

import java.util.Optional;

public class Address {
    private City city;

    Address(City city) {
        this.city = city;
    }

    public Optional<City> getCity() {
        return Optional.ofNullable(city);
    }

    public void setCity(City city) {
        this.city = city;
    }
}
