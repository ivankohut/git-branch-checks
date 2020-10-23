package com.github.ivankohut.gitbranchchecks;

import com.github.ivankohut.gitbranchchecks.git.GitCurrentBranch;
import com.github.ivankohut.gitbranchchecks.git.WithGit;
import org.gradle.api.DefaultTask;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.util.regex.Pattern;

public class CheckBranchName extends DefaultTask {

    private final Property<Pattern> pattern;
    private final Property<String> commitId;

    public CheckBranchName() {
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
        new WithGit(
                getProject().getRootDir(),
                git -> new ValidatedText(
                        "branch name",
                        pattern.get(),
                        new TextOfBranch(
                                new NonHeadBranch(
                                        new GitCurrentBranch(git),
                                        new EnvironmentVariable("TRAVIS_BRANCH")
                                )
                        )
                ).asString()
        ).run();
    }
}

