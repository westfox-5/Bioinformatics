package it.westfox5.bioinformatics.patternmatching;


import java.util.ArrayList;
import java.util.List;

/**
 * Naive implementation of a Pattern Searching algorithm.
 *
 */
public class NaiveMatcher extends PatternMatcher {
    protected NaiveMatcher() { }

    @Override
    protected List<Integer> doMatchImpl(String text, String pattern) {
        final List<Integer> list = new ArrayList<>();

        final int n = text.length();
        final int m = pattern.length();
        for (int s=0; s<=n-m; ++s) {
            String substring = text.substring(s, s+m);
            if (pattern.equals(substring)) {
                list.add(s);
            }
        }
        return list;
    }
}
