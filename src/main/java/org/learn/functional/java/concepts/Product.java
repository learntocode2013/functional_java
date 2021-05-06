package org.learn.functional.java.concepts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode(exclude = {"label","price"})
public class Product {
    private final Long id;
    private final String label;
    private final BigDecimal price;
}
