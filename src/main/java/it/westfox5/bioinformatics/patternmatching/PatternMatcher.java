package it.westfox5.bioinformatics.patternmatching;

import it.westfox5.bioinformatics.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public abstract class PatternMatcher {
    public static PatternMatcher naiveMatcher() { return new NaiveMatcher(); }
    public static PatternMatcher finiteAutomatonMatcher() { return new FiniteAutomatonMatcher(); }

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
        List<Integer> list = new ArrayList<>();

        if (StringUtils.isEmpty(text) || StringUtils.isEmpty(pattern))
            return list;

        var occurrences = doMatchImpl(text, pattern);
        if (occurrences != null) {
            list.addAll(occurrences);
        }

        return list;
    }
}