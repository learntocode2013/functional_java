package org.learn.functional.java.collectors;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.function.BinaryOperator;

public class MultiValuedMapCombiner implements BinaryOperator<Multimap<Character,String>> {

    @Override
    public Multimap<Character, String> apply(
            Multimap<Character, String> m1,
            Multimap<Character, String> m2) {
        Multimap<Character, String> result = ArrayListMultimap.create();
        result.putAll(m1);
        result.putAll(m2);
        return result;
    }
}
