package com.github.ivankohut.gitbranchchecks.git;

import com.github.ivankohut.gitbranchchecks.Branch;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.gradle.api.GradleException;

import java.io.IOException;
import java.util.Iterator;

public class GitLog implements Iterable<RevCommit> {

    private final Git git;
    private final Branch from;
    private final Branch to;

    public GitLog(Git git, Branch from, Branch to) {
        this.git = git;
        this.from = from;
        this.to = to;
    }

    @Override
    public Iterator<RevCommit> iterator() {
        Repository repository = git.getRepository();
        try {
            return git.log().addRange(repository.resolve(from.name()), repository.resolve(to.name())).call().iterator();
        } catch (IOException | GitAPIException e) {
            throw new GradleException(e.getMessage(), e);
        }
    }
}
