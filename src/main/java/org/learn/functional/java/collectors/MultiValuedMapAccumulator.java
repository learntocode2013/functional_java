package org.learn.functional.java.collectors;

import com.google.common.collect.Multimap;

import java.util.function.BiConsumer;

public class MultiValuedMapAccumulator implements BiConsumer<Multimap<Character,String>,String> {
    @Override
    public void accept(Multimap<Character, String> multiValuedMap, String s) {
        multiValuedMap.put(s.charAt(0),s);
    }
}
