package com.github.ivankohut.gitbranchchecks;

import org.cactoos.Text;

public class TextOfBranch implements Text {

    private final Branch branch;

    public TextOfBranch(Branch branch) {
        this.branch = branch;
    }

    @Override
    public String asString() {
        return branch.name();
    }
}
