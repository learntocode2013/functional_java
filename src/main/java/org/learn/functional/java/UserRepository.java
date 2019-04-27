package org.learn.functional.java;

import java.util.Arrays;
import java.util.List;

public class UserRepository {
    public List<User> fetchAll() {
        return Arrays.asList(
                new User("Dibakar", "Bangalore"),
                new User("Martin", "Massachusets"),
                new User("Ted", "New Jersey"),
                new User("Victor", "Brazil")
        );
    }
}
