package it.westfox5.bioinformatics.utils;

public class StringUtils {
    public static boolean notEmpty(String s) { return s!=null && !s.isEmpty(); }
    public static boolean isEmpty(String s) { return !notEmpty(s); }

    public static boolean isPrefix(String s, String prefix) {
        if (isEmpty(s)) return false;
        // empty string is trivially a prefix
        if (isEmpty(prefix)) return true;

        return s.startsWith(prefix);
    }

    public static boolean isSuffix(String s, String suffix) {
        if (isEmpty(s)) return false;
        // empty string is trivially a suffix
        if (isEmpty(suffix)) return true;

        return s.endsWith(suffix);
    }
}
