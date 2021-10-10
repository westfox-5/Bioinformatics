package it.westfox5.bioinformatics.patternmatching;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import it.westfox5.bioinformatics.utils.StringUtils;

public class NaiveMatcher extends PatternMatcher {
    @Override
    public List<Integer> match(String text, String pattern) {
        List<Integer> list = new ArrayList<>();

        if (StringUtils.isEmpty(text) || StringUtils.isEmpty(pattern))
            return list;

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
