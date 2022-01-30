package com.aisalin.numbergen.utils;

import java.nio.CharBuffer;
import java.util.Objects;

public class StringUtils {

    public static boolean isNullOrEmpty(String str) {
        return Objects.isNull(str) || str.trim().length() == 0;
    }

    public static String nextWordModal(String word, String letters) {
        if (isNullOrEmpty(word) || isNullOrEmpty(letters)) return word;

        if (word.length() == 1) {
            return String.valueOf(nextLetter(word.charAt(0), letters));
        }
        String incSubstr = nextWordModal(word.substring(1), letters);
        if (incSubstr.charAt(0) == letters.charAt(0) && incSubstr.charAt(0) != word.charAt(1)) {
            return nextLetter(word.charAt(0), letters) + incSubstr;
        }
        return word.charAt(0) + incSubstr;
    }

    public static char nextLetter( char prev, String letters) {
        int i = letters.indexOf(prev);
        return  i == letters.length() - 1
                ? letters.charAt(0)
                : letters.charAt(i + 1);
    }

    public static String sortDistinctChars(String str) {
        if (isNullOrEmpty(str)) return str;
        return CharBuffer.wrap(str)
                .chars()
                .sorted()
                .distinct()
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();
    }

}
