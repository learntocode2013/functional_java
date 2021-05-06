package org.learn.functional.java.concepts;

import java.math.BigDecimal;
import java.util.Map;

public record CartRow(Product product, int quantity) {
    public CartRow(Map .Entry<Product,Integer> entry) {
        this(entry.getKey(), entry.getValue());
    }

    public BigDecimal getRowPrice() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
