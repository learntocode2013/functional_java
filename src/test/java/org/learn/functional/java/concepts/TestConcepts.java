package org.learn.functional.java.concepts;

import com.google.common.flogger.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.learn.functional.java.Customer;
import org.learn.functional.java.MemberCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.learn.functional.java.concepts.Applicative.*;

class TestConcepts {
    static private final FluentLogger logger = FluentLogger.forEnclosingClass();

    @Test
    @DisplayName("Demonstrates usage of an applicative")
    void demoApplicative() {
        logger.at(Level.INFO).log("Demonstrating usage of %s", "demoApplicative");
        String[] parts = { "demo", "applicative" };
        Customer cashUser = new Customer("Victor Rantea");
        Customer aCardUser = new Customer("Dibakar");
        aCardUser.addCard(new MemberCard(100));

        cashUserWithNoDiscountFunc(cashUser);
        cardUserWithDiscountFunc(aCardUser);
        cardUserWithoutDiscountFunc(aCardUser);
    }

    @Test
    @DisplayName("Demonstrates usage of a monad")
    void demoMonad() {
        Monad testSubject = new Monad();
        testSubject.printWorkCityOfFpDev(Monad.FPDEV_NAMES.get(1));
        // This prints nothing. Observe how fail-safe it is
        testSubject.printWorkCityOfFpDev(Monad.FPDEV_NAMES.get(3));
    }

    @Test
    @DisplayName("Demonstrate transformation with function composition instead of composing transformations")
    void demoFuncComposition() {
        Function<Double, Double> addTax = price -> price + (0.09 * price);
        Function<Double, Double> addShipping = priceWithTax -> priceWithTax + 3.50;
        var priceList = List.of(120.34,200.43,400.23,599.90);
        var finalPriceList = priceList.stream()
                .map(price -> addTax.andThen(addShipping).apply(price)).collect(toList());
        for (int i = 0; i < priceList.size(); i++) {
            logger.at(Level.INFO).log("Mrp: %f | Final price: %f", priceList.get(i),
                    finalPriceList.get(i));
        }
    }

    private void cardUserWithoutDiscountFunc(Customer aCardUser) {
        Applicative testSubject = new Applicative(aCardUser);
        String expectedOutput = String.format(DISCOUNT_LINE_TEMPLATE, DEFAULT_DISCOUNT_PERCENTAGE);
        assertEquals(expectedOutput,testSubject.getPrintableDiscountLine());
    }

    private void cashUserWithNoDiscountFunc(Customer aCustomer) {
        Applicative testSubject = new Applicative(aCustomer);
        String expectedOutput = String.format(DISCOUNT_LINE_TEMPLATE, "N/A");
        assertEquals(expectedOutput,testSubject.getPrintableDiscountLine());
    }

    private void cardUserWithDiscountFunc(Customer aCustomer) {
        Function<Integer, Integer> discountFunc = lPoints -> lPoints >= 100 ? 10 : 5;
        Applicative testSubject = new Applicative(aCustomer, discountFunc);
        String expectedOutput = String.format(DISCOUNT_LINE_TEMPLATE, 10);
        assertEquals(expectedOutput,testSubject.getPrintableDiscountLine());
    }
}
