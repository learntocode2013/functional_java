package org.learn.functional.java;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class OrderRepository {
    public List<Order> fetchActiveOrders() {
        List<Order> orders = Arrays.asList(
                new Order(UUID.randomUUID().toString(), new Customer("Dibakar"), 1267.43f),
                new Order(UUID.randomUUID().toString(), new Customer("Martin"), 1267.43f),
                new Order(UUID.randomUUID().toString(), new Customer("Neal"), 1267.43f),
                new Order(UUID.randomUUID().toString(), new Customer("Victor"), 1267.43f)
        );
        return orders;
    }
}
