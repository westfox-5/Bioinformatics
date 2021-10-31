package it.westfox5.bioinformatics.patternmatching;

import it.westfox5.bioinformatics.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class PatternMatcher {
    public static PatternMatcher naiveMatcher() { return new NaiveMatcher(); }
    public static PatternMatcher finiteAutomatonMatcher() { return new FiniteAutomatonMatcher(); }
    public static PatternMatcher kmpMatcher() { return new KMPMatcher(); }
    public static PatternMatcher bmMatcher() { return new BoyerMooreMatcher(); }

    /**
     * Specific business logic of each subclass.
     *
     * @param text non-null value
     * @param pattern non-null value
     * @return list of starting indices of occurrences of pattern in text
     */
    protected abstract List<Integer> doMatchImpl(String text, String pattern);

    /**
     *
     * @param text String to scan
     * @param pattern String to look for
     * @return list of starting indices of occurrences of pattern in text
     */
    public List<Integer> match(String text, String pattern) {
        if (StringUtils.isEmpty(text) || StringUtils.isEmpty(pattern))
            return new ArrayList<>();

        final List<Integer> list = new ArrayList<>();

        var occurrences = doMatchImpl(text, pattern);
        if (occurrences != null) {
            list.addAll(occurrences);
        }

        return list;
    }

    /**
     * Given a text, the value at pi[q] is the length of
     * the longest prefix of P which is also a suffix of Pq.
     * <br /><br />
     * Invariant: pi[k] < k with k>0 and k<q
     * <br />
     * Complexity: O(m) since the for-loop body has an amortized cost which is constant
     *
     * @param text text to compute prefix function on
     * @return The array representing the prefix function
     */
    public static Integer[] computePrefixFunction(String text) {
        if (StringUtils.isEmpty(text))
            return new Integer[]{};

        final int m = text.length();
        final Integer[] pi = new Integer[m];

        pi[0] = 0;
        int k = 0; // current prefix length

        for (int q = 1; q<m; ++q) {
            char textCharQ = text.charAt(q);
            while (k>0 && text.charAt(k) != textCharQ) // mismatch
                k = pi[k-1];

            if (text.charAt(k) == textCharQ) // valid prefix
                ++k;

            pi[q] = k;
        }

        return pi;
    }

    protected Set<Character> getAlphabet(String text) {
        return text.chars()
                .distinct()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toSet());
    }
}