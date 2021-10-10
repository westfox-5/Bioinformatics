package it.westfox5.bioinformatics.test;

import it.westfox5.bioinformatics.patternmatching.PatternMatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;

public class PatternMatcherTest {

    @Test
    void naive() {
        PatternMatcher matcher = PatternMatcher.naiveMatcher();

        String text = "ACGTACGTACGT";
        String pattern = "AC";

        var occurrences = matcher.match(text, pattern).toArray(new Integer[0]);
        Assertions.assertArrayEquals(new Integer[]{0,4,8}, occurrences);
    }

    @Test
    void finiteAutomaton() {
        PatternMatcher matcher = PatternMatcher.finiteAutomatonMatcher();

        String text = "ATGATCATCATCATCCG";
        String pattern = "ATCAT";

        var occurrences = matcher.match(text, pattern).toArray(new Integer[0]);
        Assertions.assertArrayEquals(new Integer[]{3,6,9}, occurrences);
    }
}
