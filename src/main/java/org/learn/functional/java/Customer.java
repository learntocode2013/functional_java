package org.learn.functional.java;

import java.util.Optional;

import static java.util.Optional.*;

public class Customer {
    private final String _name;
    private MemberCard _card;

    public Customer(String _name) {
        this._name = _name;
    }

    public void addCard(MemberCard card) {
        this._card = card;
    }

    public String getName() {
        return _name;
    }

    public Optional<MemberCard> getCard() {
        return ofNullable(_card);
    }
}
