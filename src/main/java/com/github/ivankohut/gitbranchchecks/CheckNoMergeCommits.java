package com.github.ivankohut.gitbranchchecks;

import com.github.ivankohut.gitbranchchecks.git.GitCurrentBranch;
import com.github.ivankohut.gitbranchchecks.git.GitLog;
import com.github.ivankohut.gitbranchchecks.git.WithGit;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.TaskAction;

import java.io.File;

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
        new WithGit(
                getProject().getRootDir(),
                git -> new GitLog(git, new SimpleBranch("master"), new GitCurrentBranch(git)).forEach(
                        commit -> {
                            if (commit.getParentCount() > 1) {
                                throw new GradleException("Merge commits are not allowed: " + commit.getName() + " - " + commit.getShortMessage());
                            }
                        }
                )
        ).run();
    }
}
