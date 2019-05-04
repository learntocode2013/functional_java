package org.learn.functional.java.concepts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class TestConcepts {
    @Test
    @DisplayName("Demonstrates usage of applicatives")
    void demoApplicative() {
        String[] parts = { "demo", "applicative" };
        demoWithOutDefaultFunction(parts);
        demoWithCustomFunction(parts);
    }

    private void demoWithOutDefaultFunction(String... parts) {
        Applicative testSubject = new Applicative();
        assertEquals(String.join(" ", parts),testSubject.applyDelimiter(parts));
    }

    private void demoWithCustomFunction(String... parts) {
        Function<String, String> joinWithColon = input -> input.replaceAll("\\s+",":");
        Applicative testSubject = new Applicative(joinWithColon);
        assertEquals(String.join(":",parts), testSubject.applyDelimiter(parts));
    }
}
