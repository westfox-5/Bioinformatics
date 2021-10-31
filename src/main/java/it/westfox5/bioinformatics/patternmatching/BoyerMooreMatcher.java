package it.westfox5.bioinformatics.patternmatching;

import it.westfox5.bioinformatics.utils.StringUtils;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Implementation of the Boyer-Moore pattern matching algorithm.
 */
public class BoyerMooreMatcher extends PatternMatcher {

    public static Function<Character, Integer> computeBadCharacterFunction(String text, Set<Character> alphabet) {
        if (StringUtils.isEmpty(text) || alphabet == null || alphabet.isEmpty())
            return (a) -> -1;

        final int m = text.length();
        final Map<Character, Integer> map = new HashMap<>();
        for (int j=0; j<m; ++j) {
            map.put(text.charAt(j), j);
        }

        return (a) -> map.getOrDefault(a, -1);
    }

    public static Function<Integer, Integer> computeGoodSuffixFunction(String text) {
        if (StringUtils.isEmpty(text))
            return (i) -> 0;

        Integer[] pi = computePrefixFunction(text);
        String textReversed = new StringBuilder(text).reverse().toString();
        Integer[] piReversed = computePrefixFunction(textReversed);

        final int m = text.length();
        final Map<Integer, Integer> map = new HashMap<>();

        for (int j=0; j<m+1; ++j)
            map.put(j, m - pi[m-1]);

        for (int h=1; h<m+1; ++h) {
            int j = m-piReversed[h-1];
            int k = h-piReversed[h-1];
            if (map.get(j) > k) {
                map.put(j, k);
            }
        }

        return (a) -> map.getOrDefault(a, -1);
    }

    @Override
    protected List<Integer> doMatchImpl(String text, String pattern) {
        final List<Integer> list = new ArrayList<>();

        // Pre-processing of the pattern
        final Set<Character> alphabet = getAlphabet(text);

        Function<Character, Integer> bcr = computeBadCharacterFunction(pattern, alphabet);
        Function<Integer, Integer> gsr = computeGoodSuffixFunction(pattern);

        final int n = text.length();
        final int m = pattern.length();

        int s = 0;
        int j;
        while (s <= n-m) {
            j = m-1;
            while (j>0 && pattern.charAt(j) == text.charAt(s+j)) {
                j--;
            }

            if (j==0) {
                list.add(s);
                s += gsr.apply(0)-1;
            } else {
                s += Math.max(gsr.apply(j+1)-1, j-bcr.apply(text.charAt(s+j)));
            }
        }

        return list;
    }
}
