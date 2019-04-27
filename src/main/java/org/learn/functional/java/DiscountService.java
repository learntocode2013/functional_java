package org.learn.functional.java;

import java.util.Optional;

import static java.util.Optional.*;

public class DiscountService {
    private String getDiscountLine(Customer customer) {
        return customer.getCard()
                .flatMap(this::getDiscount)
                .map(d -> "Discount %: " + d)
                .orElse("Discount %: NA");
    }

    private Optional<Integer> getDiscount(MemberCard card) {
        if (card.getFidelityPoints() >= 100) { return of(5); }
        if (card.getFidelityPoints() <= 50) { return of(3); }
        return empty();
    }

    public static void main(String[] args) {
        DiscountService service = new DiscountService();
        Customer cashUser = new Customer("Dibakar");
        System.out.println("Cash user --- " + service.getDiscountLine(cashUser));

        // Card user
        Customer cardUser = new Customer("Victor Rantea");
        cardUser.addCard(new MemberCard(60));
        System.out.println("Unqualified card user --- " + service.getDiscountLine(cardUser));

        Customer qualifiedUser = new Customer("Neal Ford");
        qualifiedUser.addCard(new MemberCard(200));
        System.out.println("Qualified card user --- " + service.getDiscountLine(qualifiedUser));
    }
}
