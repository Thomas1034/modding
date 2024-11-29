package com.thomas.verdant.registry;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

// Make this work so that, to register, you just give it the lambda it needs.
public class RegistryHelper<T> {

    private final Set<Entry> entries;

    public RegistryHelper() {
        this.entries = new HashSet<>();
    }

    public void add(String name, Supplier<T> object) {
        entries.add(this.new Entry(name, object));
    }

    public void register(BiConsumer<String, Supplier<T>> registrar) {
        this.entries.forEach((entry) -> registrar.accept(entry.name, entry.supplier));
    }

    protected class Entry {
        String name;
        Supplier<T> supplier;

        public Entry(String name, Supplier<T> supplier) {
            this.name = name;
            this.supplier = supplier;
        }
    }
}
