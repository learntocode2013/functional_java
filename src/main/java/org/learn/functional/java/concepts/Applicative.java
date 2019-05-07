package org.learn.functional.java.concepts;

import com.google.common.flogger.FluentLogger;
import org.learn.functional.java.Customer;
import org.learn.functional.java.MemberCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.function.Function;
import java.util.logging.Level;

import static java.util.logging.Level.*;

class Applicative {
    static final int DEFAULT_DISCOUNT_PERCENTAGE = 2;
    static final String DISCOUNT_LINE_TEMPLATE = "Discount: %s";
    static private final FluentLogger logger = FluentLogger.forEnclosingClass();
    private Optional<Function<Integer, Integer>> funcWrappedInContext = Optional.empty();
    private final Customer aCustomer;

    Applicative(Customer aCustomer) {
        this.aCustomer = aCustomer;
    }

    Applicative(Customer aCustomer, Function<Integer, Integer> discountFunc) {
        this.aCustomer = aCustomer;
        this.funcWrappedInContext = Optional.of(discountFunc);
    }

    String getPrintableDiscountLine() {
        // Value wrapped in a context
        Optional<MemberCard> maybeCard = aCustomer.getCard();
        // Applies a function wrapped in a context to a value wrapped in a context
        return maybeCard.map(this::applyWrappedFuncToWrappedValue)
                .map(discountPercent -> String.format(DISCOUNT_LINE_TEMPLATE,discountPercent))
                .orElse(String.format(DISCOUNT_LINE_TEMPLATE,"N/A"));
    }

    private int applyWrappedFuncToWrappedValue(MemberCard unwrappedValue) {
        return funcWrappedInContext.or(this::getDefaultWrappedFunction)
                .get()
                .apply(unwrappedValue.getFidelityPoints());
    }

    private Optional<Function<Integer, Integer>> getDefaultWrappedFunction() {
        logger.at(INFO).log("Default applicative will be used since none was specified...");
        return Optional.of(loyaltyPoints -> DEFAULT_DISCOUNT_PERCENTAGE);
    }
}
