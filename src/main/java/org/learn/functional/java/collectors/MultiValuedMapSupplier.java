package org.learn.functional.java.collectors;

import com.google.common.collect.Multimap;
import com.google.common.collect.ArrayListMultimap;

import java.util.function.Supplier;

public class MultiValuedMapSupplier implements Supplier<Multimap<Character,String>> {
    @Override
    public Multimap<Character, String> get() {
        return ArrayListMultimap.create();
    }
}
