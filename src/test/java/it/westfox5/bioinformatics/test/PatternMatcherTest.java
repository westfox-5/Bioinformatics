package it.westfox5.bioinformatics.test;

import it.westfox5.bioinformatics.patternmatching.PatternMatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class PatternMatcherTest {

    private <T> Supplier<Stream<T>> streamSupplier(T[] array) { return () -> Arrays.stream(array); }
    private <T> Supplier<Stream<T>> streamSupplier(List<T> list) { return list::stream; }

    @Test
    void naive() {
        PatternMatcher matcher = PatternMatcher.naiveMatcher();

        String text = "ACGTACGTACGT";
        String pattern = "AC";

        var occurrences = streamSupplier(matcher.match(text, pattern));
        Assertions.assertArrayEquals(new Integer[]{0,4,8}, occurrences.get().toArray());
    }

    @Test
    void finiteAutomaton() {
        PatternMatcher matcher = PatternMatcher.finiteAutomatonMatcher();

        String text = "ATGATCATCATCATCCG";
        String pattern = "ATCAT";

        var occurrences = streamSupplier(matcher.match(text, pattern));
        Assertions.assertArrayEquals(new Integer[]{3,6,9}, occurrences.get().toArray());
    }

    @Test
    void prefixFunction() {
        String text = "CGCGACGC";

        var prefixes = streamSupplier(PatternMatcher.computePrefixFunction(text));
/*
        OutputUtils.print(text, true);
        OutputUtils.print(prefixes.get()
                        .map(String::valueOf)
                        .collect(Collectors.joining("")),
                false);
*/
        Assertions.assertArrayEquals(new Integer[]{0,0,1,2,0,1,2,3}, prefixes.get().toArray());
    }

    @Test
    void KMP() {
        PatternMatcher matcher = PatternMatcher.kmpMatcher();

        String text = "ATGAGACTAGTCAGCATCATCAGTCAGTCAGATTGTCCG";
        String pattern = "AGTCAG";

        var occurrences = streamSupplier(matcher.match(text, pattern));
        Assertions.assertArrayEquals(new Integer[]{8,21,25}, occurrences.get().toArray());
    }
}
