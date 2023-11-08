package com.github.ivankohut.gitbranchchecks;

import com.github.ivankohut.gitbranchchecks.common.Branch;
import org.gradle.api.GradleException;

public class NonHeadBranch implements Branch {

    private static final String HEAD_BRANCH_NAME = "HEAD";

    private final Branch branch;
    private final Iterable<String> defaultName;

    public NonHeadBranch(Branch branch, Iterable<String> defaultName) {
        this.branch = branch;
        this.defaultName = defaultName;
    }

    @Override
    public String name() {
        var branchName = branch.name();
        if (HEAD_BRANCH_NAME.equals(branchName)) {
            var iterator = defaultName.iterator();
            if (iterator.hasNext()) {
                return iterator.next();
            }
            throw new GradleException("Could not retrieve non-HEAD branch name.");
        }
        return branchName;
    }
}
