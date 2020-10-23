package com.github.ivankohut.gitbranchchecks;

public class SimpleBranch implements Branch {

    private final String name;

    public SimpleBranch(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }
}
