package it.westfox5.bioinformatics.test;

import it.westfox5.bioinformatics.patternmatching.BoyerMooreMatcher;
import it.westfox5.bioinformatics.patternmatching.PatternMatcher;
import it.westfox5.bioinformatics.utils.OutputUtils;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PatternMatcherTest {

    private <T> Supplier<Stream<T>> streamSupplier(T[] array) { return () -> Arrays.stream(array); }
    private <T> Supplier<Stream<T>> streamSupplier(List<T> list) { return list::stream; }

    private String text, pattern;

    private Supplier<Stream<Integer>> occurrences;
    private Integer[] expectedOccurrences;

    @BeforeAll
    void setup() {
        text = "ACGTACGAGTACAGAGTACAGAT";
        pattern = "AGTACAGA";

        // SET TO NULL TO AVOID ASSERTIONS (always test passed)
        // n.b. occurrences are 0-based.
        //      the print auto increments the values by 1, making output 1-based.
        expectedOccurrences = new Integer[] {7,14};
    }

    @Test
    @Order(1)
    @DisplayName("Naive matcher")
    void naive() {
        PatternMatcher matcher = PatternMatcher.naiveMatcher();

        occurrences = streamSupplier(matcher.match(text, pattern));
        if (expectedOccurrences != null)
            Assertions.assertArrayEquals(expectedOccurrences, occurrences.get().toArray());
    }

    @Test
    @Order(2)
    @DisplayName("Finite Automaton matcher")
    void finiteAutomaton() {
        PatternMatcher matcher = PatternMatcher.finiteAutomatonMatcher();

        occurrences = streamSupplier(matcher.match(text, pattern));
        if (expectedOccurrences != null)
            Assertions.assertArrayEquals(expectedOccurrences, occurrences.get().toArray());
    }

    @Test
    @Order(3)
    @DisplayName("Knuth-Morris-Pratt matcher")
    void KMP() {
        PatternMatcher matcher = PatternMatcher.kmpMatcher();

        occurrences = streamSupplier(matcher.match(text, pattern));
        if (expectedOccurrences != null)
            Assertions.assertArrayEquals(expectedOccurrences, occurrences.get().toArray());
    }

    @Test
    @Order(4)
    @DisplayName("Boyer-Moore matcher")
    void BM() {
        PatternMatcher matcher = PatternMatcher.bmMatcher();

        occurrences = streamSupplier(matcher.match(text, pattern));
        if (expectedOccurrences != null)
            Assertions.assertArrayEquals(expectedOccurrences, occurrences.get().toArray());
    }


    /****** PRINT HELPERS  ******/

    @BeforeAll
    @DisplayName("print Text and Pattern")
    void printTextPattern() {
        OutputUtils.printSeparator(text);
        System.out.println(" (TEXT) ");
        OutputUtils.print(text, true);
        OutputUtils.printSeparator(text);

        System.out.println(" (PATTERN) ");
        OutputUtils.print(pattern, true);
        OutputUtils.printSeparator(text);
        System.out.println();
    }

    @AfterEach
    @DisplayName("print Occurrences")
    void printOccurrences(TestInfo testInfo) {
        if (occurrences != null) {
            System.out.print(" (Occurrences found by "+testInfo.getDisplayName()+") ");
            System.out.println(Arrays.toString(occurrences.get().map(a->a+1).toArray()));
        }
    }

    @Disabled
    @Test
    @Order(90)
    @DisplayName("print Prefix function")
    void printPrefixFunction() {
        var prefixes = streamSupplier(PatternMatcher.computePrefixFunction(pattern));

        System.out.println(" (PREFIX FUNCTION) ");
        OutputUtils.print(prefixes.get()
                        .map(String::valueOf)
                        .collect(Collectors.joining("")),
                false);
    }

    @Disabled
    @Test
    @Order(100)
    @DisplayName("print Bad Character Rule")
    void printBadCharacterRule() {
        System.out.println(" (BAD CHARACTER RULE) ");
        List<Character> alphabet = List.of('A', 'C', 'G', 'T');
        var bcr = BoyerMooreMatcher.computeBadCharacterFunction(pattern, new HashSet<>(alphabet));

        for (Character a : alphabet) {
            System.out.print(a + " ");
        }
        System.out.println();
        for (Character a : alphabet) {
            System.out.print(bcr.apply(a) + 1 + " ");
        }
        System.out.println();
    }


    @Disabled
    @Test
    @Order(101)
    @DisplayName("print Good Suffix Rule")
    void printGoodSuffixRule() {
        System.out.println(" (GOOD SUFFIX RULE) ");
        var gsr = BoyerMooreMatcher.computeGoodSuffixFunction(pattern);

        for (int i = -1; i < pattern.length(); ++i) {
            Integer res = gsr.apply(i+1);
            if (res < 0)
                if (i + 1 %100 < 10) {
                    System.out.print(" " + (i + 1) + "  ");
                } else {
                    System.out.print(" " + (i + 1) + " ");
                }
            else
                System.out.print(i+1 + " ");
        }
        System.out.println();
        for (int i=0; i<pattern.length()+1;++i) {
            Integer res = gsr.apply(i);
            if (res < 0)
                System.out.print(res + "  ");
            else
                System.out.print(res + " ");
        }
        System.out.println();
    }
}
