package org.learn.functional.java.concepts;

import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class PriceAndRowsCollector implements Collector<Map.Entry<Product,Integer>,PriceAndRows,PriceAndRows> {
    @Override
    public Supplier<PriceAndRows> supplier() {
        return PriceAndRows::new;
    }

    @Override
    public BiConsumer<PriceAndRows, Map.Entry<Product, Integer>> accumulator() {
       return (priceAndRows, entry) -> {
            CartRow cartRow = new CartRow(entry);
           priceAndRows.setPrice(priceAndRows.getPrice().add(cartRow.getRowPrice()));
            priceAndRows.getRows().add(cartRow);
        };
    }

    @Override
    public BinaryOperator<PriceAndRows> combiner() {
        return (c1, c2) -> {
            c1.setPrice(c1.getPrice().add(c2.getPrice()));
            var rows = c1.getRows();
            rows.addAll(c2.getRows());
            return new PriceAndRows(c1.getPrice(),rows);
        };
    }

    @Override
    public Function<PriceAndRows, PriceAndRows> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.IDENTITY_FINISH);
    }
}
