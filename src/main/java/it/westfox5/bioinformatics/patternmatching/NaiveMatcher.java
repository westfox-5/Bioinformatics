package it.westfox5.bioinformatics.patternmatching;


import java.util.ArrayList;
import java.util.List;

/**
 * Basic implementation of a Pattern Searching algorithm.
 * <br /><br />
 *
 */
public class NaiveMatcher extends PatternMatcher {
    protected NaiveMatcher() { }

    @Override
    protected List<Integer> doMatchImpl(String text, String pattern) {
        List<Integer> list = new ArrayList<>();

        int n = text.length();
        int m = pattern.length();
        for (int s=0; s<=n-m; s++) {
            String substring = text.substring(s, s+m);
            if (pattern.equals(substring)) {
                list.add(s);
            }
        }
        return list;
    }
}
