package org.learn.functional.java.common;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.learn.functional.java.types.Effect;
import org.learn.functional.java.types.Executable;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.learn.functional.java.common.CollectionUtilities.*;

class CollectionUtilitiesTest {
    @Test
    void testFoldViaIntegerAddition() {
        List<Integer> numbers = list(1, 2, 3, 4, 5);
        Integer expected = numbers.stream().reduce(0, (x, y) -> x + y);
        Integer actual = foldLeft(numbers, 0, x -> y -> x + y);
        Integer actualRFold = foldRight(numbers, 0, x -> y -> x + y);
        assertEquals(expected,actual, "Fold operation is not working for addition!");
        assertEquals(actual, actualRFold);
    }

    @Test
    void testFoldViaIntegerMultiplication() {
        List<Integer> numbers = list(1, 2, 3, 4, 5);
        Integer expected = numbers.stream().reduce(1, (x, y) -> x * y);
        Integer actual = foldLeft(numbers, 1, x -> y -> x * y);
        Integer actualRFold = foldRight(numbers, 1, x -> y -> x * y);
        assertEquals(expected,actual, "Fold operation is not working for multiplication");
        assertEquals(actual, actualRFold);
    }

    @Test
    void testFoldViaStringConcat() {
        List<String> strings = list("d", "i", "b", "a", "k", "a", "r");
        String identity = "";
        String expected = strings.stream().reduce("", (a, b) -> a + b);
        String actual = foldLeft(strings, identity, x -> y -> x + y);
        String actualRight = foldRight(strings, identity, x -> y -> x + y);
        assertEquals(expected, actual, "Fold operation is not working for string join");
        assertEquals(expected, actualRight);
    }

    @Test
    void testListPrepend() {
        var target = List.of("sen");
        var elem = "dibakar";
        var expected = List.of("dibakar","sen");
        List<String> actual = prepend(elem, target);
        assertIterableEquals(expected, actual);
    }

    @Test
    void testListReverse() {
        var target = List.of("sen","dibakar");
        var expected = List.of("dibakar","sen");
        List<String> actual = reverse(target);
        assertIterableEquals(expected, actual);
    }

    @Test
    @Tags({@Tag("Concepts")})
    void demoApplyingEffectsToList() {
        var prices = List.of(10.1, 20.4, 54.1, 19.5, 50.99);
        Function<Double, Double> addTaxToPrice = p -> p + (0.05 * p);
        Function<Double, Double> addShippingToTaxedPrice = p -> p + (0.02 * p);
        var pricesIncludingShipping = prices.stream()
                .map(price -> addTaxToPrice.andThen(addShippingToTaxedPrice).apply(price))
                .collect(toUnmodifiableList());
        // Testable piece of code which causes side effect
        Effect<Double> printPriceUpto2Decimals = p -> System.out.printf("%.2f %n", p);
        System.out.println("Demonstrating applying effects to list - Effect: print to console");
        forEach(pricesIncludingShipping, printPriceUpto2Decimals);
    }

    // Testable piece of code which causes side effect.
    // Notice that is applies a single effect to a collection
    private <T> void forEach(Collection<T> aggregate, Effect<T> e) {
        aggregate.forEach(elem -> e.apply(elem));
    }

    @Test
    @Tags({@Tag("Concepts")})
    void demoApproachingFunctionalOutput() {
        var prices = List.of(10.1, 20.4, 54.1, 19.5, 50.99);
        // First transformation
        Function<Double, Double> addTaxToPrice = p -> p + (0.05 * p);
        // Second transformation
        Function<Double, Double> addShippingToTaxedPrice = p -> p + (0.02 * p);
        var pricesIncludingShipping = prices.stream()
                // Notice the transformation of composition
                .map(price -> addTaxToPrice.andThen(addShippingToTaxedPrice).apply(price))
                .collect(toUnmodifiableList());

        Function<Executable, Function<Executable, Executable>> compose =
                x -> y -> () -> {
                    x.exec(); // a side-affect
                    y.exec(); // a side-affect
                };
        Executable ez = () -> { System.out.println("-- Demonstrating approaching functional output --"); };
        //foldLeft last parameter - Function<U, Function<T, U>> fn
        Executable program = foldLeft(pricesIncludingShipping, // collection
                ez, // identity
                identity -> d ->
                        compose.apply(identity)
                                .apply(() -> System.out.printf("%.2f %n", d))
        );
        // Notice how we transformed a side affect causing behaviour to a functional one - take an input and return a output
        program.exec();
    }
}