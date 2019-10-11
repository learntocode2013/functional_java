package org.learn.functional.java.common;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.learn.functional.java.common.CollectionUtilities.*;

class CollectionUtilitiesTest {
    @Test
    void testFoldViaIntegerAddition() {
        List<Integer> numbers = list(1, 2, 3, 4, 5);
        Integer expected = numbers.stream().reduce(0, (x, y) -> x + y);
        Integer actual = fold(numbers, 0, x -> y -> x + y);
        Integer actualRFold = foldRight(numbers, 0, x -> y -> x + y);
        assertEquals(expected,actual, "Fold operation is not working for addition!");
        assertEquals(actual, actualRFold);
    }

    @Test
    void testFoldViaIntegerMultiplication() {
        List<Integer> numbers = list(1, 2, 3, 4, 5);
        Integer expected = numbers.stream().reduce(1, (x, y) -> x * y);
        Integer actual = fold(numbers, 1, x -> y -> x * y);
        Integer actualRFold = foldRight(numbers, 1, x -> y -> x * y);
        assertEquals(expected,actual, "Fold operation is not working for multiplication");
        assertEquals(actual, actualRFold);
    }

    @Test
    void testFoldViaStringConcat() {
        List<String> strings = list("d", "i", "b", "a", "k", "a", "r");
        String identity = "";
        String expected = strings.stream().reduce("", (a, b) -> a + b);
        String actual = fold(strings, identity, x -> y -> x + y);
        String actualRight = foldRight(strings, identity, x -> y -> x + y);
        assertEquals(expected, actual, "Fold operation is not working for string join");
        assertEquals(expected, actualRight);
    }
}