package it.westfox5.bioinformatics.patternmatching;

import it.westfox5.bioinformatics.utils.StringUtils;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Implementation of a pattern matching algorithm that uses the Finite Automaton model.
 * <br /> <br />
 * For now the F.A. is implemented through the transition function, which takes the current state and next token as input
 * and produces the next state reached by the automaton.
 * <br />
 * States are implemented lonely by an Integer value, which represents the current prefix of the pattern recognised.
 *
 * TODO: implement a more complex and more useless structure for the automaton!
 *
 */
public class FiniteAutomatonMatcher extends PatternMatcher {
    protected FiniteAutomatonMatcher() { }

    private record TransitionKey(Integer state, Character nextToken) {
        public static TransitionKey of(Integer state, Character nextToken) {
            return new TransitionKey(state, nextToken);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TransitionKey that = (TransitionKey) o;
            return Objects.equals(state, that.state) && Objects.equals(nextToken, that.nextToken);
        }

        @Override
        public int hashCode() {
            return Objects.hash(state, nextToken);
        }
    }

    /**
     * Produces the mapping given by the transition function of the automaton.<br/>
     * <br/>
     *
     * Complexity: O( m^3 * |alphabet| )
     *
     * @param text text to compute the automaton on (i.e. the pattern to search)
     * @param alphabet the set of characters composing the full-text string (not the local text variable!)
     * @return a BiFunction that takes integer (current state) and character (next token) and returns the next state of the automaton
     */
    protected static BiFunction<Integer, Character, Integer> computeTransitionFunction(String text, Set<Character> alphabet) {
        final Map<TransitionKey, Integer> transitionsMap = new HashMap<>();

        final int m = text.length();
        int k;
        for (int q=0; q<=m; ++q) {
            for (Character a: alphabet) {
                // start with an optimistic guess…
                k = Math.min(m+1, q+2);
                // …and keep decreasing the index until text[0..k]  ]  text[0..q]a
                do {
                    k--;
                } while (!StringUtils.isSuffix( text.substring(0, q).concat(String.valueOf(a)), text.substring(0, k) ));

                transitionsMap.put(TransitionKey.of(q, a), k);
            }
        }

        // BiFunction as lambda
        return (q, a) -> transitionsMap.get( TransitionKey.of(q,a) );
    }

    @Override
    protected List<Integer> doMatchImpl(String text, String pattern) {
        final List<Integer> list = new ArrayList<>();

        // Pre-processing of the pattern
        final Set<Character> alphabet = getAlphabet(text);
        BiFunction<Integer, Character, Integer> transitionFn = computeTransitionFunction(pattern, alphabet);

        final int n = text.length();
        final int m = pattern.length();

        int q = 0; // current state of the automaton
        for (int s=0; s<n; ++s) {
            q = transitionFn.apply(q, text.charAt(s));

            if (q == m) { // last state of automaton reached
                // a match occurred m positions before the current shift s
                list.add( s - m + 1 );
            }
        }
        return list;
    }
}
