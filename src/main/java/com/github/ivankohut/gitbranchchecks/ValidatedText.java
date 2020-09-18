package com.github.ivankohut.gitbranchchecks;

import org.gradle.api.GradleException;

import java.util.regex.Pattern;

public class ValidatedText {

    private final Pattern pattern;
    private final String text;
    private final String name;

    public ValidatedText(String name, Pattern pattern, String text) {
        this.pattern = pattern;
        this.text = text;
        this.name = name;
    }

    @Override
    public String toString() {
        if (!pattern.matcher(text).matches()) {
            throw new GradleException("Illegal " + name + ": " + text + "\n  Expected pattern is: " + pattern.pattern());
        }
        return text;
    }
}
