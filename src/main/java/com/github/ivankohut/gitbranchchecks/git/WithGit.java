package com.github.ivankohut.gitbranchchecks.git;

import org.eclipse.jgit.api.Git;
import org.gradle.api.GradleException;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public class WithGit implements Runnable {

    private final Consumer<Git> operation;
    private final File directory;

    public WithGit(File directory, Consumer<Git> operation) {
        this.operation = operation;
        this.directory = directory;
    }

    @Override
    public void run() {
        try (Git git = Git.open(directory)) {
            operation.accept(git);
        } catch (IOException e) {
            throw new GradleException(e.getMessage(), e);
        }
    }
}
