package com.github.ivankohut.gitbranchchecks;

import org.gradle.api.Project;
import org.gradle.api.provider.Property;

import java.util.regex.Pattern;

public class GitBranchChecksPluginExtension {

    private final Property<Pattern> branchPattern;
    private final Property<Pattern> messagePattern;

    public GitBranchChecksPluginExtension(Project project) {
        branchPattern = project.getObjects().property(Pattern.class).value(Pattern.compile("(master)|(feature/#\\d+/[a-z0-9\\-]+)"));
        messagePattern = project.getObjects().property(Pattern.class).value(Pattern.compile("#\\d+ \\S.*"));
    }

    public Property<Pattern> getBranchPattern() {
        return branchPattern;
    }

    public Property<Pattern> getMessagePattern() {
        return messagePattern;
    }
}
