package com.thomas.verdant.registry;

import com.thomas.verdant.Constants;

import java.util.Collection;
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

    public void add(String name, Supplier<? extends T> object) {
        entries.add(this.new Entry(name, object));
    }

    public void register(BiConsumer<String, Supplier<? extends T>> registrar) {
        Constants.LOG.warn("Registering {} entries.", this.entries.size());
        this.entries.forEach((entry) -> registrar.accept(entry.name, entry.supplier));
    }

    public Collection<Entry> getEntries() {
        return this.entries;
    }

    public class Entry {
        protected final String name;
        protected final Supplier<? extends T> supplier;

        public Entry(String name, Supplier<? extends T> supplier) {
            this.name = name;
            this.supplier = supplier;
        }

        public Supplier<? extends T> get() {
            return supplier;
        }

        public String name() {
            return this.name;
        }
    }
}
