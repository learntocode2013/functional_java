package org.learn.functional.java.collectors;

import com.google.common.collect.Multimap;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class GroupByFirstCharacterCollector implements Collector<String,Multimap<Character,String>,Multimap<Character,String>> {
    @Override
    public Supplier<Multimap<Character, String>> supplier() {
        return new MultiValuedMapSupplier();
    }

    @Override
    public BiConsumer<Multimap<Character, String>, String> accumulator() {
        return new MultiValuedMapAccumulator();
    }

    @Override
    public BinaryOperator<Multimap<Character, String>> combiner() {
        return new MultiValuedMapCombiner();
    }

    @Override
    public Function<Multimap<Character, String>, Multimap<Character, String>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.IDENTITY_FINISH);
    }
}
