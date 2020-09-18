package com.github.ivankohut.gitbranchchecks;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;

public class CheckNoMergeCommits extends DefaultTask {

    private final Property<String> commitId;

    public CheckNoMergeCommits() {
        setGroup("verification");
        this.commitId = getProject().getObjects().property(String.class);
    }

    @Input
    public Property<String> getCommitId() {
        return commitId;
    }

    @OutputFile
    public File getOutputFile() {
        return new File(getProject().getBuildDir(), "nothing");
    }

    @TaskAction
    public void check() {
        try (Git git = Git.open(getProject().getRootDir())) {
            Repository repository = git.getRepository();
            String branchName = repository.getBranch();
            git.log().addRange(repository.resolve("master"), repository.resolve(branchName)).call().forEach(
                    commit -> {
                        if (commit.getParentCount() > 1) {
                            throw new GradleException("Merge commits are not allowed: " + commit.getName() + " - " + commit.getShortMessage());
                        }
                    }
            );
        } catch (IOException | GitAPIException e) {
            throw new GradleException(e.getMessage(), e);
        }
    }
}
