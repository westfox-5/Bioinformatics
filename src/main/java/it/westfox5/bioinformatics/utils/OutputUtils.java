package it.westfox5.bioinformatics.utils;

public class OutputUtils {

    private static String getSpacer(Character spacer, int i) {
        if (i % 100 < 9) return String.valueOf(spacer);
        else return String.valueOf(spacer).repeat(2);
    }

    private static String getFormattedString(String value, Character spacer, int i) {
        return value + getSpacer(spacer, i);
    }

    private static String getFormattedString(Integer index, Character spacer, int i) {
        return index + "" + spacer ;
    }

    public static void print(String text, boolean withIndexes) {
        Character spacer = ' ';
        if (withIndexes) {
            for (int i = 0; i < text.length(); ++i) {
                System.out.print(getFormattedString((i+1), spacer, i));
            }
            System.out.println();
        }
        for (int i = 0; i < text.length(); ++i) {
            System.out.print(getFormattedString(text.charAt(i) + "", spacer, i));
        }
        System.out.println();
    }

}
