package org.learn.functional.java.concepts;

import com.google.common.flogger.FluentLogger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.learn.functional.java.Customer;
import org.learn.functional.java.MemberCard;
import org.learn.functional.java.collectors.GroupByFirstCharacterCollector;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.learn.functional.java.concepts.Applicative.DEFAULT_DISCOUNT_PERCENTAGE;
import static org.learn.functional.java.concepts.Applicative.DISCOUNT_LINE_TEMPLATE;

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

    @Test
    void demo_custom_collector_groupByFirstCharacter() {
        var groupedByFirstChar = Stream.of(
                "Java", "Javascript", "Shell script", "Scala", "Golang", "Groovy"
        ).collect(new GroupByFirstCharacterCollector());

        System.out.println(groupedByFirstChar);
    }

    @Test
    void demo_teeing_collector_insteadOfStreamingTwice() {
        var productInventory = List.of(
                new Product(1L,"iPhone-12S", BigDecimal.valueOf(90_000)),
                new Product(2L, "Das Mechanical Keyboard", BigDecimal.valueOf(25_000))
        );

        Cart shoppingCart = new Cart();

        productInventory.forEach(product -> shoppingCart.add(product,2));

        var priceAndRows = shoppingCart.getProducts()
                .entrySet()
                .stream()
                .map(CartRow::new)
                .collect(Collectors.teeing(
                   Collectors.reducing(BigDecimal.ZERO,CartRow::getRowPrice,BigDecimal::add),
                        Collectors.toList(),
                        PriceAndRows::new
                ));

        assertNotNull(priceAndRows);

        // Some custom UI logic
        displayCartDetails_BeforePayment(priceAndRows);
    }

    @Test
    void demo_custom_collector_insteadOfStreamingTwice() {
        var productInventory = List.of(
                new Product(1L,"iPhone-12S", BigDecimal.valueOf(90_000)),
                new Product(2L, "Das Mechanical Keyboard", BigDecimal.valueOf(25_000))
        );

        Cart shoppingCart = new Cart();
        // Add some products to the shopping cart
        productInventory.forEach(p -> shoppingCart.add(p,2));

        // Display the cart before final payment.
        var priceAndRows = shoppingCart.getProducts()
                .entrySet()
                .stream()
                .collect(new PriceAndRowsCollector());
        assertNotNull(priceAndRows);
        // Some custom UI logic
        displayCartDetails_BeforePayment(priceAndRows);
    }

    private void displayCartDetails_BeforePayment(PriceAndRows priceAndRows) {
        System.out.println("----- Confirm order details ------");
        priceAndRows.getRows().forEach(r -> System.out.printf("%s(%d) x %d = %d %n",
                r.product().getLabel(),
                r.product().getPrice().intValue(),
                r.quantity(),
                r.getRowPrice().intValue()));
        System.out.println("----------------------------------");
        System.out.printf("Total: %d \n", priceAndRows.getPrice().intValue());
    }

    @Test
    @DisplayName("Demonstrates the conversion of a 3 parameter method call to method reference")
    void demoMethodReferenceWith_ThreeParams_PassThroughType() {
        assertNotEquals(0,process(this::addThreeNums));
        logger.at(Level.INFO).log("Result from method reference: %d ",
                process(this::addThreeNums));
    }

    private int process(TriFunction<Integer,Integer,Integer,Integer> func) {
        return func.apply(
                1,
                2,
                3);
    }
    private int addThreeNums(int one, int two, int three) {
        return one + two + three;
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
