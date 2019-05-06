package org.learn.functional.java.concepts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

class Monad {
    static final List<String> FPDEV_NAMES = List.of(
            "Ted Neward", "Martin Fowler", "Adam Bien", "Dibakar","Victor Rantea"
    );
    private final Logger logger = LoggerFactory.getLogger(Monad.class.getSimpleName());
    private final Map<String, Person> personMap;

    Monad() {
        this.personMap = getAddrsOfFpDevs();
    }

    // Monad takes in a function and returns a value wrapped in a context
    void printWorkCityOfFpDev(String devName) {
         findFpDev(devName)
                .flatMap(Person::getAddress)
                .flatMap(Address::getCity)
                .ifPresent(city -> process(devName, city));
    }

    private void process(String devName, City city) {
        logger.info("{} works in {}",devName, city.getName());
    }

    private Optional<Person> findFpDev(String name) {
        return Optional.ofNullable(personMap.get(name));
    }

    private Map<String, Person> getAddrsOfFpDevs() {
        Address bglAddr  = new Address(new City("Bangalore", 560100));
        Address nyAddr   = new Address(new City("New York", 560100));
        Address sfAddr   = new Address(new City("San Francisco", 560100));
        Address gmbhAddr = new Address(new City("Berlin", 560100));

        Person ted    = new Person(FPDEV_NAMES.get(0)); ted.setAddress(nyAddr);
        Person martin = new Person(FPDEV_NAMES.get(1)); martin.setAddress(nyAddr);
        Person adam   = new Person(FPDEV_NAMES.get(2)); adam.setAddress(gmbhAddr);
        Person disen  = new Person(FPDEV_NAMES.get(3));
        Person victor = new Person(FPDEV_NAMES.get(4));

        return Map.of(
                ted.getName(), ted,
                martin.getName(), martin,
                adam.getName(), adam,
                disen.getName(), disen,
                victor.getName(), victor);
    }
}
