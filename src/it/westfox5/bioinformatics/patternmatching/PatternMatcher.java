package it.westfox5.bioinformatics.patternmatching;

import java.util.List;

public abstract class PatternMatcher {

    public abstract List<Integer> match(String text, String pattern);


}