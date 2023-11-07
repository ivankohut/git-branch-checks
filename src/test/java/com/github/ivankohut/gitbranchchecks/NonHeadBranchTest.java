package com.github.ivankohut.gitbranchchecks;

import org.gradle.api.GradleException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NonHeadBranchTest {

    @ParameterizedTest
    @CsvSource({
            "HEAD, default, default",
            "nonHEAD, default, nonHEAD"
    })
    void givenBranchInNonHeadOtherwiseDefault(String branch, String defaultBranch, String expected) {
        var sut = new NonHeadBranch(new SimpleBranch(branch), Collections.singleton(defaultBranch));
        // exercise
        var result = sut.name();
        // verify
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void exceptionIfHeadAndNoDefaultBranchGiven() {
        var sut = new NonHeadBranch(new SimpleBranch("HEAD"), Collections.emptyList());
        // exercise
        // verify
        assertThatThrownBy(sut::name)
                .isInstanceOf(GradleException.class)
                .hasMessage("Could not retrieve non-HEAD branch name.");
    }
}
