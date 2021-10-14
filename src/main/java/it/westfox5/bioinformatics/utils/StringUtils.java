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

    public static String prefix(String text, Integer endIdx) {
        if (isEmpty(text)) return "";

        return text.substring(0, Math.min(endIdx+1, text.length()));
    }

    public static String suffix(String text, Integer beginIdx) {
        if (isEmpty(text)) return "";

        return text.substring(Math.max(beginIdx-1, 0));
    }
}
