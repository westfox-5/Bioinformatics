package it.westfox5.bioinformatics.utils;

public class OutputUtils {

    private final static String SPACER = " ";
    private final static String SEPARATOR = "-";

    private static String getPaddedValue(String value, int i) {
        if (i % 100 < 9) return value;
        else return value.repeat(2);
    }

    private static String getFormattedString(String value, int i) {
        return value + getPaddedValue( SEPARATOR.equals(value) ? SEPARATOR : SPACER, i);
    }

    private static String getFormattedString(Integer index, int i) {
        return index + "" + SPACER ;
    }

    public static void printSeparator(String text) {
        for (int i = 0; i < text.length(); ++i) {
            System.out.print(getFormattedString(SEPARATOR+"", i));
        }
        System.out.println();
    }

    public static void printIndexes(String text, boolean zeroBased) {
        for (int i = (zeroBased ? -1 : 0); i < text.length(); ++i) {
            System.out.print(getFormattedString((i+1), i));
        }
        System.out.println();

    }

    public static void print(String text, boolean withIndexes) {
        if (withIndexes) {
            printIndexes(text, false);
        }
        for (int i = 0; i < text.length(); ++i) {
            System.out.print(getFormattedString(text.charAt(i) + "", i));
        }
        System.out.println();
    }



}
