package com.github.ivankohut.gitbranchchecks;

import org.eclipse.jgit.api.Git;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.io.File;
import java.io.IOException;

public class GitBranchChecksPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        var extension = project.getExtensions().create("gitBranchChecks", GitBranchChecksPluginExtension.class, project);
        var tasks = project.getTasks();
        var headCommitId = headCommitId(project.getRootDir());
        tasks.register("checkBranchName", CheckBranchName.class, task -> {
            task.getPattern().set(extension.getBranchPattern());
            task.getCommitId().set(headCommitId);
        });
        tasks.register("checkCommitMessages", CheckCommitMessages.class, task -> {
            task.getPattern().set(extension.getMessagePattern());
            task.getCommitId().set(headCommitId);
        });
        tasks.register("checkNoMergeCommits", CheckNoMergeCommits.class, task -> task.getCommitId().set(headCommitId));
    }

    private static String headCommitId(File rootDir) {
        try (var git = Git.open(rootDir)) {
            return git.getRepository().findRef("HEAD").getObjectId().getName();
        } catch (IOException e) {
            throw new GradleException(e.getMessage(), e);
        }
    }
}
