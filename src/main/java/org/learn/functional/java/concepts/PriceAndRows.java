package org.learn.functional.java.concepts;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class PriceAndRows {
    private BigDecimal price;
    private List<CartRow> rows = new ArrayList<>();

    public PriceAndRows(BigDecimal iPrice, List<CartRow> iRows) {
        this.price = iPrice;
        rows.addAll(iRows);
    }

    public PriceAndRows() {
        this(BigDecimal.ZERO, new ArrayList<>());
    }
}
