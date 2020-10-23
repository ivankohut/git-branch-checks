package com.github.ivankohut.gitbranchchecks;

import java.util.Collections;
import java.util.Iterator;

public class EnvironmentVariable implements Iterable<String> {

    private final String name;

    public EnvironmentVariable(String name) {
        this.name = name;
    }

    @Override
    public Iterator<String> iterator() {
        String value = System.getenv(name);
        if (value == null) {
            return Collections.emptyIterator();
        }
        return Collections.singleton(value).iterator();
    }
}
