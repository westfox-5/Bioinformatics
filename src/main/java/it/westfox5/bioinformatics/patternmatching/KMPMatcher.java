package it.westfox5.bioinformatics.patternmatching;

import it.westfox5.bioinformatics.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the Knuth-Morris-Pratt pattern matching algorithm.
 */
public class KMPMatcher extends PatternMatcher {

    @Override
    protected List<Integer> doMatchImpl(String text, String pattern) {
        final List<Integer> list = new ArrayList<>();

        final int n = text.length();
        final int m = pattern.length();

        Integer[] pi = computePrefixFunction(pattern);
        int q = 0; // # of chars matched
        for (int i=0; i<n; ++i) {
            char textCharI = text.charAt(i);
            while (q>0 && pattern.charAt(q) != textCharI)
                q = pi[q-1];

            if (pattern.charAt(q) == textCharI)
                ++q;

            if (q == m) {
                list.add(i-m+1);
                q = pi[q-1];
            }
        }

        return list;
    }
}
