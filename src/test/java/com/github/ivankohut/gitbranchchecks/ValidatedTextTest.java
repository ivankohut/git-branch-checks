package com.github.ivankohut.gitbranchchecks;

import org.gradle.api.GradleException;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ValidatedTextTest {

    @Test
    void givenTextIfItMatchesGivenPattern() {
        var text = "aaa";
        var sut = new ValidatedText("test", Pattern.compile("a*"), text);
        // exercise
        var result = sut.toString();
        // verify
        assertThat(result).isEqualTo(text);
    }

    @Test
    void gradleExceptionIfTheTextDoesNotMatchGivenPattern() {
        var sut = new ValidatedText("test", Pattern.compile("a*"), "aab");
        // exercise
        // verify
        assertThatThrownBy(sut::toString)
                .isInstanceOf(GradleException.class)
                .hasMessage("Illegal test: aab\n  Expected pattern is: a*");
    }
}
