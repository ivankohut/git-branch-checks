package com.github.ivankohut.gitbranchchecks;

import org.cactoos.Text;
import org.cactoos.text.TextOf;
import org.cactoos.text.UncheckedText;
import org.gradle.api.GradleException;

import java.util.regex.Pattern;

public class ValidatedText implements Text {

    private final Pattern pattern;
    private final UncheckedText text;
    private final String name;

    public ValidatedText(String name, Pattern pattern, Text text) {
        this.pattern = pattern;
        this.text = new UncheckedText(text);
        this.name = name;
    }

    public ValidatedText(String test, Pattern compile, String text) {
        this(test, compile, new TextOf(text));
    }

    @Override
    public String asString() {
        var textAsString = text.asString();
        if (!pattern.matcher(textAsString).matches()) {
            throw new GradleException("Illegal " + name + ": " + textAsString + "\n  Expected pattern is: " + pattern.pattern());
        }
        return textAsString;
    }
}
