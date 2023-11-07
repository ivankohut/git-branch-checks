package com.github.ivankohut.gitbranchchecks.git;

import com.github.ivankohut.gitbranchchecks.Branch;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.gradle.api.GradleException;

import java.io.IOException;

public class GitCurrentBranch implements Branch {

    private static final String HEAD_BRANCH_NAME = "HEAD";

    private final Git git;

    public GitCurrentBranch(Git git) {
        this.git = git;
    }
    @Override
    public String name() {
        try {
            var branchName = git.getRepository().getBranch();
            var commitId = git.log().setMaxCount(1).call().iterator().next().getId().getName();
            if (commitId.equals(branchName)) {
                return HEAD_BRANCH_NAME;
            }
            // branchName can be HEAD as well
            return branchName;
        } catch (IOException | GitAPIException e) {
            throw new GradleException(e.getMessage(), e);
        }
    }
}
