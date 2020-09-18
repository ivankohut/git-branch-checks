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
import java.util.regex.Pattern;

public class CheckCommitMessages extends DefaultTask {

    private final Property<Pattern> pattern;
    private final Property<String> commitId;

    public CheckCommitMessages() {
        setGroup("verification");
        this.pattern = getProject().getObjects().property(Pattern.class);
        this.commitId = getProject().getObjects().property(String.class);
    }

    @Input
    public Property<Pattern> getPattern() {
        return pattern;
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
                    commit -> new ValidatedText("commit message", pattern.get(), commit.getShortMessage()).toString()
            );
        } catch (IOException | GitAPIException e) {
            throw new GradleException(e.getMessage(), e);
        }
    }
}
